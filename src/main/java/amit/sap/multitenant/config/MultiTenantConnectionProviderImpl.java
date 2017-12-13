package amit.sap.multitenant.config;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.spi.Stoppable;
import org.springframework.beans.factory.annotation.Autowired;

public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider, Stoppable {

	private static final long serialVersionUID = -5696470278514138392L;

	@Autowired
	DataSource dataSource;

	@Override
	public Connection getAnyConnection() throws SQLException {
		return dataSource.getConnection();
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		connection.close();
	}

	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {
		final Connection connection = getAnyConnection();
		try {
			connection.createStatement().execute("SET search_path TO " + tenantIdentifier + ";");
		} catch (SQLException e) {
			throw new HibernateException(
					"Could not alter JDBC connection to specified schema [" + tenantIdentifier + "]", e);
		}
		return connection;
	}

	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
		try {
			connection.createStatement().execute("SET search_path TO public;");
		} catch (SQLException e) {
			// on error, throw an exception to make sure the connection is not
			// returned to the pool.
			// your requirements may differ
			throw new HibernateException(
					"Could not alter JDBC connection to specified schema [" + tenantIdentifier + "]", e);
		}
		connection.close();
	}

	@Override
	public boolean isUnwrappableAs(Class unwrapType) {
		// TODO Auto-generated method stub
		try {
			return !dataSource.isWrapperFor(unwrapType);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stop() {

	}

	@Override
	public boolean supportsAggressiveRelease() {
		// TODO Auto-generated method stub
		return false;
	}

}
