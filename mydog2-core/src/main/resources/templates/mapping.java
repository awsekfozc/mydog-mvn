package ${package};

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import ${domainPackage}.${domainName};
<#if '' != rootClass>import ${root};</#if>

public interface ${thisClassName} <#if '' != rootClass>extends ${rootClass}</#if>{
	@Delete({
		"delete from ${tableName}",
		"where id = ${r'#'}{${pri.property},jdbcType=${pri.columnTypeName}}"
	})
	int deleteByPrimaryKey(${pri.generateClassName} ${pri.property});
	
	
	@Insert({
        "insert into ${tableName} (<#list tableInfos as info>${info.columnLabel}<#if info_has_next>,</#if></#list>)",
        "values (<#list tableInfos as info>${r'#'}{${info.property},jdbcType=${info.columnTypeName}}<#if info_has_next>,</#if></#list>)"
    })
    int insert(${domainName} ${tableName});
    
    
     @Select({
        "select",
        "<#list tableInfos as info>${info.columnLabel}<#if info_has_next>,</#if></#list>",
        "from ${tableName}"
    })
    @Results({
    	<#list tableInfos as info>${r'@'}Result(column="${info.columnLabel}", property="${info.property}", jdbcType=JdbcType.${info.columnTypeName} <#if info.pri>,id=true</#if>)
    	<#if info_has_next>,</#if></#list>
    })
    List<${domainName}> selectAll();
    
    
     @Select({
        "select",
        "<#list tableInfos as info>${info.columnLabel}<#if info_has_next>,</#if></#list>",
        "from ${tableName} where ${pri.columnLabel}=${r'#'}{${pri.property},jdbcType=${pri.columnTypeName}}"
    })
    @Results({
    	<#list tableInfos as info>${r'@'}Result(column="${info.columnLabel}", property="${info.property}", jdbcType=JdbcType.${info.columnTypeName} <#if info.pri>,id=true</#if>)
    	<#if info_has_next>,</#if></#list>
    })
    ${domainName} selectByPrimaryKey(${pri.generateClassName} ${pri.property});
    
     @Update({  
        "update ${tableName}" , 
        "set <#list tableInfos as info><#if !info.pri>${info.columnLabel} = ${r'#'}{${info.property},jdbcType=${info.columnTypeName}}<#if info_has_next>,</#if></#if></#list>",
        "where id = ${r'#'}{${pri.columnLabel},jdbcType=${pri.columnTypeName}}" 
    })
    int updateByPrimaryKey(${domainName} ${tableName});
     
     @SelectProvider(method = "selectInActive" , type=${sqlProvider}.class)
     @Results({
     	<#list tableInfos as info>${r'@'}Result(column="${info.columnLabel}", property="${info.property}", jdbcType=JdbcType.${info.columnTypeName} <#if info.pri>,id=true</#if>)
     	<#if info_has_next>,</#if></#list>
     })
     List<${domainName}> selectInActive(${domainName} obj);
}