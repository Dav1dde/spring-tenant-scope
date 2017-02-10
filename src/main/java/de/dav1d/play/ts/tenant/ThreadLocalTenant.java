package de.dav1d.play.ts.tenant;

public class ThreadLocalTenant implements TenantSetter, TenantGetter
{
    private ThreadLocal<String> threadLocal;

    public ThreadLocalTenant(String defaultTenant)
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
