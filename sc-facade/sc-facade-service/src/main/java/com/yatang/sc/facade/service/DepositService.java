package com.yatang.sc.facade.service;

import com.yatang.sc.facade.domain.DepositRecordPo;

import java.util.List;
import java.util.Map;

/**
 * @描述: 供应商服务接口.
 * @作者: yiepng
 * @创建时间: 2017年06月02日16:45:53
 * @版本: 1.0 .
 */
public interface DepositService {

  List<DepositRecordPo> depositRecords(Map<String, Object> paramMap);

  void createRecord(DepositRecordPo po);

}
