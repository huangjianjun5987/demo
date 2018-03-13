package com.yatang.sc.facade.dto.pm;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PmPurchaseReceiptExportDto implements Serializable{

    private static final long serialVersionUID = 8187798060927327437L;

    /**收货单号*/
    private String purchaseReceiptNo;

    /**采购单号*/
    private String purchaseOrderNo;

    /**采购单项总额(含税)*/
    private BigDecimal itemTotalAmount;

    /**商品编码*/
    private String productCode;

    /**商品条码*/
    private String internationalCode;

    /**商品名称*/
    private String productName;

    /**一级商品分类名称*/
    private String firstLevelCategoryName;

    /**二级商品分类名称*/
    private String secondLevelCategoryName;

    /**三级商品分类名称*/
    private String thirdLevelCategoryName;

    /**采购数量*/
    private Integer purchaseNumber;

    /**采购内装数*/
    private Integer purchaseInsideNumber;

    /**供应商出库数量*/
    private Integer deliveryNumber;

    /**收货数量*/
    private Integer receivedNumber;

    /**收货单价(不含税)*/
    private BigDecimal receivedUnitPriceWithoutTax;

    /**税金*/
    private BigDecimal taxAmount;

    /**收货单价(含税)*/
    private BigDecimal receivedUnitPrice;

    /**收货单总金额*/
    private BigDecimal receiptTotalAmount;

    /**采购日期(审核日期)*/
    private Date auditTime;

    /**采购单类型*/
    private Integer purchaseOrderType;

    /**物流状态 1已收货 0未收货 */
    private Integer shippingStatus;

    /**子公司名*/
    private String branchCompanyName;

    /**供应商编号*/
    private String spNo;

    /**供应商名称*/
    private String spName;

    /**供应商地点编码*/
    private String spAdrNo;

    /**供应商地点名称*/
    private String spAdrName;

    /**仓库地点*/
    private String adrName;

    /**预计送货日期*/
    private Date estimatedDeliveryDate;

    /**预计到货日期*/
    private Date estimatedReceivedDate;

    /**收货日期*/
    private Date receivedTime;

    /**收货单状态*/
    private Integer status;

}
