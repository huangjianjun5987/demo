package com.yatang.sc.common.localcache.aop;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.common.localcache.LocalCacheContext;
import com.yatang.sc.common.localcache.annotation.LocalCache;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class LocalCacheAop implements MethodInterceptor {
    private static Logger mLogger = LoggerFactory.getLogger(LocalCacheAop.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (null == invocation.getMethod().getAnnotation(LocalCache.class)) {
            return invocation.proceed();
        }
        String key = genKey(invocation.getArguments());
        if (StringUtils.isEmpty(key)) {
            return invocation.proceed();
        }

        key = key.toUpperCase();
        LocalCache localCache = invocation.getMethod().getAnnotation(LocalCache.class);
        Object val = LocalCacheContext.get(localCache.group(), key, invocation.getMethod().getReturnType());
        if (null == val) {
            val = invocation.proceed();
            if (val != null) {
                LocalCacheContext.put(localCache.group(), key, val, invocation.getMethod().getReturnType(), localCache.initialCapacity(), localCache.expireAfterWrite(), localCache.expireAfterAccess(), localCache.maximumSize(), localCache.concurrencyLevel());
            }
        }
        return val;
    }

    public static String genKey(Object[] args) {
        if (args == null || args.length == 0) {
            return null;
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            for (Object o : args) {
                stringBuilder.append(JSON.toJSONString(o));
            }
            MessageDigest md = MessageDigest.getInstance("MD5");
            return byte2hex(md.digest(stringBuilder.toString().getBytes("utf-8")));
        } catch (Exception pE) {
            mLogger.error("获取缓存Key异常", pE);
        }
        return null;
    }

    /**
     * 二进制转字符串
     *
     * @param b
     * @return
     */
    private static String byte2hex(byte[] b) {

        StringBuffer hs = new StringBuffer();
        String tmp;
        for (int n = 0; n < b.length; n++) {
            tmp = (Integer.toHexString(b[n] & 0XFF));
            if (tmp.length() == 1)
                hs.append("0").append(tmp);
            else
                hs.append(tmp);
        }

        return hs.toString().toUpperCase();
    }
}
