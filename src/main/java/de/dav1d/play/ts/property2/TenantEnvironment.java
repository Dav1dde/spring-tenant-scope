package de.dav1d.play.ts.property2;

import de.dav1d.play.ts.property.TenantPropertyTransformer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.web.context.support.StandardServletEnvironment;


public class TenantEnvironment extends StandardServletEnvironment
{
    private final WrappingMutablePropertySources propertySources =
        new WrappingMutablePropertySources(super.getPropertySources());

    @Override
    public MutablePropertySources getPropertySources()
    {
        return propertySources;
    }

    @Override
    public void merge(ConfigurableEnvironment parent)
    {
        super.merge(parent);
        propertySources.ensureWrapped();
    }

    public void setPropertyTransformer(TenantPropertyTransformer propertyTransformer)
    {
        propertySources.setPropertyTransformer(propertyTransformer);
    }
}
