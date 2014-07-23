/**
 * 
 */
package com.iisi.util.log;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.apache.commons.logging.*;
import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import com.opensymphony.xwork2.ActionContext;
import com.iisi.util.FrameworkSetting;

/**
 * @author 陳明珍
 */
@Aspect
@Component
public class LogInterceptor {

	private static Log log = LogFactory.getLog(LogInterceptor.class);

	@Pointcut(FrameworkSetting.SPRINGHANDLER_MATCHER)
	public void springHandlerCall() {
	}

	@Pointcut(FrameworkSetting.DAO_LAYER_MATCHER)
	public void daoCall() {
	}

	@Pointcut(FrameworkSetting.SERVICE_LAYER_MATCHER)
	public void serviceCall() {
	}

	@Pointcut(FrameworkSetting.ACTION_LAYER_MATCHER)
	public void actionCall() {
	}

	/** 对SpringHandler抛出的异常进行捕获并抛出 */
	@AfterThrowing(pointcut = "springHandlerCall()", throwing = "ex")
	public void springHandlerInterceptor(Throwable ex) {
		if (ex instanceof BasicException)
			throw (BasicException) ex;
		else if (ex instanceof DataAccessException)
			throw (DataAccessException) ex;
		else if (ex instanceof HibernateException)
			throw (HibernateException) ex;
		else if (ex instanceof SQLException)
			throw new BasicException(ex);
		else
			throw new BasicException(ex);
	}

	/** 向DAO层织入"异常捕获及抛出" */
	@Around(value = "daoCall()")
	public Object daoInterceptor(ProceedingJoinPoint pj) throws Throwable {
		Object object = null;
		try {
			object = pj.proceed();
		} catch (BasicException e) {
			throw e;
		} catch (DataAccessException e) {
			throw e;
		} catch (HibernateException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}

		return object;
	}

	/** 向Service层织入"异常捕获及抛出, 并记录日志 " */
	@Around(value = "serviceCall() && @annotation(buss)")
	public Object serviceInterceptor(ProceedingJoinPoint pj, BusinessLog buss)
			throws Throwable {
		Object object = null;
		try {
			object = pj.proceed();
		} catch (BasicException e) {
			log.error("***error: ", e.getCause());
			if (buss != null) {
				/** 抛出重新包装的Exception */
				throw new BusinessException(buss.exMsg(), e);
			} else
				throw e;
		} catch (Exception e) {
			log.error("***error: ", e);
			if (buss != null) {
				/** 抛出重新包装的Exception */
				throw new BusinessException(buss.exMsg(), e);
			} else
				throw e;
		}

		return object;
	}

	/** 向ACTION层织入"异常捕获及处理" */
	@Around(value = "actionCall()")
	public Object actionInterceptor(ProceedingJoinPoint pj) throws Throwable {
		Object object = null;
		String msg = null;
		try {
			object = pj.proceed();
		} catch (BusinessException e) {
			HttpServletRequest request = getRequest();
			HttpServletResponse response = getResponse();
			msg = e.getMessage();
			request.setAttribute("message", msg);
			request.getRequestDispatcher(FrameworkSetting.ERROR_PAGE_PATH)
					.forward(request, response);
		} catch (Exception e) {
			log.error("***error: ", e);
			HttpServletRequest request = getRequest();
			HttpServletResponse response = getResponse();
			msg = e.getMessage();
			request.setAttribute("message", FrameworkSetting.ACTION_LAYER_EXCEPTION_MSG);
			request.getRequestDispatcher(FrameworkSetting.ERROR_PAGE_PATH)
					.forward(request, response);
		}

		return object;
	}

	/*
	 * @Before("springHandlerCall()") public void tese(JoinPoint jp) {
	 * 
	 * log.error("module_name: " ); }
	 */

	@Before("serviceCall() && @annotation(buss)")
	public void beforeMethodCalled(JoinPoint jp, BusinessLog buss) {

		log.error("module_name: " + buss.moduleName() + ", desc: "
				+ buss.desc());

		String methodName = jp.getSignature().getName();
		log.error("method_call_begin: " + jp.getTarget().getClass().getName()
				+ ", " + methodName);
		Object[] args = jp.getArgs();
		for (int i = 0; i < args.length; i++) {
			log.error("params[" + i + "]:" + args[i].toString());
		}
	}

	@After("serviceCall() && @annotation(buss)")
	public void afterMethodCalled(JoinPoint jp, BusinessLog buss) {
		String methodName = jp.getSignature().getName();
		log.error("method_call_end: " + jp.getTarget().getClass().getName()
				+ ", " + methodName);
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) ActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
	}

	private HttpServletResponse getResponse() {
		return (HttpServletResponse) ActionContext.getContext().get(
				ServletActionContext.HTTP_RESPONSE);
	}

}
