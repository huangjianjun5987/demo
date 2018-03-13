package com.yatang.sc.facade.dubboservice;

import java.util.List;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.system.ResourceDto;
import com.yatang.sc.facade.dto.system.RoleDto;
import com.yatang.sc.facade.dto.system.UserDto;

/**
 * @描述: 角色dubbo服务接口.
 * @作者: liuxiaokun
 * @创建时间: 2018/1/10
 */
public interface RoleDubboService {


    /**
     * 创建角色
     * @param record
     */
    Response<Boolean> createRole(RoleDto record);

    /**
     * 修改角色
     * @param record
     */
    Response<Boolean> updateRole(RoleDto record);
    

    /**
     * 查找角色
     * @param roleId
     * @return
     */
    Response<RoleDto> findOne(Long roleId);
    

    /**
     * 角色删除
     * @return
     */
    Response<Boolean> deleteRole(List<Long> ids);

    /**
     * 判断当前角色是否与用户有关联
     * @return
     */
    Response<Boolean> isRelatedWithUser(Long id);

    /**
     * 查询当前角色所关联的用户列表
     * @param id
     * @return
     */
    Response<List<UserDto>> queryRelevantUsers(Long id);

    /**
     * 查询当前角色关联的权限列表
     * @param id
     * @return
     */
    Response<List<ResourceDto>> queryRelevantRes(Long id);

    /**
     * 按条件查询role列表
     * @param record
     * @return
     */
    Response<PageResult<RoleDto>> queryRoleByParam(RoleDto record);

    /**
     * 为该角色增加权限
     * @param roleDto
     * @return
     */
    Response<Boolean> addResToRole(RoleDto roleDto);

    /**
     * 为该角色删除权限
     * @param roleDto
     * @return
     */
    Response<Boolean> removeResFromRole(RoleDto roleDto);

    Response<PageResult<RoleDto>> queryRolesByLoginName(String loginName, Integer pageSize, Integer pageNum);
}
