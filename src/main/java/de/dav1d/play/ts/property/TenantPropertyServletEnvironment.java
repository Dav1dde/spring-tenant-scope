package de.dav1d.play.ts.property;

import de.dav1d.play.ts.tenant.TenantGetter;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.web.context.support.StandardServletEnvironment;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;


public class TenantPropertyServletEnvironment extends StandardServletEnvironment
{
    public static final String TENANT_PROPERTY_SOURCE_NAME = "tenantPropertySource";

    private final ApplicationContext applicationContext;

    public TenantPropertyServletEnvironment(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void customizePropertySources(MutablePropertySources propertySources)
    {
        propertySources.addLast(new PropertySource.StubPropertySource(TENANT_PROPERTY_SOURCE_NAME));
        super.customizePropertySources(propertySources);
    }

    @Override
    public void initPropertySources(ServletContext servletContext, ServletConfig servletConfig)
    {
        String[] tenantBeans = applicationContext.getBeanNamesForType(TenantGetter.class);

        MutablePropertySources propertySources = getPropertySources();
        if (servletContext != null && propertySources.contains(TENANT_PROPERTY_SOURCE_NAME) &&
            propertySources.get(TENANT_PROPERTY_SOURCE_NAME) instanceof PropertySource.StubPropertySource &&
            tenantBeans.length > 0)
        {
            propertySources.replace(
                TENANT_PROPERTY_SOURCE_NAME,
                new TenantPropertySource(
                    TENANT_PROPERTY_SOURCE_NAME, servletContext, applicationContext.getBean(TenantGetter.class)
                )
            );
        }

        super.initPropertySources(servletContext, servletConfig);
    }
}
