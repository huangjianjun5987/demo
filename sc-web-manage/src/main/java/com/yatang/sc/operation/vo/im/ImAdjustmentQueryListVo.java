package com.yatang.sc.operation.vo.im;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @描述:分页查询返回封装类
 * @类名:ImAdjustmentQueryListVo
 * @作者: lvheping
 * @创建时间: 2017/8/30 13:48
 * @版本: v1.0
 */
@Getter
@Setter
@ToString
public class ImAdjustmentQueryListVo implements Serializable {
	private static final long			serialVersionUID	= 1884136495310730250L;
	private Long						id;											// pk

	private String						adjustmentNo;								// 单据编号

	private Integer						type;										// 类型:0:物流丢失（WLDS）、1:仓库报溢（CKBY）、2:仓库报损（CKBS）、3:业务调增（YWTZ）、4:业务调减（YWTJ）、5:仓库同步调增（CKTBZ）、6:仓库同步调减（CKTBJ）

	private Integer						status;										// 状态:0:制单;1:已生效

	private Date						adjustmentTime;								// 调整日期

	private String						warehouseCode;								// 调整仓库,逻辑仓code

	private Long						totalQuantity;								// 调整数量合计

	private BigDecimal					totalAdjustmentCost;						// 调整成本合计

	private String						description;								// 备注

	private String						externalBillNo;								// 外部单据号

	private String						failedReason;								// 失败原因

	private String						auditUserId;								// 批准人ID

	private String						auditUserName;								// 批准人名称

	private Date						auditTime;									// 批准日期

	private Date						createTime;

	private Date						modifyTime;

	private String						createUserId;								// 创建人id

	private String						createUserName;								// 创建人名称

	private String						modifyUserId;								// 修改人id

	private String						modifyUserName;								// 修改人名称

	private String						branchCompanyId;							// 子公司id

	private List<ImAdjustmentItemVo>	imAdjustmentItemVos;						// 库存调整单商品集合

	private String						warehouseName;								// 调整仓库,逻辑仓name
}
