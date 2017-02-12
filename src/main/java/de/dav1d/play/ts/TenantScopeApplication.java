package de.dav1d.play.ts;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan("de.dav1d.play.ts")
public class TenantScopeApplication extends SpringBootServletInitializer
{
    public static void main(String[] args)
    {
        new SpringApplicationBuilder(TenantScopeApplication.class)
            //.listeners(new PropertyEnvironmentListener())
            .run(args);
        //SpringApplication.run(TenantScopeApplication.class, args);
    }
}
