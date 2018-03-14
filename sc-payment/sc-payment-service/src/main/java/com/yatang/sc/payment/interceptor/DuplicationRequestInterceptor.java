package com.yatang.sc.payment.interceptor;

import com.yatang.sc.payment.common.CommonsEnum;
import com.yatang.sc.payment.dto.request.NoneStrRequestDto;
import com.yatang.sc.payment.exception.PrePayInfoException;
import com.yatang.xc.oc.biz.adapter.RedisAdapterServie;
import com.yatang.xc.oc.biz.redis.dubboservice.RedisPlatform;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by yuwei on 2017/7/17.
 */
public class DuplicationRequestInterceptor {
    private Logger mLogger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "redisDubboAdapterServie")
    private RedisAdapterServie<String, String> redisDubboServie;

    public void beforeInvoke(JoinPoint point) throws Throwable {

        if (point.getArgs() == null || point.getArgs().length == 0) {
            return;
        }
        if (!(point.getArgs()[0] instanceof NoneStrRequestDto)) {
            return;
        }

        NoneStrRequestDto noneStrRequestDto = (NoneStrRequestDto) point.getArgs()[0];
        if (StringUtils.isEmpty(noneStrRequestDto.getNonceStr())) {
            mLogger.info("请求随机字符串为空");
            return;
        }
        String noneStr = redisDubboServie.get(RedisPlatform.common,noneStrRequestDto.getNonceStr(), String.class);
        if (StringUtils.isNotEmpty(noneStr)) {
            mLogger.warn("重复的接口请求：{}", noneStrRequestDto.getNonceStr());
            throw new PrePayInfoException(CommonsEnum.DUPLICATION_REQUEST.getCode(), CommonsEnum.DUPLICATION_REQUEST.getName());
        }
        redisDubboServie.setex(RedisPlatform.common,noneStrRequestDto.getNonceStr(),"true",600);
    }
}
