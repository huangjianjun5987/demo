/**
 * 
 */
package com.yatang.sc.facade.mongo.assemble;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @author 邓东山
 *
 */
@Component
public class StringAssemble implements TypeAssemble {

    @Override
    public boolean assemble(Entry<String, Object> entry, Map<String, Criteria> criteriaMap) {
        if (entry.getValue() instanceof String && !entry.getKey().startsWith("$")) {
            criteriaMap.put(entry.getKey(), Criteria.where(entry.getKey()).is(entry.getValue()));
            return true;
        }
        return false;
    }
}
