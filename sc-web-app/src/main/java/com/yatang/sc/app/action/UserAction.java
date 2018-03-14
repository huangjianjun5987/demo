package com.yatang.sc.app.action;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.xc.mbd.biz.org.dto.StoreDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @描述: 用户信息的action
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/11/10 19:14
 * @版本: v1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/sc/user")
public class UserAction extends AppBaseAction {


    @Autowired
    private OrganizationService organizationService;


    @RequestMapping(value = "getStoreType")
    public Response<Integer> getStoreType() {
        Response<Integer> response = new Response<>();
        try {
            String storeId = getStoreId();
            Response<StoreDto> storeDtoResponse = organizationService.queryStoreById(storeId);
            if (!storeDtoResponse.isSuccess() || storeDtoResponse.getResultObject() == null) {
                log.error("查询门店失败{}", JSON.toJSONString(storeDtoResponse));
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
                response.setResultObject(null);
                return response;
            }
            StoreDto storeDto = storeDtoResponse.getResultObject();
            /** 门店类型(1直营,2加盟) */
            Integer storeType = storeDto.getStoreType();//获取门店类型
            if (null == storeType) {
                log.error("查询门店类型失败:{}", JSON.toJSONString(storeType));
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
                response.setResultObject(null);
                return response;

            }
            response.setResultObject(storeType);
            response.setSuccess(true);
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
        }


        return response;


    }


}
