## Multi-tenant Schema level
  - Simple spring-boot-application with Postgresql with multi-tenancy

### /config
  - `CloudConfiguration` : Provides the application instance information from the cloud.
  - `MultiTenantConnectionProviderImpl` : binds the `datasource` instance to the particular `tenantId` provided by `TenantProvider`.
  - `MultitenantHibernateJpaAutoConfiguration` : Main class which initialises/configures the hibernate JPA for crud operation.
    - How: `customizeVendorProperties` method configures the hibernate JPA for crud operation by taking correct tenantId from `TenantIdentifierResolverImpl` and `datasource pointing to particular tenantId` from `MultiTenantConnectionProviderImpl`.  
  - `TenantIdentifierResolverImpl` : Provides the tenantId
  - `TenantProvider` : Simple class file to get tenantId from Security-context.
