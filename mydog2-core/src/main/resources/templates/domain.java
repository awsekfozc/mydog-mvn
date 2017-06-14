package ${package}; 

<#if '' != rootClass>import ${root};</#if>

public class ${thisClassName} <#if '' != rootClass>extends ${rootClass}</#if>{
	
	<#list tableInfos as info>
	/*
	* ${info.comment}
	*/
	<#if 'java.util.Date' == info.generateClassName>
	@com.fasterxml.jackson.annotation.JsonFormat(pattern = "yyyy-MM-dd")
	</#if>
	private ${info.generateClassName} ${info.property};
	</#list>
	
	<#list tableInfos as info>
	
	/*
	* ${info.comment}
	*/
	public void ${info.writeMethod}(${info.generateClassName} ${info.property}){
		this.${info.property} = ${info.property} ;
	}
	/*
	* ${info.comment}
	*/
	public ${info.generateClassName} ${info.readMethod}(){
		return this.${info.property} ; 
	}
	</#list>
}