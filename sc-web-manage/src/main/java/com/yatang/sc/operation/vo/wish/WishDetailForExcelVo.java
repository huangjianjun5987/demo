package com.yatang.sc.operation.vo.wish;

import com.yatang.sc.web.view.XlsHealder;
import com.yatang.sc.web.view.XlsSheet;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 心愿单详情dto ---后端(用于导出excel)
 * @author: yinyuxin
 * @date: 2018年1月11日15:27:05
 * @version: v1.0
 */
@XlsSheet("预定单详情")
public class WishDetailForExcelVo implements Serializable{

	private static final long serialVersionUID = -2950418133825756547L;

	/**
	 * 商品条码
	 */
	@XlsHealder(value = "商品条码",width = 18)
	private String gbCode;

	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 心愿单id
	 */
	private Long wishListId;

	/**
	 * 门店编号
	 */
	@XlsHealder(value = "门店编号",width = 18)
	private String storeId;

	/**
	 * 加盟商编号
	 */
	private String franchiserId;

	/**
	 * 门店名称
	 */
	@XlsHealder(value = "门店名称",width = 36)
	private String storeName;

	/**
	 * 数量
	 */
	@XlsHealder(value = "需求数量",width = 10)
	private Long quantity;

	/**
	 * 创建时间
	 */
	@XlsHealder(value = "提交时间",width = 18)
	private Date createTime;



	public static long getSerialVersionUID() {
		return serialVersionUID;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getWishListId() {
		return wishListId;
	}



	public void setWishListId(Long wishListId) {
		this.wishListId = wishListId;
	}



	public String getStoreId() {
		return storeId;
	}



	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}



	public String getFranchiserId() {
		return franchiserId;
	}



	public void setFranchiserId(String franchiserId) {
		this.franchiserId = franchiserId;
	}



	public String getStoreName() {
		return storeName;
	}



	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}



	public Long getQuantity() {
		return quantity;
	}



	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}



	public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public String getGbCode() {
		return gbCode;
	}



	public void setGbCode(String gbCode) {
		this.gbCode = gbCode;
	}
}
