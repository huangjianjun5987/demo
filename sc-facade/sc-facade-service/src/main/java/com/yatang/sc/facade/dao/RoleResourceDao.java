package com.yatang.sc.facade.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.yatang.sc.facade.domain.RoleResourcePo;

public interface RoleResourceDao {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleResourcePo record);

    int insertSelective(RoleResourcePo record);

    RoleResourcePo selectByPrimaryKey(Integer id);

    Set<Long> selectByRoleId(Long roleId);
    
    int deleteByRoleIdAndResId(@Param("roleId")Long roleId,@Param("resourceIds")Set<Long> resourceIds);
    
    int deleteRoleResRelation(Long roleId);

    int countRelatedResWithRole(Long resourceId);

    int deleteResRoleRelation(Long resourceId);
}