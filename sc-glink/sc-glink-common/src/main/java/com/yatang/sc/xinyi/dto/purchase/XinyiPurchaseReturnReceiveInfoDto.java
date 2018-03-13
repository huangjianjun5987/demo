package com.yatang.sc.xinyi.dto.purchase;

import com.thoughtworks.xstream.annotations.XStreamAliasType;

import java.io.Serializable;

/**
 * @description: 采购退货单 心怡入参dto (收件人信息)
 * @author: yinyuxin
 * @date: 2017/11/9 19:13
 * @version: v1.0
 */
@XStreamAliasType("receiverInfo")
public class XinyiPurchaseReturnReceiveInfoDto implements Serializable{

	private static final long serialVersionUID = -8158956254412980552L;

	/**
	 * 供应商地点的供应商姓名
	 */
	private String company;

	/**
	 * 供应商地点的联系信息的供应商姓名
	 */
	private String name;

	/**
	 * 供应商地点的联系信息的供应商电话
	 */
	private String mobile;

	/**
	 * 供应商地点的联系信息的供应商邮箱
	 */
	private String email;

	/**
	 * 供应商证照信息的公司所在省
	 */
	private String province;

	/**
	 * 供应商证照信息的公司所在市
	 */
	private String city;

	/**
	 * 供应商证照信息的公司所在区
	 */
	private String area;

	/**
	 * 供应商证照信息的公司详细地址
	 */
	private String detailAddress;



	public String getCompany() {
		return company;
	}



	public void setCompany(String company) {
		this.company = company;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getMobile() {
		return mobile;
	}



	public void setMobile(String mobile) {
		this.mobile = mobile;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getProvince() {
		return province;
	}



	public void setProvince(String province) {
		this.province = province;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getArea() {
		return area;
	}



	public void setArea(String area) {
		this.area = area;
	}



	public String getDetailAddress() {
		return detailAddress;
	}



	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
}
