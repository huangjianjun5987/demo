package com.yatang.sc.facade.dto.pm;

public class PmPurchaseRefundAuditResultDto extends PmPurchaseRefundDto {

	private static final long	serialVersionUID	= 1L;

	private String				processNodeName;
	private String				processNodeId;



	public String getProcessNodeId() {
		return processNodeId;
	}



	public void setProcessNodeId(String processNodeId) {
		this.processNodeId = processNodeId;
	}



	public String getProcessNodeName() {
		return processNodeName;
	}



	public void setProcessNodeName(String processNodeName) {
		this.processNodeName = processNodeName;
	}

}
