/**
 * 
 */
package com.yatang.sc.facade.mongo.assemble;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author 邓东山
 *
 */
@Component
public class DateAssemble implements TypeAssemble {

    @Override
    public boolean assemble(Entry<String, Object> entry, Map<String, Criteria> criteriaMap) {
        if (entry.getValue() instanceof String) {

            if (entry.getKey().toString().startsWith("$gte_") || entry.getKey().toString().startsWith("$lte_")) {
                dateAssemble(criteriaMap, entry);
                return true;
            }
        }
        return false;
    }

    private void dateAssemble(Map<String, Criteria> criteriaMap, Entry<String, Object> entry) {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(entry.getValue().toString());
            if (entry.getKey().toString().startsWith("$gte_")) {

                String dateKey = entry.getKey().replace("$gte_", "");
                Criteria criteria = criteriaMap.get(dateKey);
                if (criteria != null) {
                    criteria.gte(entry.getValue());
                } else {
                    criteriaMap.put(dateKey, Criteria.where(dateKey).gte(date));
                }
            }
            if (entry.getKey().toString().startsWith("$lte_")) {
                String dateKey = entry.getKey().replace("$lte_", "");
                Criteria criteria = criteriaMap.get(dateKey);
                if (criteria != null) {
                    criteria.lte(entry.getValue());
                } else {
                    criteriaMap.put(dateKey, Criteria.where(dateKey).lte(date));
                }
            }
        }catch (Exception e){

        }
    }
}
