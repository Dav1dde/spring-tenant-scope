package de.dav1d.play.ts.property;

import de.dav1d.play.ts.scope.TenantScoped;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@TenantScoped
@Component
@ConfigurationProperties(prefix = "tenant")
public class SomeProperties
{
    private String current;

    private String other;

    public String getCurrent()
    {
        return current;
    }

    public void setCurrent(String current)
    {
        this.current = current;
    }

    public String getOther()
    {
        return other;
    }

    public void setOther(String other)
    {
        this.other = other;
    }

    @Override public String toString()
    {
        return "ExampleProperties{"
            + "current='" + current + '\''
            + ", other='" + other + '\''
            + '}';
    }
}
