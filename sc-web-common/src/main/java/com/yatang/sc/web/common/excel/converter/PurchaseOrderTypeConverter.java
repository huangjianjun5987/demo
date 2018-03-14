package com.yatang.sc.web.common.excel.converter;

import com.yatang.sc.web.common.excel.annotation.ExcelColConverter;
import org.springframework.stereotype.Component;

@Component
@ExcelColConverter(type = "purchaseOrderType")
public class PurchaseOrderTypeConverter extends AbstractConverter  {

    @Override
    public String exportValue(Object value) {
        if (!(value instanceof Integer)){
            return "类型异常";
        }
        switch ((Integer) value){  //采购单类型
            case 0:
                return "普通采购";
            case 1:
                return "赠品采购";
            case 2:
                return "促销采购";
        }
        return "未知类型";
    }

}
