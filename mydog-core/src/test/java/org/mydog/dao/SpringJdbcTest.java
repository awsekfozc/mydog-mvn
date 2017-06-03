package org.mydog.dao;

import java.util.Map;

import org.mydog.config.utils.ResultType;
import org.mydog.loader.xml.XmlConfigLoader;

public class SpringJdbcTest {

	public static void main(String[] args) throws Exception {
		String schemaFile = "C:\\Users\\Administrator\\git\\mydog\\mydog-test\\src\\main\\resources\\generatorConfig.xml" ;
		XmlConfigLoader.getLoader().setSchemaFile(schemaFile).load(); 
		SpringJdbc springJdbc = new SpringJdbc(); 
		
		Map<String, ResultType> queryTableMeta = springJdbc.queryTableMeta("ttt");
		queryTableMeta.values().forEach(System.out::println); 
	}
}
