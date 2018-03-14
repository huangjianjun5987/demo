package com.yatang.sc.operation.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yatang.sc.web.jackson.HtmlFileUrlDeserializer;
import com.yatang.sc.web.jackson.HtmlFileUrlSerializer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaticPageInfoVo implements Serializable {
	private static final long	serialVersionUID	= 612926799564655297L;
	private Integer				id;										// 序号

	private String				name;										// 静态页名称
	@JsonSerialize(using = HtmlFileUrlSerializer.class)
	@JsonDeserialize(using = HtmlFileUrlDeserializer.class)
	private String				linkUrl;									// 链接地址

	private String				description;								// 描述

	private Date				createTime;								// 创建时间

	private String				createUserId;								// 创建人id

	private Date				updateTime;								// 最后修改时间

	private String				updateUserId;								// 修改人id

	private String				pageContent;								// 页面编辑器编辑的内容

	private String				createUser;								// 创建人姓名

	private String				updateuser;								// 修改人姓名

}