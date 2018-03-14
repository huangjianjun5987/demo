package com.yatang.sc.operation.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <class description>ZIP文件上传信息
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年11月7日
 */
public class ZiptoftpInfoResultVo implements Serializable {
	private Integer				id;

	private String				rootPath;

	private Date				uploadTime;

	private String				description;

	private Date				effectTime;

	private Date				invalidTime;

	private String				activityName;

	private String				zipName;

	private String				uploadUserId;

	private static final long	serialVersionUID	= 1L;



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getRootPath() {
		return rootPath;
	}



	public void setRootPath(String rootPath) {
		this.rootPath = rootPath == null ? null : rootPath.trim();
	}



	public Date getUploadTime() {
		return uploadTime;
	}



	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}



	public Date getEffectTime() {
		return effectTime;
	}



	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}



	public Date getInvalidTime() {
		return invalidTime;
	}



	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}



	public String getActivityName() {
		return activityName;
	}



	public void setActivityName(String activityName) {
		this.activityName = activityName == null ? null : activityName.trim();
	}



	public String getZipName() {
		return zipName;
	}



	public void setZipName(String zipName) {
		this.zipName = zipName == null ? null : zipName.trim();
	}



	public String getUploadUserId() {
		return uploadUserId;
	}



	public void setUploadUserId(String uploadUserId) {
		this.uploadUserId = uploadUserId == null ? null : uploadUserId.trim();
	}
}