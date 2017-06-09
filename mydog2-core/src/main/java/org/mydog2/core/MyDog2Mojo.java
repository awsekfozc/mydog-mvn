package org.mydog2.core;

import java.io.File;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.mydog2.logs.MyDogLog;
import org.mydog2.xml.XmlConfigLoader;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class MyDog2Mojo extends AbstractMojo {

	@Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
	private File outputDirectory ; 

	
	@Parameter(property="configXml")
	private String configXml ;
	
	
	public void execute() throws MojoExecutionException {
		File file = new File(configXml) ;
		if(!file.exists() || !file.isFile()){
			MyDogLog.getLog().info(String.format("%s not exists exit 1", configXml));
			return ; 
		}
		XmlConfigLoader.getConfigLoader().setXmlConfig( file.getAbsolutePath() )
		.setOutputDirectory(outputDirectory.getAbsolutePath()) 
		.load(); 
		
		MyDogGenerate.handler(); 
	}
}
