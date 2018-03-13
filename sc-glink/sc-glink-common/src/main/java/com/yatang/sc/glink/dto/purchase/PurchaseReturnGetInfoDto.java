package com.yatang.sc.glink.dto.purchase;

import java.io.Serializable;

public class PurchaseReturnGetInfoDto implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private String				getCompany;					// 供应商地点的供应商姓名
	private String				getName;					// 供应商地点的联系信息的供应商姓名
	private String				getMobile;					// 供应商地点的联系信息的供应商电话
	private String				getEmail;					// 供应商地点的联系信息的供应商邮箱
	private String				getProvince;				// 供应商证照信息的公司所在省
	private String				getCity;					// 供应商证照信息的公司所在市
	private String				getArea;					// 供应商证照信息的公司所在区
	private String				getDetailAddress;			// 供应商证照信息的公司详细地址



	public String getGetCompany() {
		return getCompany;
	}



	public void setGetCompany(String getCompany) {
		this.getCompany = getCompany;
	}



	public String getGetName() {
		return getName;
	}



	public void setGetName(String getName) {
		this.getName = getName;
	}



	public String getGetMobile() {
		return getMobile;
	}



	public void setGetMobile(String getMobile) {
		this.getMobile = getMobile;
	}



	public String getGetEmail() {
		return getEmail;
	}



	public void setGetEmail(String getEmail) {
		this.getEmail = getEmail;
	}



	public String getGetProvince() {
		return getProvince;
	}



	public void setGetProvince(String getProvince) {
		this.getProvince = getProvince;
	}



	public String getGetCity() {
		return getCity;
	}



	public void setGetCity(String getCity) {
		this.getCity = getCity;
	}



	public String getGetArea() {
		return getArea;
	}



	public void setGetArea(String getArea) {
		this.getArea = getArea;
	}



	public String getGetDetailAddress() {
		return getDetailAddress;
	}



	public void setGetDetailAddress(String getDetailAddress) {
		this.getDetailAddress = getDetailAddress;
	}

}
