package com.yatang.sc.facade.dao;

import java.util.List;
import java.util.Set;




import org.apache.ibatis.annotations.Param;

import com.yatang.sc.facade.domain.RoleResourcePo;
import com.yatang.sc.facade.domain.UserRolePo;

public interface UserRoleDao {
    int deleteByPrimaryKey(Integer id);
    
    int deleteByUserAndRole(@Param("userId")Long userId,@Param("roleId")Long roleId);

    int insert(UserRolePo record);

    int insertSelective(UserRolePo record);

    UserRolePo selectByPrimaryKey(Integer id);
    
    Set<Long> selectByUserId(Long userId);
    
    void deleteByUserIdAndRolesId(@Param("userId")Long userId,@Param("roleIds")String[] roleIds);
    
    void insertByBatch(List<UserRolePo> attachList);

    int countUserByRole(Long roleId);

    int deleteUserRoleRelation(Long roleId);

    List<Long> selectUserIdsByRoleId(Long id);
}