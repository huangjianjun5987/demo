package com.yatang.sc.inventory.dto.im;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @描述: 推送库存调整单数据
 * @作者: yangshuang
 * @创建时间:2017年09月04日15:07
 */

public class KiddImAdjustmentDataDto implements Serializable {

    private static final long serialVersionUID = 7719539663639148713L;

    private String warehouseCode;//仓库编码

    private String checkOrderCode;//盘点单编码


    private String ownerCode;//货主编码(子公司code)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkTime;//盘点时间 YYYY-MM-DD HH:MM:SS 否
    private String outBizCode;//外部业务编码 消息ID用于去重，ISV对于同一请求，分配一个唯一性的编码。用来保证因为网络等原因导致重复传输，请求不会被重复处理

    private String remark;//备注 调整类型

    private List<KiddImAdjustmentItemDto> items;

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getCheckOrderCode() {
        return checkOrderCode;
    }

    public void setCheckOrderCode(String checkOrderCode) {
        this.checkOrderCode = checkOrderCode;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getOutBizCode() {
        return outBizCode;
    }

    public void setOutBizCode(String outBizCode) {
        this.outBizCode = outBizCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<KiddImAdjustmentItemDto> getItems() {
        return items;
    }

    public void setItems(List<KiddImAdjustmentItemDto> items) {
        this.items = items;
    }
}
