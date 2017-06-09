package ${package}; 

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import ${domainPackage}.${domainName};

public class ${sqlProvider}{
	
	public String selectInActive(${domainName} obj){
		SQL sql = new SQL();
		sql.SELECT( <#list tableInfos as info>"${info.columnLabel}" <#if info_has_next>,</#if> </#list>); 
		sql.FROM( "${tableName}" );
		
		<#list tableInfos as info>
			<#if 'String' == info.generateClassName >
				if( StringUtils.isNotBlank( obj.${info.readMethod}() )){ 
			<#else>
				if( null != obj.${info.readMethod}() ){
			</#if>
			
			<#if info.columnSymbol??>
				sql.AND().WHERE("${info.columnLabel} ${info.columnSymbol.symbol}  ${info.columnSymbol.queryValue}") ; 
			<#else>
				sql.AND().WHERE("${info.columnLabel} = ${r'#'}{${info.property}}") ; 
			</#if>
			}
		</#list>
		
		return sql.toString() ;  
	}
	
}