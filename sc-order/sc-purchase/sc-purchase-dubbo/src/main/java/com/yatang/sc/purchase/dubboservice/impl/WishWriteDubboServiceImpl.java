package com.yatang.sc.purchase.dubboservice.impl;

import com.busi.common.resp.Response;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.purchase.dto.wish.SaveWishDto;
import com.yatang.sc.purchase.dubboservice.WishWriteDubboService;
import com.yatang.sc.purchase.service.WishServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("wishWriteDubboService")
public class WishWriteDubboServiceImpl implements WishWriteDubboService {

    protected final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    WishServiceHelper wishServiceHelper;

    @Override
    public Response<Boolean> saveWishLists(SaveWishDto saveWishDto) {
        Response<Boolean> response = new Response<>();
        log.info("saveWish,start.");
        try{
            boolean success = wishServiceHelper.saveWishLists(saveWishDto);
            if(!success){
                response.setSuccess(false);
                response.setResultObject(false);
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
                return response;
            }
            response.setSuccess(true);
            response.setResultObject(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        }catch (Exception e){
            log.error(e.getMessage(),e);
            response.setSuccess(false);
            response.setResultObject(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            return response;
        }
        log.info("saveWish,end.");
        return response;
    }
}
