package de.dav1d.play.ts.service;

import de.dav1d.play.ts.scope.TenantScoped;
import de.dav1d.play.ts.tenant.TenantHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@TenantScoped
public class SomeScopedService
{
    @Autowired
    private TenantHolder tenantHolder;

    @Value("${foo.bar}")
    private String fooBarValue;

    @Autowired
    private SomeScopedProperty someScopedProperty;

    public String identity()
    {
        return String.format(
            "%s -> %s | %s | %s",
            tenantHolder.getTenant(), Integer.toHexString(this.hashCode()), fooBarValue, someScopedProperty.getProperty()
        );
    }
}
