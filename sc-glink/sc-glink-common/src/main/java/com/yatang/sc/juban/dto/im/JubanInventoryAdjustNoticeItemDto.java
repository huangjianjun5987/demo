package com.yatang.sc.juban.dto.im;

import com.busi.kidd.serialize.xml.LevinDateConverter;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述: 心怡库存调整itemDto
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/28 10:43
 * @版本: v1.0
 */
@Getter
@Setter
@XStreamAliasType("item")
public class JubanInventoryAdjustNoticeItemDto implements Serializable {
    private static final long serialVersionUID = -7300559352038475287L;

    private String itemCode;//商品编码

    private String itemId;//仓储系统商品ID

    private String inventoryType;//库存类型

    private Integer quantity;//盘盈盘亏商品变化量，int，必填，盘盈为正数，盘亏为负数

    private Integer totalQty;//盘点商品总数

    private String batchCode;//批次编码

    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd"})
    private Date productDate;//商品生产日期

    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd"})
    private Date expireDate;//商品过期日期


    private String produceCode;//生产批号

    private String snCode;//商品序列号

    private String remark;//备注






}
