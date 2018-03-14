package com.yatang.sc.operation.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 供应商子表审批.
 * @作者: kangdong
 * @创建时间: 2017年6月19日-上午20:34:57 .
 * @版本: 1.0 .
 * @param <T>
 */
@Getter
@Setter
public class ApprovalSupplierVo implements Serializable {

	private static final long	serialVersionUID	= 3192656721592021053L;
	private String				spId;										// 供应商主表ID

	private Integer				id;										// 修改前的ID

	private Integer				newId;										// 修改后的ID

	private Integer				status;									// 状态(0:待审批,1:审核失败)

	private String				failedReason;								// 失败原因
}