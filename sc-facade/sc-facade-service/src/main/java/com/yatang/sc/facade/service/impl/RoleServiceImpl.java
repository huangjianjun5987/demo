package com.yatang.sc.facade.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.dao.UserDao;
import com.yatang.sc.facade.dao.UserRoleDao;
import com.yatang.sc.facade.domain.UserPo;
import com.yatang.sc.facade.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yatang.sc.facade.dao.RoleDao;
import com.yatang.sc.facade.dao.RoleResourceDao;
import com.yatang.sc.facade.domain.ResourcePo;
import com.yatang.sc.facade.domain.RolePo;
import com.yatang.sc.facade.domain.RoleResourcePo;
import com.yatang.sc.facade.service.RoleService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @描述: 角色服务类.
 * @作者: liuxiaokun
 * @创建时间: 2018/1/10 下午3:06:25 .
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleResourceDao roleResDao;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private UserDao userDao;

	@Override
    public Boolean createRole(RolePo role) {
		role.setCreateTime(new Date());
		role.setUpdateTime(new Date());
        return roleDao.insertSelective(role) >= 1;
    }

	@Override
    public Boolean updateRole(RolePo role) {
		role.setUpdateTime(new Date());
        return roleDao.updateByPrimaryKeySelective(role) >= 1;
    }

    @Override
    public Boolean deleteRole(List<Long> roleIds) {
		for(Long roleId : roleIds){
			deleteRoleResRelation(roleId);
			deleteUserRoleRelation(roleId);
			roleDao.deleteByPrimaryKey(roleId);
		}
        return true;
    }

    @Override
	public Boolean deleteUserRoleRelation(Long roleId) {
		return userRoleDao.deleteUserRoleRelation(roleId) >= 1;
	}

	@Override
	public Boolean deleteRoleResRelation(Long roleId) {
		return roleResDao.deleteRoleResRelation(roleId) >= 1;
	}

	@Override
	public List<ResourcePo> queryRelevantRes(Long id) {
		Set<Long> resIds = roleResDao.selectByRoleId(id);
		if(CollectionUtils.isEmpty(resIds)){
			return Collections.EMPTY_LIST;
		}
		List<ResourcePo> pos = resourceService.queryResByIds(resIds);
		pos = resourceService.makeTree(0L, pos);
		return pos;
	}

	@Override
	public PageInfo<RolePo> queryRoleByParam(RolePo rolePo) {
		PageHelper.startPage(rolePo.getPageNum(), rolePo.getPageSize());
		List<RolePo> rolePos = roleDao.queryRoleByParam(rolePo);
		PageInfo<RolePo> pageInfo = new PageInfo<>(rolePos);
		return pageInfo;
	}

	@Override
	public void addResToRole(RolePo convert) {
		Set<Long> resIds = convert.getResourceIds();
		for(Long resId : resIds){
			RoleResourcePo roleResourcePo = new RoleResourcePo();
			roleResourcePo.setResourceId(resId);
			roleResourcePo.setRoleId(convert.getId());
			roleResDao.insertSelective(roleResourcePo);
		}
	}

	@Override
	public void removeResFromRole(RolePo convert) {
		Set<Long> resIds = convert.getResourceIds();
		roleResDao.deleteByRoleIdAndResId(convert.getId(), resIds);
	}

	@Override
	public PageInfo<RolePo> queryRolesByLoginName(String loginName, Integer pageSize, Integer pageNum) {

		UserPo userPo = userDao.findByUsername(loginName);
		if(userPo == null){
			return new PageInfo<>();
		}
		Set<Long> roleIds = userRoleDao.selectByUserId(userPo.getId());
		if(CollectionUtils.isEmpty(roleIds)){
			return new PageInfo<>();
		}

		PageHelper.startPage(pageNum, pageSize);
		List<RolePo> rolePos = roleDao.findRolesByIds(roleIds);
		PageInfo<RolePo> pageInfo = new PageInfo<>(rolePos);

		return pageInfo;
	}

	@Override
	public Boolean countUserByRole(Long id) {
		return userRoleDao.countUserByRole(id) >= 1;
	}

	@Override
	public List<UserPo> queryRelevantUsers(Long id) {

		List<Long> userIds = userRoleDao.selectUserIdsByRoleId(id);
		if(CollectionUtils.isEmpty(userIds)){
			return Collections.EMPTY_LIST;
		}
		List<UserPo> users = userDao.findUsersByIds(userIds);
		return users;
	}

	@Override
    public RolePo findOne(Long roleId) {
        return roleDao.selectByPrimaryKey(roleId);
    }

	@Override
	public List<RolePo> findRolesByIds(Set<Long> roleIds) {
		return roleDao.findRolesByIds(roleIds);
	}

}
