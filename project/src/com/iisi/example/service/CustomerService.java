package com.iisi.example.service;

import java.util.List;

import com.iisi.example.model.test1.Customer;

public interface CustomerService {
	/**
	 * 通过客户名称查找相应的客户资料
	 * @param customerName
	 * @return
	 */
	Customer getCutomerDetailByName(String customerName);
	
	/**
	 * 查找所有客户资料
	 * @return
	 */
	List<Customer> getAllCustomerDetail();
	
	
	public void exceptionCall();
	
}
