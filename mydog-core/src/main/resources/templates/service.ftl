package ${package};

import java.util.List;
import ${modelPackage}.${modelName};
<#if '' != rootClass>import ${root};</#if>
import ${mapppingPackage}.${mapppingName}; 

public class ${thisClassName} <#if '' != rootClass>extends ${rootClass}</#if>{
	
	private ${mapppingName} mapping;
	
	public int deleteByPrimaryKey(${pri.javaType} ${pri.property}){
		return mapping.deleteByPrimaryKey(${pri.property});
	}
	
   public int insert(${modelName} ${tableName}){
    	return mapping.insert( ${tableName} );
    }
    
    public List<${modelName}> selectAll(){
   	 return mapping.selectAll() ;
    }
    
    public ${modelName} selectByPrimaryKey(${pri.javaType} ${pri.property}){
    	return mapping.selectByPrimaryKey( ${pri.property} ) ;
    }
    
    public int updateByPrimaryKey(${modelName} ${tableName}){
    	return mapping.updateByPrimaryKey( ${tableName} ) ; 
    }
}