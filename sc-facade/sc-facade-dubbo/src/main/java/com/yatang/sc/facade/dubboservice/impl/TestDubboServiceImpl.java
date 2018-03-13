package com.yatang.sc.facade.dubboservice.impl;

import com.busi.common.datatable.PageResult;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.facade.dto.MongoTestBeanDto;
import com.yatang.sc.facade.dto.QueryRequestDto;
import com.yatang.sc.facade.dubboservice.TestDubboService;
import com.yatang.sc.facade.mongo.CommonQueryRequest;
import com.yatang.sc.facade.mongo.MongoTestBean;
import com.yatang.sc.facade.service.TestService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tangqi on 2018/1/9 20:25.
 */
@Service("testDubboService")
public class TestDubboServiceImpl implements TestDubboService {

    @Autowired
    private TestService testService;

    @Override
    public Response insert(MongoTestBeanDto bean) {
        Response response = new Response();
        try{
            testService.insert(BeanConvertUtils.convert(bean, MongoTestBean.class));
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
        }catch (Exception e){
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response update(MongoTestBeanDto bean) {
        Response response = new Response();
        try{
            testService.update(BeanConvertUtils.convert(bean, MongoTestBean.class));
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
        }catch (Exception e){
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response bulkInsert(List<MongoTestBeanDto> beans) {
        Response response = new Response();
        try{
            testService.bulkInsert(BeanConvertUtils.convertList(beans, MongoTestBean.class));
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
        }catch (Exception e){
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response bulkDelete(List<String> ids) {
        Response response = new Response();
        try{
            testService.bulkDelete(ids);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
        }catch (Exception e){
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response<MongoTestBeanDto> findById(String id) {
        Response response = new Response();
        try{
            MongoTestBean bean = testService.findById(id);
            response.setResultObject(BeanConvertUtils.convert(bean, MongoTestBeanDto.class));
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
        }catch (Exception e){
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response<List<MongoTestBeanDto>> findAll() {
        Response response = new Response();
        try{
            List<MongoTestBean> beans = testService.findAll();
            response.setResultObject(BeanConvertUtils.convertList(beans, MongoTestBeanDto.class));
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
        }catch (Exception e){
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response<PageResult<List<MongoTestBeanDto>>> queryPage(QueryRequestDto requestDto) {

        Response response = new Response();
        try{
            PageResult<List<MongoTestBean>> pageResultRes = testService.queryPage(BeanConvertUtils.convert(requestDto, CommonQueryRequest.class));
            response.setResultObject(pageResultRes);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
        }catch (Exception e){
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
}
