// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation （1.1.2_01，编译版 R40）
// Generated source version: 1.1.2

package com.amalto.core.webservice;


public class WSGetUniverseByRevision {
    protected java.lang.String namepattern;
    protected java.lang.String revision;
    protected com.amalto.core.webservice.WSGetUniverseByRevisionType type;
    
    public WSGetUniverseByRevision() {
    }
    
    public WSGetUniverseByRevision(java.lang.String namepattern, java.lang.String revision, com.amalto.core.webservice.WSGetUniverseByRevisionType type) {
        this.namepattern = namepattern;
        this.revision = revision;
        this.type = type;
    }
    
    public java.lang.String getNamepattern() {
        return namepattern;
    }
    
    public void setNamepattern(java.lang.String namepattern) {
        this.namepattern = namepattern;
    }
    
    public java.lang.String getRevision() {
        return revision;
    }
    
    public void setRevision(java.lang.String revision) {
        this.revision = revision;
    }
    
    public com.amalto.core.webservice.WSGetUniverseByRevisionType getType() {
        return type;
    }
    
    public void setType(com.amalto.core.webservice.WSGetUniverseByRevisionType type) {
        this.type = type;
    }
}
