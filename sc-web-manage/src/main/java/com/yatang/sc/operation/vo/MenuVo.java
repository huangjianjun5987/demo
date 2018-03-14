package com.yatang.sc.operation.vo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * @描述 系统菜单传输对象
 * @作者 libin
 * @创建时间 2017年6月26日-上午11:27:40
 * @版本 1.0
 *
 */
@Getter
@Setter
public class MenuVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -779389492531353452L;
	// 权限id
	private Integer authorityId;
	// 权限名
	@JsonIgnore
	private String authorityName;
	// 权限编码
	private String authorityCode;
	// 备注
	@JsonIgnore
	private String authorityRemark;
	// 创建时间
	@JsonIgnore
	private Date authorityCreateTime;
	// 创建人
	@JsonIgnore
	private Integer authorityCreateUserId;
	// 最后一次修改时间
	@JsonIgnore
	private Date authorityUpdateTime;
	// 最后一次修改人
	@JsonIgnore
	private Integer authorityUpdateUserId;
	// 显示名称
	private String displayName;
	// url
	@JsonIgnore
	private String menuUrl;
	// 父节点
	@JsonIgnore
	private Integer parentId;
	// 资源排序
	@JsonIgnore
	private Integer authoritySeq;
	// 权限类型 0=菜单，1=页面，2=权限
	@JsonIgnore
	private Integer authorityType;
	// 子节点
	private List<MenuVo> menuList = new ArrayList<MenuVo>();
	// 登录名
	@JsonIgnore
	private String loginName;

}