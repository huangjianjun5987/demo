package com.yatang.sc.facade.dto.prod.place;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @描述: 区域组查询条件
 * @作者: zhudanning
 * @创建时间: 2018/1/11-15:03 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class ParamAreaGroupDto implements Serializable {

	private static final long serialVersionUID = 4304990987302453945L;

	/**
	 * 区域组编码
	 */
	private String areaGroupCode;

	/**
	 * 区域组名称
	 */
	private String areaGroupName;

	/**
	 * 区域组类型
	 */
	private String type;

	/**
	 * 区域组地点编码
	 */
	private String areaAddrCode;

	/**
	 * 区域组地点名称
	 */
	private String areaAddrName;

}
