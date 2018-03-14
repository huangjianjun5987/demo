package com.yatang.sc.purchase.dto.returned;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: App退换货查询条件
 * @类名: ReturnRequestQueryParamDto
 * @作者: kangdong
 * @创建时间: 2017/10/17 15:11
 * @版本: v1.0
 */
public class ReturnRequestQueryParamDto implements Serializable {

	private static final long serialVersionUID = -7220810096194528778L;
	private String profileId;

	private String state;

	private Integer pageNum;

	private Integer pageSize;

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
