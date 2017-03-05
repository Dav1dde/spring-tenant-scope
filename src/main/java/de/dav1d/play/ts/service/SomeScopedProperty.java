package de.dav1d.play.ts.service;

import de.dav1d.play.ts.scope.TenantScoped;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@TenantScoped
@ConfigurationProperties(prefix = "scoped")
public class SomeScopedProperty
{
    private String property;

    public String getProperty()
    {
        return property;
    }

    public void setProperty(String property)
    {
        this.property = property;
    }
}
