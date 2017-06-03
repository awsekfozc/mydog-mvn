package ${package};

import java.util.List;
import ${modelPackage}.${modelName};
<#if '' != rootClass>import ${root};</#if>
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ${mapppingPackage}.${mapppingName}; 

@Service
public class ${thisClassName} <#if '' != rootClass>extends ${rootClass}</#if>{
	
	@Autowired
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