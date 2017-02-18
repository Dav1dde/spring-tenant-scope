package de.dav1d.play.ts.config;

import de.dav1d.play.ts.scope.TenantScope;
import de.dav1d.play.ts.tenant.TenantHolder;
import de.dav1d.play.ts.tenant.ThreadLocalTenantHolder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TenantConfig
{
    private ConfigurableBeanFactory configurableBeanFactory;

    @Bean
    public TenantHolder tenantHolder()
    {
        return new ThreadLocalTenantHolder("default");
    }

    @Bean
    public CustomScopeConfigurer tenantScope(TenantHolder tenantHolder)
    {
        CustomScopeConfigurer scopeConfigurer = new CustomScopeConfigurer();
        scopeConfigurer.addScope("tenant", new TenantScope(tenantHolder));
        return scopeConfigurer;
    }
}
