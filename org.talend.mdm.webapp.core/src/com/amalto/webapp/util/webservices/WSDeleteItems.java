// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation ��1.1.2_01������� R40��
// Generated source version: 1.1.2

package com.amalto.webapp.util.webservices;


public class WSDeleteItems {
    protected com.amalto.webapp.util.webservices.WSDataClusterPK wsDataClusterPK;
    protected java.lang.String conceptName;
    protected com.amalto.webapp.util.webservices.WSWhereItem wsWhereItem;
    protected int spellTreshold;
    protected boolean override;

    public WSDeleteItems() {
    }
    
    public WSDeleteItems(com.amalto.webapp.util.webservices.WSDataClusterPK wsDataClusterPK, java.lang.String conceptName, com.amalto.webapp.util.webservices.WSWhereItem wsWhereItem, int spellThreshold,boolean isOverride) {
        this.wsDataClusterPK = wsDataClusterPK;
        this.conceptName = conceptName;
        this.wsWhereItem = wsWhereItem;
        this.spellTreshold = spellThreshold;
        override = isOverride;
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
    
    public com.amalto.webapp.util.webservices.WSWhereItem getWsWhereItem() {
        return wsWhereItem;
    }
    
    public void setWsWhereItem(com.amalto.webapp.util.webservices.WSWhereItem wsWhereItem) {
        this.wsWhereItem = wsWhereItem;
    }
    
    public int getSpellTreshold() {
        return spellTreshold;
    }
    
    public void setSpellTreshold(int spellTreshold) {
        this.spellTreshold = spellTreshold;
    }

    public boolean isOverride() {
        return override;
    }

    public void setOverride(boolean override) {
        this.override = override;
    }
}
