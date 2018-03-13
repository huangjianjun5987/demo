package com.yatang.sc.facade.mongo.assemble;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by tangqi on 2018/1/11 9:30.
 */
@Component
public class NumberAssemble implements TypeAssemble {

    @Override
    public boolean assemble(Map.Entry<String, Object> entry, Map<String, Criteria> criteriaMap) {
        if(entry.getValue() instanceof Integer || entry.getValue() instanceof Double || entry.getValue() instanceof Long){
            String key = entry.getKey();
            Criteria criteria = criteriaMap.get(key);
            if(criteria == null){
                criteriaMap.put(key, Criteria.where(key).is(entry.getValue()));
            }else {
                criteria.is(entry.getValue());
            }
            return true;
        }
        return false;
    }
}
