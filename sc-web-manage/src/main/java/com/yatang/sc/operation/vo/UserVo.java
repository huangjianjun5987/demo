package com.yatang.sc.operation.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 用户VO.
 * @作者: libin
 * @创建时间: 2017年6月13日 下午1:59:04 .
 */
@Getter
@Setter
public class UserVo implements Serializable {

	private static final long	serialVersionUID	= -3353562447177674658L;

	// 用户id
	private Integer				userId;
	// 账号登录名
	private String				employeeLogin;
	// 密码
	// private String employeePassword;
	// 员工姓名
	private String				employeeName;
	// 性员工性别 0=男 1=女
	private int					employeeSex;
	// 员工年龄
	private String				employeeAge;
	// 员工电话号码
	private String				employeePhonenumber;
	// 员工所属公司Id
	private Integer				employeeCompanyId;
	// 员工所属公司名称
	private String				companyName;
	// 员工居住地址
	private String				employeeAddress;
	// 员工在职状态1=在职 2=离职
	private int					employeeStatus;
	// 员工入职时间
	private Date				employeeJoinDate;
	// 修改者ID
	private String				modifierId;
	// 修改者
	private String				modifier;
	// 修改时间
	private Date				gmtModify;
	// 是否删除 0=未删除，1=删除
	private int					isDeleted;
	// 金融账号
	private String				financeAccount;
	// 员工类型
	private Integer				employeeType;
	// 员工所属公司类型
	private int					companyType;

	private List<RoleVo>		roles;

	private List<MenuVo>		topMenus;

	// public List<LeftMenuVO> getMenus() {
	// return menus;
	// }
	//
	// public void setMenus(List<LeftMenuVO> menus) {
	// this.menus = menus;
	// }

	// private Long id;
	//
	// private String userName;
	//
	// private Boolean locked;
	//
	// private String fullName;
	//
	// private String position;
	//
	// private String mobile;
	//
	// private String email;
	//
	// private Date createTime;
	//
	// private Date updateTime;
	//
	// private Set<Long> roleIds; // 拥有的角色列表
	//
	// private Set<ResourceVo> menus; // 拥有的一级、二级菜单列表
	//
	// public Long getId() {
	// return id;
	// }
	//
	// public void setId(Long id) {
	// this.id = id;
	// }
	//
	// public String getUserName() {
	// return userName;
	// }
	//
	// public void setUserName(String userName) {
	// this.userName = userName;
	// }
	//
	// public Boolean getLocked() {
	// return locked;
	// }
	//
	// public void setLocked(Boolean locked) {
	// this.locked = locked;
	// }
	//
	// public String getFullName() {
	// return fullName;
	// }
	//
	// public void setFullName(String fullName) {
	// this.fullName = fullName;
	// }
	//
	// public String getPosition() {
	// return position;
	// }
	//
	// public void setPosition(String position) {
	// this.position = position;
	// }
	//
	// public String getMobile() {
	// return mobile;
	// }
	//
	// public void setMobile(String mobile) {
	// this.mobile = mobile;
	// }
	//
	// public String getEmail() {
	// return email;
	// }
	//
	// public void setEmail(String email) {
	// this.email = email;
	// }
	//
	// public Date getCreateTime() {
	// return createTime;
	// }
	//
	// public void setCreateTime(Date createTime) {
	// this.createTime = createTime;
	// }
	//
	// public Date getUpdateTime() {
	// return updateTime;
	// }
	//
	// public void setUpdateTime(Date updateTime) {
	// this.updateTime = updateTime;
	// }
	//
	// public Set<Long> getRoleIds() {
	// return roleIds;
	// }
	//
	// public void setRoleIds(Set<Long> roleIds) {
	// this.roleIds = roleIds;
	// }
	//
	// public Set<ResourceVo> getMenus() {
	// return menus;
	// }
	//
	// public void setMenus(Set<ResourceVo> menus) {
	// this.menus = menus;
	// }

}
