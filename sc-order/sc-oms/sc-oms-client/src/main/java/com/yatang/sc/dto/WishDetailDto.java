package com.yatang.sc.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 心愿单详情dto ---后端
 * @author: yinyuxin
 * @date: 2018/1/3 11:55
 * @version: v1.0
 */
public class WishDetailDto implements Serializable{

	private static final long serialVersionUID = 6778926581765728276L;
	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 商品国标码
	 */
	private String gbCode;

	/**
	 * 心愿单id
	 */
	private Long wishListId;

	/**
	 * 门店编号
	 */
	private String storeId;

	/**
	 * 加盟商编号
	 */
	private String franchiserId;

	/**
	 * 门店名称
	 */
	private String storeName;

	/**
	 * 数量
	 */
	private Long quantity;

	/**
	 * 创建时间
	 */
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
