package de.dav1d.play.ts.property;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.ArrayList;
import java.util.List;


public class GetPropertyNamesAdvice implements MethodInterceptor
{
    private final WrappingMutablePropertySources wrappingMutablePropertySources;

    public GetPropertyNamesAdvice(WrappingMutablePropertySources wrappingMutablePropertySources)
    {
        this.wrappingMutablePropertySources = wrappingMutablePropertySources;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable
    {
        PropertyTransformer transformer = wrappingMutablePropertySources.getPropertyTransformer();

        String[] names = (String[]) invocation.proceed();
        List<String> result = new ArrayList<>(names.length);
        for (String name : names)
        {
            if (!transformer.isTransformed(name))
            {
                result.add(name);
            }
        }
        return result.toArray(new String[0]);
    }
}
