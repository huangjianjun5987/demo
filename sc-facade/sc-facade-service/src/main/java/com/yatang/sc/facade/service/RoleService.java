package com.yatang.sc.facade.service;

import java.util.List;
import java.util.Set;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.domain.ResourcePo;
import com.yatang.sc.facade.domain.RolePo;
import com.yatang.sc.facade.domain.UserPo;

/**
 * @描述: 角色服务.
 * @作者: liuxiaokun
 * @创建时间: 2018年1月10日
 */
public interface RoleService {

	Boolean createRole(RolePo rolePo);

	Boolean updateRole(RolePo rolePo);

	RolePo findOne(Long roleId);

	List<RolePo> findRolesByIds(Set<Long> roleIds);

	Boolean deleteRole(List<Long> ids);

	/**
	 * 根据角色查询当前有几个关联用户
	 * @param id
	 * @return
	 */
	Boolean countUserByRole(Long id);

	List<UserPo> queryRelevantUsers(Long id);

	Boolean deleteUserRoleRelation(Long roleId);

	Boolean deleteRoleResRelation(Long roleId);

	List<ResourcePo> queryRelevantRes(Long id);

	PageInfo<RolePo> queryRoleByParam(RolePo rolePo);

    void addResToRole(RolePo convert);

	void removeResFromRole(RolePo convert);

	PageInfo<RolePo> queryRolesByLoginName(String loginName, Integer pageSize, Integer pageNum);
}
