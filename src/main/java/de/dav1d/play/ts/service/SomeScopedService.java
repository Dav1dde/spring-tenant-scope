package de.dav1d.play.ts.service;

import de.dav1d.play.ts.scope.TenantScoped;
import de.dav1d.play.ts.tenant.TenantGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@TenantScoped
public class SomeScopedService
{
    @Autowired
    private TenantGetter tenantGetter;

    @Value("${tenant.current}")
    private String tenantValue;

    @Value("${example.value}")
    private String exampleValue;

    public String identity()
    {
        return String.format(
            "%s -> %s | %s | %s",
            tenantGetter.getTenant(), Integer.toHexString(this.hashCode()), tenantValue, exampleValue
        );
    }
}
