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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @描述: 供应商银行信息.
 * @作者: huangjianjun
 * @创建时间: 2017年5月13日-上午10:59:57 .
 * @版本: 1.0 .
 * @param <T>
 */
@Getter
@Setter
public class SupplierBankInfoVo implements Serializable {

	private static final long	serialVersionUID	= -7550039390911841722L;

	private String             spId;										// 供应商主表ID

	@NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
	private Integer				id;

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String				accountName;								// 开户名字

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	@Pattern(regexp="^[\u4e00-\u9fa5]{0,}$", groups = {GroupOne.class, GroupTwo.class}, message = "{msg.ChineseOnly.message}")
	@Length(max=50, groups = {GroupOne.class, GroupTwo.class}, message = "msg.length.message")
	private String				openBank;									// 开户行

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	@Pattern(regexp = "^[0-9]*$", groups = {GroupOne.class, GroupTwo.class}, message = "{msg.numbersZeroToNine.message}")
	@Length(max=30, groups = {GroupOne.class, GroupTwo.class}, message = "{msg.length.message}")
	private String				bankAccount;								// 银行账户

	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	private String				bankAccountLicense;							// 银行开户许可证电子版url

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String				bankLocProvince;							// 省份名

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String				bankLocCity;								// 城市名

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String				bankLocCounty;								// 地区名

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String				bankLocProvinceCode;						// 省份

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String				bankLocCityCode;							// 城市

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String				bankLocCountyCode;							// 地区

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	@Length(max=250, groups = {GroupOne.class, GroupTwo.class}, message = "{msg.length.message}")
	private String				invoiceHead;								//供应商发票抬头

	private Integer				modifyId;									// 修改信息ID

	@NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
	private Integer				status;										// 状态0：待审批,1;审核失败,默认为0

	private String				failedReason;								// 失败原因

	@JsonIgnore
	private SupplierBankInfoVo modification;								// 供应商银行信息

}