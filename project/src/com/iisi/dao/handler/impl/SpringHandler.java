package com.iisi.dao.handler.impl;




import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.*;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;


import com.iisi.dao.handler.IHandler;
import com.iisi.util.log.BasicException;



/**
 * @author 陈明珍
 * @className: SpringHandler.java
 * @classDescription: Spring持久层
 * @createTime: December 29, 2011
 */
public class SpringHandler<ID extends Serializable> implements IHandler<ID> { 
	
    private HibernateTemplate hibernateTemplate;


	public SpringHandler()
	{
	    
	}
	

	/**
	 * 保存对象
	 * 
	 * @param obj
	 *            要保存的对象
	 * @return 布尔值
	 */
	public boolean saveObj(Object obj) {
		boolean result = false;
		try {
			this.getHibernateTemplate().save(obj);
			result = true;
		} catch (DataAccessException e) {
			throw e;
		}
		return result;
	}

	/**
	 * 删除对象
	 * 
	 * @param objQ
	 *            要删除的对象
	 * @return 布尔值
	 * @throws DataAccessException 
	 */
	public boolean deleteObj(Object obj){ 
		boolean result = false;
		try {
			this.getHibernateTemplate().delete(obj);
			result = true;
		} catch (DataAccessException e) {
			throw e;
		}
		
		return result;
	}

	/**
	 * 修改对象
	 * 
	 * @param obj
	 *            要修改的对象
	 * @return 布尔值
	 */
	public boolean alterObj(Object obj) {
		boolean result = false;
		try {
			this.getHibernateTemplate().merge(obj);
			result = true;
		} catch (DataAccessException e) {
			throw e;
		}
		return result;
	}

	/**
	 * 根据id查询对象
	 * 
	 * @param className
	 *            类名
	 * @param id
	 *            类id
	 * @return 对象
	 */
	public Object findObjById(Class className, ID id) {
		if (null == id || "".equals(id)) {
			return null;

		} else {
			return this.getHibernateTemplate().get(className, id);
		}
	}
	/**
	 * 根据id查询延时对象
	 * 
	 * @param className
	 *            类名
	 * @param id
	 *            类id
	 * @return 对象
	 */
	public Object findObjByIdLoad(Class className, ID id) {
		if (null == id || "".equals(id)) {
			return null;
		} else {
			return this.getHibernateTemplate().load(className, id);
		}
	}

	/**
	 * 查询对象
	 * 
	 * @param hql
	 *            查找语句
	 * @return 对象
	 */
	public Object findObj(final String hql) {
		if (null == hql || "".equals(hql)) {
			return null;
		}
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(0);
				query.setMaxResults(1);
				Object obj = query.uniqueResult();
				return obj;
			}
		});
	}

	/**
	 * 查询对象(防止依赖注入)
	 * 
	 * @param hql
	 *            查找语句
	 * @param map
	 *            参数
	 * @return 对象
	 */
	public Object findObj(final String hql,
			final Map<Serializable, Serializable> map) {
		if (null == hql || "".equals(hql)) {
			return null;
		}
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				for (Serializable key : map.keySet()) {
					query.setParameter((String) key, map.get(key));
				}
				query.setFirstResult(0);
				query.setMaxResults(1);
				Object obj = query.uniqueResult();
				return obj;
			}
		});
	}

	/**
	 * 查询集合
	 * 
	 * @param sql
	 *            查询语句
	 * @return 集合
	 */
	public List findListOfObj(final String hql) {
		if (null == hql || "".equals(hql)) {
			return null;
		}
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}

	/**
	 * 主要用于防止sql依赖注入
	 * 
	 * @param hql
	 *            hql语句（例如" from UserInfo where id=:id and
	 *            password=:password")query.setParameter("id",userForm.getId())
	 * @param map
	 * @return List集合
	 */
	public List findListOfObj(final String hql,
			final Map<Serializable, Serializable> map) {
		if (null == hql || "".equals(hql)) {
			return null;
		}
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				for (Serializable key : map.keySet()) {
					query.setParameter((String) key, map.get(key));
				}
				List list = query.list();
				if (list.size() == 0) {
					return null;
				} else {
					return list;
				}
			}
		});
	}

	/**
	 * 获取记录的总条数
	 * 
	 * @param sql
	 *            要保存的对象
	 * @return 返回个数
	 */
	public int findCountBySql( String hql) {
		final String sql="select count(*) " + hql;
		 HibernateCallback cb = new HibernateCallback() {
		     public Object doInHibernate(Session session)
		         throws HibernateException {
		         return Integer.parseInt(session.createQuery(sql).list().get(0).toString());

		     } // end function
		 };// end callback
		 return   (Integer) this.getHibernateTemplate().execute(cb);
	}

	/**
	 * 根据查询语句以及输入页数，以及显示条数查询对象（分页）
	 * 
	 * @param hql
	 *            hql语句
	 * @param page
	 *            显示的页数
	 * @param count
	 *            显示的条数
	 * @return 集合对象
	 */
	public List findListOfObj(final String hql, final int page, final int count) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				if (null == hql || "".equals(hql)) {
					return null;
				}
				Query query = session.createQuery(hql);
				int index = 0;
				int tempPageCount = 0;
				// 如果page为负数,将page设置为1
				if (page < 1) {
					tempPageCount = 1;
				} else {
					tempPageCount = page;
				}
				// 如果为第一页,索引为0
				if (tempPageCount == 1) {
					// 但前页数索引
					index = count;
				} else {
					index = tempPageCount * count;
				}
				// 设置取出的第一条索引
				query.setFirstResult(index - count);
				// 设置取出得最大的索引
				query.setMaxResults(count);
				return query.list();
			}
		});
	}
	
	// -------------------sql语句-----------------------//
	/**
	 * 根据sql查询语句返回ResultSet对象
	 * 
	 * @param sql
	 *            sql查询语句，
	 * @return ResultSet
	 */
	public ResultSet findResultBySql(String sql) {
		// 记录集
		ResultSet result = null;
		// 获取会话
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();

		// 查询多个持久对象
		try {
			result = session.connection().createStatement().executeQuery(sql);
		} catch (HibernateException e) {
			throw e;
		} catch (SQLException e) {
			throw new BasicException(e);
		}

		// 注意关闭链接
		return result;
	}

	/**
	 * sql语句操作数据库
	 * 
	 * @param sql
	 * @return boolean
	 */
	public boolean executeSql(final String sql) {
		boolean result = true;
		try {
			HibernateCallback cb = new HibernateCallback() {
				public Object doInHibernate(Session session) {
					return session.createSQLQuery(sql).executeUpdate();
				} // end function
			};// end callback

			this.getHibernateTemplate().execute(cb);
		} catch (Exception e) {
			result = false;
			throw new BasicException(e);
		}

		return result;
	}

	/**
	 * sql语句查询集合
	 * 
	 * @param sql
	 * @return boolean
	 */
	public List findListBySql(final String sql,Class className){
		HibernateCallback cb = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				return session.createSQLQuery(sql).list();
			} // end function
		};// end callback

		List list=(List) this.getHibernateTemplate().execute(cb);	
		
		return list;
	}

    /**
     * @return the hibernateTemplate
     */
    public HibernateTemplate getHibernateTemplate()
    {
        return hibernateTemplate;
    }

    /**
     * @param hibernateTemplate the hibernateTemplate to set
     */
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate)
    {
        this.hibernateTemplate = hibernateTemplate;
    }
	
	public SessionFactory getSessionFactory(){
		return this.hibernateTemplate.getSessionFactory();
	}


	public Session getCurrentSession() {
		return this.getSessionFactory().getCurrentSession();
	}

}

