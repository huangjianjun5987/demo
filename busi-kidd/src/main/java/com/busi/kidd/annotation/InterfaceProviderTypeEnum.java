package com.busi.kidd.annotation;

import com.google.common.base.Objects;

/**
 * 接口提供类型枚举
 * 
 * @author yangqingsong
 *
 */
public enum InterfaceProviderTypeEnum {

	GLINK("际链", "glink"), XINYI("心怡", "xinyi"), JUBAN("桔瓣", "juban");

	private final String name;
	private final String value;

	private InterfaceProviderTypeEnum(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}


	public static String getInterfaceProviderName(String interfaceProvider) {
		if (Objects.equal(interfaceProvider, InterfaceProviderTypeEnum.GLINK.getValue())) {
			return InterfaceProviderTypeEnum.GLINK.getName();
		} else if (Objects.equal(interfaceProvider, InterfaceProviderTypeEnum.XINYI.getValue())) {
			return InterfaceProviderTypeEnum.XINYI.getName();
		} else if (Objects.equal(interfaceProvider, InterfaceProviderTypeEnum.JUBAN.getValue())) {
			return InterfaceProviderTypeEnum.JUBAN.getName();
		} else {
			throw new RuntimeException("仓库服务商:[" + interfaceProvider + "]，未对接");
		}
	}

	public static String getInterfaceProvider(String warehouseService) {
		if (Objects.equal(warehouseService, InterfaceProviderTypeEnum.GLINK.getName())) {
			return InterfaceProviderTypeEnum.GLINK.getValue();
		} else if (Objects.equal(warehouseService, InterfaceProviderTypeEnum.XINYI.getName())) {
			return InterfaceProviderTypeEnum.XINYI.getValue();
		} else if (Objects.equal(warehouseService, InterfaceProviderTypeEnum.JUBAN.getName())) {
			return InterfaceProviderTypeEnum.JUBAN.getValue();
		} else {
			throw new RuntimeException("仓库服务商:[" + warehouseService + "]，未对接");
		}
	}

}
