package com.yatang.sc.facade.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.dao.DepositRecordDao;
import com.yatang.sc.facade.domain.DepositRecordPo;
import com.yatang.sc.facade.service.DepositService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.yatang.sc.facade.enums.PublicEnum.*;

@Service
@Transactional
public class DepositServiceImpl implements DepositService {

  @Autowired
  private DepositRecordDao depositRecordDao;


  @Override
  public List<DepositRecordPo> depositRecords(Map<String, Object> paramMap) {
    int page = MapUtils.getInteger(paramMap, MAP_PAGE_NUM.getCodeStr(), Integer.valueOf(DEFAULT_PAGE_NUM.getCodeStr()));
    int size = MapUtils.getInteger(paramMap, MAP_PAGE_SIZE.getCodeStr(), Integer.valueOf(DEFAULT_PAGE_SIZE.getCodeStr()));
    PageHelper.startPage(page, size);
    return depositRecordDao.listByParam(paramMap);
  }

  @Override
  public void createRecord(DepositRecordPo po) {
    depositRecordDao.insertSelective(po);
  }
}
