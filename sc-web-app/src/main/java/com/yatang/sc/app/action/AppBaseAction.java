package com.yatang.sc.app.action;

import com.alibaba.fastjson.JSONObject;
import com.busi.common.utils.StringUtils;
import com.yatang.xc.oc.biz.adapter.RedisAdapterServie;
import com.yatang.xc.oc.biz.redis.dubboservice.RedisPlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by qiugang on 7/27/2017.
 */
public class AppBaseAction {
    private Logger log = LoggerFactory.getLogger(AppBaseAction.class);

    @Autowired
    RedisAdapterServie redisDubboServie;

    public HttpServletRequest getRequest(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public String getToken(HttpServletRequest request){

        return request.getHeader("token");
    }

    public String getStoreId(){
        return getStoreId(getRequest());
    }

    public String getStoreId(HttpServletRequest request){
        return request.getHeader("shopNum");
    }
    public String getBranchCompanyId(HttpServletRequest request){
        return request.getHeader("branchOfficeId");
    }


    public String getUserId(){
        return getUserId(getRequest());
    }


    public String getUserId(HttpServletRequest request){

        String token = getToken(request);
        if(StringUtils.isEmpty(token)){
            log.error("token is empty!!!!");
            return null;
        }
        JSONObject userInfo = (JSONObject) redisDubboServie.get(RedisPlatform.common, getToken(request), JSONObject.class);
        log.info("userInfo:"+userInfo);
        if(userInfo == null){
            log.error("userInfo is null,query by:" + token);
            return null;
        }
        return (String)userInfo.get("userId");
    }
}
