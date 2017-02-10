package de.dav1d.play.ts.property;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.ConfigurableWebApplicationContext;


public class PropertyEnvironmentInitializer implements ApplicationContextInitializer<ConfigurableWebApplicationContext>
{
    @Override
    public void initialize(ConfigurableWebApplicationContext applicationContext)
    {
        ConfigurableEnvironment newEnvironment = new TenantPropertyServletEnvironment(applicationContext);
        newEnvironment.merge(applicationContext.getEnvironment());
        applicationContext.setEnvironment(newEnvironment);
    }
}
