package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryIconPo implements Serializable {
	private static final long	serialVersionUID	= -4332582085007660539L;
	private String				id;

	private String				iconUrl;

	private Date				createTime;

}