package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.DepositRecordPo;

import java.util.List;
import java.util.Map;

public interface DepositRecordDao {

  int deleteByPrimaryKey(String id);

  int insert(DepositRecordPo record);

  int insertSelective(DepositRecordPo record);

  DepositRecordPo queryById(String id);

  int updateByPrimaryKeySelective(DepositRecordPo record);

  int updateByPrimaryKey(DepositRecordPo record);

  /**
   * 根据条件查询供应商保证金记录列表
   *
   * @param paramMap
   * @return 返回集合
   */
  List<DepositRecordPo> listByParam(Map<String, Object> paramMap);

}