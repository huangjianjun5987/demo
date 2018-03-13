package com.yatang.sc.bpm.dto;

/**
 * @描述: 流程定义信息传输对象
 * @作者: huangjianjun
 * @创建时间: 2017年11月21日-下午5:42:52 .
 */
public class ProcessDefinitionDto implements java.io.Serializable{
	private static final long	serialVersionUID	= -7376489943158725797L;
	private String	id;						// 流程定义id
	private String	name;					// 流程定义名称
	private String	key;					// 流程定义key
	private Integer	version;				// 流程定义版本号
	private String	deploymentId;			// 部署id
	private String	resourceName;			// 流程定义规则文件名称
	private String	diagramResourceName;	// 流程定义规则图片名称



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



	public String getKey() {
		return key;
	}



	public void setKey(String key) {
		this.key = key;
	}



	public Integer getVersion() {
		return version;
	}



	public void setVersion(Integer version) {
		this.version = version;
	}



	public String getDeploymentId() {
		return deploymentId;
	}



	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}



	public String getResourceName() {
		return resourceName;
	}



	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}



	public String getDiagramResourceName() {
		return diagramResourceName;
	}



	public void setDiagramResourceName(String diagramResourceName) {
		this.diagramResourceName = diagramResourceName;
	}
}
