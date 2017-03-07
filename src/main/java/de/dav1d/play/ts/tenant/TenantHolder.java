package de.dav1d.play.ts.tenant;

import java.util.Set;


public interface TenantHolder
{
    Object getTenant();

    <T> T getTenant(Class<T> clazz);

    void setTenant(Object tenant) throws InvalidTenantException;

    void setAllTenants(Set<Object> tenants);

    Set<Object> getAllTenants();

    <T> Set<T> getAllTenants(Class<T> clazz);
}
