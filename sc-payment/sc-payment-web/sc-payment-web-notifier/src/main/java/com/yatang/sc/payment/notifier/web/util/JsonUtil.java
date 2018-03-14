package com.yatang.sc.payment.notifier.web.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import java.util.Date;


/**
 * @描述: json转换工具类.
 * @作者: huangjianjun
 * @创建时间: 2017年5月17日-下午10:09:43 .
 * @版本: 1.0 .
 * @param <T>
 */
public class JsonUtil {
	
	public static SerializeConfig mapping = new SerializeConfig();
	
	static { 
	    mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss")); 
	}
	
	 /** 
     * Json字符串转对象 
     * @param <T> 
     * @param jsonStr 
     * @param clazz 
     * @return 
     * @throws Exception 
     */  
    public static <T> T jsonStrToBean(String jsonStr, Class<T> clazz) throws Exception {  
    	return JSONObject.parseObject(jsonStr, clazz);
    }  
  
    /** 
     * 对象转Json字符串 
     * @param bean 
     * @return 
     * @throws Exception 
     */  
    public static String beanToJsonStr(Object obj) throws Exception {  
        return JSONObject.toJSONString(obj);  
    }  
    
    /** 
     * 对象转Json字符串 
     * @param bean 
     * @return 
     * @throws Exception 
     */  
    public static String beanToJsonStrFormat(Object obj) throws Exception {  
    	return JSONObject.toJSONString(obj,mapping);  
    }  
}
