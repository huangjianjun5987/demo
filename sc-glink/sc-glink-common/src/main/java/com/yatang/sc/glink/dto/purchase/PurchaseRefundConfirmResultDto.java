package com.yatang.sc.glink.dto.purchase;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * <class description>
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年11月9日
 */
public class PurchaseRefundConfirmResultDto implements Serializable {
	private static final long							serialVersionUID	= 1L;
	private Integer										total;						// 满足该条件的总记录数
	private Integer										pageNo;						// 当前页号
	private Integer										pageSize;					// 每页记录数
	private List<PurchaseRefundConfirmResultResultDto>	result;						// 没有满足条件记录时数组元素为0，每个元素的详细数据结构。



	public Integer getTotal() {
		return total;
	}



	public void setTotal(Integer total) {
		this.total = total;
	}



	public Integer getPageNo() {
		return pageNo;
	}



	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}



	public Integer getPageSize() {
		return pageSize;
	}



	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}



	public List<PurchaseRefundConfirmResultResultDto> getResult() {
		return result;
	}



	public void setResult(List<PurchaseRefundConfirmResultResultDto> result) {
		this.result = result;
	}

}
