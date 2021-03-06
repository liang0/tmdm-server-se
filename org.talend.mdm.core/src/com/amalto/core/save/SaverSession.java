/*
 * Copyright (C) 2006-2019 Talend Inc. - www.talend.com
 *
 * This source code is available under agreement available at
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
 *
 * You should have received a copy of the agreement along with this program; if not, write to Talend SA 9 rue Pages
 * 92150 Suresnes, France
 */

package com.amalto.core.save;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import org.talend.mdm.commmon.metadata.FieldMetadata;
import org.talend.mdm.commmon.util.core.EUUIDCustomType;
import org.talend.mdm.commmon.util.webapp.XSystemObjects;

import com.amalto.core.history.DeleteType;
import com.amalto.core.history.Document;
import com.amalto.core.history.MutableDocument;
import com.amalto.core.save.context.SaverContextFactory;
import com.amalto.core.save.context.SaverSource;
import com.amalto.core.save.context.StorageDocument;
import com.amalto.core.save.context.StorageSaverSource;
import com.amalto.core.server.ServerContext;
import com.amalto.core.storage.record.DataRecord;
import com.amalto.core.storage.transaction.Transaction;
import com.amalto.core.storage.transaction.TransactionManager;
import com.amalto.core.storage.transaction.Transaction.Lifetime;
import com.amalto.core.util.MDMEhCacheUtil;
import static com.amalto.core.util.MDMEhCacheUtil.UPDATE_REPORT_EVENT_CACHE;
import com.amalto.core.util.Util;

public class SaverSession {
    private static final Logger LOGGER = Logger.getLogger(SaverSession.class);

    private static final String AUTO_INCREMENT_TYPE_NAME = "AutoIncrement"; //$NON-NLS-1$

    private static final Map<String, SaverSource> saverSourcePerUser = new HashMap<String, SaverSource>();

    private static SaverSource defaultSaverSource;

    private static Committer defaultCommitter;

    private final SaverContextFactory contextFactory = new SaverContextFactory();

    private final Map<String, List<Document>> itemsToUpdate = new HashMap<String, List<Document>>();

    private final Map<String, List<Document>> itemsToLogicDelete = new HashMap<String, List<Document>>();

    private final Map<String, List<Document>> itemsToPhysicalDelete = new HashMap<String, List<Document>>();

    private final SaverSource dataSource;

    private boolean hasMetAutoIncrement = false;

