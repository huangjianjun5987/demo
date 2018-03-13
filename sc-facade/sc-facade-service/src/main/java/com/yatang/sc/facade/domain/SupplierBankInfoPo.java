package com.yatang.sc.facade.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 供应商银行信息.
 * @作者: huangjianjun
 * @创建时间: 2017年5月13日-上午10:59:57 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class SupplierBankInfoPo implements Serializable {

	private static final long	serialVersionUID	= -7550039390911841722L;

	/**
	 *供应商主表id
	 * */
	private String				spId;

	/**
	 *供应商银行信息表id
	 * */
	private Integer				id;

	/**
	 *开户名
	 * */
	private String				accountName;

	/**
	 *开户行
	 * */
	private String				openBank;

	/**
	 *银行账号
	 * */
	private String				bankAccount;

	/**
	 *银行开户许可证电子版url
	 * */
	private String				bankAccountLicense;

	/**
	 *省份code
	 * */
	private String				bankLocProvinceCode;

	/**
	 *省份
	 * */
	private String				bankLocProvince;

	/**
	 *城市code
	 * */
	private String				bankLocCityCode;

	/**
	 *城市
	 * */
	private String				bankLocCity;

	/**
	 *地区code
	 * */
	private String				bankLocCountyCode;

	/**
	 *地区
	 * */
	private String				bankLocCounty;

	/**
	 * 供应商发票抬头
	 * */
	private String				invoiceHead;

	/**
	 *修改后数据的id
	 * */
	private Integer				modifyId;

	/**
	 *状态 0：待审批, 1:审核失败
	 * */
	private Integer				status;


	private SupplierBankInfoPo	modification;



	public void setAccountName(String accountName) {
		this.accountName = accountName == null ? null : accountName.trim();
	}



	public void setOpenBank(String openBank) {
		this.openBank = openBank == null ? null : openBank.trim();
	}



	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount == null ? null : bankAccount.trim();
	}



	public void setBankAccountLicense(String bankAccountLicense) {
		this.bankAccountLicense = bankAccountLicense == null ? null : bankAccountLicense.trim();
	}



	public void setBankLocProvince(String bankLocProvince) {
		this.bankLocProvince = bankLocProvince == null ? null : bankLocProvince.trim();
	}



	public void setBankLocCity(String bankLocCity) {
		this.bankLocCity = bankLocCity == null ? null : bankLocCity.trim();
	}



	public void setBankLocCounty(String bankLocCounty) {
		this.bankLocCounty = bankLocCounty == null ? null : bankLocCounty.trim();
	}


}