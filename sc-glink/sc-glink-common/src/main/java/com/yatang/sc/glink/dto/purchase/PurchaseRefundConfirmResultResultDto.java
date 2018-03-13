package com.yatang.sc.glink.dto.purchase;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * <class description>
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年11月9日
 */
public class PurchaseRefundConfirmResultResultDto implements Serializable {
	private static final long							serialVersionUID	= 1L;
	private String										deliver_Order_type;			// 是
																					// 出库单类型：PTCK=普通出库单（退仓），DBCK=调拨出库，QTCK=其他出库
	private String										orderCodeOwner;				// 是
																					// 货主的出库单号
	private String										orderCodeWms;				// 否
																					// WMS系统中生成的货主单号
	private String										ownerCode;					// 是
																					// 货主自定义编码
	private String										warehouseCode;				// 是
																					// 仓储商自定义编码
	private String										projectCode;				// 是
																					// 项目自定义编码
	private String										status;						// 是
																					// NEW-未开始处理,
																					// ACCEPT-仓库接单
																					// ,PARTDELIVERED-部分发货完成,
																					// DELIVERED-发货完成,EXCEPTION-异常,
																					// CANCELED-取消,
																					// CLOSED-关闭,REJECT-拒单,
																					// CANCELEDFAIL-取消失败
	private Date										operatorCode;				// 当前状态操作员编码
	private String										operatorName;				// 当前状态操作员姓名
	private Date										operateTime;				// 当前状态操作时间
																					// 日期格式为yyyy-MM-ddHH:mm:ss形式的字符串。
	private Date										orderConfirmTime;			// 订单完成时间
	private String										packagesNum;				// 条件必填
																					// 包裹数，状态为PARTDELIVERED-部分发货完成,DELIVERED-发货完成且包裹没有编码时，包裹数必填；
	private String										actualVolume;				// 条件必填所有包裹总的实际出库的体积。保留到3位小数点，不足0.001按照0.001传，包裹数必填时，实际出库体积必填
	private String										actualWeight;				// 条件必填所有包裹总的实际出库的重量。保留到2位小数点.包裹数必填时，实际出库重量必填
	// private PurchaseRefundConfirmPackagesDto packages; // 包裹没有编码时packages不用传值
	private String										remark;						//
	private List<PurchaseRefundConfirmOrderLinesDto>	orderLines;					// 货品信息，无货品相关信息时不传值。具体数据结构见下表。



	public String getDeliver_Order_type() {
		return deliver_Order_type;
	}



	public void setDeliver_Order_type(String deliver_Order_type) {
		this.deliver_Order_type = deliver_Order_type;
	}



	public String getOrderCodeOwner() {
		return orderCodeOwner;
	}



	public void setOrderCodeOwner(String orderCodeOwner) {
		this.orderCodeOwner = orderCodeOwner;
	}



	public String getOrderCodeWms() {
		return orderCodeWms;
	}



	public void setOrderCodeWms(String orderCodeWms) {
		this.orderCodeWms = orderCodeWms;
	}



	public String getOwnerCode() {
		return ownerCode;
	}



	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}



	public String getWarehouseCode() {
		return warehouseCode;
	}



	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}



	public String getProjectCode() {
		return projectCode;
	}



	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public Date getOperatorCode() {
		return operatorCode;
	}



	public void setOperatorCode(Date operatorCode) {
		this.operatorCode = operatorCode;
	}



	public String getOperatorName() {
		return operatorName;
	}



	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}



	public Date getOperateTime() {
		return operateTime;
	}



	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}



	public Date getOrderConfirmTime() {
		return orderConfirmTime;
	}



	public void setOrderConfirmTime(Date orderConfirmTime) {
		this.orderConfirmTime = orderConfirmTime;
	}



	public String getPackagesNum() {
		return packagesNum;
	}



	public void setPackagesNum(String packagesNum) {
		this.packagesNum = packagesNum;
	}



	public String getActualVolume() {
		return actualVolume;
	}



	public void setActualVolume(String actualVolume) {
		this.actualVolume = actualVolume;
	}



	public String getActualWeight() {
		return actualWeight;
	}



	public void setActualWeight(String actualWeight) {
		this.actualWeight = actualWeight;
	}



	// public PurchaseRefundConfirmPackagesDto getPackages() {
	// return packages;
	// }
	//
	//
	//
	// public void setPackages(PurchaseRefundConfirmPackagesDto packages) {
	// this.packages = packages;
	// }

	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public List<PurchaseRefundConfirmOrderLinesDto> getOrderLines() {
		return orderLines;
	}



	public void setOrderLines(List<PurchaseRefundConfirmOrderLinesDto> orderLines) {
		this.orderLines = orderLines;
	}

}
