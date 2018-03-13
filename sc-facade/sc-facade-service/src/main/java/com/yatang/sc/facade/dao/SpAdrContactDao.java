package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.SpAdrContactPo;

public interface SpAdrContactDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SpAdrContactPo record);

    int insertSelective(SpAdrContactPo record);

    SpAdrContactPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SpAdrContactPo record);

}