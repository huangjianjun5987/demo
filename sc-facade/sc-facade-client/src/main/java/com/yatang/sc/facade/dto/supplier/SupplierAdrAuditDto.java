package com.yatang.sc.facade.dto.supplier;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * <class description>供应商地点DTO
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年7月24日
 */
@Getter
@Setter
public class SupplierAdrAuditDto implements Serializable {
	private static final long	serialVersionUID	= 1L;
	// 供应商地点ID
	private Integer				id;
	// 审核人ID
	private String				auditUserId;
	// 是否通过
	private Boolean				pass;
	// 失败原因
	private String				failedReason;
}
