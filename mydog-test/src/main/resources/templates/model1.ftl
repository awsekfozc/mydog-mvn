package ${package}; 

<#if '' != rootClass>import ${root};</#if>

public class ${thisClassName} <#if '' != rootClass>extends ${rootClass}</#if>{
	
	<#list tableInfos as info>
	/*
	* ${info.comment}
	*/
	private ${info.javaType} ${info.property};
	</#list>
	
	<#list tableInfos as info>
	/*
	* ${info.comment}
	*/
	public void ${info.writeMethod}(${info.javaType} ${info.property}){
		this.${info.property} = ${info.property} ;
	}
	
	public ${info.javaType} ${info.readMethod}(){
		return this.${info.property} ; 
	}
	</#list>
}