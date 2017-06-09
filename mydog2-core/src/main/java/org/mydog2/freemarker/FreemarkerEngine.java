package org.mydog2.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.mydog2.logs.MyDogLog;
import org.mydog2.ui.MydogTemplateLoader;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.Version;

public class FreemarkerEngine {
	
	protected final static String TEMPLATE_CLASSPATH = "classpath:/" ;
	
	protected static String templateClassPath = TEMPLATE_CLASSPATH ;

	static final Version FREEMARKER_VERSION = Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS;

	static Configuration freeMarkerCfg = new Configuration(FREEMARKER_VERSION);

	private static ResourceLoader resourceLoader = new DefaultResourceLoader();

	static {
		
	}
	
	protected static TemplateLoader getTemplateLoaderForPath(String templateLoaderPath  ) {
		return new MydogTemplateLoader(resourceLoader, templateLoaderPath );
	}

	public static void process(Map<String, Object> data, String tmpName, String destFile) {
		Writer writer = null;
		try {
			new File(destFile).getParentFile().mkdirs();  
			
			MyDogLog.getLog().info("outputfile:" + destFile);
			
			writer = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(destFile),"UTF-8"));
			freeMarkerCfg.setTemplateLoader(getTemplateLoaderForPath( templateClassPath  ));
			
			freeMarkerCfg.setObjectWrapper(new DefaultObjectWrapper(FREEMARKER_VERSION));
			Template template = freeMarkerCfg.getTemplate(tmpName);
			
			template.process(data, writer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	public static void setTemplateClassPath(String templateClassPath) {
		FreemarkerEngine.templateClassPath = templateClassPath;
	}
	
}
