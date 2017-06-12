package org.mydog2.utils;

import java.io.File;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTools {

	public static String toString(Object obj){
		return null == obj ? "" : obj.toString().trim() ;
	}
	
	public static String toClassName(String text){
		String frist = text.substring(0 , 1) ;
		String end = text.substring(1);
		String[] splits = end.split("_");
		StringBuffer buffer = new StringBuffer();
		buffer.append(frist.toUpperCase()) ;
		for(int x=0;x<splits.length;x++){
			String split = splits[x];
			if(x>0){
				split = toClassName(split) ;
			}
			buffer.append(split);
		}
		return buffer.toString() ;
	}
	
	
	public static String getClassName(String className){
		int lastIndexOf = className.lastIndexOf(".");
		if(lastIndexOf > -1){
			return className.substring(lastIndexOf + 1) ;
		}
		return className;
	}
	
	public static String getClassNameForFile(String className){
		File f = new File(className);
		className = f.getName() ;
		int lastIndexOf = className.lastIndexOf(".");
		if(lastIndexOf > -1){
			return className.substring(0 , lastIndexOf) ;
		}
		return className;
	}
	
	public static String toProperty(String text){
		String frist = text.substring(0 , 1) ;
		String end = text.substring(1);
		String[] splits = end.split("_");
		StringBuffer buffer = new StringBuffer();
		buffer.append(frist.toLowerCase()) ;
		for(int x=0;x<splits.length;x++){
			String split = splits[x];
			if(x>0){
				split = toClassName(split) ;
			}
			buffer.append(split);
		}
		return buffer.toString() ;
	}
	
	public static String replace(String source,Map<String, Object> data){
		
		Pattern pattern = Pattern.compile("\\{[^}]+\\}");
		String resultStr = source ; 
		Matcher matcher = pattern.matcher(source);
		while(matcher.find()){
			String str = matcher.group();
			str = str.substring(1, str.length() - 1);
			resultStr = resultStr.replace("{"+str+"}", StringTools.toString(data.get(str)) ) ;
		}
		return resultStr.toLowerCase() ;
	}
	
	public static void main(String[] args) {
		Map<String, Object> data = new KeyMap<Object>();
		data.put("tableName", "users");
		
		System.out.println(replace("admin.{tableName}", data));
	}
}
