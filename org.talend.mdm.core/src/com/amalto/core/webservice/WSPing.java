/*
 * Copyright (C) 2006-2019 Talend Inc. - www.talend.com
 *
 * This source code is available under agreement available at
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
 *
 * You should have received a copy of the agreement along with this program; if not, write to Talend SA 9 rue Pages
 * 92150 Suresnes, France
 */
package com.amalto.core.webservice;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="WSPing")
public class WSPing {
    protected java.lang.String echo;

    public WSPing() {
    }

    public WSPing(java.lang.String echo) {
        this.echo = echo;
    }

    public java.lang.String getEcho() {
        return echo;
    }

    public void setEcho(java.lang.String echo) {
        this.echo = echo;
    }
}
