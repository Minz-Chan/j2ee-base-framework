package com.iisi.example.model.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iisi.dao.impl.BaseDAO;
import com.iisi.example.model.test1.Customer;

@Repository
@Transactional
public class CustomerDAO extends BaseDAO<Customer, Integer> {
	@Resource(name = "hibernateTemplate1")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		super.setHibernateTemplate(hibernateTemplate);
	}
	
	public Customer findByCustomerName(String customerName){
		return find("from Customer as c Where c.customerName ='"
				+ customerName.trim() + "'");
	}
	
	public List<Customer> findAllCustomers(){
		return  findList("from Customer");
	}
	
}
