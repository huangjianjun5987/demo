package com.yatang.sc.operation.vo.supplier;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @描述: 供应商入驻联系人信息.
 * @作者: huangjianjun
 * @创建时间: 2017年5月13日-上午10:59:57 .
 * @版本: 1.0 .
 * @param <T>
 */
@Getter
@Setter
public class SupplierSettledContInfoVo implements Serializable {

	private static final long			serialVersionUID	= -7508924366136438522L;

	private String                spId;										// 供应商主表ID

	private Integer						id;											// pk

	private String						name;										// 入驻联系人姓名

	private String						phone;										// 联系电话

	private String						email;										// 邮件

	private Integer						modifyId;									// 修改信息id,如有修改操作关联到修改记录

	private Integer						status;										// 状态(0:待审批,1:审核失败)
	private String						failedReason;								// 失败原因
	@JsonIgnore
	private SupplierSettledContInfoVo modification;

}