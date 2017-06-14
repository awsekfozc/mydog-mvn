package io.mycat.core;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import io.mycat.mydog.freemarker.FreemarkerEngine;
import io.mycat.mydog.jdbc.SpringJdbc;
import io.mycat.mydog.jdbc.model.ResultType;
import io.mycat.mydog.model.GeneratorModel;
import io.mycat.mydog.model.TableModel;
import io.mycat.mydog.utils.KeyMap;
import io.mycat.mydog.utils.StringTools;
import io.mycat.mydog.xml.XmlConfigLoader;

public class MyDogGenerate {
	static final String SOURCE_DIR = "generate-source" ;
	public static void handler() {
		XmlConfigLoader configLoader = XmlConfigLoader.getConfigLoader();
		Map<String, TableModel> tableMap = configLoader.getTableMap();
		List<GeneratorModel> generators = configLoader.getGenerators();
		for (Map.Entry<String, TableModel> entry : tableMap.entrySet()) {
			SpringJdbc jdbc = new SpringJdbc();
			Map<String, ResultType> jdbcTableData = jdbc.queryTableMetas(entry.getKey());
			Map<String, Object> templateDataMap = new KeyMap<Object>();
			templateDataMap.putAll(configLoader.getVarMap()); 
			templateDataMap.put("tableInfos", jdbcTableData.values());
			for (Map.Entry<String, ResultType> tableEntry : jdbcTableData.entrySet()) {
				if (tableEntry.getValue().isPri()) {
					templateDataMap.put("pri", tableEntry.getValue());
				}
			}
			
			List<String> queryColumns = entry.getValue().getQueryColumns();
			List<ResultType> queryColumnTypes = new Vector<ResultType>();
			for(String column:queryColumns){
				ResultType resultType = jdbcTableData.get(column);  
				queryColumnTypes.add( resultType );
			}
			templateDataMap.put("queryColumns", queryColumnTypes ); 
			
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
				
				int indexOf = destFile.getAbsolutePath().indexOf( SOURCE_DIR ) ;
				String substring = destFile.getAbsolutePath().substring(indexOf + SOURCE_DIR.length() ) ;
				substring = substring.replaceAll("\\\\", "/")  ; 
				data.put(className + "Page", substring + "/" + objectname + StringTools.getFileName(tpl)) ;  
			} 
			int index = 0 ;
			for (String tpl : model.getTmplates()) {
				index++;
				File output = null ; 
				if(destFile.getName().indexOf(".") == -1){
					if(!tpl.endsWith("java")){
						String fileName = new File(tpl).getName() ;
						output = new File(destFile , objectname + fileName ) ;
					}else{
						output = new File(destFile , objectname + StringTools.toClassName(new File(tpl).getName())) ; 
					}
				}else{
					if(index == 1){
						output = destFile ;  
					}else{
						output = new File(destFile.getParentFile() , objectname + StringTools.toClassName(new File(tpl).getName())) ; 
					}
				}
				
				FreemarkerEngine.process(data, tpl, output.getAbsolutePath());
			}
		}
	}
}
