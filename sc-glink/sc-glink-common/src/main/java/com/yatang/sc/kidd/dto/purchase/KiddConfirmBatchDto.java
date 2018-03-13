package com.yatang.sc.kidd.dto.purchase;

import com.alibaba.fastjson.annotation.JSONField;
import com.busi.kidd.serialize.xml.LevinDateConverter;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述:分批次商品详细信息
 * @类名:KiddConfirmBatchDto
 * @作者: lvheping
 * @创建时间: 2017/9/27 10:31
 * @版本: v1.0
 */
@XStreamAliasType("batch")
public class KiddConfirmBatchDto implements Serializable {
    private static final long serialVersionUID = -2862392967285368693L;
    private String batchCode;//批次编号
    @JSONField(format = "yyyy-MM-dd")
    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd"})
    private Date productDate;//生产日期
    @JSONField(format = "yyyy-MM-dd")
    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd"})
    private Date expireDate;//过期日期
    private String produceCode;//生产批号
    private String inventoryType;//库存类型
    private Integer actualQty;//实收数量

    @Override
    public String toString() {
        return "KiddConfirmBatchDto{" +
                "batchCode='" + batchCode + '\'' +
                ", productDate=" + productDate +
                ", expireDate=" + expireDate +
                ", produceCode='" + produceCode + '\'' +
                ", inventoryType='" + inventoryType + '\'' +
                ", actualQty=" + actualQty +
                '}';
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

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public void setActualQty(Integer actualQty) {
        this.actualQty = actualQty;
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

    public String getInventoryType() {
        return inventoryType;
    }

    public Integer getActualQty() {
        return actualQty;
    }
}
