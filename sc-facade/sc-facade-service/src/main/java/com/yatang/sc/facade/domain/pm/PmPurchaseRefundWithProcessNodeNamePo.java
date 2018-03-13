package com.yatang.sc.facade.domain.pm;

/**
 * 
 * <class description>包含审批日志的退货单
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年10月23日
 */
public class PmPurchaseRefundWithProcessNodeNamePo extends PmPurchaseRefundPo {

	private static final long	serialVersionUID	= 1L;
	private String				processNodeName;
	private String				processNodeId;



	public String getProcessNodeName() {
		return processNodeName;
	}



	public void setProcessNodeName(String processNodeName) {
		this.processNodeName = processNodeName;
	}



	public String getProcessNodeId() {
		return processNodeId;
	}



	public void setProcessNodeId(String processNodeId) {
		this.processNodeId = processNodeId;
	}

}
