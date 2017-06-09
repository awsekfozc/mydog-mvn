package org.mydog2.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.mydog2.jdbc.model.ResultType;
import org.mydog2.logs.MyDogLog;
import org.mydog2.model.TableModel;
import org.mydog2.model.TableModel.ColumnOverride;
import org.mydog2.model.TableModel.ColumnSymbol;
import org.mydog2.utils.DataSourceTools;
import org.mydog2.utils.KeyMap;
import org.mydog2.utils.StringTools;
import org.mydog2.xml.XmlConfigLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class SpringJdbc {

	final String SHOW_TABLE_DESC_SQL = "SHOW FULL COLUMNS FROM %s";
	protected JdbcTemplate jdbc = null;

	protected DataSource dataSource;

	public SpringJdbc() {
		TransactionSynchronizationManager.initSynchronization();
		dataSource = DataSourceTools.getDataSource();
		jdbc = new JdbcTemplate(dataSource);
	}
		
	/**
	 * 查询表元数据相关信息
	 */
	public Map<String, ResultType> queryTableMetas(String tableName) {
		Map<String, ResultType> resultTypes = new HashMap<String, ResultType>();
		Map<String, Map<String, Object>> columnInfo = query(String.format(SHOW_TABLE_DESC_SQL, tableName), "field");
		Connection connection = DataSourceUtils.getConnection(dataSource);
		PreparedStatement ps = null;
		ResultSet rs = null;
		XmlConfigLoader configLoader = XmlConfigLoader.getConfigLoader();
		TableModel tableModel = configLoader.getTableMap().get(tableName) ;
		try {
			ps = connection.prepareStatement(String.format("select * from %s", tableName));
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int cloumnLength = rsmd.getColumnCount();
			
			for(int x=0;x<cloumnLength;x++){
				ResultType resultType = new ResultType();
				String field = rsmd.getColumnLabel(x+1);
				Map<String, Object> fieldMap = columnInfo.get( field );
				String extra = StringTools.toString(fieldMap.get("extra")) ;
				resultType.setAutoIncrement("auto_increment".equals(extra)) ; 
				resultType.setPri("pri".equalsIgnoreCase(StringTools.toString(fieldMap.get("key")))) ; 
				resultType.setColumnLabel(field) ;
				resultType.setColumnName( rsmd.getColumnName(x+1) ) ;
				ColumnOverride columnOverride = tableModel.getColumnOverride().get( field ) ;
				
				resultType.setColumnType( rsmd.getColumnType(x+1) ) ;
				if(columnOverride == null){
					resultType.setColumnTypeName( resultType.getJdbcType().name() ) ;
					resultType.setClassName(rsmd.getColumnClassName(x+1)) ;
				}else{
					resultType.setColumnTypeName(columnOverride.getJdbcType().name()) ;
					resultType.setClassName( columnOverride.getJavaType() ) ; 
				}
				
				resultType.setProperty( StringTools.toProperty(field) ) ;
				resultType.setComment( StringTools.toString( fieldMap.get("comment"))) ;
				resultType.setGenerateClassName( resultType.getClassName().replaceAll("java.lang.", "")); 
				
				/*修改链接符号*/
				ColumnSymbol columnSymbol = tableModel.getColumnSymbols().get( field ) ;
				if(null != columnSymbol){
					columnSymbol.setQueryValue( columnSymbol.getValue().replaceAll("\\{value\\}", "#{"+resultType.getProperty()+"}")); 
					resultType.setColumnSymbol( columnSymbol );
				}
				/*设置字段显示的值*/
				resultType.setColumnValue( tableModel.getColumnValue().get(field) ) ;
				resultTypes.put(field, resultType) ; 
				
			}
		} catch (Exception e) {
			MyDogLog.getLog().error(" queryTableMetas ", e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(ps);
			DataSourceUtils.releaseConnection(connection, dataSource);
		}

		return resultTypes;
	}
	
	/**
	 * 
	 * */
	public Map<String, Map<String, Object>> query(String sql, String key) {
		try {
			Map<String, Map<String, Object>> resultMap = new KeyMap<Map<String, Object>>();
			Connection conn = DataSourceUtils.getConnection(dataSource);
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				while (rs.next()) {
					Map<String, Object> itemMap = new KeyMap<Object>();
					for (int x = 0; x < rsmd.getColumnCount(); x++) {
						itemMap.put(rsmd.getColumnLabel(x + 1), rs.getObject(x + 1));
					}
					resultMap.put(StringTools.toString(itemMap.get(key)), itemMap);
				}
			} catch (Exception e) {
				MyDogLog.getLog().error(" query ", e);
			} finally {
				JdbcUtils.closeResultSet(rs);
				JdbcUtils.closeStatement(ps);
				DataSourceUtils.releaseConnection(conn, dataSource);
			}

			return resultMap;
		} finally {
			DataSourceUtils.releaseConnection(DataSourceUtils.getConnection(dataSource), dataSource);
		}
	}

}
