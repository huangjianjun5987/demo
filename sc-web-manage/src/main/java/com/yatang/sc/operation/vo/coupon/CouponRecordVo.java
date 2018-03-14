package com.yatang.sc.operation.vo.coupon;

import com.yatang.sc.web.view.XlsHealder;
import com.yatang.sc.web.view.XlsSheet;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 优惠券使用记录展示vo
 * @author: yinyuxin
 * @date: 2017/9/21 10:23
 * @version: v1.0
 */
@XlsSheet("已使用列表")
public class CouponRecordVo implements Serializable{

	private static final long serialVersionUID = -7737595058010783461L;

	private Long id;                                       //主键id

	@XlsHealder(value = "所属子公司", width = 20)
	private String branchCompanyName;                      //所属子公司名称

	@XlsHealder(value = "加盟商编号", width = 20)
	private String franchiseeId;                           //加盟商编号

	@XlsHealder(value = "加盟商名称", width = 20)
	private String franchinessController;                  //加盟商名称

	@XlsHealder(value = "门店编号", width = 20)
	private String storeId;                                //门店iD

	@XlsHealder(value = "门店名称", width = 20)
	private String storeName;                              //门店名称

	@XlsHealder(value = "券ID", width = 20)
	private String promoId;                                //优惠券id

	@XlsHealder(value = "券名称", width = 20)
	private String promotionName;                          //优惠券名称

	@XlsHealder(value = "订单号", width = 20)
	private String orderId;                                //订单编号

	@XlsHealder(value = "使用时间", width = 30)
	private Date   recordTime;                             //使用时间

	@XlsHealder(value = "订单状态", width = 20)
	private String orderState;                             //订单状态 W:待审核 M:待人工审核 A:已审核 Q:已取消 C:已完成 SP:已拆单

	@XlsHealder(value = "物流状态", width = 20)
	private String shippingState;                          //配送状态 DCL:待处理 WCS:未传送 DCK:待出库 WJS:仓库拒收 DSH:待收货 YQS:已签收 WSD:未送达 QXZ:取消中 QX:取消送货 CGWDH:库存不足

	@XlsHealder(value = "支付状态", width = 20)
	private String paymentState;                           //支付状态 WZF:未支付  YZF:已支付  TKD:退款待审核   TKQ:退款待确认   YTK:已退款   QXFK:取消付款  GSN:公司内

	@XlsHealder(value = "订单金额", width = 20)
	private Double orderPrice;                             //订单金额



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getBranchCompanyName() {
		return branchCompanyName;
	}



	public void setBranchCompanyName(String branchCompanyName) {
		this.branchCompanyName = branchCompanyName;
	}



	public String getFranchiseeId() {
		return franchiseeId;
	}



	public void setFranchiseeId(String franchiseeId) {
		this.franchiseeId = franchiseeId;
	}



	public String getFranchinessController() {
		return franchinessController;
	}



	public void setFranchinessController(String franchinessController) {
		this.franchinessController = franchinessController;
	}



	public String getStoreId() {
		return storeId;
	}



	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}



	public String getStoreName() {
		return storeName;
	}



	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}



	public String getPromoId() {
		return promoId;
	}



	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}



	public String getPromotionName() {
		return promotionName;
	}



	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}




	public String getOrderId() {
		return orderId;
	}



	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public Date getRecordTime() {
		return recordTime;
	}



	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}



	public String getOrderState() {
		return orderState;
	}



	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}



	public String getShippingState() {
		return shippingState;
	}



	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}



	public String getPaymentState() {
		return paymentState;
	}



	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
	}



	public Double getOrderPrice() {
		return orderPrice;
	}



	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}
}
