package de.dav1d.play.ts.scope;

import de.dav1d.play.ts.tenant.TenantHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class TenantScope implements Scope
{
    private static final Log logger = LogFactory.getLog(TenantScope.class);


    protected final TenantHolder tenantHolder;
    protected final Map<String, Map<String, Object>> tenantScope = new ConcurrentHashMap<>();

    public TenantScope(TenantHolder tenantHolder)
    {
        this.tenantHolder = tenantHolder;
    }

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory)
    {
        String tenant = tenantHolder.getTenant();
        Map<String, Object> scope =
            tenantScope.computeIfAbsent(tenant, k -> new ConcurrentHashMap<>());

        return scope.computeIfAbsent(name, k -> objectFactory.getObject());
    }

    @Override
    public Object remove(String name)
    {
        Map<String, Object> scope = tenantScope.get(tenantHolder.getTenant());
        return scope == null ? null : scope.remove(name);
    }

    @Override
    public void registerDestructionCallback(String s, Runnable runnable)
    {
        // no support for descruction callbacks
        logger.warn("TenantScope does not support destruction callbacks.");
    }

    @Override
    public Object resolveContextualObject(String s)
    {
        return null;
    }

    @Override
    public String getConversationId()
    {
        return tenantHolder.getTenant();
    }
}
