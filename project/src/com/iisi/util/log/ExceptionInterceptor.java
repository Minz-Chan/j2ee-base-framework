/**
 * 
 */
package com.iisi.util.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author 陈明珍
 * 
 */

public class ExceptionInterceptor extends AbstractInterceptor {


	private static Log log = LogFactory.getLog(ExceptionInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String actionName = invocation.getInvocationContext().getName();
		try {
			String result = invocation.invoke();
			return result;
		} catch(BusinessException eBuss){
			log.error(actionName, eBuss);
			return "exception";
		}catch (Exception e) {
			log.error(actionName, e);
			return "exception";
		}
	}
}


