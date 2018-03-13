package com.yatang.sc.facade.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendKeywordsPo implements Serializable {
	private Integer				id;

	private Integer				sort;

	private String				content;

	private Integer				inputKey;

	private static final long	serialVersionUID	= 1L;

}