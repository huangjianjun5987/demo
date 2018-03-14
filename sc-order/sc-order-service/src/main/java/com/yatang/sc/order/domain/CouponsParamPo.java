package com.yatang.sc.order.domain;

import com.yatang.sc.common.BasePo;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 优惠券查询参数po
 * @author: yinyuxin
 * @date: 2017/9/19 21:01
 * @version: v1.0
 */
public class CouponsParamPo extends BasePo implements Serializable {

	private static final long serialVersionUID = -4842892608207330445L;


	private String				id;               //id

	private String              promotionName;    //券名称

	private String              branchCompanyId;  //分公司id

	private Date				startDate;        //生效时间

	private Date                endDate;          //过期时间

	private String              status;           //状态

	private String              grantChannel;     //发放方式 ('personal'、'platform')

	private String 				couponType;		  //优惠券类型('default'、'toGive')



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



	public String getGrantChannel() {
		return grantChannel;
	}



	public void setGrantChannel(String grantChannel) {
		this.grantChannel = grantChannel;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

}
