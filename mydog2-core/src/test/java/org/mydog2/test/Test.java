package org.mydog2.test;

import java.io.File;

import org.mydog2.core.MyDogGenerate;
import org.mydog2.jdbc.enums.JdbcType;
import org.mydog2.xml.XmlConfigLoader;

public class Test {

	public static void main(String[] args) {

		XmlConfigLoader.getConfigLoader().setXmlConfig(new File("src/main/resources/generatorConfig.xml").getAbsolutePath())
		.setOutputDirectory(new File("").getAbsolutePath() + "/target/")
				.load();
			
		MyDogGenerate.handler(); 
		
		System.out.println( JdbcType.forName("VARCHAR") );
	}
}
