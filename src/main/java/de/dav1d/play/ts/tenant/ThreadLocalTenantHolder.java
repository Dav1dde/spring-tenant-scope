package de.dav1d.play.ts.tenant;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


public class ThreadLocalTenantHolder implements TenantHolder
{
    private ThreadLocal<Object> threadLocal;
    private Set<Object> allTenants;

    public ThreadLocalTenantHolder(Object defaultTenant)
    {
        this.threadLocal = ThreadLocal.withInitial(() -> defaultTenant);
    }

    @Override
    public void setTenant(Object tenant) throws InvalidTenantException
    {
        if (allTenants != null && !allTenants.contains(tenant))
        {
            throw new InvalidTenantException("Tenant \"" + tenant + "\" is not a valid tenant!", tenant);
        }
        threadLocal.set(tenant);
    }

    @Override
    public Object getTenant()
    {
        return threadLocal.get();
    }

    @Override
    public <T> T getTenant(Class<T> clazz)
    {
        return clazz.cast(getTenant());
    }

    @Override
    public void setAllTenants(Set<Object> allTenants)
    {
        this.allTenants = allTenants;
    }

    @Override
    public Set<Object> getAllTenants()
    {
        return Collections.unmodifiableSet(allTenants);
    }

    @Override
    public <T> Set<T> getAllTenants(Class<T> clazz)
    {
        return allTenants.stream().map(clazz::cast).collect(Collectors.toSet());
    }
}
