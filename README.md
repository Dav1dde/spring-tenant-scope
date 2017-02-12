tenant-scope
============


Example of a multi tenant setup with a custom scope 
for beans which need to behave differently for every tenant.


*Bonus:* A custom property source which requires a ServletContext 
and uses the Tenant-Getter for tenant specific properties.


*Problem:* since `Environment.initPropertySources` is called twice
it is hard to detect when there should be beans available, as of
now the implementation will quietly not replace the stub property
source when there is no `TenantGetter` bean available.

*TODO:* replace the hardcoded TenantPropertySource with a bean 
and read it from the application context instead.


## Setup

For simple property sources you can set Environment 
on with the `SpringApplicationBuilder` or register the property source direcly
in the listener.

The listener can also be registered by using the `SpringApplicationBuilder`,
but this only works for JAR-deployment. For WAR-deployment you **have**
to register the listener through the `spring.factories` file.


## ServletContext Property Source

For the example in this repository you need to use the `PropertyEnvironmentListener`,
since the `TenantPropertySource` requires the Application Context to retrieve 
the tenant getter.
The example implementation shows how to implement tenant dependant beans with 
tenant dependant configurations.
