package com.yatang.sc.facade.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.dao.UserDao;
import com.yatang.sc.facade.dao.UserRoleDao;
import com.yatang.sc.facade.domain.ResourcePo;
import com.yatang.sc.facade.domain.RolePo;
import com.yatang.sc.facade.domain.UserPo;
import com.yatang.sc.facade.domain.UserRolePo;
import com.yatang.sc.facade.service.RoleService;
import com.yatang.sc.facade.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @描述: 用户服务类.
 * @作者: huangjianjun
 * @创建时间: 2017年6月9日 下午3:06:47 .
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordHelper passwordHelper;
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private UserRoleDao userRoleDao;
    

    /**
     * 创建用户
     * @param user
     */
    public Boolean createUser(UserPo user) {
        //加密密码
    	passwordHelper.encryptPassword(user);
        userDao.insertSelective(user);
        if(user.getRoleIds() != null){
        	UserRolePo record = null;
        	for (Long roleId : user.getRoleIds()) {
        		record = new UserRolePo();
        		record.setUserId(user.getId());
        		record.setRoleId(roleId);
        		userRoleDao.insert(record);
			}
        }
        return true;
    }

    @Override
    public Boolean updateUser(UserPo user) {
    	if(user == null){
    		return false;
    	}
//    	Set<Long> newRoleIds = user.getRoleIds();
//    	if(newRoleIds != null){
//    		Set<Long> haveRoles = this.findRolesId(user.getUserName());
//    		Set<Long> diffRoles = CollectionUtils.getDiffrent(newRoleIds, haveRoles);
//    		Set<Long> insertResult = new HashSet<Long>();
//			Set<Long> deleteResult = new HashSet<Long>();
//    		for (Long diff : diffRoles) {
//    			//如果差异值在不在选中列表但是在原有权限列表则删除原有权限值
//				if(!newRoleIds.contains(diff) && haveRoles.contains(diff)){
//					deleteResult.add(diff);
//				}
//				//如果差异值在在选中列表但是不在在原有权限列表则新增差异权限值
//				if(newRoleIds.contains(diff) && !haveRoles.contains(diff)){
//					insertResult.add(diff);
//				}
//			}
//    		if(insertResult.size() >= 1){
//    			List<UserRolePo> attachList = new ArrayList<UserRolePo>();
//    			UserRolePo record = null;
//    			for (Long roleId : insertResult) {
//    				record = new UserRolePo();
//    				record.setUserId(user.getId());
//    				record.setRoleId(roleId);
//    				attachList.add(record);
//    			}
//    			userRoleDao.insertByBatch(attachList);
//    		}
//    		if(deleteResult.size() >= 1){
//    			userRoleDao.deleteByUserIdAndRolesId(user.getId(), deleteResult.toArray(new Long[0]));
//    		}
//    	}
//    	passwordHelper.encryptPassword(user);
        return userDao.updateByPrimaryKeySelective(user) >= 1;
    }

    @Override
    public Boolean deleteUser(Long userId) {
        return userDao.deleteByPrimaryKey(userId) >= 1;
    }

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    @SuppressWarnings("null")
	public void changePassword(Long userId, String newPassword) {
        UserPo user = userDao.selectByPrimaryKey(userId);
        if(user != null) {
        	user.setPassword(newPassword);
        	passwordHelper.encryptPassword(user);
        	userDao.updateByPrimaryKeySelective(user);
        }
    }

    @Override
    public UserPo findOne(Long userId) {
        return userDao.selectByPrimaryKey(userId);
    }

    @Override
    public List<UserPo> findAll() {
        return null;
    }

    /**
     * 根据用户名查找用户
     * @return
     */
    public UserPo findByUsername(String userName) {
        return userDao.findByUsername(userName);
    }

    /**
     * 根据用户名查找其角色
     * @return
     */
    public Set<RolePo> findRoles(String userName) {
        UserPo user = findByUsername(userName);
        if(user == null) {
            return Collections.EMPTY_SET;
        }
        return null;
    }
    
    /**
     * 根据用户名查找其角色
     * @return
     */
    public Set<Long> findRolesId(String userName) {
    	UserPo user = findByUsername(userName);
    	if(user == null) {
    		return Collections.EMPTY_SET;
    	}
    	return null;
    }

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username) {
        UserPo user = findByUsername(username);
        if(user == null) {
            return Collections.EMPTY_SET;
        }
        return null;
    }

	@Override
	public PageInfo<UserPo> findByPage(UserPo record,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<UserPo> pos =  userDao.findByExample(record);
		PageInfo<UserPo> pageInfo = new PageInfo<UserPo>(pos);
		return pageInfo;
	}

	@Override
	public Set<ResourcePo> findMenusPermissions(String username) {
       UserPo user = findByUsername(username);
        if(user == null) {
            return Collections.EMPTY_SET;
        }
        return null;
	}

	@Override
	public void authorRoles(Long userId, String roleIds) {
		String[] roleArr = roleIds.split(",");
		UserRolePo record = null;
		List<UserRolePo> attachList = new ArrayList<UserRolePo>();
		Set<Long> haveRoles = userRoleDao.selectByUserId(userId);
		for (String roleId : roleArr) {
			if(haveRoles.contains(Long.parseLong(roleId))){
				continue;
			}
			record = new UserRolePo();
			record.setUserId(userId);
			record.setRoleId(Long.parseLong(roleId));
			attachList.add(record);
		}
		if(attachList.size() >=1){
			userRoleDao.insertByBatch(attachList);
		}
	}

	
	@Override
	@SuppressWarnings("null")
	public boolean changeCurUserPassword(Long userId, String oldPassword, String newPassword) {
		 UserPo user = userDao.selectByPrimaryKey(userId);
         if(user != null) {
			 String persistOldPassword = user.getPassword();
			 user.setPassword(oldPassword);
			 passwordHelper.encryptPassword(user);
    		 if(!user.getPassword().equals(persistOldPassword)){
    			 return false;
    		 }
			 user.setSalt(null);
			 user.setPassword(newPassword);
			 passwordHelper.encryptPassword(user);
        	 userDao.updateByPrimaryKeySelective(user);
         }
		return true;
	}

	@Override
	public boolean checkSpIdExist(String spId) {
		UserPo record = new UserPo();
		record.setSpId(spId);
		List<UserPo> list =userDao.findByExample(record);
		return list != null && list.size() >= 1;
	}

	@Override
	public void deleteUserRole(Long userId, String roleIds) {
//		Set<Long> haveRoles = userRoleDao.selectByUserId(userId);
//		for (String roleId : roleArr) {
//			//如果已有角色包含所选角色删除所选角色
//			if(haveRoles.contains(Long.parseLong(roleId))){
//				continue;
//			}
//		}
		String [] roleArr = roleIds.split(",");
		userRoleDao.deleteByUserIdAndRolesId(userId, roleArr);
	}

	@Override
	public boolean checkUserExist(String username) {
		UserPo userPo =userDao.findByUsername(username);
		return userPo != null;
	}

}
