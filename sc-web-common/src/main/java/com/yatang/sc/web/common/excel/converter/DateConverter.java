/**
 * 
 */
package com.yatang.sc.web.common.excel.converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import org.springframework.stereotype.Component;

import com.yatang.sc.web.common.excel.annotation.ExcelColConverter;

/**
 * @author 邓东山
 *
 */
@Component
@ExcelColConverter(type = "date")
public class DateConverter extends AbstractConverter {

    @Override
    public String exportValue(Object value) {
        if(value==null){
            return null;
        }
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return time.format(value);
    }

}
