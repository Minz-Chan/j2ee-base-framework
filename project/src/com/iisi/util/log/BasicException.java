/**
 * 
 */
package com.iisi.util.log;

/**
 * @author 陈明珍
 *
 */
public class BasicException extends RuntimeException {
	private Throwable cause;

	public BasicException() {
		super();
		this.cause = null;
	}

	public BasicException(String message, Throwable cause) {
		super(message);
		this.cause = cause;
	}

	public BasicException(String message) {
		super(message);
		this.cause = null;
	}
	
	public BasicException(Throwable cause) {
		this.cause = cause;
	}
	
	public Throwable getCause(){
		return cause;
	}

	

	
	
}
