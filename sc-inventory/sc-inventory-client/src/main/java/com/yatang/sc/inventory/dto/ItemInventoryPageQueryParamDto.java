package com.yatang.sc.inventory.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 分页查询库存信息
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/29 下午3:40
 * @版本: v1.0
 */

public class ItemInventoryPageQueryParamDto extends BaseDto implements Serializable {
    private static final long serialVersionUID = -8858801227977272220L;


    private String productId;//商品id

    private String productCode;//商品编号

    private String logicWareHouseCode;//逻辑仓库code

    private List<String> branchCompanyIds;//分公司编号

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getLogicWareHouseCode() {
        return logicWareHouseCode;
    }

    public void setLogicWareHouseCode(String logicWareHouseCode) {
        this.logicWareHouseCode = logicWareHouseCode;
    }

    public List<String> getBranchCompanyIds() {
        return branchCompanyIds;
    }

    public void setBranchCompanyIds(List<String> branchCompanyIds) {
        this.branchCompanyIds = branchCompanyIds;
    }
}
