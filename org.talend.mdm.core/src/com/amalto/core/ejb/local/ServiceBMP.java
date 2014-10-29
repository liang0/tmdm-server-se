package com.amalto.core.ejb.local;

import com.amalto.core.ejb.dao.ServiceData;
import com.amalto.core.ejb.remote.ServiceValue;

/**
 * BMP layer for Service.
 *
 * @author XDoclet
 * @version ${version}
 * @xdoclet-generated
 */
public class ServiceBMP extends com.amalto.core.ejb.ServiceBean implements javax.ejb.EntityBean {

    private String serviceName;

    private String configuration;

    private String serviceData;

    private boolean dirty = false;

    private ServiceValue serviceValue = null;

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
        makeDirty();
    }

    public String getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
        makeDirty();
    }

    public String getServiceData() {
        return this.serviceData;
    }

    public void setServiceData(String serviceData) {
        this.serviceData = serviceData;
        makeDirty();
    }

    public boolean isModified() {
        return dirty;
    }

    protected void makeDirty() {
        dirty = true;
    }

    protected void makeClean() {
        dirty = false;
    }

    public com.amalto.core.ejb.dao.ServiceData getData() {
        try {
            ServiceData dataHolder = new ServiceData();
            dataHolder.setServiceName(getServiceName());
            dataHolder.setConfiguration(getConfiguration());
            dataHolder.setServiceData(getServiceData());
            return dataHolder;
        } catch (RuntimeException e) {
            throw new javax.ejb.EJBException(e);
        }
    }

    public void ejbLoad() throws javax.ejb.EJBException, java.rmi.RemoteException {
        super.ejbLoad();
        makeClean();
    }

    public void ejbStore() throws javax.ejb.EJBException, java.rmi.RemoteException {
        if (isModified()) {
            super.ejbStore();
            makeClean();
        }
    }

    public void ejbPassivate() throws javax.ejb.EJBException, java.rmi.RemoteException {
        super.ejbPassivate();
        serviceValue = null;
    }

    public ServiceValue getServiceValue() {
        try {
            serviceValue = new ServiceValue();
            serviceValue.setServiceName(getServiceName());
            serviceValue.setConfiguration(getConfiguration());
            serviceValue.setServiceData(getServiceData());
            return serviceValue;
        } catch (Exception e) {
            throw new javax.ejb.EJBException(e);
        }
    }
}