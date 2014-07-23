package com.iisi.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iisi.example.model.dao.impl.CustomerDAO;
import com.iisi.example.model.test1.Customer;
import com.iisi.example.service.CustomerService;
import com.iisi.util.log.BusinessLog;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDAO customerDAO;
	
	
	@BusinessLog(moduleName = "管理模块", desc = "获取客户资料", exMsg = "获取客户资料失败")
	public Customer getCutomerDetailByName(String customerName) {
		return customerDAO.findByCustomerName(customerName);
	}

	@BusinessLog(moduleName = "管理模块", desc = "获取所有客户资料", exMsg = "获取所有客户资料失败")
	public List<Customer> getAllCustomerDetail() {
		return customerDAO.findAllCustomers();
	}
	
	@BusinessLog(moduleName = "管理模块", desc = "异常捕获测试", exMsg = "Service层异常捕获测试")
	public void exceptionCall() {
		
		int a = 0;
		int b = 1;
		int c;
		
		/** make an exception */
		c = b / a;	
	}
}
