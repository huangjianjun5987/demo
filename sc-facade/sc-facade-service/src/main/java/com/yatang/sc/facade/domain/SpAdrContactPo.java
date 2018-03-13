package com.yatang.sc.facade.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpAdrContactPo implements Serializable {

	private static final long	serialVersionUID	= -9003207511138119530L;

	private Integer				id;

	private String				providerName;

	private String				providerPhone;

	private String				providerEmail;

	private String				purchaseName;

	private String				purchasePhone;

	private String				purchaseEmail;

	private Integer				modifyId;

	private Integer				status;

	private SpAdrContactPo		modification;
}