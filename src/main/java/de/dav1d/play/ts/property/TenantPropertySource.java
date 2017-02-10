package de.dav1d.play.ts.property;

import de.dav1d.play.ts.tenant.TenantGetter;
import org.springframework.core.env.PropertySource;

import javax.servlet.ServletContext;


public class TenantPropertySource extends PropertySource<ServletContext>
{
    private final TenantGetter tenantGetter;

    public TenantPropertySource(String name, ServletContext source, TenantGetter tenantGetter)
    {
        super(name, source);

        this.tenantGetter = tenantGetter;
    }

    @Override
    public Object getProperty(String name)
    {
        if ("tenant.current".equals(name))
        {
            return tenantGetter.getTenant();
        }

        return null;
    }
}
