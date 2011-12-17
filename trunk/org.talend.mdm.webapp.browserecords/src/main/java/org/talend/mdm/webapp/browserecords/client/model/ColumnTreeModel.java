// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.mdm.webapp.browserecords.client.model;

import java.io.Serializable;
import java.util.List;

public class ColumnTreeModel implements Serializable {

    private List<ColumnElement> columnElements;

    public ColumnTreeModel() {

    }

    public List<ColumnElement> getColumnElements() {
        return columnElements;
    }

    public void setColumnElements(List<ColumnElement> columnElements) {
        this.columnElements = columnElements;
    }

}
