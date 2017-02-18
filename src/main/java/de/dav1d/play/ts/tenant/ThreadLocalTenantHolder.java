package de.dav1d.play.ts.tenant;


public class ThreadLocalTenantHolder implements TenantHolder
{
    private ThreadLocal<String> threadLocal;

    public ThreadLocalTenantHolder(String defaultTenant)
    {
        this.threadLocal = ThreadLocal.withInitial(() -> defaultTenant);
    }

    @Override
    public void setTenant(String tenant)
    {
        threadLocal.set(tenant);
    }

    @Override
    public String getTenant()
    {
        return threadLocal.get();
    }
}
