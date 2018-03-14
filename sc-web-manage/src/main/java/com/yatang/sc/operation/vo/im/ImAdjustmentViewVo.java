package com.yatang.sc.operation.vo.im;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @描述:库存调整前台列表展示
 * @类名:ImAdjustmentView
 * @作者: lvheping
 * @创建时间: 2017/9/4 15:47
 * @版本: v1.0
 */
@Getter
@Setter
@ToString
public class ImAdjustmentViewVo implements Serializable {

	private static final long	serialVersionUID	= 1032145863960338260L;
	private Long				id;

	private String				adjustmentNo;								// 单据编号

	private Integer				status;										// 状态:0:制单;1:已生效

	private Long				totalQuantity;								// 调整数量合计

	private BigDecimal			totalAdjustmentCost;						// 调整成本合计

    private String						externalBillNo;								// 外部单据号

    private String						warehouseName;								// 调整仓库,逻辑仓name
}
