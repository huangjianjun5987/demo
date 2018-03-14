package com.yatang.sc.operation.vo.supplier;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * <class description>供应商地点审核提交信息
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年7月18日
 */
@Getter
@Setter
public class SupplierAdrAuditVo implements Serializable {
	private static final long	serialVersionUID	= 1L;
	// 供应商主表ID
	@NotNull
	private Integer				id;
	// 是否通过
	@NotNull
	private Boolean				pass;
	// 失败原因
	private String				failedReason;
}
