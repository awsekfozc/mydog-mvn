package hp.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hp.edu.controller.view.DataTableResult;
import hp.edu.orm.domain.Users;
import ${servicePackage}.${serviceName};

@RequestMapping("${url_head!}/${domainName}")
@RestController
public class UsersController {

	@Autowired
	protected ${serviceName} service;
	
	@RequestMapping("list")
	public DataTableResult list(Users users,DataTableResult result) {
		service.selectInActive(users , result );  
		return result;
	}
}
