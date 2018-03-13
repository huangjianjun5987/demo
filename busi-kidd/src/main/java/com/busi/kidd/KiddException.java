package com.busi.kidd;

/**
 * 异常类
 * 
 * @author yangqingsong
 *
 */
public class KiddException extends Exception {

	private static final long serialVersionUID = -8537761530047261595L;

	public KiddException(String message, Throwable cause) {
		super(message, cause);
	}

	public KiddException(String message) {
		super(message);
	}

	public KiddException(Throwable cause) {
		super(cause);
	}

}
