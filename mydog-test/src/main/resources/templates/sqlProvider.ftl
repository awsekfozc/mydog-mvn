package ${package}; 

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import ${modelPackage}.${modelName};

public class ${thisClassName}{
	
	public String selectInActive(${modelName} obj){
		SQL sql = new SQL();
		sql.SELECT( <#list tableInfos as info>"${info.field}" <#if info_has_next>,</#if> </#list>); 
		sql.FROM( "${tableName}" );
		
		<#list tableInfos as info>
			<#if info.javaType == "String">
				if(StringUtils.isNotBlank(obj.${info.readMethod}())){
					sql.AND().WHERE("${info.field} = ${r'#'}{${info.property}}") ;
				}
			</#if>
		</#list>
		
		return sql.toString() ;  
	}
	
}