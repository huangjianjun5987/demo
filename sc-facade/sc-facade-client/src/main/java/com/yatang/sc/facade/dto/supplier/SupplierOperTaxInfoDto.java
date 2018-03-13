package com.yatang.sc.facade.dto.supplier;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @描述: 供应商经营及税务信息.
 * @作者: huangjianjun
 * @创建时间: 2017年5月13日-上午10:59:57 .
 * @版本: 1.0 .
 * @param <T>
 */
@Getter
@Setter
public class SupplierOperTaxInfoDto implements Serializable {

	private static final long		serialVersionUID	= 4890084941210033149L;

	private String					spId;										// 供应商主表ID

	private Integer					id;										// 编号

	private String					companyLocProvinceCode;					// 省份code

	private String					companyLocCityCode;						// 城市code

	private String					companyLocCountyCode;						// 地区code

	private String					companyLocProvince;						// 省份名

	private String					companyLocCity;							// 城市名

	private String					companyLocCounty;							// 地区名

	private String					companyDetailAddress;						// 公司详细地址

	private String					registrationCertificate;					// 商标注册证/受理通知书

	private Date					regCerExpiringDate;						// 商标注册证/受理通知书到期日

	private String					qualityIdentification;						// 食品安全认证

	private Date					quaIdeExpiringDate;						// 食品安全认证到期日

	private String 					foodBusinessLicense;						//食品经营许可证

	private Date					businessLicenseExpiringDate;				//食品经营许可证到期日期

	private String					generalTaxpayerQualifiCerti;				// 一般纳税人资格证电子版

	private Date					taxpayerCertExpiringDate;					// 一般纳税人资格证到期日

	private Integer					modifyId;									// 修改信息id,如有修改操作关联到修改记录

	private Integer					status;									// 0：待审批,1:审核失败

	@JsonIgnore
	@JSONField(serialize = false)
	private SupplierOperTaxInfoDto	modification;

	public void setRegCerExpiringDate(String regCerExpiringDate){
		if (regCerExpiringDate.length() == 10) {
			regCerExpiringDate = regCerExpiringDate.concat("000");
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = format.format(new Date(Long.valueOf(regCerExpiringDate)));
			Date newDate = format.parse(format1);
			this.regCerExpiringDate = newDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void setQuaIdeExpiringDate(String quaIdeExpiringDate){
		if (quaIdeExpiringDate.length() == 10) {
			quaIdeExpiringDate = quaIdeExpiringDate.concat("000");
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = format.format(new Date(Long.valueOf(quaIdeExpiringDate)));
			Date newDate = format.parse(format1);
			this.quaIdeExpiringDate = newDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void setBusinessLicenseExpiringDate(String businessLicenseExpiringDate){
		if (businessLicenseExpiringDate.length() == 10) {
			businessLicenseExpiringDate = businessLicenseExpiringDate.concat("000");
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = format.format(new Date(Long.valueOf(businessLicenseExpiringDate)));
			Date newDate = format.parse(format1);
			this.businessLicenseExpiringDate = newDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void setTaxpayerCertExpiringDate(String taxpayerCertExpiringDate){
		if (taxpayerCertExpiringDate.length() == 10) {
			taxpayerCertExpiringDate = taxpayerCertExpiringDate.concat("000");
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = format.format(new Date(Long.valueOf(taxpayerCertExpiringDate)));
			Date newDate = format.parse(format1);
			this.taxpayerCertExpiringDate = newDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}