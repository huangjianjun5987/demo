package com.yatang.sc.common.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
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

    public static Object mapToBean(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);

                Object[] args = new Object[1];
                args[0] = value;

                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }
}
