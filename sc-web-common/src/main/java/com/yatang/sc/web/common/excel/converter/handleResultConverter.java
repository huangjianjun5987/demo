package com.yatang.sc.web.common.excel.converter;

import com.yatang.sc.web.common.excel.annotation.ExcelColConverter;
import org.springframework.stereotype.Component;

@Component
@ExcelColConverter(type = "handleResultConverter")
public class handleResultConverter  extends AbstractConverter  {

    @Override
    public String exportValue(Object value) {
        if (!(value instanceof Integer)){
            return "未知处理结果";
        }
        switch ((Integer) value){  //采购单类型
            case 0:
                return "错误";
            case 1:
                return "已验证";
            case 2:
                return "已提交";
        }
        return "未知类型";
    }
}
