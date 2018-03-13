package com.yatang.sc.facade.dto.pm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @描述:查询供应商采购订单信息
 * @类名:PmSupplierPurchaseOrderDetailDto
 * @作者: kangdong
 * @创建时间: 2018/1/11 10:34
 * @版本: v1.0
 */
@Setter
@Getter
@ToString
public class PmSupplierPurchaseOrderDetailDto implements Serializable {
	private static final long				serialVersionUID	= -5940757684440063544L;
	private Long							id;											// pk

	private String							purchaseOrderNo;							// 采购单号
	private String							spName;										// 供应商名称
	private Integer							purchaseOrderType;							// 类型:0:普通采购单;1:赠品采购;2:促销釆购
	private String							createUserId;								// 创建人
	private String							createUserName;
	// 收货单位
	private String						adrTypeCode;								// 地点类型编码
	private String						adrTypeName;								// 地点类型名称

	private String							adrName;									// 门店和仓库详细地址（收货地址）

	private String							phone;										// 联系电话
	private String							currencyCode;								// 货币种类代码,CNY
	private String							secondCategoryId;							// 大类分类id
	private String							secondCategoryName;							// 大类分类名称
	private Date							estimatedDeliveryDate;						// 预计送货日期

	private List<PmPurchaseOrderItemDto>	pmPurchaseOrderItems;						// 采购单商品表po类

	private BigDecimal						totalAmount;								// 总采购金额，含税
	private Integer							totalNumber;								// 合计数量
	private String							numberToCN;									// 合计金额（大写）

	private String							barCodeUrl;									// 条码地址

	private Integer						businessMode;								// 经营模式:0:经销;1:代销;2:寄售
	// private String spId; // 供应商ID
	// private String spNo; // 供应商编码
	// private String spAdrId; // 供应商地点ID
	// private String spAdrNo; // 供应商地点编码
	// private String spAdrName; // 供应商地点名称
}
