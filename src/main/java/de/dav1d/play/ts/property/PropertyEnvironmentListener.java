package de.dav1d.play.ts.property;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;


public class PropertyEnvironmentListener implements ApplicationListener<ApplicationPreparedEvent>
{
    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event)
    {
        ConfigurableEnvironment newEnvironment = new TenantPropertyServletEnvironment(event.getApplicationContext());
        newEnvironment.merge(event.getApplicationContext().getEnvironment());
        event.getApplicationContext().setEnvironment(newEnvironment);

    }
}
