package com.yatang.sc.juban.dto.purchase;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.busi.kidd.serialize.xml.LevinDateConverter;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.yatang.sc.kidd.dto.purchase.KiddConfirmBatchDto;

/**
 * @描述:入库确认服务商品详细信息
 * @类名:KiddConfirmOrderLinesDto
 * @作者: lvheping
 * @创建时间: 2017/9/27 10:19
 * @版本: v1.0
 */
@XStreamAliasType("orderLine")
public class JubanConfirmOrderLinesDto implements Serializable {
    private static final long serialVersionUID = -7771228372801114887L;
    private String outBizCode;//外部业务编码, 消息ID, 用于去重
    private String orderLineNo;//单据行号
    private String ownerCode;//货主编码
    private String itemCode;//商品编码
    private String itemId;//仓储系统商品ID
    private String itemName;//商品名称
    private String inventoryType;//库存类型
    private Integer planQty;//应收数量
    private Integer actualQty;//实收数量
    private String batchCode;//批次编码
    @JSONField(format = "yyyy-MM-dd")
    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd"})
    private Date productDate;//商品生产日期
    @JSONField(format = "yyyy-MM-dd")
    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd"})
    private Date expireDate;//商品过期日期
    private String produceCode;//生产批号
    private List<KiddConfirmBatchDto> batchs;//同一行号下多批次支持

    @Override
    public String toString() {
        return "XinyiConfirmOrderLinesDto{" +
                "outBizCode='" + outBizCode + '\'' +
                ", orderLineNo='" + orderLineNo + '\'' +
                ", ownerCode='" + ownerCode + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", inventoryType='" + inventoryType + '\'' +
                ", planQty=" + planQty +
                ", actualQty=" + actualQty +
                ", batchCode='" + batchCode + '\'' +
                ", productDate=" + productDate +
                ", expireDate=" + expireDate +
                ", produceCode='" + produceCode + '\'' +
                ", batchs=" + batchs +
                '}';
    }

    public void setOutBizCode(String outBizCode) {
        this.outBizCode = outBizCode;
    }

    public void setOrderLineNo(String orderLineNo) {
        this.orderLineNo = orderLineNo;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public void setPlanQty(Integer planQty) {
        this.planQty = planQty;
    }

    public void setActualQty(Integer actualQty) {
        this.actualQty = actualQty;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public void setProduceCode(String produceCode) {
        this.produceCode = produceCode;
    }

    public void setBatchs(List<KiddConfirmBatchDto> batchs) {
        this.batchs = batchs;
    }

    public String getOutBizCode() {
        return outBizCode;
    }

    public String getOrderLineNo() {
        return orderLineNo;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public Integer getPlanQty() {
        return planQty;
    }

    public Integer getActualQty() {
        return actualQty;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public Date getProductDate() {
        return productDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public String getProduceCode() {
        return produceCode;
    }

    public List<KiddConfirmBatchDto> getBatchs() {
        return batchs;
    }
}
