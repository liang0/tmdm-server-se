// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation 
// Generated source version: 1.1.2

package com.amalto.webapp.util.webservices;


public class WSVersioningTagItems {
    protected java.lang.String versioningSystemName;
    protected java.lang.String tag;
    protected java.lang.String comment;
    protected com.amalto.webapp.util.webservices.WSItemPK[] wsItemPKs;
    
    public WSVersioningTagItems() {
    }
    
    public WSVersioningTagItems(java.lang.String versioningSystemName, java.lang.String tag, java.lang.String comment, com.amalto.webapp.util.webservices.WSItemPK[] wsItemPKs) {
        this.versioningSystemName = versioningSystemName;
        this.tag = tag;
        this.comment = comment;
        this.wsItemPKs = wsItemPKs;
    }
    
    public java.lang.String getVersioningSystemName() {
        return versioningSystemName;
    }
    
    public void setVersioningSystemName(java.lang.String versioningSystemName) {
        this.versioningSystemName = versioningSystemName;
    }
    
    public java.lang.String getTag() {
        return tag;
    }
    
    public void setTag(java.lang.String tag) {
        this.tag = tag;
    }
    
    public java.lang.String getComment() {
        return comment;
    }
    
    public void setComment(java.lang.String comment) {
        this.comment = comment;
    }
    
    public com.amalto.webapp.util.webservices.WSItemPK[] getWsItemPKs() {
        return wsItemPKs;
    }
    
    public void setWsItemPKs(com.amalto.webapp.util.webservices.WSItemPK[] wsItemPKs) {
        this.wsItemPKs = wsItemPKs;
    }
}
