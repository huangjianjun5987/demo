package com.yatang.sc.facade.dto.supplier;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpAdrContactDto implements Serializable {

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

	@JsonIgnore
	private SpAdrContactDto 	modification;					// 联系人信息

}