package io.mycat.mydog.model;

import java.io.Serializable;

public class JdbcConnection implements Serializable {

	private static final long serialVersionUID = 5684099384919574769L;

	protected String driverClass;

	protected String connectionUrl;

	protected String username;

	protected String password;

	public String getDriverClass() {
		return driverClass;
	}

	public JdbcConnection setDriverClass(String driverClass) {
		this.driverClass = driverClass;
		return this;
	}

	public String getConnectionUrl() {
		return connectionUrl;
	}

	public JdbcConnection setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public JdbcConnection setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public JdbcConnection setPassword(String password) {
		this.password = password;
		return this;
	}
	
}
