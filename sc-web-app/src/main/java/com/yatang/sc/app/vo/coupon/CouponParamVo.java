package com.yatang.sc.app.vo.coupon;


import java.io.Serializable;
import java.util.Date;

/**
 * @description: 优惠券查询参数vo
 * @author: yinyuxin
 * @date: 2017/9/21 9:50
 * @version: v1.0
 */
public class CouponParamVo implements Serializable{

	private static final long serialVersionUID = 8681828485893613612L;

	private String				id;            //id

	private String              promotionName; //券名称

	private String              branchCompanyId;//分公司id

	private Date 				startDate;    	//开始时间

	private Date                endDate;        //结束时间

	private String              status;         //状态 released: 已发布,unreleased:未发布,closed:已关闭,ended:已结束

	private Integer				pageNum=1;									// 当前页

	private Integer				pageSize=15;									// 每页显示记录数

	private String              storeId;        //门店id，用于领取操作传参



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getPromotionName() {
		return promotionName;
	}



	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}



	public String getBranchCompanyId() {
		return branchCompanyId;
	}



	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}



	public Date getStartDate() {
		return startDate;
	}



	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	public Date getEndDate() {
		return endDate;
	}



	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
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
}


