/**
 * 
 */
package com.yatang.sc.web.common.excel.converter;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.yatang.sc.web.common.excel.annotation.ExcelColConverter;

/**
 * @author 邓东山
 *
 */
@Component
@ExcelColConverter(type = "listEntity")
public class ListEntityConverter extends AbstractConverter {

    @Override
    public String exportValue(Object value) {
        if(value instanceof List<?>){
            List<Map<String,Object>> entities=(List<Map<String, Object>>) value;
            StringBuffer ids = new StringBuffer();
            for(Map<String,Object>entity:entities){
                String id = (String)entity.get("id");
                if(StringUtils.isEmpty(ids)){
                    ids.append(id);
                    continue;
                }
                ids.append(","+id);
            }
            return ids.toString();
        }
        if(value instanceof Map<?,?>){
            StringBuffer ids = new StringBuffer();
            Map<String,Object> entities=(Map<String, Object>) value;
            ids.append(entities.get("id"));
            return ids.toString();
        }
        return null;
    }

}
