package com.yatang.sc.facade.dto.prod.place;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AreaStoreDto implements Serializable{

	private static final long serialVersionUID = 8030237601960320434L;

	/**
	 * 门店Id
	 */
	private String storeId;

	/**
	 * 门店名称
	 */
	private String storeName;

	/**
	 * 所属子公司名称
	 */
	private String branchCompanyName;
	/**
	 * 门店所属城市的名名称
	 */
	private String cityName;

	/**
	 * 门店所属区的名称
	 */
	private String districtName;

	/**
	 * 门店所属省份的名字
	 */
	private String provinceName;


}
