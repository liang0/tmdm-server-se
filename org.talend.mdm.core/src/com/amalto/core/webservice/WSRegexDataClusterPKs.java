// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package com.amalto.core.webservice;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="WSRegexDataClusterPKs")
public class WSRegexDataClusterPKs {
    protected java.lang.String regex;
    
    public WSRegexDataClusterPKs() {
    }
    
    public WSRegexDataClusterPKs(java.lang.String regex) {
        this.regex = regex;
    }
    
    public java.lang.String getRegex() {
        return regex;
    }
    
    public void setRegex(java.lang.String regex) {
        this.regex = regex;
    }
}
