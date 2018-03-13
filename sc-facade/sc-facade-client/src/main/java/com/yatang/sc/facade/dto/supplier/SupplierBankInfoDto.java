package com.yatang.sc.facade.dto.supplier;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @描述: 供应商银行信息.
 * @作者: huangjianjun
 * @创建时间: 2017年5月13日-上午10:59:57 .
 * @版本: 1.0 .
 * @param <T>
 */
@Getter
@Setter
public class SupplierBankInfoDto implements Serializable {

	private static final long	serialVersionUID	= -7550039390911841722L;

	private String				spId;											// 供应商主表ID

	private Integer				id;

	private String				accountName;									// 开户名字

	private String				openBank;										// 开户行

	private String				bankAccount;									// 银行账户

	private String				bankAccountLicense;								// 银行开户许可证电子版url

	private String				bankLocProvince;								// 省份名

	private String				bankLocCity;									// 城市名

	private String				bankLocCounty;									// 地区名

	private String				bankLocProvinceCode;							// 省份

	private String				bankLocCityCode;								// 城市

	private String				bankLocCountyCode;								// 地区

	private String				invoiceHead;									// 供应商发票抬头

	private Integer				modifyId;										// 修改信息ID

	private Integer				status;											// 状态0：待审批,1;审核失败,默认为0

	@JsonIgnore
	private SupplierBankInfoDto	modification;									// 供应商银行信息

}