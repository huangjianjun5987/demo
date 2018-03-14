package com.yatang.sc.sorder.vo;


import com.yatang.sc.common.utils.excel.ExcelFieldName;
import com.yatang.sc.common.utils.excel.ExcelName;

import java.io.Serializable;

@ExcelName( name="shangpindaoru" )
public class ImportExcelVo implements Serializable{

    private static final long serialVersionUID = -4128182916263183317L;

    @ExcelFieldName(name="商品编码")
    private String productCode;

    @ExcelFieldName(name="商品数量")
    private long quantity;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

}
