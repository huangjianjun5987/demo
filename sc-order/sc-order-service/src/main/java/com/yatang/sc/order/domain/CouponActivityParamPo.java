package com.yatang.sc.order.domain;

import com.yatang.sc.common.BasePo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description: 优惠券领取记录查询参数po
 * @author: yinyuxin
 * @date: 2017/9/20 18:57
 * @version: v1.0
 */
public class CouponActivityParamPo extends BasePo implements Serializable {

	private static final long serialVersionUID = -7237421673597663686L;

	private Long id;                        		//id

	private List<String> storeIds;                  //门店iD集合

	private String promoId;                         //优惠券id

	private Date activityDateStart;              	//领取时间(开始)

	private Date activityDateEnd;             	    //领取时间(结束)

	private Date canceledDateStart;					//作废时间（开始）

	private Date canceledDateEnd;					//作废时间（结束）

	private String state;                           //状态(active,used)

	private Integer queryType;						//查询类型 1:查询未使用; 2:查询作废记录


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public List<String> getStoreIds() {
		return storeIds;
	}



	public void setStoreIds(List<String> storeIds) {
		this.storeIds = storeIds;
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



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getPromoId() {
		return promoId;
	}



	public void setPromoId(String promoId) {
		this.promoId = promoId;
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
