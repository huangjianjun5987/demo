package com.yatang.sc.operation.vo.im;

import com.yatang.sc.operation.common.BaseVo;
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
public class ItemInventoryPageQueryParamVo extends BaseVo implements Serializable {


    private static final long serialVersionUID = -8899851033169312782L;
    private String productId;//商品id

    private String productCode;//商品编码

    private String logicWareHouseCode;//逻辑仓库code

    private String branchCompanyId;//分公司编号


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


    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }
}
