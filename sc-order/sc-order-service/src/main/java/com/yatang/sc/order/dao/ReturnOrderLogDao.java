package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.ReturnOrderLogPo;

public interface ReturnOrderLogDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ReturnOrderLogPo record);

    int insertSelective(ReturnOrderLogPo record);

    ReturnOrderLogPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReturnOrderLogPo record);

    int updateByPrimaryKey(ReturnOrderLogPo record);
}