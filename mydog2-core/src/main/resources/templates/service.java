package ${package};

import java.util.List;
import hp.edu.controller.view.DataTableResult;
import hp.edu.orm.domain.Users;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import ${mapppingPackage}.${mapppingName}; 

@Service
public class ${thisClassName} <#if '' != rootClass>extends ${rootClass}</#if>{
	
	@Autowired
	private ${mapppingName} mapping;
	
	public int deleteByPrimaryKey(${pri.generateClassName} ${pri.property}){
		return mapping.deleteByPrimaryKey(${pri.property});
	}
	
   public int insert(${domainName} ${tableName}){
    	return mapping.insert( ${tableName} );
    }
   
   
    public List<${domainName}> selectAll(){
   	 return mapping.selectAll() ;
    }
    
    public ${domainName} selectByPrimaryKey(${pri.generateClassName} ${pri.property}){
    	return mapping.selectByPrimaryKey( ${pri.property} ) ;
    }
    
    public int updateByPrimaryKey(${domainName} ${tableName}){
    	return mapping.updateByPrimaryKey( ${tableName} ) ; 
    }
    
    public DataTableResult selectInActive(${domainName} obj,DataTableResult result){
    	Page<${domainName}> page = PageHelper.startPage(result.getStart(), result.getLength()) ;
    	mapping.selectInActive(obj) ;
    	result.setAaData(page);
		result.setRecordsFiltered(page.getTotal());
		result.setRecordsTotal( page.getTotal() );
    	return result;
    }
}