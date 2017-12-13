package amit.sap.multitenant.config;

import org.springframework.stereotype.Component;


@Component
public class TenantProvider {
	String tenantId;
	
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantId() {
		// get the tenant-id from security context
		return tenantId;
	}

	
}