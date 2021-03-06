/*
 * Copyright (C) 2006-2019 Talend Inc. - www.talend.com
 *
 * This source code is available under agreement available at
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
 *
 * You should have received a copy of the agreement along with this program; if not, write to Talend SA 9 rue Pages
 * 92150 Suresnes, France
 */

package com.amalto.core.save.context;

import org.apache.commons.lang.StringUtils;
import org.talend.mdm.commmon.metadata.ComplexTypeMetadata;
import org.talend.mdm.commmon.metadata.MetadataRepository;

import com.amalto.core.history.MutableDocument;
import com.amalto.core.history.accessor.Accessor;
import com.amalto.core.save.DocumentSaverContext;
import com.amalto.core.save.SaverSession;
import com.amalto.core.util.Util;

class Save implements DocumentSaver {

    private String[] savedId = new String[0];

    @Override
    public void save(SaverSession session, DocumentSaverContext context) {
        String typeName = context.getUserDocument().getType().getName();
        savedId = context.getId();
        if (savedId.length == 0) {
            throw new IllegalStateException("No ID information to save instance of '" + typeName + "'"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        for (String id : savedId) {
            Util.checkIdValidation(id);
        }
        MutableDocument databaseDocument = context.getDatabaseDocument();
        if (!StringUtils.EMPTY.equals(context.getTaskId())) {
            databaseDocument.setTaskId(context.getTaskId());
        }
        if (databaseDocument.isDeleted()) {
            session.delete(context.getDataCluster(), databaseDocument, databaseDocument.getDeleteType());
        } else {
            session.save(context.getDataCluster(), databaseDocument);
        }
        // Save update report (if any)
        MutableDocument updateReportDocument = context.getUpdateReportDocument();
        if (updateReportDocument != null) {
            saveUpdateReport(updateReportDocument, session.getSaverSource(), session);
        }
    }

    private static void saveUpdateReport(MutableDocument updateReportDocument, SaverSource saverSource, SaverSession session) {
        MetadataRepository metadataRepository = saverSource.getMetadataRepository(UpdateReport.UPDATE_REPORT_DATA_MODEL);
        ComplexTypeMetadata updateReportType = metadataRepository.getComplexType(UpdateReport.UPDATE_REPORT_TYPE);
        if (updateReportType == null) {
            throw new IllegalStateException("Could not find UpdateReport type."); //$NON-NLS-1$
        }

        long updateReportTime = 0;

        Accessor updateTimeAccessor = updateReportDocument.createAccessor("TimeInMillis"); //$NON-NLS-1$
        if (StringUtils.EMPTY.equals(updateTimeAccessor.get())) { // No update report need to save.
            return;
        } else {
            updateReportTime = Long.parseLong(updateTimeAccessor.get());
        }

        if (updateReportTime < 1) { // This is unexpected (would mean update report "TimeInMillis" is not present).
            throw new IllegalStateException("Missing update report time value."); //$NON-NLS-1$
        }
        // Call session's save to save all items in correct order (one transaction per data cluster for the XML db).
        session.save(UpdateReport.UPDATE_REPORT_DATA_MODEL, updateReportDocument);
    }

    @Override
    public String[] getSavedId() {
        return savedId;
    }

    @Override
    public String getSavedConceptName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getBeforeSavingMessage() {
        return StringUtils.EMPTY;
    }

    @Override
    public String getBeforeSavingMessageType() {
        return BeforeSaving.TYPE_INFO;
    }
}