package com.yatang.sc.purchase.dubboservice.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiangyonghong on 2017/7/17.
 */
public class MyBeanUtils {

    public static Map<String,Object> beanToMap(Object t){
        Map<String,Object> map = new HashMap<String,Object>();
        BeanWrapper bw = new BeanWrapperImpl(t);

        PropertyDescriptor[] pd = bw.getPropertyDescriptors();
        for(PropertyDescriptor p:pd){
            String name = p.getName();
            if(!name.equals("class")){
                Object value = bw.getPropertyValue(name);
                if(value!= null){
                    map.put(p.getName(),value);
                }
            }
        }
        return map;
    }
}
