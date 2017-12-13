package amit.sap.multitenant.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;


public class TenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

	@Autowired
	private TenantProvider tenantProvider;

	@Override
	public String resolveCurrentTenantIdentifier() {
		return tenantProvider.getTenantId();
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return false;
	}

}
