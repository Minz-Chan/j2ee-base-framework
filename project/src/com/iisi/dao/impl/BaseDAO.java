package com.iisi.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.iisi.dao.IBaseDAO;
import com.iisi.dao.handler.IHandler;
import com.iisi.dao.handler.impl.SpringHandler;




/**
 * @author 陈明珍
 * @clssName: BaseDAO.java
 * @classDecription: 公共DAO，实现对数据库的增删改查
 * @createTime: December 29, 2011
 */
public class BaseDAO<T, ID extends Serializable> implements IBaseDAO<T, ID>
{
   
    private SpringHandler<ID> handler; 
    
    private Class<T> clazz;
    @SuppressWarnings("unchecked")
	public BaseDAO() {
         /*clazz =(Class<T>) ((ParameterizedType) getClass()
                                    .getGenericSuperclass()).getActualTypeArguments()[0];*/
         
         Type genType = getClass().getGenericSuperclass();
         if (!(genType instanceof ParameterizedType)){
        	 clazz = (Class<T>) Object.class;
        	 return;
         }
         
         Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
         if (!(params[0] instanceof Class)) {  
        	 clazz = (Class<T>) Object.class;
        	 return;
         }
         clazz =  (Class<T>) params[0];
         
         handler = new SpringHandler<ID>();
      }
    /**
     * 保存对象
     * 
     * @param obj
     *            要保存的对象
     * @return 布尔值
     */
    public boolean save(T obj) {
        
        return handler.saveObj(obj);

    }

    /**
     * 删除对象
     * 
     * @param obj
     *            要删除的对象
     * @return 布尔值
     */
    public boolean delete(T obj) {
        return handler.deleteObj(obj);
    }
    /**
     * 根据Id删除对象
     * 
     * @param obj
     *            要删除的对象
     * @return 布尔值
     */
    public boolean deleteById(ID id) {
        return handler.deleteObj(handler.findObjById(clazz, id));
    }
    /**
     * 修改对象
     * 
     * @param obj
     *            要修改的对象
     * @return 布尔值
     */
    public boolean alter(T obj) {

        return handler.alterObj(obj);

    }

    /**
     * 查询对象
     * 
     * @param hql
     *            查找语句
     * @return 对象
     */
    public T find(final String hql) {
        return (T) handler.findObj(hql);        
    }
    /**
     * 查询对象(防止依赖注入)
     * 
     * @param hql
     *            查找语句
     * @param map 参数           
     * @return 对象
     */
    public T findOfMap(final String hql,Map<Serializable,Serializable> map) {
        return (T) handler.findObj(hql,map);        
    }
    /**
     * 主要用于防止sql依赖注入
     * 
     * @param hql
     *            hql语句
     * @param map
     * @return List集合
     */
    public List<T> findListOfMap(final String hql,
            final Map<Serializable, Serializable> map) {
        return handler.findListOfObj(hql, map);
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
    public T findById(ID id) {

        return (T) handler.findObjById(clazz, id);

    }
    /**
     * 根据id查询缓存对象
     * 
     * @param className
     *            类名
     * @param id
     *            类id
     * @return 对象
     */
    public T findByIdLoad(ID id) {

        return (T) handler.findObjByIdLoad(clazz, id);

    }

    /**
     * 查询集合
     * 
     * @param sql
     *            要保存的对象
     * @return 集合
     */
    public List<T> findList(String hql) {

        return handler.findListOfObj(hql);

    }

    /**
     * 根据页数和条数查询集合
     * 
     * @param sql
     *            要保存的对象
     * @param page
     *            页数
     * @param count
     *            显示条数
     * @return 集合
     */
    public List<T> findList(String hql, int page, int count) {

        return handler.findListOfObj(hql, page, count);

    }

    /**
     * 根据hql查找集合的条数
     * 
     * @param hql
     *            hql语句
     * @return 返回查询总数
     */
    public int findCountBySql(String hql) {
        return handler.findCountBySql(hql);
    }



    /**
     * sql语句操作数据库
     * 
     * @param sql
     * @return boolean
     */
    public boolean executeSql(String sql) {
        return handler.executeSql(sql);
    }
    /**
     * sql语句查询集合
     * 
     * @param sql
     * @return boolean
     */
    public List findListBySql(final String sql,Class className){
        return handler.findListBySql(sql, className);
    }
    
    
    public IHandler<ID> getHandler() {
        return handler;
    }

    
    public void setHandler(SpringHandler<ID> handler) {
        this.handler = handler;
    }
    
    public SessionFactory getSessionFactory(){
    	return handler.getSessionFactory();
    }
    
    public Session getCurrentSession(){
    	return handler.getCurrentSession();
    }   
    
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate)
    {
       handler.setHibernateTemplate(hibernateTemplate);
    }
    
}
