package com.yatang.sc.facade.dto.pm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PmPurchaseOrderDto implements Serializable {

	private static final long	serialVersionUID	= -7163758416697977036L;
	private Long				id;											// pk

	private String				purchaseOrderNo;							// 采购单号

	private String				spId;										// 供应商ID

	private String				spNo;										// 供应商编码

	private String				spName;										// 供应商名称

	private String				spAdrId;									// 供应商地点ID

	private String				spAdrNo;									// 供应商地点编码

	private String				spAdrName;									// 供应商地点名称

	private Date				estimatedDeliveryDate;						// 预计送货日期

	private Integer				settlementPeriod;							// 账期

	private Integer				payType;									// 供应商付款方式

	private Integer				payCondition;									// 付款条件(1:票到七天,2:票到十五天,3:票到三十天)

	private Integer				adrType;									// 地点类型:0:仓库;1:门店

	private String				adrTypeCode;								// 地点类型编码

	private String				adrTypeName;								// 地点类型名称

	private String				secondCategoryId;							// 大类分类id

	private String				secondCategoryName;							// 大类分类名称

	private String				currencyCode;								// 货币种类代码,CNY

	private String				branchCompanyId;							// 子公司ID

	private Integer				purchaseOrderType;							// 类型:0:普通采购单;1:赠品采购;2:促销釆购

	private Integer				status;										// 状态:0:草稿;1:待审核;2:已审核;3:已拒绝;4:已关闭;5:已取消 *18-1-8zby新增状态5。*

	private String				failedReason;								// 失败原因

	private String				auditUserId;								// 审核人ID

	private Date				createTime;									// 创建时间

	private Date				modifyTime;									// 修改时间

	private String				createUserId;								// 创建人

	private String				modifyUserId;								// 修改人

	private Date				auditTime;									// 审核日期
	private String				adrName;									// 门店和仓库详细地址（收货地址）

	private String				phone;										// 联系电话

	private String				barCodeUrl;									// 条码地址

	private BigDecimal			totalTaxAmount;								// 总税额
																			// new

	private BigDecimal			totalWithoutTaxAmount;						// 不含税采购金额
																			// new

	private BigDecimal			totalAmount;								// 总采购金额，含税
																			// new

	private Integer 			businessMode; 								// 经营模式:0:经销;1:代销;2:寄售 *18-1-8zby新增状态2。*

	private Integer				purchasingMode;								// 采购模式:0:地采;1:统采

	private Integer 			spAcceptStatus;							// *18-1-8zby新增字段。*供应商接单状态:0未接单;1:已接单

    /**
     * 销售订单id
     */
    private String saleOrderId;
}