package org.mydog2.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.mydog2.jdbc.enums.JdbcType;
import org.mydog2.utils.KeyMap;

import com.alibaba.fastjson.JSON;

public class TableModel implements Serializable {
	public static class ColumnOverride{
		private String column ;
		private String javaType ;
		private JdbcType jdbcType ;
		public String getColumn() {
			return column;
		}
		public String getJavaType() {
			return javaType;
		}
		public JdbcType getJdbcType() {
			return jdbcType;
		}
		public void setColumn(String column) {
			this.column = column;
		}
		public void setJavaType(String javaType) {
			this.javaType = javaType;
		}
		public void setJdbcType(String jdbcType) {
			this.jdbcType = JdbcType.forName(jdbcType);
		}
	}
	public static class ColumnSymbol{
		private String column ;
		private String queryValue ;
		private String symbol ; 
		private String value ; 
		public String getColumn() {
			return column;
		}
		 
		public String getQueryValue() {
			return queryValue;
		}

		public String getSymbol() {
			return symbol;
		}

		public String getValue() {
			return value;
		}
		public void setColumn(String column) {
			this.column = column;
		}
		public void setQueryValue(String queryValue) {
			queryValue=queryValue.replaceAll("\"", "\\\\\"");
			this.queryValue = queryValue;
		}
		 
		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	}

	public static class ColumnValue {
		private Map<String, String> map = new HashMap<String, String>();
		private String name;

		public Map<String, String> getMap() {
			return map;
		}

		public String getName() {
			return name;
		}

		public void setMap(Map<String, String> map) {
			this.map = map;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	private static final long serialVersionUID = 1766187215300907854L;

	private Map<String,ColumnOverride> columnOverride = new KeyMap<ColumnOverride>();
	private Map<String,ColumnSymbol> columnSymbols = new KeyMap<ColumnSymbol>();
	
	private Map<String,ColumnValue> columnValue = new KeyMap<ColumnValue>();

	private String domainObjectName;

	private String name;
	private String tableName;
	
	public Map<String, ColumnOverride> getColumnOverride() {
		return columnOverride;
	}

	public Map<String, ColumnSymbol> getColumnSymbols() {
		return columnSymbols;
	}

	public Map<String, ColumnValue> getColumnValue() {
		return columnValue;
	}
	
	public String getDomainObjectName() {
		return domainObjectName;
	}

	public String getName() {
		return name;
	}

	public String getTableName() {
		return tableName;
	}

	public void setColumnOverride(Map<String, ColumnOverride> columnOverride) {
		this.columnOverride = columnOverride;
	}

	public void setColumnSymbols(Map<String, ColumnSymbol> columnSymbols) {
		this.columnSymbols = columnSymbols;
	}

	public void setColumnValue(Map<String, ColumnValue> columnValue) {
		this.columnValue = columnValue;
	}

	public void setDomainObjectName(String domainObjectName) {
		this.domainObjectName = domainObjectName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this) ;
	}
}
