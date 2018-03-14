package com.yatang.sc.web.common.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {

    static Logger logger = LoggerFactory.getLogger(JSONUtils.class);

    /**
     * 讲java对象转化为json对象,
     * 此方法中不能调用任何会使用toString的方法, 否则会死循环
     * 
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erorr happen when parsing object to json");
        }
    }

    /**
     * 将json字符串转化为map
     * 
     * @param json
     * @return
     */
    public static Map toMap(String json) {
        return toObject(json, Map.class);
    }

    /**
     * 将对象转化为一个包含属性为key的map
     * 
     * @param object
     * @return
     */
    public static Map toMap(Object object) {
        String json = toJson(object);
        return toMap(json);
    }

    /**
     * 将json字符串转化为对应的类对象
     * 
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            logger.debug("Convent json to object, json: {}, class: {}, return: {}", json, clazz, null);
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        T returnObject = null;
        try {
            returnObject = mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Error happen when convent json to object, json: " + json + ", class: " + clazz,
                    e);
        }
        logger.debug("Convent json to object, json: {}, class: {}, return: {}", json, clazz, json);
        return returnObject;
    }

    /**
     * 将map转化为class类型的对象
     * 
     * @param map
     * @param clazz
     * @return
     */
    public static <T> T toObject(Map map, Class<T> clazz) {
        if (map == null) {
            return null;
        }

        String json = toJson(map);
        logger.debug("Convent map to jso, map: {}, json {}", map, json);
        return toObject(json, clazz);
    }

    /**
     * 判断一个对象是否为字符串或基本数据类型包装类, 这些类型对象无法转换为一个map或json对象
     * 
     * @param object
     * @return
     */
    public static boolean isNullOrPrimaryType(Object object) {
        return object == null || object instanceof String || object instanceof Number || object instanceof Boolean
                || object instanceof Character;
    }

    /**
     * 获取以bean的属性全路径名为key的map, 比如 user.address.zip=200129
     * 
     * @param object
     * @return
     */
    public static Map<String, Object> getBeanFlatMap(Object object) {

        if (object == null) {
            return null;
        }

        Map<String, Object> prefixedMap = getMapWithKeyPrefix("", JSONUtils.toMap(object));

        //删除key的第一个字符 ".", 这是由于 getMapWithKeyPrefix第一层属性处理也会给属性加上.
        Map<String, Object> returnMap = new HashMap<>();
        if (returnMap != null) {
            for (Map.Entry<String, Object> entry : prefixedMap.entrySet()) {
                returnMap.put(entry.getKey().replaceFirst("\\.", ""), entry.getValue());
            }
        }

        return returnMap;
    }

    /**
     * 获取以bean的属性全路径名为key的http参数格式字符串, 比如 user.address.zip=200129&user.address.city=cd
     * 
     * @param object
     * @return
     */
    public static String getParamsString(Object object) {

        if (object == null) {
            return null;
        }
        Map<String, Object> beanFlatMap = getBeanFlatMap(object);
        String paramsString = "";
        if (beanFlatMap != null) {
            for (Map.Entry<String, Object> entry : beanFlatMap.entrySet()) {
                if (entry.getValue() != null) {
                    paramsString += entry.getKey() + "=" + URLEncoder.encode(entry.getValue().toString()) + "&";
                }
            }
        }

        return paramsString;
    }

    /**
     * 给返回的对象map的属性key统一添加前缀, 前缀为当前对象在父对象中的相对路径 例如 address对象是name的属性,
     * 那么prefix为address, key为address.name
     * 
     * @param prefix
     * @param object
     * @return
     */
    private static Map<String, Object> getMapWithKeyPrefix(String prefix, Object object) {

        Map<String, Object> returnMap = new HashMap<>();
        if (JSONUtils.isNullOrPrimaryType(object)) {
            returnMap.put(prefix, object);
        } else if (object instanceof Map) {
            Map<String, Object> map = (Map) object;
            if (map != null) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {

                    Object value = entry.getValue();
                    String key = entry.getKey();
                    Map<String, Object> innerMap = getMapWithKeyPrefix(prefix + "." + key, value);
                    returnMap.putAll(innerMap);
                }
            }
        } else if (object instanceof Collection) {
            Collection collection = (Collection) object;
            int i = 0;
            for (Object object2 : collection) {
                Map<String, Object> innerMap = getMapWithKeyPrefix(prefix + "[" + i + "]", object2);
                returnMap.putAll(innerMap);
                i++;
            }
        }
        return returnMap;
    }

    public static <T> T toObject(String jsonString, TypeReference<T> typeReference) {
        if (jsonString == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        T returnObject = null;
        try {
            returnObject = mapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Error happen when convent json to object, json: " + jsonString + ", class: " + typeReference, e);
        }

        return returnObject;
    }

    /**   
         * 获取泛型的Collection Type  
           * @param collectionClass 泛型的Collection   
           * @param elementClasses 元素类   
           * @return JavaType Java类型   
           * @since 1.0   
          */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
    
    public static <T> T toCollectionObject(String jsonString, Class<?> collectionClass,Class<?> elementClasses){
        JavaType javaType = JSONUtils.getCollectionType(collectionClass, elementClasses); 
        T returnObject = null;
        try {
            returnObject  = new ObjectMapper().readValue(jsonString, javaType);
        }catch (IOException e) {
            throw new RuntimeException(
                    "Error happen when convent json to object, json: " + jsonString + ", collectionClass: "+collectionClass+", class: " + elementClasses, e);
        }
        return returnObject;
    }
}
