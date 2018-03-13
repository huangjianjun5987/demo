package com.yatang.sc.facade.dto.prod;

import java.util.Date;

import com.yatang.sc.facade.common.BaseDto;
/**
 * 
* <class description>商品价格变更查询参数VO
*		
* @author: zhoubaiyun
* @version: 1.0, 2017年12月6日
 */
public class ProdPriceChangeQueryDto extends BaseDto{

	private static final long	serialVersionUID	= 1L;

	private Integer				changeType;					// 变更类型:0:采购进价变更;1:售价变更,
	private String				spId;						// 供应商ID,
	private String				spAdrId;					// 供应商地点ID,
	private String				branchCompanyId;			// 子公司id,
	private String				productId;					// 商品ID,
	private Date				startTime;					// 开始日期
	private Date				endTime;					// 结束日期



	public Integer getChangeType() {
		return changeType;
	}



	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}



	public String getSpId() {
		return spId;
	}



	public void setSpId(String spId) {
		this.spId = spId;
	}



	public String getSpAdrId() {
		return spAdrId;
	}



	public void setSpAdrId(String spAdrId) {
		this.spAdrId = spAdrId;
	}



	public String getBranchCompanyId() {
		return branchCompanyId;
	}



	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}



	public String getProductId() {
		return productId;
	}



	public void setProductId(String productId) {
		this.productId = productId;
	}



	public Date getStartTime() {
		return startTime;
	}



	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}



	public Date getEndTime() {
		return endTime;
	}



	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}



	@Override
	public String toString() {
		return "ProdPriceChangeQueryVo [changeType=" + changeType + ", spId=" + spId + ", spAdrId=" + spAdrId
				+ ", branchCompanyId=" + branchCompanyId + ", productId=" + productId + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}

}
