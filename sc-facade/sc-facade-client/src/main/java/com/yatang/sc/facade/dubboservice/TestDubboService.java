package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.busi.common.datatable.PageResult;
import com.yatang.sc.facade.dto.MongoTestBeanDto;
import com.yatang.sc.facade.dto.QueryRequestDto;

import java.util.List;

/**
 * Created by tangqi on 2018/1/9 20:24.
 */
public interface TestDubboService {

    Response insert(MongoTestBeanDto bean);

    Response update(MongoTestBeanDto bean);

    Response bulkInsert(List<MongoTestBeanDto> beans);

    Response bulkDelete(List<String> ids);

    Response<MongoTestBeanDto> findById(String id);

    Response<List<MongoTestBeanDto>> findAll();

    Response<PageResult<List<MongoTestBeanDto>>> queryPage(QueryRequestDto requestDto);
}
