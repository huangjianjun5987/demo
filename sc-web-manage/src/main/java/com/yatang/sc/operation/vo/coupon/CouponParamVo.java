package com.yatang.sc.operation.vo.coupon;

import com.yatang.sc.operation.common.BaseVo;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 优惠券查询参数vo
 * @author: yinyuxin
 * @date: 2017/9/21 9:50
 * @version: v1.0
 */
public class CouponParamVo extends BaseVo implements Serializable{

	private static final long serialVersionUID = 8681828485893613612L;

	private String				id;            //id

	private String              promotionName; //券名称

	private String              branchCompanyId;//分公司id

	private Date 				startDate;    	//开始时间

	private Date                endDate;        //结束时间

	private String              status;         //状态 released: 已发布,unreleased:未发布,closed:已关闭,ended:已结束

	private String 				couponType;		//优惠券类型('default'、'toGive')



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

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
}


