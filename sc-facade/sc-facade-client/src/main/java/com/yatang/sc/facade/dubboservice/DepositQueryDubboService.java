package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.dto.DepositRecordDto;

import java.util.Map;

/**
 * @描述: 保证金dubbo服务接口.
 * @作者: yipeng
 * @创建时间: 2017年05月23日12:42:04
 * @版本: 1.0 .
 */
public interface DepositQueryDubboService {

  Response<PageInfo<DepositRecordDto>> depositRecords(Map<String, Object> paramMap);

}
