package com.yatang.sc.facade.dubboservice.impl;

import com.busi.common.resp.Response;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yatang.sc.facade.domain.DepositRecordPo;
import com.yatang.sc.facade.dto.DepositRecordDto;
import com.yatang.sc.facade.dubboservice.DepositQueryDubboService;
import static com.yatang.sc.facade.enums.PublicEnum.*;

import com.yatang.sc.facade.service.DepositService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("depositQueryDubboService")
public class DepositQueryDubboServiceImpl implements DepositQueryDubboService {

  @Autowired
  private DepositService service;

  @Override
  public Response<PageInfo<DepositRecordDto>> depositRecords(Map<String, Object> paramMap) {
    Response<PageInfo<DepositRecordDto>> response = new Response<>();

    int page = MapUtils.getInteger(paramMap, MAP_PAGE_NUM.getCodeStr(), Integer.valueOf(DEFAULT_PAGE_NUM.getCodeStr()));
    int size = MapUtils.getInteger(paramMap, MAP_PAGE_SIZE.getCodeStr(), Integer.valueOf(DEFAULT_PAGE_SIZE.getCodeStr()));
    PageHelper.startPage(page, size);
    List<DepositRecordPo> list = service.depositRecords(paramMap);
    PageInfo pageInfo = new PageInfo<>(list);

    List<DepositRecordDto> dtos = Lists.transform(list, new Function<DepositRecordPo, DepositRecordDto>() {
      @Override
      public DepositRecordDto apply(DepositRecordPo po) {
        DepositRecordDto dto = new DepositRecordDto();
        BeanUtils.copyProperties(po, dto);
        dto.setPayChannel(po.getPayChannel().getDescription());
        dto.setPaymentType(po.getPaymentType().getDescription());
        if (po.getSupplier() != null && po.getSupplier().getSupplierBasicInfo() != null) {
          dto.setSpId(po.getSupplier().getId());
          dto.setSpNo(po.getSupplier().getSupplierBasicInfo().getSpNo());
          dto.setCompanyName(po.getSupplier().getSupplierBasicInfo().getCompanyName());
        }
        return dto;
      }
    });
    pageInfo.setList(dtos);

    response.setSuccess(true);
    response.setResultObject((PageInfo<DepositRecordDto>) pageInfo);

    return response;
  }
}
