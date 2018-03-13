package com.busi.kidd.behavior;

import java.util.Date;

/**
 * 行为记录信息
 * 
 * @author yangqingsong
 *
 */
public class RecordBean {
	public static final String STATUS_SUCCESS = "success";
	public static final String STATUS_FAILURES = "failure";
	private String interfaceName;
	private String status;
	private Date recordDate;
	private Object request;
	private Object response;
	private String errorMsg;

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Object getRequest() {
		return request;
	}

	public void setRequest(Object request) {
		this.request = request;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
