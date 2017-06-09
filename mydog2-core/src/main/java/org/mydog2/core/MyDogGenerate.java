package org.mydog2.core;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mydog2.freemarker.FreemarkerEngine;
import org.mydog2.jdbc.SpringJdbc;
import org.mydog2.jdbc.model.ResultType;
import org.mydog2.model.GeneratorModel;
import org.mydog2.model.TableModel;
import org.mydog2.utils.KeyMap;
import org.mydog2.utils.StringTools;
import org.mydog2.xml.XmlConfigLoader;
import org.springframework.util.CollectionUtils;

public class MyDogGenerate {

	public static void handler() {
		XmlConfigLoader configLoader = XmlConfigLoader.getConfigLoader();
		Map<String, TableModel> tableMap = configLoader.getTableMap();
		List<GeneratorModel> generators = configLoader.getGenerators();
		for (Map.Entry<String, TableModel> entry : tableMap.entrySet()) {
			SpringJdbc jdbc = new SpringJdbc();
			Map<String, ResultType> data = jdbc.queryTableMetas(entry.getKey());
			Map<String, Object> templateDataMap = new KeyMap<Object>();
			templateDataMap.put("tableInfos", data.values());

			for (Map.Entry<String, ResultType> tableEntry : data.entrySet()) {
				if (tableEntry.getValue().isPri()) {
					templateDataMap.put("pri", tableEntry.getValue());
				}
			}
			templateDataMap.put("tableName", entry.getKey());
			templateDataMap.put("ObjectName", StringUtils.isBlank(entry.getValue().getDomainObjectName())
					? StringTools.toClassName(entry.getKey()) : entry.getValue().getDomainObjectName());

			for (GeneratorModel generatorModel : generators) {
				generateFile(templateDataMap, generatorModel);
			}
		}
	}

	protected static void generateFile(Map<String, Object> data, GeneratorModel model) {
		XmlConfigLoader configLoader = XmlConfigLoader.getConfigLoader();
		data.put("package", model.getTargetPackage());
		File destFile = new File(configLoader.getOutputDirectory(), "generate-source/" + 
		StringTools.replace(model.getFolder(), data));
		String objectname = StringTools.toString(data.get("objectname"));
		String thisClassName = null;
		if (StringUtils.equalsAnyIgnoreCase(model.getType(), "domain")) {
			thisClassName = objectname;
			destFile = new File(destFile, thisClassName + ".java");
		} else if (StringUtils.equalsAnyIgnoreCase(model.getType(), "mappping", "service", "controller", "dao",
				"daoImpl", "sqlProvider", "Provider")) {
			thisClassName = objectname + StringTools.toClassName(model.getType());

			destFile = new File(destFile, objectname + StringTools.toClassName(model.getType()) + ".java");
		}
		
		data.put("thisClassName", thisClassName);
		data.put(model.getType() + "Name", thisClassName);
		data.put(model.getType() + "Package", model.getTargetPackage());
		data.put("root", model.getRoot());
		if (StringUtils.isNoneBlank(model.getRoot())) {
			data.put("rootClass", StringTools.getClassName(model.getRoot()));
		}else{
			data.put("rootClass","");
		}
		if (!CollectionUtils.isEmpty(model.getTmplates())) {
			
			for(String tpl : model.getTmplates()){
				String className = StringTools.getClassNameForFile(tpl);
				data.put(className,objectname + StringTools.toClassName(className));  
			} 
			
			int index = 0 ;
			for (String tpl : model.getTmplates()) {
				index++ ;
				
				
				if(destFile.getName().lastIndexOf(".") == -1){
					destFile = new File(destFile , objectname + new File(tpl).getName()) ; 
				}
				
				FreemarkerEngine.process(data, tpl, index == 1 ? destFile.getAbsolutePath() : String
						.format("%s/%s", destFile.getParentFile().getAbsolutePath() ,
								objectname + StringTools.toClassName(new File(tpl).getName())));
			}
		}
	}
}
