package com.yatang.sc.order.domain;

public class CompanyCancelNoPayPo {

	private int id;
	/**
	 * 公司Id
	 */
	private String companyId;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 取消时间
	 */
	private int cancelTime;
	/**
	 * 规则的状态(1:生效,0:失效)
	 */
	private int state;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(int cancelTime) {
		this.cancelTime = cancelTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
