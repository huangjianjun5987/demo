package com.yatang.sc.operation.vo.supplier;

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

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @描述: 供应商营业执照(副本)信息.
 * @作者: huangjianjun
 * @创建时间: 2017年5月13日-上午10:59:57 .
 * @版本: 1.0 .
 * @param <T>
 */
@Getter
@Setter
public class SupplierlicenseInfoVo implements Serializable {

	private static final long		serialVersionUID	= -1638862775186861996L;

	@NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
	private Integer					id;											// 编号

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String					companyName;								// 公司名称

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	@Pattern(regexp = "^[A-Za-z0-9]+$", groups = {GroupOne.class, GroupTwo.class}, message = "{msg.letter&NumberOnly.message}")
	@Length(max=25, groups = {GroupOne.class, GroupTwo.class}, message = "{msg.length.message}")
	private String					registLicenceNumber;						// 注册号(营业执照号)

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	@Pattern(regexp="^[\u4e00-\u9fa5]{0,}$", groups = {GroupOne.class, GroupTwo.class}, message = "{msg.ChineseOnly.message}")
	@Length(max=6, groups = {GroupOne.class, GroupTwo.class}, message = "{msg.length.message}")
	private String					legalRepresentative;						// 法定代表人

	@Pattern(regexp = "^\\d{8,18}|[0-9x]{8,18}|[0-9X]{8,18}?$", groups = {GroupOne.class, GroupTwo.class}, message = "{msg.identity.card.message}")
	private String					legalRepreCardNum;							// 法人身份证号

	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	private String					legalRepreCardPic1;							// 法人身份证电子版1

	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	private String					legalRepreCardPic2;							// 法人身份证电子版2

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String					licenseLocProvince;							// 省份名

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String					licenseLocCity;								// 城市名

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String					licenseLocCounty;							// 地区名

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String					licenseLocProvinceCode;						// 省份code

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String					licenseLocCityCode;							// 城市code

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String					licenseLocCountyCode;						// 地区code

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	@Length(max=30, groups = {GroupOne.class, GroupTwo.class}, message = "{msg.length.message}")
	private String					licenseAddress;								// 营业执照详细地址

	@NotNull(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	@Past(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.date.past.message}")
	private Date					establishDate;								// 成立日期

	private Date					startDate;									// 营业开始日期

	private Date					endDate;									// 营业结束日期

	@NotNull(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private Integer					perpetualManagement;						// 永续经营(0:否,1:是)

	@NotNull(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	@DecimalMin(groups = {GroupOne.class, GroupTwo.class}, value = "0", inclusive = false, message = "{msg.largeThanZero.message}")
	@Digits(integer = Integer.MAX_VALUE, fraction = 2, groups = {GroupOne.class, GroupTwo.class}, message = "{msg.bigDecimal.message}")
	private BigDecimal				registeredCapital;							// 注册资本

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	@Length(max=300, groups = {GroupOne.class, GroupTwo.class}, message = "{msg.length.message}")
	private String					businessScope;								// 经营范围

	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String					registLicencePic;							// 营业执照副本电子版

	@DecimalMin(groups = {GroupOne.class, GroupTwo.class}, value = "0", message = "{msg.min.message}")
	private BigDecimal              guaranteeMoney;								//供应商质保金收取金额（保证金）

	private Integer					modifyId;									// 修改信息id,如有修改操作关联到修改记录

	@NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
	private Integer					status;										// 0：待审批,1:审核失败

	private String					failedReason;								// 失败原因

	@JsonIgnore
	private SupplierlicenseInfoVo	modification;

	private String					spId;										//供应商主表id


}