    public SaverSession(SaverSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @param saverSource A custom implementation of {@link SaverSource}.
     * @return A {@link SaverSession} with a custom {@link SaverSource} implementation.
     */
    public static SaverSession newSession(SaverSource saverSource) {
        return new SaverSession(saverSource);
    }

    /**
     * @return A {@link SaverSession} with default user.
     */
    public static synchronized SaverSession newSession() {
        if (defaultSaverSource == null) {
            defaultSaverSource = new StorageSaverSource();
        }
        return new SaverSession(defaultSaverSource);
    }

    /**
     * @param userName A MDM user name
     * @return A {@link SaverSession} with the user name passed as parameter.
     */
    public static SaverSession newUserSession(String userName) {
        SaverSource saverSource = saverSourcePerUser.get(userName);
        if (saverSource == null) {
            saverSource = new StorageSaverSource(userName);
            saverSourcePerUser.put(userName, saverSource);
        }
        SaverSource dataSource = saverSource;
        return new SaverSession(dataSource);
    }

    /**
     * To check whether this item's concept model is "AutoIncrement" or not.
     *
     * @param item The item to be checked.
     * @return <code>true</code> if item is an AutoIncrement document, <code>false</code> otherwise.
     */
    private static boolean isAutoIncrementItem(Document item) {
        return item.getType().getName().equals(AUTO_INCREMENT_TYPE_NAME);
    }

    public SaverContextFactory getContextFactory() {
        return contextFactory;
    }

    /**
     * Start a transaction for this session on a given data cluster.
     *
     * @param dataCluster The data cluster where a transaction should be started.
     */
    public void begin(String dataCluster) {
        begin(dataCluster, getDefaultCommitter());
    }

    protected Committer getDefaultCommitter() {
        if (defaultCommitter == null) {
            defaultCommitter = new DefaultCommitter();
        }
        return defaultCommitter;
    }

    public static void setDefaultCommitter(Committer committer) {
        defaultCommitter = committer;
    }

    /**
     * Start a transaction for this session on a given data cluster.
     *
     * @param dataCluster The data cluster where a transaction should be started.
     * @param committer A {@link Committer} committer for interaction between save session and underlying storage.
     */
    public void begin(String dataCluster, Committer committer) {
        synchronized (itemsToUpdate) {
            committer.begin(dataCluster);
            if (!itemsToUpdate.containsKey(dataCluster)) {
                itemsToUpdate.put(dataCluster, new ArrayList<Document>());
            }
        }
    }

    /**
     * End this session (means commit on all data clusters where a transaction was started).
     */
    public void end() {
        end(getDefaultCommitter());
    }

    /**
     * End this session (means commit on all data clusters where a transaction was started).
     *
     * @param committer A {@link Committer} committer to use when committing transactions on underlying storage.
     */
    public void end(Committer committer) {
        SaverSource saverSource = getSaverSource();
        // Physical delete
        synchronized (itemsToPhysicalDelete) {
            for (Map.Entry<String, List<Document>> currentTransaction : itemsToPhysicalDelete.entrySet()) {
                String dataCluster = currentTransaction.getKey();
                committer.begin(dataCluster);
                for (Document currentItemToCommit : currentTransaction.getValue()) {
                    // Don't do clean up in case of exception here: rollback (abort()) takes care of the clean up.
                    committer.delete(currentItemToCommit, DeleteType.PHYSICAL);
                }
                committer.commit(dataCluster);
            }
        }
        // Logical delete
        synchronized (itemsToLogicDelete) {
            for (Map.Entry<String, List<Document>> currentTransaction : itemsToLogicDelete.entrySet()) {
                String dataCluster = currentTransaction.getKey();
                committer.begin(dataCluster);
                for (Document currentItemToCommit : currentTransaction.getValue()) {
                    // Don't do clean up in case of exception here: rollback (abort()) takes care of the clean up.
                    committer.delete(currentItemToCommit, DeleteType.LOGICAL);
                }
                committer.commit(dataCluster);
            }
        }
        // Items to update
        synchronized (itemsToUpdate) {
            boolean needResetAutoIncrement = false;
            for (Map.Entry<String, List<Document>> currentTransaction : itemsToUpdate.entrySet()) {
                String dataCluster = currentTransaction.getKey();
                int itemCounter = 0;
                String recordId = ""; //$NON-NLS-1$
                try {
                    committer.begin(dataCluster);
                    Iterator<Document> iterator = currentTransaction.getValue().iterator();
                    while (iterator.hasNext()) {
                        itemCounter++;
                        Document currentItemToCommit = iterator.next();
                        if (StorageDocument.class.isInstance(currentItemToCommit)) {
                            recordId = DataRecord.getId(((StorageDocument)currentItemToCommit).getDataRecord());
                        }
                        if (!needResetAutoIncrement) {
                            needResetAutoIncrement = isAutoIncrementItem(currentItemToCommit);
                        }
                        // Don't do clean up in case of exception here: rollback (abort()) takes care of the clean up.
                        committer.save(currentItemToCommit);
                        // Keep update reports for routeItem (see below).
                        if (!XSystemObjects.DC_UPDATE_PREPORT.getName().equals(dataCluster)) {
                            iterator.remove();
                        }
                    }
                    committer.commit(dataCluster);
                } catch (Exception e) {
                    committer.rollback(dataCluster);
                    if (e.getCause() != null) {
                        throw new MultiRecordsSaveException(getCauseMessage(e), e.getCause(), recordId, itemCounter);
                    }
                    throw e;
                }

                if (Util.TRANSACTION_WAIT_MILLISECONDS_VALUE > 0) {
                    try {
                        Thread.sleep(Util.TRANSACTION_WAIT_MILLISECONDS_VALUE);
                    } catch (InterruptedException e) {
                        LOGGER.warn("Update process has been interrupted.", e); //$NON-NLS-1$
                    }
                }
            }

            // If any change was made to data cluster "UpdateReport", route committed update reports.
            List<Document> updateReport = itemsToUpdate.get(XSystemObjects.DC_UPDATE_PREPORT.getName());
            if (updateReport != null && !DataRecord.ValidateRecord.get()) {
                String longTransactionId = getLongTransactionId();
                if (longTransactionId == null) {
                    routeItems(updateReport);
                } else {
                    List<String> stringObjects = MDMEhCacheUtil.getCache(UPDATE_REPORT_EVENT_CACHE, longTransactionId);
                    if (stringObjects == null) {
                        stringObjects = new ArrayList<>();
                    }
                    for (Document document : updateReport) {
                        stringObjects.add(document.exportToString());
                    }
                    MDMEhCacheUtil.addCache(UPDATE_REPORT_EVENT_CACHE, longTransactionId, stringObjects);
                }
            }

            // reset the AutoIncrement
            if (needResetAutoIncrement) {
                saverSource.initAutoIncrement();
            }
            // Save current state of auto-increment when save is completed.
            if (hasMetAutoIncrement) {
                // TMDM-3964 : Auto-Increment Id can not be saved immediately to DB
                String dataCluster = XSystemObjects.DC_CONF.getName();
                committer.begin(dataCluster);
                try {
                    saverSource.saveAutoIncrement();
                    committer.commit(dataCluster);
                } catch (Exception e) {
                    committer.rollback(dataCluster);
                    throw new RuntimeException("Could not save auto increment counter state.", e); //$NON-NLS-1$
                }
            }
        }
    }

    public void routeItems(List<Document> updateReportList) {
        SaverSource saverSource = getSaverSource();
        Iterator<Document> iterator = updateReportList.iterator();
        while (iterator.hasNext()) {
            MutableDocument document = (MutableDocument) iterator.next();
            String dataCluster = document.getDataCluster();
            String typeName = document.getType().getName();

            Collection<FieldMetadata> keyFields = document.getType().getKeyFields();
            String[] itemsId = new String[keyFields.size()];
            int i = 0;
            for (FieldMetadata keyField : keyFields) {
                itemsId[i++] = document.createAccessor(keyField.getName()).get();
            }
            saverSource.routeItem(dataCluster, typeName, itemsId);
            iterator.remove();
        }
    }

    private String getLongTransactionId() {
        final TransactionManager transactionManager = ServerContext.INSTANCE.get().getTransactionManager();
        if (transactionManager.hasTransaction()) {
            Transaction currentTransaction = transactionManager.currentTransaction();
            if (currentTransaction != null && currentTransaction.getLifetime() == Lifetime.LONG) {
                return currentTransaction.getId();
            }
        }
        return null;
    }

    /**
     * Get cause message.
     * @param throwable exception.
     */
    public String getCauseMessage(Throwable throwable) {
        if (throwable != null) {
            if (throwable.getCause() == null) {
                return throwable.getMessage();
            } else {
                return getCauseMessage(throwable.getCause());
            }
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * Adds a new record to be saved in this session.
     * @param dataCluster Data cluster where the record should be saved.
     * @param document The item to save.
     */
    public void save(String dataCluster, Document document) {
        synchronized (itemsToUpdate) {
            if (!this.hasMetAutoIncrement) {
                Collection<FieldMetadata> allFields = document.getType().getFields();
                boolean hasAutoIncrementKey = false;
                for (FieldMetadata keyField : allFields) {
                    if (EUUIDCustomType.AUTO_INCREMENT.getName().equals(keyField.getType().getName())) {
                        hasAutoIncrementKey = true;
                        break;
                    }
                }
                this.hasMetAutoIncrement = hasAutoIncrementKey;
            }

            List<Document> documentsToSave = itemsToUpdate.get(dataCluster);
            if (documentsToSave == null) {
                documentsToSave = new ArrayList<Document>();
                itemsToUpdate.put(dataCluster, documentsToSave);
            }
            documentsToSave.add(document);
        }
    }

    /**
     * Adds a new record to be saved in this session.
     * @param dataCluster Data cluster where the record should be saved.
     * @param document The item to save.
     */
    public void delete(String dataCluster, Document document, DeleteType deleteType) {
        Map<String, List<Document>> itemList;
        switch (deleteType) {
        case LOGICAL:
            itemList = itemsToLogicDelete;
            break;
        case PHYSICAL:
            itemList = itemsToPhysicalDelete;
            break;
        default:
            throw new IllegalArgumentException("Delete type '" + deleteType + "' is not supported.");
        }
        synchronized (itemList) {
            List<Document> documentsToDelete = itemList.get(dataCluster);
            if (documentsToDelete == null) {
                documentsToDelete = new ArrayList<Document>();
                itemList.put(dataCluster, documentsToDelete);
            }
            documentsToDelete.add(document);
        }
    }

    /**
     * @return {@link SaverSource} to interact with MDM server.
     */
    public SaverSource getSaverSource() {
        return dataSource;
    }

    /**
     * Aborts current transaction (means rollback on all data clusters where a transaction was started).
     */
    public void abort() {
        abort(getDefaultCommitter());
    }

    /**
     * Aborts current transaction (means rollback on all data clusters where a transaction was started).
     *
     * @param committer A {@link Committer} committer for interaction between save session and underlying storage.
     */
    public void abort(Committer committer) {
        // TODO not pretty here!
        synchronized (itemsToUpdate) {
            for (Map.Entry<String, List<Document>> currentTransaction : itemsToUpdate.entrySet()) {
                String dataCluster = currentTransaction.getKey();
                committer.rollback(dataCluster);
            }
            if (hasMetAutoIncrement) {
                committer.rollback(XSystemObjects.DC_CONF.getName());
            }
            itemsToUpdate.clear();
        }
        synchronized (itemsToLogicDelete) {
            for (Map.Entry<String, List<Document>> currentTransaction : itemsToLogicDelete.entrySet()) {
                String dataCluster = currentTransaction.getKey();
                committer.rollback(dataCluster);
            }
            itemsToLogicDelete.clear();
        }
        synchronized (itemsToPhysicalDelete) {
            for (Map.Entry<String, List<Document>> currentTransaction : itemsToPhysicalDelete.entrySet()) {
                String dataCluster = currentTransaction.getKey();
                committer.rollback(dataCluster);
            }
            itemsToPhysicalDelete.clear();
        }
    }

    /**
     * Invalidate any type cache entry for the data model.
     *
     * @param dataModelName A data model name.
     */
    public void invalidateTypeCache(String dataModelName) {
        dataSource.invalidateTypeCache(dataModelName);
    }

    public interface Committer {

        /**
         * Begin a transaction on a data cluster
         *
         * @param dataCluster A data cluster name.
         */
        void begin(String dataCluster);

        /**
         * Commit a transaction on a data cluster
         *
         * @param dataCluster A data cluster name
         */
        void commit(String dataCluster);

        /**
         * Saves a MDM record for a given revision.
         *
         * @param item The item to save.
         */
        void save(Document item);

        /**
         * Deletes a MDM record.
         *
         * @param document The item to delete.
         * @param deleteType The type of delete to perform (either {@link com.amalto.core.history.DeleteType#LOGICAL
         * logical} or {@link com.amalto.core.history.DeleteType#PHYSICAL physical}).
         */
        void delete(Document document, DeleteType deleteType);

        /**
         * Rollbacks changes done on a data cluster.
         *
         * @param dataCluster Data cluster name.
         */
        void rollback(String dataCluster);
    }
}
