package com.yatang.sc.payment.notifier.web.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @描述: Request处理工具类.
 * @作者: huangjianjun
 * @创建时间: 2017年5月19日-下午2:27:20 .
 * @版本: 1.0 .
 */
public class RequestUtils {
    private static final Logger logger = LoggerFactory.getLogger(RequestUtils.class.getClass());
    /**
     * 根据request中的参数产生参数map
     *
     * @param request
     * @return
     */
    public static Map<String, Object> genParamMap(HttpServletRequest request) {
        Enumeration<?> names = request.getParameterNames();
        Map<String, Object> paraMap = new HashMap<String, Object>();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String values[] = request.getParameterValues(name);
            if (values.length == 1) {
                paraMap.put(name, values[0]);
            } else {
                paraMap.put(name, values);
            }
        }
        return paraMap;
    }


    /**
     * 根据request中的参数产生参数map,包含json就把json的值转换为map
     *
     * @param request
     * @return
     */
    public static Map<String, Object> getParamMap(HttpServletRequest request) {
        Enumeration<?> names = request.getParameterNames();
        Map<String, Object> paraMap = new HashMap<String, Object>();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            if (name.equals("json"))
                continue;
            String values[] = request.getParameterValues(name);
            if (values.length == 1) {
                paraMap.put(name, values[0]);
            } else {
                paraMap.put(name, values);
            }
        }
        String json = request.getParameter("json");
        if (StringUtils.isNotBlank(json)) {
            Map<String, Object> jsonMap = JSONObject.parseObject(json);
            paraMap.put("perPage", 10);
            paraMap.putAll(jsonMap);
        }
        return paraMap;
    }

    /**
     * 获取请求头的ip地址
     *
     * @param request
     * @return ip地址
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null) {
            ipAddress = request.getHeader("X_FORWARDED_FOR");
            if (ipAddress == null) {
                ipAddress = request.getHeader("X-Forward-For");
                if (ipAddress == null) {
                    ipAddress = request.getRemoteAddr();
                }
            }
        }
        return ipAddress;
    }

    /**
     * 获得请求参数map并 过滤掉为空的参数
     *
     * @param request
     * @return
     */
    public static Map<String, Object> createCriteria(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();

        Map<String, Object> paraMap = genParamMap(request);
        for (String key : paraMap.keySet()) {
            Object value = paraMap.get(key);
            if (null != value && !value.toString().equals("")) {
                params.put(key, value);
            }
        }
        return params;
    }

    public static Map<String, String> createRequest(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"),"gbk");
            params.put(name, valueStr);
        }
        return params;
    }

    public static Map<String, String> createRequestFromStream(HttpServletRequest request) {
        try {
            ServletInputStream inputStream = request.getInputStream();
            BufferedReader br = null;

            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String returnXml = sb.toString();
            return XmlUtils.xml2Map(returnXml, "xml");
        } catch (Exception pE) {
            logger.error("解析参数异常",pE);
        }
        return new HashMap<>();
    }
}
