// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation （1.1.2_01，编译版 R40）
// Generated source version: 1.1.2

package com.amalto.webapp.util.webservices;


public class WSGetItems {
    protected com.amalto.webapp.util.webservices.WSDataClusterPK wsDataClusterPK;
    protected java.lang.String conceptName;
    protected com.amalto.webapp.util.webservices.WSWhereItem whereItem;
    protected int spellTreshold;
    protected int skip;
    protected int maxItems;
    protected java.lang.Boolean totalCountOnFirstResult;
    
    public WSGetItems() {
    }
    
    public WSGetItems(com.amalto.webapp.util.webservices.WSDataClusterPK wsDataClusterPK, java.lang.String conceptName, com.amalto.webapp.util.webservices.WSWhereItem whereItem, int spellTreshold, int skip, int maxItems, java.lang.Boolean totalCountOnFirstResult) {
        this.wsDataClusterPK = wsDataClusterPK;
        this.conceptName = conceptName;
        this.whereItem = whereItem;
        this.spellTreshold = spellTreshold;
        this.skip = skip;
        this.maxItems = maxItems;
        this.totalCountOnFirstResult = totalCountOnFirstResult;
    }
    
    public com.amalto.webapp.util.webservices.WSDataClusterPK getWsDataClusterPK() {
        return wsDataClusterPK;
    }
    
    public void setWsDataClusterPK(com.amalto.webapp.util.webservices.WSDataClusterPK wsDataClusterPK) {
        this.wsDataClusterPK = wsDataClusterPK;
    }
    
    public java.lang.String getConceptName() {
        return conceptName;
    }
    
    public void setConceptName(java.lang.String conceptName) {
        this.conceptName = conceptName;
    }
    
    public com.amalto.webapp.util.webservices.WSWhereItem getWhereItem() {
        return whereItem;
    }
    
    public void setWhereItem(com.amalto.webapp.util.webservices.WSWhereItem whereItem) {
        this.whereItem = whereItem;
    }
    
    public int getSpellTreshold() {
        return spellTreshold;
    }
    
    public void setSpellTreshold(int spellTreshold) {
        this.spellTreshold = spellTreshold;
    }
    
    public int getSkip() {
        return skip;
    }
    
    public void setSkip(int skip) {
        this.skip = skip;
    }
    
    public int getMaxItems() {
        return maxItems;
    }
    
    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems;
    }
    
    public java.lang.Boolean getTotalCountOnFirstResult() {
        return totalCountOnFirstResult;
    }
    
    public void setTotalCountOnFirstResult(java.lang.Boolean totalCountOnFirstResult) {
        this.totalCountOnFirstResult = totalCountOnFirstResult;
    }
}
