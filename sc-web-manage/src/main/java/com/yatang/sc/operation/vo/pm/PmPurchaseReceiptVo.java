package com.yatang.sc.operation.vo.pm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 收货单列表vo
 * 
 * @author: yinyuxin
 * @version: 1.0, 2017年7月26日
 */
@Setter
@Getter
@ToString
public class PmPurchaseReceiptVo implements Serializable {

	private static final long serialVersionUID = 9173937339645800714L;

	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 收货单号
	 */
	private String purchaseReceiptNo;

	/**
	 * 采购单号
	 */
	private String purchaseOrderNo;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * ASN
	 */
	private String asn;

	/**
	 * 供应商编号
	 */
	private String spNo;

	/**
	 * 供应商名称
	 */
	private String spName;

	/**
	 * 供应商地点编号
	 */
	private String spAdrNo;

	/**
	 * 供应商地点名称
	 */
	private String spAdrName;

	/**
	 * 地点类型:0:仓库;1:门店
	 */
	private Integer adrType;

	/**
	 * 地点编码
	 */
	private String adrTypeCode;

	/**
	 * 地点名称
	 */
	private String adrTypeName;

	/**
	 * 预计到货日期,系统自动默认，值为当前日期.注：预留与供应商系统对接预发货通知
	 */
	private Date estimatedReceivedDate;

	/**
	 * 收货日期,系统自动默认，值为创建收货单时间
	 */
	private Date receivedTime;

	/**************************************************************************/
	/**
	 * 采购类型 0:普通采购单;1:赠品采购;2:促销釆购
	 */
	private Integer purchaseOrderType;

	/**
	 * 预计送货时间
	 */
	private Date estimatedDeliveryDate;

	/*******************************************************************/
	/**
	 * 合计收货数量
	 */
	private  Integer receiveTotalNumber;

	/**
	 * 收货合计金额
	 */
	private BigDecimal receiveTotalPrice;

	/**
	 * 经营模式:0:经销;1:代销
	 * */
	private Integer	businessMode;


	/**
	 * 修改者id
	 */
	private String modifyUserId;

	/**
	 * 修改时间
	 */
	private Date modifyTime;
}
