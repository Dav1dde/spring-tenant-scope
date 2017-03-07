package de.dav1d.play.ts.property;

import org.springframework.core.env.PropertySource;


public class TenantPropertySource<T> extends PropertySource<T>
{
    private final PropertySource<T> realPropertySource;
    private PropertyTransformer propertyTransformer;

    public TenantPropertySource(PropertySource<T> realPropertySource, PropertyTransformer propertyTransformer)
    {
        super(realPropertySource.getName(), realPropertySource.getSource());

        this.realPropertySource = realPropertySource;
        this.propertyTransformer = propertyTransformer;
    }

    public void setPropertyTransformer(PropertyTransformer propertyTransformer)
    {
        this.propertyTransformer = propertyTransformer;
    }

    @Override
    public String getName()
    {
        return realPropertySource.getName();
    }

    @Override
    public T getSource()
    {
        return realPropertySource.getSource();
    }

    @Override
    public boolean containsProperty(String name)
    {
        for (String transformedName : propertyTransformer.transform(name))
        {
            if (realPropertySource.containsProperty(transformedName))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object getProperty(String name)
    {
        for (String transformedName : propertyTransformer.transform(name))
        {
            Object result = realPropertySource.getProperty(name);
            if (result != null)
            {
                return result;
            }
        }
        return null;
    }
}
