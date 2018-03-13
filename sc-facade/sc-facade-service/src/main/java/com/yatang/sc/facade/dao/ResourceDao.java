package com.yatang.sc.facade.dao;

import java.util.List;
import java.util.Set;

import com.yatang.sc.facade.domain.ResourcePo;
import org.apache.ibatis.annotations.Param;

public interface ResourceDao {
    int deleteByPrimaryKey(Long id);

    int insert(ResourcePo record);

    int insertSelective(ResourcePo record);

    ResourcePo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ResourcePo record);

    int updateByPrimaryKey(ResourcePo record);
    
    List<ResourcePo> findAll();
    
    List<ResourcePo> queryResByIds(@Param("resIds") Set<Long> resIds);

    List<ResourcePo> queryPermissionsByRoleIds(@Param("roleIds") Set<Long> roleIds);

    List<ResourcePo> selectByParentId(Long id);
}