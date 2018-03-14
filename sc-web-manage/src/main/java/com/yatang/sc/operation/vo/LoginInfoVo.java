package com.yatang.sc.operation.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @描述 登录用户数据对象，包括基本信息,可访问的顶级系统菜单
 * @作者 libin
 * @创建时间 2017年6月26日-下午2:17:31
 * @版本 
 *
 */
@Getter
@Setter
public class LoginInfoVo implements Serializable {

	
	private static final long serialVersionUID = 6571078193766076994L;	
	// 用户id
	private Integer userId;
	// 账号登录名
	private String employeeLogin;
	// 员工姓名
	private String employeeName;
	// 员工电话号码
	private String employeePhonenumber;
	// 员工所属公司Id
	private Integer employeeCompanyId;
	//员工所属公司名称
	private String companyName;
	//员工所属公司类型
    private int  companyType;
    //员工角色
    @JsonIgnore
	private List<RoleVo> roles;
	//顶级系统菜单
	private List<MenuVo> topMenus;
}
