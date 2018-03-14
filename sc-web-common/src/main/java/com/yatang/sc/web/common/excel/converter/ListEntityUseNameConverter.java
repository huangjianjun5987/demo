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
@ExcelColConverter(type = "listEntityUseName")
public class ListEntityUseNameConverter extends AbstractConverter {

    @Override
    public String exportValue(Object value) {
	if (value instanceof List<?>) {
	    List<Map<String, Object>> entities = (List<Map<String, Object>>) value;
	    StringBuffer names = new StringBuffer();
	    for(Map<String,Object>entity:entities){
            String id = (String)entity.get("id");
            if(StringUtils.isEmpty(names)){
            	names.append(id);
                continue;
            }
            names.append(","+id);
        }
	    return names.toString();
	}
	if (value instanceof Map<?, ?>) {
	    StringBuffer names = new StringBuffer();
	    Map<String, Object> entities = (Map<String, Object>) value;
	    names.append(entities.get("name"));
	    return names.toString();
	}
	return null;
    }

}
