package de.dav1d.play.ts.controller;

import com.google.common.collect.ImmutableMap;
import de.dav1d.play.ts.property.SomeProperties;
import de.dav1d.play.ts.service.SomeScopedService;
import de.dav1d.play.ts.tenant.TenantGetter;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class EchoController
{
    @Autowired
    private TenantGetter tenantGetter;

    @Autowired
    private SomeScopedService someScopedService;

    @Autowired
    private SomeProperties someProperties;

    @Value("${tenant.current}")
    private String currentTenant;

    @RequestMapping("/echo")
    public Map echo(@RequestParam String arg) throws Exception
    {
        return new ImmutableMap.Builder<String, Object>()
            .put("tenant", tenantGetter.getTenant())
            .put("tenantFromValue", currentTenant)
            .put("identity", someScopedService.identity())
            .put("properties", new ImmutableMap.Builder<String, Object>()
                .put("string", someProperties.toString())
                .put("hash", Integer.toHexString(((Advised)someProperties).getTargetSource().getTarget().hashCode()))
                .build()
            )
            .put("echo", arg)
            .build();
    }
}
