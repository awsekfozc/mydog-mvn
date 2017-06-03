package org.mydog.config.utils;

public class ResultType {

	private JdbcType jdbcType;

	private String columnTypeName;

	private String columnName;

	private int columnType;

	public ResultType(int columnType, String columnTypeName, String columnName) {
		this.columnType = columnType;
		this.columnTypeName = columnTypeName;
		this.columnName = columnName;

		jdbcType = JdbcType.forCode(columnType);
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnTypeName() {
		return columnTypeName;
	}

	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}

	public int getColumnType() {
		return columnType;
	}

	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}

	public JdbcType getJdbcType() {
		return jdbcType;
	}

	@Override
	public String toString() {
		return "jdbcType=" + jdbcType + ", columnTypeName=" + columnTypeName + ", columnName=" + columnName
				+ ", columnType=" + columnType;
	}

}
