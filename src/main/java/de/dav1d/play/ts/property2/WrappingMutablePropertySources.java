package de.dav1d.play.ts.property2;

import de.dav1d.play.ts.property.NullPropertyTransformer;
import de.dav1d.play.ts.property.PropertyTransformer;
import de.dav1d.play.ts.property.TenantPropertyTransformer;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;


public class WrappingMutablePropertySources extends MutablePropertySources
{
    private final MutablePropertySources realPropertySources;
    private PropertyTransformer propertyTransformer;

    public WrappingMutablePropertySources(MutablePropertySources realPropertySources)
    {
        this.realPropertySources = realPropertySources;
        this.propertyTransformer = new NullPropertyTransformer();
        ensureWrapped();
    }

    public void setPropertyTransformer(TenantPropertyTransformer propertyTransformer)
    {
        this.propertyTransformer = propertyTransformer;

        for (PropertySource<?> ps : this)
        {
            if (ps instanceof TenantEnumerablePropertySource<?>)
            {
                ((TenantEnumerablePropertySource) ps).setPropertyTransformer(propertyTransformer);
            }
            else if (ps instanceof TenantPropertySource<?>)
            {
                ((TenantPropertySource) ps).setPropertyTransformer(propertyTransformer);
            }
        }
    }

    public PropertyTransformer getPropertyTransformer()
    {
        return propertyTransformer;
    }

    protected PropertySource<?> wrap(PropertySource<?> propertySource)
    {
        if (propertySource instanceof PropertySource.StubPropertySource)
        {
            // don't wrap a stub propertysource, often these will be replaced at a later state
            // often the implementations check if the property source has not been replaced yet
            // with a check of `instanceof StubPropertySource`.
            return propertySource;
        }

        if (AopUtils.isAopProxy(propertySource) && propertySource instanceof Advised)
        {
            // check if this is a proxy created by us .. if so, don't proxy the proxy
            Advisor[] advisors = ((Advised) propertySource).getAdvisors();
            if (advisors.length == 3
                && advisors[0].getAdvice() instanceof GetPropertyAdvice
                && advisors[1].getAdvice() instanceof ContainsPropertyAdvice
                && advisors[2].getAdvice() instanceof GetPropertyNamesAdvice)
            {
                return propertySource;
            }
        }

        ProxyFactory proxyFactory = new ProxyFactory(propertySource);
        proxyFactory.setProxyTargetClass(true);
        NameMatchMethodPointcut getPropertyPointcut = new NameMatchMethodPointcut();
        getPropertyPointcut.setMappedName("getProperty");
        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(getPropertyPointcut, new GetPropertyAdvice(this)));
        NameMatchMethodPointcut containsPropertyPointcut = new NameMatchMethodPointcut();
        containsPropertyPointcut.setMappedName("containsProperty");
        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(containsPropertyPointcut, new ContainsPropertyAdvice(this)));
        NameMatchMethodPointcut getPropertyNamesPointcut = new NameMatchMethodPointcut();
        getPropertyNamesPointcut.setMappedName("getPropertyNames");
        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(getPropertyNamesPointcut, new GetPropertyNamesAdvice(this)));

        PropertySource<?> result = (PropertySource<?>) proxyFactory.getProxy();
        // TODO get rid of this:
        //  1) Make a real subclass instead of some delegating proxy (ByteBuddy???)
        //  2) Wrap only after all property sources have been configured, if something accesses fields directly after,
        //      it at least will crash with a NPE - name maybe still needs to be copied for comparison???
        // Fields need to be copied over since some static some static methods access them directly,
        // e.g. ConfigFileApplicationListener$ConfigurationPropertySources.finishAndRelocate, orX
        // (Stub)PropertySource.equals which is used for comparing a property by name
        // -> contains etc. will fail if the name is not copied.
        ReflectionUtils.doWithFields(propertySource.getClass(), f -> {
            ReflectionUtils.makeAccessible(f);
            ReflectionUtils.setField(f, result, ReflectionUtils.getField(f, propertySource));
        }, f -> !Modifier.isStatic(f.getModifiers()));
        return result;
    }

    public void ensureWrapped()
    {
        List<PropertySource<?>> sources = new ArrayList<>();
        realPropertySources.forEach(sources::add);
        for (PropertySource<?> ps : sources)
        {
            realPropertySources.replace(ps.getName(), wrap(ps));
        }
        realPropertySources.contains("systemProperties");
    }

    @Override
    public PropertySource<?> get(String name)
    {
        return realPropertySources.get(name);
    }

    @Override
    public int precedenceOf(PropertySource<?> propertySource)
    {
        return realPropertySources.precedenceOf(propertySource);
    }

    @Override
    public PropertySource<?> remove(String name)
    {
        return realPropertySources.remove(name);
    }

    @Override
    public int size()
    {
        return realPropertySources.size();
    }

    @Override
    public boolean contains(String name)
    {
        return realPropertySources.contains(name);
    }

    @Override
    public void replace(String name, PropertySource<?> propertySource)
    {
        realPropertySources.replace(name, wrap(propertySource));
    }

    @Override
    public void addFirst(PropertySource<?> propertySource)
    {
        realPropertySources.addFirst(wrap(propertySource));
    }

    @Override
    public void addLast(PropertySource<?> propertySource)
    {
        realPropertySources.addLast(wrap(propertySource));
    }

    @Override
    public void addBefore(String relativePropertySourceName, PropertySource<?> propertySource)
    {
        realPropertySources.addBefore(relativePropertySourceName, wrap(propertySource));
    }

    @Override
    public void addAfter(String relativePropertySourceName, PropertySource<?> propertySource)
    {
        realPropertySources.addAfter(relativePropertySourceName, wrap(propertySource));
    }

    @Override
    public Iterator<PropertySource<?>> iterator()
    {
        return realPropertySources.iterator();
    }

    @Override
    public void forEach(Consumer<? super PropertySource<?>> consumer)
    {
        realPropertySources.forEach(consumer);
    }

    @Override
    public Spliterator<PropertySource<?>> spliterator()
    {
        return realPropertySources.spliterator();
    }
}
