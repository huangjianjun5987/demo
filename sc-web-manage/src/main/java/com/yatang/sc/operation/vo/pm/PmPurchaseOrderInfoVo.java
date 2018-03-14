package com.yatang.sc.operation.vo.pm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderItemDto;
import com.yatang.sc.web.jackson.ImageUrlDeserializer;
import com.yatang.sc.web.jackson.ImageUrlSerializer;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:分装关联查询采购订单信息
 * @类名:PmPurchaseOrderInfoVo
 * @作者: lvheping
 * @创建时间: 2017/7/28 10:34
 * @版本: v1.0
 */
@Setter
@Getter
public class PmPurchaseOrderInfoVo implements Serializable {
	private static final long				serialVersionUID	= -5940757684440063544L;
	private Long							id;											// pk

	private String							purchaseOrderNo;							// 采购单号

	private String							spId;										// 供应商ID

	private String							spNo;										// 供应商编码

	private String							spName;										// 供应商名称

	private String							spAdrId;									// 供应商地点ID

	private String							spAdrNo;									// 供应商地点编码

	private String							spAdrName;									// 供应商地点名称

	private Date							estimatedDeliveryDate;						// 预计送货日期

	private Integer							settlementPeriod;							// 账期

	private Integer							payType;									// 供应商付款方式

	private Integer							adrType;									// 地点类型:0:仓库;1:门店

	private String							adrTypeCode;								// 地点类型编码

	private String							adrTypeName;								// 地点类型名称

	private String							secondCategoryId;							// 大类分类id

	private String							secondCategoryName;							// 大类分类名称

	private String							currencyCode;								// 货币种类代码,CNY

	private String							branchCompanyId;							// 子公司ID

	private Integer							purchaseOrderType;							// 类型:0:普通采购单;1:赠品采购;2:促销釆购

	private Integer							status;										// 状态:0:草稿;1:待审核;2:已审核;3:已拒绝;4:已关闭;5:已取消 *18-1-8zby新增状态5。*

	private String							failedReason;								// 失败原因

	private String							auditUserId;								// 审核人ID

	private Date							createTime;									// 创建时间

	private Date							modifyTime;									// 修改时间

	private String							createUserId;								// 创建人Id

	private String							createUserName;								// 创建人名字

	private String							auditUserName;								// 审核人名称

	private String							modifyUserId;								// 修改人

	private Date							auditTime;									// 审核日期
	private String							adrName;									// 门店和仓库详细地址（收货地址）

	private String							phone;										// 联系电话

	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	private String							barCodeUrl;									// 条码地址

	private BigDecimal						totalTaxAmount;								// 总税额
	// new

	private BigDecimal						totalWithoutTaxAmount;						// 不含税采购金额
	// new

	private BigDecimal						totalAmount;								// 总采购金额，含税

	private String							numberToCN;									// 大写金额
	private Integer							totalNumber;								// 合计数量
	private Integer							isValid;									// 是否有效:0,无效,1:有效
	// new
	private List<PmPurchaseOrderItemDto>	pmPurchaseOrderItems;						// 采购单商品表po类
	private Integer							payCondition;								// 付款条件(1:票到七天,2:票到十五天,3:票到三十天)

	private Integer							businessMode;								// 经营模式:0:经销;1:代销;2:寄售 *18-1-8zby新增状态2。*

	/**
	 * 供应商接单状态:0未接单;1:已接单
	 */
	private Integer spAcceptStatus;


	/**
	 * 销售订单id
	 */
	private String saleOrderId;


}
