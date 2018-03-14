package com.yatang.sc.app.web;

import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.StringUtils;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationSCService;
import com.yatang.xc.mbd.biz.org.sc.dto.StoreScPurchaseDto;
import com.yatang.xc.oc.biz.adapter.RedisAdapterServie;
import com.yatang.xc.oc.biz.redis.dubboservice.RedisPlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by qiugang on 8/4/2017.
 */
public class AppTokenCheckInterceptor  implements HandlerInterceptor{

    Logger log = LoggerFactory.getLogger(AppTokenCheckInterceptor.class);
    @Autowired
    RedisAdapterServie redisDubboServie;

    @Autowired
    OrganizationSCService organizationSCService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            log.error("token is empty!!!!");
            return false;
        }
        JSONObject userInfo = (JSONObject) redisDubboServie.get(RedisPlatform.common, token, JSONObject.class);
        if(userInfo == null){
            response.setStatus(401);
            response.getWriter().print("session 已过期！");
            return false;
        }

        String storeId = request.getHeader("shopNum");
        Response<StoreScPurchaseDto> storeDtoResponse = organizationSCService.queryStoreScPurchaseById(storeId);
        if (!storeDtoResponse.isSuccess() || null == storeDtoResponse.getResultObject() || null == storeDtoResponse.getResultObject().getScPurchaseFlag()
                || 0 == storeDtoResponse.getResultObject().getScPurchaseFlag()){
            response.setStatus(403);
            response.getWriter().print("该门店不支持采购");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse Response, Object obj, ModelAndView mav) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse Response, Object obj, Exception e) throws Exception {

    }

}
