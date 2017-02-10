package de.dav1d.play.ts.config;

import de.dav1d.play.ts.scope.TenantScope;
import de.dav1d.play.ts.tenant.TenantGetter;
import de.dav1d.play.ts.tenant.ThreadLocalTenant;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TenantConfig
{
    private ConfigurableBeanFactory configurableBeanFactory;

    @Bean
    public ThreadLocalTenant tenantHolder()
    {
        return new ThreadLocalTenant("default");
    }

    @Bean
    public CustomScopeConfigurer tenantScope(TenantGetter tenantGetter)
    {
        CustomScopeConfigurer scopeConfigurer = new CustomScopeConfigurer();
        scopeConfigurer.addScope("tenant", new TenantScope(tenantGetter));
        return scopeConfigurer;
    }
}
