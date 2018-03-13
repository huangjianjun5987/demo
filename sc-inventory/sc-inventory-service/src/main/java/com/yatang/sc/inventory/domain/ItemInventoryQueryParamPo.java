package com.yatang.sc.inventory.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @描述: 商品库存相关信息查询条件po类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/15 16:05
 * @版本: v1.0
 */
public class ItemInventoryQueryParamPo implements Serializable {


    private static final long serialVersionUID = -8947969047863927522L;
    private String productId;//商品id

    private String productCode;//商品编号

    private String logicWareHouseCode;//逻辑仓库code

    private String branchCompanyId;//分公司编号

    /**********************用于采购退货start***************************************/
    private Integer refundAmount;//采购退货数量：用于采购退货

    private Integer realRefundAmount;//采购退货实际成功数量

    private List<String> branchCompanyIds;//分公司编号



    /**********************用于采购退货end***************************************/




    private long quantity;//订单的数量

    public long getQuantity() { return quantity; }

    public void setQuantity(long quantity) { this.quantity = quantity; }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getLogicWareHouseCode() {
        return logicWareHouseCode;
    }

    public void setLogicWareHouseCode(String logicWareHouseCode) {
        this.logicWareHouseCode = logicWareHouseCode;
    }


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }



    public Integer getRefundAmount() {
        return refundAmount;
    }



    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount;
    }



    public Integer getRealRefundAmount() {
        return realRefundAmount;
    }


    public void setRealRefundAmount(Integer realRefundAmount) {
        this.realRefundAmount = realRefundAmount;
    }

    public List<String> getBranchCompanyIds() {
        return branchCompanyIds;
    }

    public void setBranchCompanyIds(List<String> branchCompanyIds) {
        this.branchCompanyIds = branchCompanyIds;
    }
}
