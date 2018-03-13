package com.yatang.sc.facade.dao;

import java.util.List;
import java.util.Set;

import com.yatang.sc.facade.domain.RolePo;
import org.apache.ibatis.annotations.Param;

public interface RoleDao {
    int deleteByPrimaryKey(Long id);

    int insert(RolePo record);

    int insertSelective(RolePo record);

    RolePo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RolePo record);

    int updateByPrimaryKey(RolePo record);
    
    List<RolePo> findAll();

    List<RolePo> queryRoleByParam(RolePo rolePo);

    List<RolePo> findRolesByIds(@Param("roleIds") Set<Long> roleIds);
}