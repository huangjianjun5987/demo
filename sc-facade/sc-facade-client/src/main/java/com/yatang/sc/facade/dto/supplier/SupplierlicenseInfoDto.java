package com.yatang.sc.facade.dto.supplier;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @描述: 供应商营业执照(副本)信息.
 * @作者: huangjianjun
 * @创建时间: 2017年5月13日-上午10:59:57 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class SupplierlicenseInfoDto implements Serializable {

	private static final long		serialVersionUID	= -1638862775186861996L;

	private Integer					id;											// 编号

	private String					companyName;								// 公司名称

	private String					registLicenceNumber;						// 注册号(营业执照号)

	private String					legalRepresentative;						// 法定代表人

	private String					legalRepreCardNum;							// 法人身份证号

	private String					legalRepreCardPic1;							// 法人身份证电子版1

	private String					legalRepreCardPic2;							// 法人身份证电子版2

	private String					licenseLocProvince;							// 省份名

	private String					licenseLocCity;								// 城市名

	private String					licenseLocCounty;							// 地区名

	private String					licenseLocProvinceCode;						// 省份code

	private String					licenseLocCityCode;							// 城市code

	private String					licenseLocCountyCode;						// 地区code

	private String					licenseAddress;								// 营业执照详细地址

	private Date					establishDate;								// 成立日期

	private Date					startDate;									// 营业开始日期

	private Date					endDate;									// 营业结束日期

	private Integer					perpetualManagement;						// 永续经营(0:否,1:是)

	private BigDecimal				registeredCapital;							// 注册资本

	private String					businessScope;								// 经营范围

	private String					registLicencePic;							// 营业执照副本电子版

	private BigDecimal              guaranteeMoney;								//供应商质保金收取金额（保证金）

	private Integer					modifyId;									// 修改信息id,如有修改操作关联到修改记录

	private Integer					status;										// 0：待审批,1:审核失败
	
	@JsonIgnore
	@JSONField(serialize = false)
	private SupplierlicenseInfoDto	modification;
	
	private String					spId;										//供应商主表id
	
	public void setEstablishDate(String establishDate){
		if (establishDate.length() == 10) {
			establishDate = establishDate.concat("000");
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = format.format(new Date(Long.valueOf(establishDate)));
			Date newDate = format.parse(format1);
			this.establishDate = newDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void setStartDate(String startDate){
		if (startDate.length() == 10) {
			startDate = startDate.concat("000");
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = format.format(new Date(Long.valueOf(startDate)));
			Date newDate = format.parse(format1);
			this.startDate = newDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void setEndDate(String endDate){
		if (endDate.length() == 10) {
			endDate = endDate.concat("000");
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = format.format(new Date(Long.valueOf(endDate)));
			Date newDate = format.parse(format1);
			this.endDate = newDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}