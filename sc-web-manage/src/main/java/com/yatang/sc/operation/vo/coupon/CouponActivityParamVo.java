package com.yatang.sc.operation.vo.coupon;

import com.yatang.sc.operation.common.BaseVo;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 优惠券领取记录查询vo
 * @author: yinyuxin
 * @date: 2017/9/21 10:30
 * @version: v1.0
 */
public class CouponActivityParamVo extends BaseVo implements Serializable{

	private static final long serialVersionUID = -1608667040896399918L;

	private Long id;                        		//id

	private String storeId;                       //门店iD

	private String storeName;                       //门店名称

	private String promoId;                         //优惠券id

	private Date activityDateStart;              //领取时间(开始)

	private Date activityDateEnd;             	 //领取时间(结束)

	private Date canceledDateStart;				//作废时间(开始)

	private Date canceledDateEnd;				//作废时间(结束)

	private String franchiseeId;                 //加盟商编号

	private String franchinessController;        //加盟商名称

	private String branchCompanyId;              //分公司id

	private Integer queryType;					//查询类型 1:查询未使用; 2:查询作废记录


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
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



	public Date getActivityDateStart() {
		return activityDateStart;
	}



	public void setActivityDateStart(Date activityDateStart) {
		this.activityDateStart = activityDateStart;
	}



	public Date getActivityDateEnd() {
		return activityDateEnd;
	}



	public void setActivityDateEnd(Date activityDateEnd) {
		this.activityDateEnd = activityDateEnd;
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

	public Date getCanceledDateStart() {
		return canceledDateStart;
	}

	public void setCanceledDateStart(Date canceledDateStart) {
		this.canceledDateStart = canceledDateStart;
	}

	public Date getCanceledDateEnd() {
		return canceledDateEnd;
	}

	public void setCanceledDateEnd(Date canceledDateEnd) {
		this.canceledDateEnd = canceledDateEnd;
	}

	public Integer getQueryType() {
		return queryType;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}
}
