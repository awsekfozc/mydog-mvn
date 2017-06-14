package io.mycat.mydog.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class SqlParser {

	public static class SQL {
		private List<String> columns = new ArrayList<String>();

		private List<String> join = new ArrayList<String>();

		private List<String> leftOuterJoin = new ArrayList<String>();
		private List<String> rightOuterJoin = new ArrayList<String>();
		private List<String> from = new ArrayList<String>();
		private List<String> where = new ArrayList<String>();

		public List<String> getWhere() {
			return where;
		}

		public List<String> getColumns() {
			return columns;
		}

		public List<String> getJoin() {
			return join;
		}

		public List<String> getLeftOuterJoin() {
			return leftOuterJoin;
		}

		public List<String> getRightOuterJoin() {
			return rightOuterJoin;
		}

		public List<String> getFrom() {
			return from;
		}

		public SQL getSelf() {
			return this;
		}

	}

	public static void main(String[] args) {
		String sql = "select users.password,users.user_type, users.create_time, users.mobile_phone, users.name, users.id, users.portrait_url, users.email, users.remarks, users.enabled, users.username from users right join user_login on users.id = user_login.userid"
				+ " where 1=1";
		
		
		SQL sqlAppend = parseQuerySql(sql);

		System.out.println(sqlAppend.getColumns());
		System.out.println(sqlAppend.getFrom());
		System.out.println(sqlAppend.getLeftOuterJoin());
		System.out.println(sqlAppend.getRightOuterJoin());
		System.out.println(sqlAppend.getWhere());
	}
	
	/**
	 * 解析查询语句
	 * */
	public static SQL parseQuerySql(String sql) {
		String[] splits = sql.split(" ");
		List<String> thisList = null;
		SQL sqlAppend = new SQL();
		for (int x = 0; x < splits.length; x++) {
			String split = splits[x];
			if ("select".equalsIgnoreCase(split)) {
				thisList = sqlAppend.getColumns();
				continue;
			}

			if ("from".equalsIgnoreCase(split)) {
				thisList = sqlAppend.getFrom();
				continue;
			}

			if ("left".equalsIgnoreCase(split)) {
				split = splits[++x];
				if (StringUtils.equalsAnyIgnoreCase(split, "outer", "join")) {
					if (StringUtils.equalsIgnoreCase("outer", split)) {
						++x;
					}
					thisList = sqlAppend.getLeftOuterJoin();
				}
				continue;
			}
			
			if ("right".equalsIgnoreCase(split)) {
				split = splits[++x];
				if (StringUtils.equalsAnyIgnoreCase(split, "outer", "join")) {
					if (StringUtils.equalsIgnoreCase("outer", split)) {
						++x;
					}
					thisList = sqlAppend.getRightOuterJoin();
				}
				continue;
			}

			if ("where".equalsIgnoreCase(split)) {
				thisList = sqlAppend.getWhere();
				continue;
			}

			if (split.trim().endsWith(",")) {
				split = split.substring(0, split.length() - 1);
			}
			if (StringUtils.isNoneEmpty(splits)) {
				thisList.add(split);
			}
		}
		return sqlAppend;
	}
}
