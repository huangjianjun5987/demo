package com.yatang.sc.facade.dubboservice.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.domain.DepositRecordPo;
import com.yatang.sc.facade.dto.CreateDepositRecordDto;
import com.yatang.sc.facade.dubboservice.DepositWriteDubboService;
import com.yatang.sc.facade.enums.PublicEnum;
import com.yatang.sc.facade.service.DepositService;

@Service("depositWriteDubboService")
public class DepositWriteDubboServiceImpl implements DepositWriteDubboService {

  @Autowired
  private DepositService service;

  @Override
  public Response<Void> createRecord(CreateDepositRecordDto dto) {

    Response<Void> response = new Response<>();
    DepositRecordPo po = BeanConvertUtils.convert(dto, DepositRecordPo.class);
    po.setCreatedTime(new Date());
    service.createRecord(po);

    response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
    response.setSuccess(true);

    return response;
  }
}
