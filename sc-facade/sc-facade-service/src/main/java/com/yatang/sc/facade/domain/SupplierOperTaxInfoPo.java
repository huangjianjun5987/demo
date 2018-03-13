package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 供应商经营及税务信息.
 * @作者: huangjianjun
 * @创建时间: 2017年5月13日-上午10:59:57 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class SupplierOperTaxInfoPo implements Serializable {

	private static final long		serialVersionUID	= 4890084941210033149L;

	private String					spId;										// 供应商主表ID

	private Integer					id;

	private String					companyLocProvince;

	private String					companyLocCity;

	private String					companyLocCounty;

	private String					companyLocProvinceCode;

	private String					companyLocCityCode;

	private String					companyLocCountyCode;

	private String					companyDetailAddress;

	/**
	 * 商标注册证/受理通知书
	 * */
	private String					registrationCertificate;

	/**
	 * 商标注册证/受理通知书到期日
	 * */
	private Date					regCerExpiringDate;

	/**
	 * 食品安全认证
	 * */
	private String					qualityIdentification;

	/**
	 * 食品安全认证到期日
	 * */
	private Date					quaIdeExpiringDate;

	/**
	 * 食品经营许可证
	 */
	private String 					foodBusinessLicense;

	/**
	 * 食品经营许可证到期日期
	 */
	private Date					businessLicenseExpiringDate;

	/**
	 * 一般纳税人资格证电子版
	 * */
	private String					generalTaxpayerQualifiCerti;

	/**
	 * 一般纳税人资格证到期日
	 * */
	private Date					taxpayerCertExpiringDate;

	private Integer					modifyId;

	private Integer					status;

	private SupplierOperTaxInfoPo	modification;



	public void setCompanyLocProvince(String companyLocProvince) {
		this.companyLocProvince = companyLocProvince == null ? null : companyLocProvince.trim();
	}



	public void setCompanyLocCity(String companyLocCity) {
		this.companyLocCity = companyLocCity == null ? null : companyLocCity.trim();
	}



	public void setCompanyLocCounty(String companyLocCounty) {
		this.companyLocCounty = companyLocCounty == null ? null : companyLocCounty.trim();
	}



	public void setCompanyDetailAddress(String companyDetailAddress) {
		this.companyDetailAddress = companyDetailAddress == null ? null : companyDetailAddress.trim();
	}



	public void setRegistrationCertificate(String registrationCertificate) {
		this.registrationCertificate = registrationCertificate == null ? null : registrationCertificate.trim();
	}



	public void setQualityIdentification(String qualityIdentification) {
		this.qualityIdentification = qualityIdentification == null ? null : qualityIdentification.trim();
	}



	public void setGeneralTaxpayerQualifiCerti(String generalTaxpayerQualifiCerti) {
		this.generalTaxpayerQualifiCerti = generalTaxpayerQualifiCerti == null ? null : generalTaxpayerQualifiCerti
				.trim();
	}

}