package com.yatang.sc.operation.vo.coupon;

import com.yatang.sc.web.view.XlsHealder;
import com.yatang.sc.web.view.XlsSheet;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 优惠券领取记录显示vo
 * @author: yinyuxin
 * @date: 2017/9/21 10:28
 * @version: v1.0
 */
@XlsSheet("未使用列表")
public class CouponActivityVo implements Serializable{

	private static final long serialVersionUID = -4692326400709744613L;

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

	@XlsHealder(value = "券名称", width = 20)
	private String promotionName;                          //优惠券名称

	@XlsHealder(value = "券ID", width = 20)
	private String promoId;                                //优惠券id

	@XlsHealder(value = "领取时间", width = 20)
	private Date activityDate;                             //领取时间

	private String state;                                  //状态(active:已领取,used:已使用,canceled:已作废)

	private String modifyUser;                             //(作废操作)修改人id

	private Date modifyTime;                               //作废时间

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



	public String getPromotionName() {
		return promotionName;
	}



	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}



	public String getPromoId() {
		return promoId;
	}



	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}



	public Date getActivityDate() {
		return activityDate;
	}



	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}
