package com.yatang.sc.operation.vo;

import java.io.Serializable;

/**
 * 
 * <class description>发放优惠券的门店信息
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年9月20日
 */
public class GrantCouponStoreVo implements Serializable {
	private static final long	serialVersionUID	= 1L;

	/** 门店编号 */
	private String				storeId;

	/** 门店名称 */
	private String				storeName;

	/** 加盟商编号 */
	private String				franchiseeId;

	/** 加盟商名称 */
	private String				franchinessController;

	/** 加盟商姓名 */
	private String				franchiseeName;

	/** 加盟商联系电话 */
	private String				franchiseePhone;

	/** 所属子公司 */
	private String				branchCompanyName;



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



	public String getFranchiseeName() {
		return franchiseeName;
	}



	public void setFranchiseeName(String franchiseeName) {
		this.franchiseeName = franchiseeName;
	}



	public String getFranchiseePhone() {
		return franchiseePhone;
	}



	public void setFranchiseePhone(String franchiseePhone) {
		this.franchiseePhone = franchiseePhone;
	}



	public String getBranchCompanyName() {
		return branchCompanyName;
	}



	public void setBranchCompanyName(String branchCompanyName) {
		this.branchCompanyName = branchCompanyName;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
