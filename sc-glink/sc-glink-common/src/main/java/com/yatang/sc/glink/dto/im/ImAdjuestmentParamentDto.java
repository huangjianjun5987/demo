package com.yatang.sc.glink.dto.im;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @描述:传入际链的参数信息
 * @类名:ImAdjuestmentParamentDto
 * @作者: lvheping
 * @创建时间: 2017/9/4 18:00
 * @版本: v1.0
 */

public class ImAdjuestmentParamentDto implements Serializable {
	private static final long	serialVersionUID	= -6638727707669964246L;
	//@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date				startTime;									// 查询开始时间
	//@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date				endTime;									// 查询截止时间
	private String				ownerCode;									// 货主机构编码（子公司编码）
	private String				projectCode;								// 项目编码
	private String				itemCode;									// 商品编码
	private String				inventoryType;								// 库存类型
	private int					pageNo;										// 当前页码
	private int					pageSize;									// 条数

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getOwnerCode() {
		return ownerCode;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public String getInventoryType() {
		return inventoryType;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setStartTime(Date startTime) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String format = simpleDateFormat.format(startTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date parse = sdf.parse(format + " 00:00:00");
			this.startTime = parse;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void setEndTime(Date endTime) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String format = simpleDateFormat.format(endTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date parse = sdf.parse(format + " 23:59:59");
			this.endTime = parse;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "ImAdjuestmentParamentDto{" +
				"startTime=" + startTime +
				", endTime=" + endTime +
				", ownerCode='" + ownerCode + '\'' +
				", projectCode='" + projectCode + '\'' +
				", itemCode='" + itemCode + '\'' +
				", inventoryType='" + inventoryType + '\'' +
				", pageNo=" + pageNo +
				", pageSize=" + pageSize +
				'}';
	}
}
