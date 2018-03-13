package com.yatang.sc.facade.mongo.assemble;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by tangqi on 2018/1/11 10:37.
 */
@Component
public class AboutAssemble implements TypeAssemble {
    @Override
    public boolean assemble(Map.Entry<String, Object> entry, Map<String, Criteria> criteriaMap) {
        if(entry.getValue() instanceof String && entry.getKey().startsWith("$about_")){
            String key = entry.getKey().replace("$about_", "");
            Criteria criteria = criteriaMap.get(key);
            if(criteria == null){
                criteriaMap.put(key, Criteria.where(key).regex("^.*" + entry.getValue().toString() + ".*$"));
            }else{
                criteria.regex("^.*" + entry.getValue().toString() + ".*$");
            }
            return true;
        }
        return false;
    }
}
