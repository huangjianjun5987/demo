package com.yatang.sc.web.common.excel.converter;

import com.yatang.sc.web.common.excel.annotation.ExcelColConverter;
import org.springframework.stereotype.Component;

@Component
@ExcelColConverter(type = "purchaseReceiptOrderStatus")
public class PurchaseReceiptOrderStatusConverter  extends AbstractConverter  {
    @Override
    public String exportValue(Object value) {
        if (!(value instanceof Integer)){
            return "类型异常";
        }
        switch ((Integer) value){  //收货单状态
            case 0:
                return "待下发";
            case 1:
                return "已下发";
            case 2:
                return "已收货";
            case 3:
                return "已取消";
            case 4:
                return "异常";
        }
        return "未知类型";
    }
}
