package com.yatang.sc.payment.gateway.web.common;

/**
 * @author huangjj  2016年3月22日
 */
public class ApiResponse {

	public static final String SUCCESS_CODE = "0000";
	public static final String FAILURE_CODE = "9999";

	public static final String SUCCESS_MSG = "操作成功";
	public static final String FAILURE_MSG = "操作失败";

	private String code;
	private String msg;
	private Object data;

	public ApiResponse() {

	}

	public ApiResponse(boolean success) {
		this.code = success ? SUCCESS_CODE : FAILURE_CODE;
		this.msg = success ?  SUCCESS_MSG : FAILURE_MSG;
	}
	
	public ApiResponse(boolean success, String msgage) {
		this.code = success ? SUCCESS_CODE : FAILURE_CODE;
		this.msg = msgage;
	}

	public ApiResponse(Object data) {
		this(true, SUCCESS_MSG, data);
	}

	public ApiResponse(boolean success, String msg, Object data) {
		this.code = success ? SUCCESS_CODE : FAILURE_CODE;
		this.msg = msg;
		this.data = data;
	}
	
	public ApiResponse(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public ApiResponse(String code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
