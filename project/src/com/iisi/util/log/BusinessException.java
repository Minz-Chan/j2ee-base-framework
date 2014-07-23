/**
 * 
 */
package com.iisi.util.log;

/**
 * @author 陈明珍
 *
 */


public class BusinessException extends RuntimeException{   
	    
	public BusinessException(String msg, Throwable ex){      
		super(msg, ex);
	}   
	    
	public BusinessException(String msg){   
		super(msg);   
	}   
	    
	public BusinessException(Throwable ex){   
		super(ex);  
	}   
}   