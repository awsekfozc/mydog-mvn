package org.mydog2.jdbc.model;

import org.apache.commons.lang3.StringUtils;
import org.mydog2.jdbc.enums.JdbcType;
import org.mydog2.model.TableModel.ColumnSymbol;
import org.mydog2.model.TableModel.ColumnValue;
import org.mydog2.utils.StringTools;

import com.alibaba.fastjson.JSON;

public class ResultType {

	protected enum MethodType {
		READ, WRITE
	}
	protected String className;

	protected String columnLabel;

	protected String columnName;

	public ColumnSymbol columnSymbol;

	protected int columnType;

	protected String columnTypeName;
	public ColumnValue columnValue;
	
	protected String comment;

	protected String generateClassName;

	protected boolean isAutoIncrement;
	protected boolean isPri;
	
	protected JdbcType jdbcType;

	private String property;

	protected String readMethod ;

	protected String writeMethod ;

	public ResultType() {
	}

	public String getClassName() {
		return className;
	}

	public String getColumnLabel() {
		return columnLabel;
	}

	public String getColumnName() {
		return columnName;
	}

	public ColumnSymbol getColumnSymbol() {
		return columnSymbol;
	}

	public int getColumnType() {
		return columnType;
	}

	public String getColumnTypeName() {
		return columnTypeName;
	}

	public ColumnValue getColumnValue() {
		return columnValue;
	}

	public String getComment() {
		return comment;
	}

	public String getGenerateClassName() {
		return generateClassName;
	}


	public JdbcType getJdbcType() {
		return jdbcType;
	}

	protected String getMethodName(String field, String type, MethodType methodType) {
		if (methodType == MethodType.READ) {
			String readHead = "get";
			if (StringUtils.equalsAnyIgnoreCase("Boolean", type)) {
				readHead = "is";
			}
			return readHead + StringTools.toClassName(field);
		}
		String writeHead = "set"; 
		return writeHead + StringTools.toClassName(field);
	}

	public String getProperty() {
		return property;
	}

	public String getReadMethod() {
		return getMethodName(columnLabel, getGenerateClassName(), MethodType.READ);
	}

	public String getWriteMethod() {
		
		
		return getMethodName(columnLabel, getGenerateClassName(), MethodType.WRITE) ;
	}

	public boolean isAutoIncrement() {
		return isAutoIncrement;
	}

	public boolean isPri() {
		return isPri;
	}

	public ResultType setAutoIncrement(boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
		return this;
	}

	public ResultType setClassName(String className) {
		if ("[B".equalsIgnoreCase(className)) {
			className = "byte[]";
		}
		this.className = className;
		return this;
	}

	public ResultType setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
		return this;
	}

	public ResultType setColumnName(String columnName) {
		this.columnName = columnName;
		return this;
	}

	public void setColumnSymbol(ColumnSymbol columnSymbol) {
		this.columnSymbol = columnSymbol;
	}

	public ResultType setColumnType(int columnType) {
		this.columnType = columnType;
		jdbcType = JdbcType.forCode(columnType);
		return this;
	}

	public ResultType setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
		return this;
	}

	public ResultType setColumnValue(ColumnValue columnValue) {
		this.columnValue = columnValue;
		return this;
	}

	public ResultType setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public void setGenerateClassName(String generateClassName) {
		this.generateClassName = generateClassName;
	}

	public ResultType setJdbcType(JdbcType jdbcType) {
		this.jdbcType = jdbcType;
		return this;
	}

	public ResultType setPri(boolean isPri) {
		this.isPri = isPri;
		return this;
	}

	public ResultType setProperty(String property) {
		this.property = property;
		return this ;
	}

	public void setReadMethod(String readMethod) {
		this.readMethod = readMethod;
	}
	
	public void setWriteMethod(String writeMethod) {
		this.writeMethod = writeMethod;
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
