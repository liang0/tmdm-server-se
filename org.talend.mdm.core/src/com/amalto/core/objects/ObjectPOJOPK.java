/*
 * Copyright (C) 2006-2019 Talend Inc. - www.talend.com
 *
 * This source code is available under agreement available at
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
 *
 * You should have received a copy of the agreement along with this program; if not, write to Talend SA 9 rue Pages
 * 92150 Suresnes, France
 */
package com.amalto.core.objects;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.talend.mdm.commmon.util.core.MDMConfiguration;

import com.amalto.core.util.Util;

public class ObjectPOJOPK implements Serializable {

    private static final long serialVersionUID = 8946570431961920706L;

    private static final Logger LOGGER = Logger.getLogger(ObjectPOJOPK.class);

    String[] ids = null;

    static {
        try {
            MDMConfiguration.createConfiguration("", true);
        } catch (IllegalStateException e) {
            LOGGER.warn("MDMConfiguration had been initialized");
        }
    }

    public ObjectPOJOPK(String[] itemIds) {
        this.ids = itemIds;
    }

    public ObjectPOJOPK(String uniqueid) {
        this(uniqueid == null ? null : uniqueid.split("\\.\\.")); //$NON-NLS-1$
    }

    /**
     * For marshalling purposes only
     */
    public ObjectPOJOPK() {
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return Util.joinStrings(ids, ".."); //$NON-NLS-1$
    }

    public String getUniqueId() {
        return toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ObjectPOJOPK && this.getUniqueId().equals(((ObjectPOJOPK) obj).getUniqueId());
    }
}
