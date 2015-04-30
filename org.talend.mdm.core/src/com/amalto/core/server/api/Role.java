/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.server.api;

import com.amalto.core.objects.role.RolePOJO;
import com.amalto.core.objects.role.RolePOJOPK;

public interface Role {

    /**
     * Creates or updates a Role
     *
     * @throws com.amalto.core.util.XtentisException
     */
    public RolePOJOPK putRole(RolePOJO role) throws com.amalto.core.util.XtentisException;

    /**
     * Get Role
     *
     * @throws com.amalto.core.util.XtentisException
     */
    public RolePOJO getRole(RolePOJOPK pk) throws com.amalto.core.util.XtentisException;

    /**
     * Get a Role - no exception is thrown: returns null if not found
     *
     * @throws com.amalto.core.util.XtentisException
     */
    public RolePOJO existsRole(RolePOJOPK pk) throws com.amalto.core.util.XtentisException;

    /**
     * Remove an item
     *
     * @throws com.amalto.core.util.XtentisException
     */
    public RolePOJOPK removeRole(RolePOJOPK pk) throws com.amalto.core.util.XtentisException;

    /**
     * Retrieve all Role PKS
     *
     * @throws com.amalto.core.util.XtentisException
     */
    public java.util.Collection getRolePKs(String regex) throws com.amalto.core.util.XtentisException;

}