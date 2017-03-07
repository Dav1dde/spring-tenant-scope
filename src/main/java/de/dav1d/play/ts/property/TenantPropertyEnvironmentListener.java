package de.dav1d.play.ts.property;

import de.dav1d.play.ts.tenant.TenantHolder;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;


public class TenantPropertyEnvironmentListener implements ApplicationListener<ApplicationPreparedEvent>
{
    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event)
    {
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();
        TenantEnvironment newEnvironment = new TenantEnvironment();
        newEnvironment.merge(environment);
        event.getApplicationContext().setEnvironment(newEnvironment);

        event.getApplicationContext().addBeanFactoryPostProcessor(
            beanFactory -> newEnvironment
                .setPropertyTransformer(new TenantPropertyTransformer(beanFactory.getBean(TenantHolder.class)))
        );
    }
}
