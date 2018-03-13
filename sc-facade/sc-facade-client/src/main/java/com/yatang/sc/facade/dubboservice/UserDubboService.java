package com.yatang.sc.facade.dubboservice;

import java.util.List;
import java.util.Set;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.system.ResourceDto;
import com.yatang.sc.facade.dto.system.RoleDto;
import com.yatang.sc.facade.dto.system.UserDetailDto;
import com.yatang.sc.facade.dto.system.UserDto;

/**
 * @描述: 用户dubbo服务接口.
 * @作者: huangjianjun
 * @创建时间: 2017年6月8日 下午9:36:56 .
 */
public interface UserDubboService {

    /**
     * 创建用户
     * @param UserDto
     */
    public Response<Boolean> createUser(UserDto record);

    /**
     * 修改用户
     * @param UserDto
     */
    public Response<Boolean> updateUser(UserDto record);

    /**
     * 删除用户
     * @param UserDto
     */
    public Response<Boolean> deleteUser(Long userId);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public Response<Boolean> changePassword(Long userId, String newPassword);

    
    /**
     * 修改当前登录用户密码
     * @param userId
     * @param newPassword
     */
    public Response<Boolean> changeCurUserPassword(Long userId,String oldPassword, String newPassword);


    /**
     * 创建用户
     * @param UserDto
     */
    Response<UserDetailDto> findOne(Long userId);

    /**
     * 创建用户
     * @param UserDto
     */
    Response<List<UserDto>> findAll();
    
    /**
     * 查询用户分页信息
     * @param record
     * @return
     */
    Response<PageResult<UserDetailDto>> findByPage(UserDetailDto record,Integer pageNum,Integer pageSize);

    /**
     * 根据用户名查找用户
     * @param userName
     * @return
     */
    Response<UserDto> findByUsername(String userName);

    /**
     * 根据用户名查找所属角色
     * @param userName
     * @return
     */
    Response<Set<RoleDto>> findUserRoles(String userName);

    /**
     * 根据用户名查找其权限
     * @param userName
     * @return
     */
    Response<Set<String>> findPermissions(String userName);
    
    /**
     * 根据用户名查找菜单权限
     * @param userName
     * @return
     */
    Response<Set<ResourceDto>> findMenusPermissions(String userName);

    /**
     * @Description: 用户授权角色
     * @author huangjianjun
     * @date 2018年1月19日下午1:36:10
     */
	Response<Boolean> authorRoles(Long userId,String roleIds);
	/**
	 * @Description: 删除用户角色
	 * @author huangjianjun
	 * @date 2018年1月19日下午1:36:10
	 */
	Response<Boolean> deleteUserRoles(Long userId,String roleIds);

}
