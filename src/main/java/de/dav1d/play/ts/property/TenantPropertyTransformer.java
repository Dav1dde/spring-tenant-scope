package de.dav1d.play.ts.property;

import de.dav1d.play.ts.tenant.TenantHolder;


public class TenantPropertyTransformer implements PropertyTransformer
{
    private final TenantHolder tenantHolder;

    public TenantPropertyTransformer(TenantHolder tenantHolder)
    {
        this.tenantHolder = tenantHolder;
    }

    @Override
    public String[] transform(String key)
    {
        if (tenantHolder != null)
        {
            String tenant = tenantHolder.getTenant();
            return new String[]{key + "@" + tenant, key};
        }
        return new String[]{key};
    }

    @Override
    public boolean isTransformed(String name)
    {
        return name != null && name.contains("@");
    }
}
