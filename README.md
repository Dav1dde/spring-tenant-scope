tenant-scope
============


Example of a multi tenant setup with a custom scope and support
for different properties per tenant.


### TODO

* Don't copy fields in wrapped property source as it is done now
this might break somewhere and you will have no clue what actually 
broke
* Wrap all property sources only once after all have been set 
(check how this would work with refreshes, especially spring cloud)
* Maybe make the environment delegating 
`ctx.setEnv(new FooEnv(ctx.getEnv()))`
