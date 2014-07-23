package com.iisi.example.model.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iisi.dao.impl.BaseDAO;
import com.iisi.example.model.test1.Users;


@Repository
@Transactional
public class UsersDAO extends BaseDAO<Users, String> {
	@Resource(name = "hibernateTemplate1")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		super.setHibernateTemplate(hibernateTemplate);
	}
}
