package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.SpAdrDeliveryPo;

import java.util.Set;

public interface SpAdrDeliveryDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SpAdrDeliveryPo record);

    int insertSelective(SpAdrDeliveryPo record);

    SpAdrDeliveryPo selectByPrimaryKey(Integer id);

    Set<SpAdrDeliveryPo> selectDeliveryList(Integer id);

    int updateByPrimaryKeySelective(SpAdrDeliveryPo record);

    int deleteBySupplierAddId(Integer addId);
}