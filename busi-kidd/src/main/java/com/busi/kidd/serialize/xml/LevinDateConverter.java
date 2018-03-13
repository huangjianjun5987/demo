package com.busi.kidd.serialize.xml;

import com.thoughtworks.xstream.converters.basic.DateConverter;
import org.apache.commons.lang3.StringUtils;

import java.util.TimeZone;

/**
 * 日期转换
 *
 * @author yipeng
 */
public class LevinDateConverter extends DateConverter {
    public LevinDateConverter(String dateFormat) {
        super(dateFormat, new String[]{dateFormat}, TimeZone.getDefault());
    }

    public Object fromString(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        return super.fromString(str);
    }

}
