package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 供应商营业执照(副本)信息.
 * @作者: huangjianjun
 * @创建时间: 2017年5月13日-上午10:59:57 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class SupplierlicenseInfoPo implements Serializable {

	private static final long		serialVersionUID	= -1638862775186861996L;

	private Integer					id;

	private String					companyName;

	private String					registLicenceNumber;

	private String					legalRepresentative;

	private String					legalRepreCardNum;

	private String					legalRepreCardPic1;

	private String					legalRepreCardPic2;

	private String					licenseLocProvince;

	private String					licenseLocCity;

	private String					licenseLocCounty;

	private String					licenseLocProvinceCode;

	private String					licenseLocCityCode;

	private String					licenseLocCountyCode;

	private String					licenseAddress;

	private Date					establishDate;

	private Date					startDate;

	private Date					endDate;

	/**
	 * 永续经营(0:否,1:是)
	 * */
	private Integer					perpetualManagement;

	private BigDecimal				registeredCapital;

	private String					businessScope;

	/**
	 * 营业执照副本电子版
	 * */
	private String					registLicencePic;

	/**
	 * 供应商质保金收取金额（保证金）
	 * */
	private BigDecimal				guaranteeMoney;

	private Integer					modifyId;

	/**
	 * 0：待审批, 1:审核失败, 2:审核通过
	 * */
	private Integer					status;

	private SupplierlicenseInfoPo	modification;

	private String					spId;											// 供应商主表id



	public void setCompanyName(String companyName) {
		this.companyName = companyName == null ? null : companyName.trim();
	}



	public void setRegistLicenceNumber(String registLicenceNumber) {
		this.registLicenceNumber = registLicenceNumber == null ? null : registLicenceNumber.trim();
	}



	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative == null ? null : legalRepresentative.trim();
	}



	public void setLegalRepreCardNum(String legalRepreCardNum) {
		this.legalRepreCardNum = legalRepreCardNum == null ? null : legalRepreCardNum.trim();
	}



	public void setLegalRepreCardPic1(String legalRepreCardPic1) {
		this.legalRepreCardPic1 = legalRepreCardPic1 == null ? null : legalRepreCardPic1.trim();
	}



	public void setLegalRepreCardPic2(String legalRepreCardPic2) {
		this.legalRepreCardPic2 = legalRepreCardPic2 == null ? null : legalRepreCardPic2.trim();
	}



	public void setLicenseLocProvince(String licenseLocProvince) {
		this.licenseLocProvince = licenseLocProvince == null ? null : licenseLocProvince.trim();
	}



	public void setLicenseLocCity(String licenseLocCity) {
		this.licenseLocCity = licenseLocCity == null ? null : licenseLocCity.trim();
	}



	public void setLicenseLocCounty(String licenseLocCounty) {
		this.licenseLocCounty = licenseLocCounty == null ? null : licenseLocCounty.trim();
	}



	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope == null ? null : businessScope.trim();
	}



	public void setRegistLicencePic(String registLicencePic) {
		this.registLicencePic = registLicencePic == null ? null : registLicencePic.trim();
	}

}