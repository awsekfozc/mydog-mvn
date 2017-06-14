package hp.edu.controller;


import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hp.edu.controller.view.ApiResult;
import hp.edu.controller.view.DataTableResult;
import ${domainPackage}.${domainName};
import ${servicePackage}.${serviceName};

@RequestMapping("${url_head!}/${domainName}")
@RestController
public class ${thisClassName} {

	Logger log = Logger.getLogger("${thisClassName}") ;
	
	@Autowired
	protected ${serviceName} service;
	
	@RequestMapping("list")
	public DataTableResult list(${domainName} obj,DataTableResult result) {
		service.selectInActive(obj , result );  
		return result;
	}
	
	
	@RequestMapping("get")
	public ApiResult get(${pri.generateClassName} ${pri.property}){
		try {
			return ApiResult.prepare().ok( service.selectByPrimaryKey( ${pri.property} ) );
		} catch (Exception e) {
			log.error(" delete ", e);
			return ApiResult.prepare().error(100, "删除失败:" + e.getMessage());
		}
	}
	
	@RequestMapping("delete")
	public ApiResult del(${pri.generateClassName} ${pri.property}){
		try {
			service.deleteByPrimaryKey( ${pri.property} ) ;
			return ApiResult.prepare().ok("删除成功");
		} catch (Exception e) {
			log.error(" delete ", e);
			return ApiResult.prepare().error(100, "删除失败:" + e.getMessage());
		}
	}
	
	@RequestMapping("save")
	public ApiResult save(${domainName} obj) {
		try {
			if (StringUtils.isEmpty(obj.${pri.readMethod}())) {
				obj.${pri.writeMethod}(UUID.randomUUID().toString().replaceAll("-", ""));
				service.insert(obj);
			} else {
				service.updateByPrimaryKey(obj);
			}
			return ApiResult.prepare().ok("保存成功");
		} catch (Exception e) {
			log.error(" save ", e);
			return ApiResult.prepare().error(100, "保存失败:" + e.getMessage());
		}
	}
}
