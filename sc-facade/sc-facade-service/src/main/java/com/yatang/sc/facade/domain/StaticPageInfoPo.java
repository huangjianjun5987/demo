package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaticPageInfoPo implements Serializable {
	private Integer				id;							// 序号ID

	private String				name;						// 静态页名称

	private String				linkUrl;					// 链接地址

	private String				description;				// 描述

	private Date				createTime;					// 创建时间

	private String				createUserId;				// 创建人id

	private Date				updateTime;					// 修改时间

	private String				updateUserId;				// 修改人id

	private String				pageContent;				// 编辑器编辑内容

	private String				createUser;					// 创建人姓名

	private String				updateuser;					// 修改人姓名

	private static final long	serialVersionUID	= 1L;
}