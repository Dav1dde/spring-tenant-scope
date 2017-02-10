package de.dav1d.play.ts.controller;

import com.google.common.collect.ImmutableMap;
import de.dav1d.play.ts.service.SomeScopedService;
import de.dav1d.play.ts.tenant.TenantGetter;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("/echo")
    public Map echo(@RequestParam String arg)
    {
        return new ImmutableMap.Builder<String, Object>()
            .put("tenant", tenantGetter.getTenant())
            .put("identity", someScopedService.identity())
            .put("echo", arg)
            .build();
    }
}
