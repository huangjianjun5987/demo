package com.yatang.sc.operation.vo.pm;

import com.yatang.sc.validgroup.DefaultGroup;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @描述:采购订单审核封装类
 * @类名:PmPurchaseOrderAuditVo
 * @作者: lvheping
 * @创建时间: 2017/7/26 19:20
 * @版本: v1.0
 */
@Getter
@Setter
public class PmPurchaseOrderAuditVo implements Serializable {
	private static final long	serialVersionUID	= -3767789524563251982L;
	private String				purchaseOrderNo;							// 采购单号
	@NotNull(groups = DefaultGroup.class, message = "{msg.notEmpty.message}")
	private String				id;
	private String				failedReason;								// 失败原因
	@NotNull(groups = DefaultGroup.class, message = "{msg.notEmpty.message}")
	@Range(min = 2, max = 3, message = "{msg.range.message}", groups = DefaultGroup.class)
	private Integer				status;										// 状态:0:草稿;1:待审核;2:已审核;3:已拒绝;4:已关闭

}
