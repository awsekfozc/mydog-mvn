package org.mydog2.utils;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mydog2.model.JdbcConnection;
import org.mydog2.xml.XmlConfigLoader;

public class DataSourceTools {

	protected static BasicDataSource dataSource;

	public static DataSource getDataSource() {
		synchronized (Thread.class) {
			if (null == dataSource) {
				dataSource = new BasicDataSource();
				JdbcConnection jdbcConfig = XmlConfigLoader.getConfigLoader().getJdbcConnection();
				dataSource = new BasicDataSource();
				dataSource.setUrl(jdbcConfig.getConnectionUrl());
				dataSource.setUsername(jdbcConfig.getUsername());
				dataSource.setPassword(jdbcConfig.getPassword());
				dataSource.setDriverClassName(jdbcConfig.getDriverClass());
			}
		}
		return dataSource;
	}
}
