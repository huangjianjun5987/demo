/**
 * 
 */
package com.yatang.sc.web.common.excel.converter;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.yatang.sc.web.common.excel.annotation.ExcelColConverter;

/**
 * @author dengdongshan
 *
 */
@Component
@ExcelColConverter(type = "unionValues")
public class UnionValuesConverter extends AbstractConverter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yatang.sc.web.common.excel.converter.AbstractConverter#exportValue(
	 * java.lang.Object)
	 */
	@Override
	public String exportValue(Object value) {
		return null;
	}

	public String exportValue(Map valueMap, String properties, String format) {
		if (StringUtils.isEmpty(properties) || StringUtils.isEmpty(format)) {
			return null;
		}
		String[] pArr = properties.split(",");
		if (pArr == null || pArr.length == 0) {
			return null;
		}
		for (String property : pArr) {
			String value = (String) valueMap.get(property);
			format = format.replace("{}", value);
		}

		return format;
	}

}
