package com.iisi.example.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.iisi.example.service.LoginService;
import com.opensymphony.xwork2.ActionSupport;

@Results({@Result(name = "success", location = "/admin/admin_main.jsp"),
		@Result(name = "failure", location = "/login.jsp")
})
@Namespace("/security")
public class LoginAction extends ActionSupport implements ServletRequestAware  {

	private String userName;
	private String password;
	
	private HttpServletRequest request;
	
	@Autowired
	private LoginService loginService;
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Action(value = "login")
	public String login() throws Exception {
		
		if(loginService.allowLogin(userName, password)){
			return "success";
		}
		
		return "failure";
	}
	
}
