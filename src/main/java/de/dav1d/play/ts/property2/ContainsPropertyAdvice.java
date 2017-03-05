package de.dav1d.play.ts.property2;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ProxyMethodInvocation;


public class ContainsPropertyAdvice implements MethodInterceptor
{
    private final WrappingMutablePropertySources wrappingMutablePropertySources;

    public ContainsPropertyAdvice(WrappingMutablePropertySources wrappingMutablePropertySources)
    {
        this.wrappingMutablePropertySources = wrappingMutablePropertySources;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable
    {
        if (!(methodInvocation instanceof ProxyMethodInvocation))
        {
            return methodInvocation.proceed();
        }

        ProxyMethodInvocation invocation = (ProxyMethodInvocation) methodInvocation;

        String name = (String) invocation.getArguments()[0];
        String[] transformedNames = wrappingMutablePropertySources.getPropertyTransformer().transform(name);
        for (String transformedName : transformedNames)
        {
            invocation.setArguments(transformedName);
            if ((boolean) invocation.proceed())
            {
                return true;
            }
        }
        return false;
    }
}
