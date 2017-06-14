package io.mycat.mydog.model;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

import io.mycat.mydog.logs.MyDogLog;
import io.mycat.mydog.utils.ArrayMap;

public class GeneratorModel implements Serializable {

	private static final long serialVersionUID = -2709059204183339229L;

	protected static ArrayMap<String> templateMap = new ArrayMap<String>();

	private String type;

	private String targetPackage;

	private String root;

	private List<String> tmplates;

	static {
		templateMap.putItem("domain", "/templates/domain.java");
		templateMap.putItem("mappping", "/templates/mapping.java");
		templateMap.putItem("mappping", "/templates/sqlProvider.java");
		templateMap.putItem("service", "/templates/service.java");
		templateMap.putItem("controller", "/templates/controller.java");
		templateMap.putItem("html", "/templates/list.html"); 
		templateMap.putItem("html", "/templates/edit.html"); 
	}

	public List<String> getTmplates() {
		return tmplates;
	}

	public void setTmplates(String tmplateStr) {
		if (StringUtils.isNoneBlank(tmplateStr)) {
			String[] temps = tmplateStr.split(",");
			tmplates = Arrays.asList(temps);
		} else {
			tmplates = templateMap.get(getType());
			if (null == tmplates) {
				tmplates = new Vector<>();
				MyDogLog.getLog().warn(getType() + " No templates have been set");
			}
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTargetPackage() {
		return targetPackage;
	}

	public void setTargetPackage(String targetPackage) {
		this.targetPackage = targetPackage;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getFolder() {
		String replace = StringUtils.replace(targetPackage, ".", File.separator);
		
		
		return replace ;
	}
}
