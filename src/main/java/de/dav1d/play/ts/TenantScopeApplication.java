package de.dav1d.play.ts;

import de.dav1d.play.ts.property.PropertyEnvironmentInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("de.dav1d.play.ts")
public class TenantScopeApplication
{
    public static void main(String[] args)
    {
        new SpringApplicationBuilder(TenantScopeApplication.class)
            //.environment(new SimplePropertyServletEnvironment())
            .initializers(new PropertyEnvironmentInitializer())
            .run(args);
        //SpringApplication.run(TenantScopeApplication.class, args);
    }
}
