package com.iisi.util;
/**
 * 
 * @author 陳明珍
 * @CreateDate: 2012-03-07
 */
public class FrameworkSetting {
	
	/** SpringHandler方法匹配字串 */
	public static final  String SPRINGHANDLER_MATCHER = 
		"execution(public * com.iisi.dao.handler.impl.*.*(..))";
	
	/** DAO層方法匹配字串 */
	public static final  String DAO_LAYER_MATCHER = 
		"execution(public * com.iisi..*DAO+.*(..))";
	
	/** SERVICE層方法匹配字串 */
	public static final  String SERVICE_LAYER_MATCHER = 
		"execution(public * com.iisi..*.*ServiceImpl.*(..))";
	
	/** ACTION層方法匹配字串 */
	public static final  String ACTION_LAYER_MATCHER = 
		"execution(public * com.iisi..*Action.*(..))";
	
	/** ACTION层异常提示字串 */
	public static final String ACTION_LAYER_EXCEPTION_MSG =
		"系统异常，请与管理员联系！";
	
	/** 錯誤頁面路徑 */
	public static final  String ERROR_PAGE_PATH = 
		"/pages/error.jsp";
}
