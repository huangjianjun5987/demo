package com.yatang.sc.bpm.dto;

import java.io.File;
import java.io.Serializable;

/**
 * @描述: 流程参数DTO
 * @作者: huangjianjun
 * @创建时间: 2017年11月24日-下午6:42:14 .
 */
public class WorkFlowDto implements Serializable{
	
	private static final long	serialVersionUID	= 3483498210833981229L;
	private File				file;										// 流程定义部署文件
	private String				filename;									// 流程定义名称
	private Long				id;											// 申请单ID
	private Integer				type;										// 申请单类型(0:采购单;1:退货单;2:商品采购维护;3商品销售维护)
	private String				deploymentId;								// 部署对象ID
	private String				imageName;									// 资源文件名称
	private String				taskId;										// 任务ID
	private String				outcome;									// 连线名称
	private String				comment;									// 备注
	private String				orderNo;									// 单号
	private String				userId;										// 操作人ID
	private String				userName;									// 操作人


	public File getFile() {
		return file;
	}



	public void setFile(File file) {
		this.file = file;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getFilename() {
		return filename;
	}



	public void setFilename(String filename) {
		this.filename = filename;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Integer getType() {
		return type;
	}



	public void setType(Integer type) {
		this.type = type;
	}



	public String getDeploymentId() {
		return deploymentId;
	}



	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}



	public String getImageName() {
		return imageName;
	}



	public void setImageName(String imageName) {
		this.imageName = imageName;
	}



	public String getTaskId() {
		return taskId;
	}



	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}



	public String getOutcome() {
		return outcome;
	}



	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}



	public String getComment() {
		return comment;
	}



	public void setComment(String comment) {
		this.comment = comment;
	}



	public String getOrderNo() {
		return orderNo;
	}



	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
	
}
