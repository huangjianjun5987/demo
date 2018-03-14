package com.yatang.sc.operation.vo;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * <class description>ZIP文件上传信息
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年11月7日
 */
public class ZiptoftpInfoVo implements Serializable {
	private Integer				Id;
	// 描述
	private String				description;
	// 生效时间
	private Date				effectTime;
	// 有效时间
	private Date				invalidTime;
	// 活动名称
	private String				activityName;
	// 上传用户ID
	@NotEmpty
	private String				uploadUserId;

	private static final long	serialVersionUID	= 1L;



	public Integer getId() {
		return Id;
	}



	public void setId(Integer id) {
		Id = id;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
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
		this.activityName = activityName;
	}



	public String getUploadUserId() {
		return uploadUserId;
	}



	public void setUploadUserId(String uploadUserId) {
		this.uploadUserId = uploadUserId;
	}

}