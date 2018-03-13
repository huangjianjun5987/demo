package com.yatang.sc.common.staticvalue;

public enum DefaultNoPayEnum {

	/**
	 * 取消时长（单位：小时）
	 */
	CANCEL_HOUR("默认最长未支付取消时间",24),

	/**
	 * 取消时长（单位：分钟）
	 */
	CANCEL_MINUTE("默认最长未支付取消时间",1440);

	private String msg;
	private int time;
	DefaultNoPayEnum(String msg,int time) {
		this.msg = msg;
		this.time = time;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}
