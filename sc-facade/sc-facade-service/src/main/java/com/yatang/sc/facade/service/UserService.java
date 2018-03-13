package com.yatang.sc.facade.service;

import java.util.List;
import java.util.Set;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.domain.ResourcePo;
import com.yatang.sc.facade.domain.RolePo;
import com.yatang.sc.facade.domain.UserPo;

/**
 * @描述: 用户服务接口.
 * @作者: huangjianjun
 * @创建时间: 2017年6月8日 下午9:36:56 .
 */
public interface UserService {

    /**
     * 创建用户
     * @param UserPo
     */
    public Boolean createUser(UserPo userPo);

    public Boolean updateUser(UserPo userPo);

    public Boolean deleteUser(Long userId);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword);


    UserPo findOne(Long userId);

    List<UserPo> findAll();
    
    /**
     * 查询用户分页信息
     * @param record
     * @return
     */
    PageInfo<UserPo> findByPage(UserPo record,Integer pageNum,Integer pageSize);

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public UserPo findByUsername(String userName);

    /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    public Set<RolePo> findRoles(String userName);

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String userName);
    
    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public Set<ResourcePo> findMenusPermissions(String userName);

    /**
     * @Description: 用户授权角色
     * @author huangjianjun
     * @date 2018年1月19日下午1:44:36
     */
	public void authorRoles(Long userId, String roleIds);

	/**
	 * @Description:修改当前登录用户密码 
	 * @author huangjianjun
	 * @date 2018年1月23日下午2:24:36
	 */
	public boolean changeCurUserPassword(Long userId, String oldPassword, String newPassword);
	
	/**
	 * @Description:校验当前供应商ID是否存在 
	 * @author huangjianjun
	 * @date 2018年1月23日下午2:24:36
	 */
	public boolean checkSpIdExist(String spId);
	
	/**
	 * @Description:删除用户所选角色 
	 * @author huangjianjun
	 * @date 2018年1月23日下午2:24:36
	 */
	public void deleteUserRole(Long userId , String roleIds);

	/**
	 * @Description: 校验账号是否存在
	 * @author tankejia
	 * @date 2018/2/2- 16:16
	 * @param username
	 */
	boolean checkUserExist(String username);

}
