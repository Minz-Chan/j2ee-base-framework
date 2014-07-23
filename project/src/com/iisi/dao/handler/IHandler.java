package com.iisi.dao.handler;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;


/**
 * @author 陈明珍
 * @className: IHandler.java
 * @classDescription: 数据库处理接口
 * @createTime: December 29, 2011
 */
public interface IHandler<ID extends Serializable> {

	/**
	 * 保存对象
	 * 
	 * @param obj
	 * @return
	 */
	public boolean saveObj(Object obj);

	/**
	 * 删除对象
	 * 
	 * @param obj
	 * @return
	 * @throws Exception 
	 */
	public boolean deleteObj(Object obj) throws Exception; 

	/**
	 * 修改对象
	 * 
	 * @param obj
	 * @return
	 */
	
	public boolean alterObj(Object obj);

	/**
	 * 查找对象
	 * 
	 * @param hql
	 *            hql语句
	 * @return 对象
	 */
	public Object findObj(String hql);

	/**
	 * 根据id查找对象
	 * 
	 * @param className
	 *            类型名
	 * @param id
	 *            对象id
	 * @return 对象
	 */
	
	public Object findObjById(Class className, ID id);
	/**
	 * 根据id查询延时对象
	 * 
	 * @param className
	 *            类名
	 * @param id
	 *            类id
	 * @return 对象
	 */
	
	public Object findObjByIdLoad(Class className, ID id);

	/**
	 * 根据hql查找集合
	 * 
	 * @param hql
	 *            hql语句
	 * @return 返回集合
	 */
	public List findListOfObj(String hql);
	
	/**
	 * 查询对象(防止依赖注入)
	 * 
	 * @param hql
	 *            查找语句
	 * @param map 参数       
	 * @return 对象
	 */
	
	public Object findObj(final String hql,final Map<Serializable, Serializable> map);

	/**
	 * 主要用于防止sql依赖注入
	 * 
	 * @param hql
	 *            hql语句（例如" from UserInfo where id=:id and
	 *            password=:password")query.setParameter("id",userForm.getId())
	 * @param map
	 * @return List集合
	 */
	public List findListOfObj(String hql, Map<Serializable, Serializable> map);

	/**
	 * 根据hql，集合索引，显示条数 查找集合（主要用于分页）
	 * 
	 * @param hql
	 *            hql查询语句
	 * @param page
	 *            当前页数
	 * @param count
	 *            取几条数据
	 * @return 集合对象
	 */
	public List findListOfObj(String hql, int page, int count);

	/**
	 * 根据hql查找集合的条数
	 * 
	 * @param hql
	 *            hql语句
	 * @return 返回查询总数
	 */
	public int findCountBySql(String hql);

	/**
	 * sql语句操作数据库
	 * 
	 * @param sql
	 * @return boolean
	 */
	public boolean executeSql(String sql);
	
	/**
	 * sql语句查询集合
	 * 
	 * @param sql
	 * @return boolean
	 */
	public List findListBySql(final String sql,Class className);
	

}
