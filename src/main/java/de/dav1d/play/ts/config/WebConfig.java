package de.dav1d.play.ts.config;

import de.dav1d.play.ts.tenant.RegexTenantFilter;
import de.dav1d.play.ts.tenant.TenantHolder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.regex.Pattern;


@Configuration
public class WebConfig extends WebMvcConfigurerAdapter
{
    @Bean
    public FilterRegistrationBean tenantFilter(TenantHolder tenantHolder)
    {
        FilterRegistrationBean filter = new FilterRegistrationBean(
            new RegexTenantFilter(
                tenantHolder, Pattern.compile("^/(?<tenant>\\w+)(?<path>/.*)$"), "/%s"
            )
        );
        filter.addUrlPatterns("/*");
        filter.setOrder(-1);
        return filter;
    }
}
