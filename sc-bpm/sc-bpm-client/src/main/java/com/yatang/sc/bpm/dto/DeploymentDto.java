package com.yatang.sc.bpm.dto;

import java.util.Date;

/**
 * @描述: 流程部署信息数据传输对象
 * @作者: huangjianjun
 * @创建时间: 2017年11月21日-下午5:38:21 .
 */
public class DeploymentDto implements java.io.Serializable{
	private static final long	serialVersionUID	= 6196247617770180984L;
	private String	id;				// 部署id
	private String	name;			// 部署名称
	private Date	deploymentTime;	// 部署时间



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Date getDeploymentTime() {
		return deploymentTime;
	}



	public void setDeploymentTime(Date deploymentTime) {
		this.deploymentTime = deploymentTime;
	}
}
