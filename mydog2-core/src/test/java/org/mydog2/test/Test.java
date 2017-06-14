package org.mydog2.test;

import java.io.File;

import io.mycat.core.MyDogGenerate;
import io.mycat.mydog.jdbc.enums.JdbcType;
import io.mycat.mydog.xml.XmlConfigLoader;

public class Test {

	public static void main(String[] args) {

		XmlConfigLoader.getConfigLoader().setXmlConfig(new File("src/main/resources/generatorConfig.xml").getAbsolutePath())
		.setOutputDirectory(new File("").getAbsolutePath() + "/target/")
				.load();
		
		MyDogGenerate.handler(); 
		
		System.out.println( JdbcType.forName("VARCHAR") );
	}
}
