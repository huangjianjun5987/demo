package com.yatang.sc.operation.vo.supplier;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @描述: 供应商紧急联系人信息.
 * @作者: huangjianjun
 * @创建时间: 2017年5月13日-上午10:59:57 .
 * @版本: 1.0 .
 * @param <T>
 */
@Getter
@Setter
public class SupplierEmerContInfoVo implements Serializable {

	private static final long		serialVersionUID	= 3357040378407501480L;

	private String                spId;										// 供应商主表ID

	private Integer					id;											// 编号

	private String					name;										// 紧急联系人名字

	private String					phone;										// 紧急联系人电话

	private String					companyPhoneNumber;							// 公司电话

	private Integer					modifyId;									// 修改信息ID

	private Integer					status;										// 状态0：待审批1;审核失败

	private String					failedReason;								// 失败原因

	@JsonIgnore
	private SupplierEmerContInfoVo modification;								// 供应商紧急联系人信息
}