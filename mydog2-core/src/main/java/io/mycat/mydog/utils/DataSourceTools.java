package io.mycat.mydog.utils;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import io.mycat.mydog.model.JdbcConnection;
import io.mycat.mydog.xml.XmlConfigLoader;

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
