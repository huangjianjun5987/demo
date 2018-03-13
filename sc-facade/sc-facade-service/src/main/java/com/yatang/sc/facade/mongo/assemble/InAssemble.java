package com.yatang.sc.facade.mongo.assemble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tangqi on 2018/1/15 17:08.
 */
@Component
public class InAssemble implements TypeAssemble {

    @Override
    public boolean assemble(Map.Entry<String, Object> entry, Map<String, Criteria> criteriaMap) {
        if(entry.getKey().startsWith("$in_")){
            String key = entry.getKey().substring(4);
            Criteria criteria = criteriaMap.get(key);
            List<String> list = (List<String>)entry.getValue();
            if(criteria == null){
                criteriaMap.put(key, Criteria.where(key).in(list));

            }else{
                criteria.in(list);
            }
            return true;
        }
        return false;
    }

}
