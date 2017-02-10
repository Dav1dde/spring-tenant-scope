tenant-scope
============


Example of a multi tenant setup with a custom scope 
for beans which need to behave differently for every tenant.


*Bonus:* A custom property source which requires a ServletContext 
and uses the Tenant-Getter for tenant specific properties.
    
    
## ServletContext Property Source

For simple property sources you can set Environment 
on with the `SpringApplicationBuilder` or register the property source direcly
in the initializer.

For the example in this repository you need to use the `PropertyEnvironmentInitializer`,
since the `TenantPropertySource` requires the Application Context to retrieve 
the tenant getter.
The example implementation shows how to implement tenant dependant beans with 
tenant dependant configurations.

