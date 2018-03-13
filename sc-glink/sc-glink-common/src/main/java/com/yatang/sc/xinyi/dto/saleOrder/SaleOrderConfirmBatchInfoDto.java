package com.yatang.sc.xinyi.dto.saleOrder;

import com.busi.kidd.serialize.xml.LevinDateConverter;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述: 销售订单出库批次信息
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/26 18:37
 * @版本: v1.0
 */
@Getter
@Setter
@XStreamAliasType("batch")
public class SaleOrderConfirmBatchInfoDto implements Serializable {
    private static final long serialVersionUID = 5295282712511264029L;


    private String batchCode;//批次编号

    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd"})
    private Date productDate;// 生产日期

    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd"})
    private Date expireDate;//过期日期

    private String produceCode;//生产批号

    private String inventoryType;//库存类型

    private Integer actualQty;//实发数量




}
