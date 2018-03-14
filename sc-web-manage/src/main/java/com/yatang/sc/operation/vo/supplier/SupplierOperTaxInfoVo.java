package com.yatang.sc.operation.vo.supplier;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;
import com.yatang.sc.web.jackson.ImageUrlDeserializer;
import com.yatang.sc.web.jackson.ImageUrlSerializer;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @描述: 供应商经营及税务信息.
 * @作者: huangjianjun
 * @创建时间: 2017年5月13日-上午10:59:57 .
 * @版本: 1.0 .
 * @param <T>
 */
@Getter
@Setter
public class SupplierOperTaxInfoVo implements Serializable {

	private static final long		serialVersionUID	= 4890084941210033149L;

	private String                	spId;									// 供应商主表ID

	@NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
	private Integer					id;										// 编号

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String					companyLocProvinceCode;					// 省份code

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String					companyLocCityCode;						// 城市code

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String					companyLocCountyCode;					// 地区code

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String					companyLocProvince;						// 省份名

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String					companyLocCity;							// 城市名

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String					companyLocCounty;						// 地区名

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	@Length(max=30, groups = {GroupOne.class, GroupTwo.class}, message = "{msg.length.message}")
	private String					companyDetailAddress;					// 公司详细地址

	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	private String					registrationCertificate;				// 商标注册证/受理通知书

	private Date 					regCerExpiringDate;						//商标注册证/受理通知书到期日

	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	private String					qualityIdentification;					// 食品安全认证

	private Date					quaIdeExpiringDate;						//食品安全认证到期日

	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	private String 					foodBusinessLicense;						//食品经营许可证

	private Date					businessLicenseExpiringDate;				//食品经营许可证到期日期

	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String					generalTaxpayerQualifiCerti;			// 一般纳税人资格证电子版

	private Date					taxpayerCertExpiringDate;				//一般纳税人资格证到期日

	private Integer					modifyId;								// 修改信息id,如有修改操作关联到修改记录

	@NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
	private Integer					status;									// 0：待审批,1:审核失败

	private String					failedReason;							// 失败原因

	@JsonIgnore
	@JSONField(serialize = false)
	private SupplierOperTaxInfoVo	modification;

}