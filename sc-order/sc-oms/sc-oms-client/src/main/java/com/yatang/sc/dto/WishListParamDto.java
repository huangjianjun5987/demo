package com.yatang.sc.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description: 查询心愿单列表参数dto ---后端
 * @author: yinyuxin
 * @date: 2018/1/3 13:51
 * @version: v1.0
 */
public class WishListParamDto implements Serializable{

	private static final long serialVersionUID = 6472650461732338767L;

	/**
	 * 商品条码
	 */
	private String gbCode;

	/**
	 * 状态，init:待处理， complete:已完成， close：关闭
	 */
	private String status;

	/**
	 * 创建时间（开始）
	 */
	private Date createTimeStart;

	/**
	 * 创建时间（结束）
	 */
	private Date createTimeEnd;

	/**
	 * 子公司id集合
	 */
	private List<String> branchCompanyIds;

	/**
	 * 分页条件:页码
	 */
	private Integer pageNum;

	/**
	 * 分页条件:每页数据量
	 */
	private Integer pageSize;

	/*******************************************心愿单明细清单查询参数****************************/

	/**
	 * 门店id
	 */
	private String storeId;

	/**
	 * 心愿单id
	 */
	private Long wishListId;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}



	public String getGbCode() {
		return gbCode;
	}



	public void setGbCode(String gbCode) {
		this.gbCode = gbCode;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public Date getCreateTimeStart() {
		return createTimeStart;
	}



	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}



	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}



	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}



	public List<String> getBranchCompanyIds() {
		return branchCompanyIds;
	}



	public void setBranchCompanyIds(List<String> branchCompanyIds) {
		this.branchCompanyIds = branchCompanyIds;
	}



	public Integer getPageNum() {
		return pageNum;
	}



	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}



	public Integer getPageSize() {
		return pageSize;
	}



	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}



	public String getStoreId() {
		return storeId;
	}



	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}



	public Long getWishListId() {
		return wishListId;
	}



	public void setWishListId(Long wishListId) {
		this.wishListId = wishListId;
	}
}
