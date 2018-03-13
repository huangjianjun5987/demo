package com.busi.kidd.security;

import java.util.Map;

/**
 * 安全顶级接口<br>
 * 实现安全验证等功能
 *
 * @author yangqingsong
 */
public interface KiddSecurity {

    /**
     * 数据签名
     *
     * @param params
     * @return
     */
    public abstract String sign(Map<String, String> params) throws Exception;

    /**
     * 数据加密
     *
     * @param str
     * @return
     */
    public abstract String encode(String str) throws Exception;

}
