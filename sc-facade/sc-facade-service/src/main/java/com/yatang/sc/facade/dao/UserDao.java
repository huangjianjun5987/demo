package com.yatang.sc.facade.dao;

import java.util.List;

import com.yatang.sc.facade.domain.UserPo;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    int deleteByPrimaryKey(Long id);

    int insert(UserPo record);

    int insertSelective(UserPo record);

    UserPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserPo record);

    int updateByPrimaryKey(UserPo record);
    
    UserPo findByUsername(String userName);

	List<UserPo> findByExample(UserPo record);

    List<UserPo> findUsersByIds(@Param("userIds") List<Long> userIds);
}