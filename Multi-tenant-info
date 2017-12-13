There are three possible methods to achieve database/persistence multi-tenancy.
Using a ‘Tenant Discriminator’ column
Using a ‘Table-per-tenant’ multi-tenancy
Using Multi-tenant Database Containers
Let us look at what these 3 methods have to offer:

Tenant Discriminator Column: This method uses a single column in the table to ensure data isolation. In this approach a single DB schema is shared between all application consumers. The tenant identifier provided by the Tenant Runtime can be used as the value in the discriminator column. To ensure data separation in the application, each SQL statement must include the tenant identifier.



To implement such a separation via a discriminator column, the multi-tenancy annotations of EclipseLink/JPA may be used. Entities which shall be tenant aware are annotated with the @Multitenant annotation. The discriminator column is defined via the @TenantDiscriminatorColumn annotation. As the length of the tenantId which is provided by the tenant runtime differs from the default length for the discriminator column in EclipseLink, it is important to set the correct length of 36 characters. When accessing data for a multi-tenant entity, the tenantId must be provided to the entity manager.

@Entity
@Table(name = "PERSON")
@Multitenant
@TenantDiscriminatorColumn(name = "TenantId", 
contextProperty = "eclipselink-tenant.id", length = 36)
@NamedQuery(name = "AllPersons", query = "select p from Person p")
public class Person {
     ...
}

Table-per-tenant multi-tenancy: This method allows multiple tenants of an application to isolate their data in one or more tenant-specific tables. This is achieved by creating a different table or schema in the database for each tenant. A tenant table discriminator specifies how to discriminate the tenant’s tables from the other tenants’ tables. The tenants’ tables can be in the same schema, using a prefix or suffix naming pattern to distinguish them; or they can be in separate schema, using a schema tenant table discriminator.

![prefix-1](https://user-images.githubusercontent.com/16852338/33935139-e40095e6-e020-11e7-93ad-a63ac61bc426.png)
![suffix-1](https://user-images.githubusercontent.com/16852338/33935141-e559aaf4-e020-11e7-8d69-6182031b9279.png)
![pic3-1](https://user-images.githubusercontent.com/16852338/33935143-e6acd11a-e020-11e7-969a-f7f266747dfb.png)





To implement such a separation, the multi-tenancy annotations of EclipseLink/JPA may be used again. EclipseLink supports a flavor of multitenancy called the ‘TABLE_PER_TENANT’ multitenancy. So, entities which shall be tenant aware are annotated with the @Multitenant(TABLE_PER_TENANT) annotation.

As mentioned earlier, multiple tenants’ tables can be in a shared schema, identified using a prefix or suffix naming pattern; or they can be in separate, tenant-specific schema based on the @TenantTableDiscriminator annotation. Using this annotation we can specify which tables are associated with which tenants. The tenant table discriminator must include a type and a context property:

The type attribute can be one of the following:
Use PREFIX to apply the tenant table discriminator as a prefix to all multi-tenant tables.
Use SUFFIX to apply the tenant table discriminator as a suffix to all multi-tenant tables.
Use SCHEMA to apply the tenant table discriminator as a schema to all multi-tenant tables.
The value of the contextproperty attribute is a tenant ID that identifies the tenant.
@Entity
@Table(name = "EMPLOYEE")
@Multitenant(TABLE_PER_TENANT)
@TenantTableDiscriminator(type=SCHEMA,
contextProperty="eclipselink-tenant.id")
public class Employee {
     ...
}
Multitenant Database Containers – MDC (*BETA): This method allows for data Isolation to be achieved by creating a different database container, also known as a tenant database, for each consumer. SAP HANA supports multiple isolated databases in a single HANA system. With this method we can achieve a high degree of isolation and security with less impact on the SQL query performance, as we do not have to worry about the complex authorizations we need to create for data isolation. More details on consumption of MDC in applications will follow once it is Generally Available (GA) on the SAP HANA Cloud Platform.

![pic4-1](https://user-images.githubusercontent.com/16852338/33935081-b56718cc-e020-11e7-81c1-03f55b066d9f.png)


As a first step, Robert decides to go with the first and the easiest approach, to use a Tenant Discriminator Column for data isolation.

As mentioned earlier, this method uses a single column in a table to differentiate data from multiple consumers. This means that different consumers should have different values for the tenant discriminator column.

But how do we generate this value? Is this hardcoded? Manually created??

A brief note on the ‘tenantid’ concept:
We know that in a provider-managed application scenario, like the one Robert is creating, each application consumer gets his own consumer-specific URL for the provider application. This is because the consumer has subscribed to the provider application. When an application is launched via a consumer-specific URL, the Tenant Runtime will be able to identify the current consumer of the application. The tenant runtime also provides an API to retrieve the current application consumer. Each application consumer is identified by a unique ID which is called tenantId.
![pic5-1](https://user-images.githubusercontent.com/16852338/33935011-7b7aeddc-e020-11e7-8bc6-5ee4fbc0ad13.png)

