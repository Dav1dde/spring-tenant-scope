package de.dav1d.play.ts.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Scope(value = "tenant", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Retention(RetentionPolicy.RUNTIME)
public @interface TenantScoped
{
}
