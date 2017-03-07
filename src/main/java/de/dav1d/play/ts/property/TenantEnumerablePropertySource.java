package de.dav1d.play.ts.property;

import org.springframework.core.env.EnumerablePropertySource;

import java.util.ArrayList;
import java.util.List;


public class TenantEnumerablePropertySource<T> extends EnumerablePropertySource<T>
{
    private final EnumerablePropertySource<T> realPropertySource;
    private PropertyTransformer propertyTransformer;

    protected TenantEnumerablePropertySource(EnumerablePropertySource<T> realPropertySource,
        PropertyTransformer propertyTransformer)
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

    @Override
    public String[] getPropertyNames()
    {
        String[] names = realPropertySource.getPropertyNames();
        List<String> result = new ArrayList<>(names.length);
        for (String name : names)
        {
            if (!propertyTransformer.isTransformed(name))
            {
                result.add(name);
            }
        }
        return result.toArray(new String[0]);
    }
}
