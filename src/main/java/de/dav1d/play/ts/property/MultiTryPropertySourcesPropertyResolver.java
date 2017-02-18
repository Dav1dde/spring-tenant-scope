package de.dav1d.play.ts.property;

import org.springframework.core.env.PropertySources;
import org.springframework.core.env.PropertySourcesPropertyResolver;


public class MultiTryPropertySourcesPropertyResolver extends PropertySourcesPropertyResolver
    implements MultiTryPropertyResolver
{
    private PropertyTransformer propertyTransformer = new NullPropertyTransformer();

    /**
     * Create a new resolver against the given property sources.
     *
     * @param propertySources the set of {@link PropertySource} objects to use
     */
    public MultiTryPropertySourcesPropertyResolver(PropertySources propertySources)
    {
        super(propertySources);
    }

    @Override
    public void setPropertyTransformer(PropertyTransformer propertyTransformer)
    {
        this.propertyTransformer = propertyTransformer;
    }

    @Override
    public boolean containsProperty(String key)
    {
        for (String transformedKey : propertyTransformer.transform(key))
        {
            if (super.containsProperty(transformedKey))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    protected <T> T getProperty(String key, Class<T> targetValueType, boolean resolveNestedPlaceholders)
    {
        for (String transformedKey : propertyTransformer.transform(key))
        {
            T result = super.getProperty(transformedKey, targetValueType, resolveNestedPlaceholders);
            if (result != null)
            {
                return result;
            }
        }
        return null;
    }

    @Override
    public <T> Class<T> getPropertyAsClass(String key, Class<T> targetValueType)
    {
        for (String transformedKey : propertyTransformer.transform(key))
        {
            Class<T> result = super.getPropertyAsClass(transformedKey, targetValueType);
            if (result != null)
            {
                return result;
            }
        }
        return null;
    }
}
