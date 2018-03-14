package com.yatang.sc.operation.vo;

import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 白名单上下线接收参数
 * @作者: tankejia
 * @创建时间: 2017/10/17-16:20 .
 * @版本: 1.0 .
 */
public class WhiteListOnOfflineVo implements Serializable {

    private static final long serialVersionUID = -6746575632796062609L;

    /**
     * 存放门店id
     * */
    @NotEmpty(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
    private List<String> storeIds;

    /**
     * 送货仓编号
     * */
    @NotBlank(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
    private String warehouseCode;

    /**
     * 送货仓名称
     * */
    @NotBlank(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
    private String warehouseName;

        public List<String> getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(List<String> storeIds) {
        this.storeIds = storeIds;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
}
