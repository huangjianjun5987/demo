package com.yatang.sc.operation.vo;

import com.yatang.sc.operation.common.BaseVo;

/**
 * 
 * <class description>查询发放优惠券加盟商VO
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年9月20日
 */
public class GrantFranchiseeVo extends BaseVo {

	private static final long	serialVersionUID	= 1L;

	/** 门店编号 */
	private String				storeId;

	/** 门店名称 */
	private String				storeName;

	/** 加盟商编号 */
	private String				franchiseeId;

	/** 加盟商名称 */
	private String				franchinessController;

	/** 所属子公司 */
	private String				branchCompanyId;



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



	public String getBranchCompanyId() {
		return branchCompanyId;
	}



	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
