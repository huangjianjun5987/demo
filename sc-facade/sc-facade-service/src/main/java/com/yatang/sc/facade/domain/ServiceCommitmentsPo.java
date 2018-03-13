package com.yatang.sc.facade.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceCommitmentsPo implements Serializable {
	private Integer				id;

	private String				promiseContent;

	private Integer				sort;

	private Integer				status;

	private static final long	serialVersionUID	= 1L;

}