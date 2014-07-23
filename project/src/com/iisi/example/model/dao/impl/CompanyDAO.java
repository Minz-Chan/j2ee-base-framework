package com.iisi.example.model.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iisi.dao.impl.BaseDAO;
import com.iisi.example.model.test2.Company;

@Repository
@Transactional
public class CompanyDAO extends BaseDAO<Company, String>  {
	@Resource(name = "hibernateTemplate2")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		super.setHibernateTemplate(hibernateTemplate);
	}
}
