package com.yatang.sc.facade.dto.pm;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClosePurchaseDto {

	private List<Long>	ids;
	private String		modifyUserId;
}
