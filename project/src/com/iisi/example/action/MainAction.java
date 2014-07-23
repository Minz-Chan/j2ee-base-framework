package com.iisi.example.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.iisi.example.model.test1.Customer;
import com.iisi.example.service.CustomerService;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/admin")
public class MainAction  extends ActionSupport implements ServletRequestAware  {

	private String userAction;
	private String userName;
	
	private HttpServletRequest request;
	
	@Autowired
	private CustomerService customerService;
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getUserAction() {
		return userAction;
	}

	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Action(value = "showCustomerList", results = { @Result(name = "success", location = "/admin/admin_main.jsp") })
	public String getCustomerList() throws Exception{
		
		List<Customer> customerList = customerService.getAllCustomerDetail();
		
		request.setAttribute("customerList", customerList);
		
		return  "success";
	}
	
	@Action(value = "exceptionTest", results = { @Result(name = "success", location = "/admin/admin_main.jsp") })
	public String exceptionTest() throws Exception{
		customerService.exceptionCall();
		return  "success";
	}
	
	@Action(value = "exceptionTest1", results = { @Result(name = "success", location = "/admin/admin_main.jsp") })
	public String exceptionTest1() throws Exception{
		int a = 0;
		int b = 1;
		int c = 2;
		
		/** make an exception */
		c = b / a;
		
		return  "success";
	}
	
}
