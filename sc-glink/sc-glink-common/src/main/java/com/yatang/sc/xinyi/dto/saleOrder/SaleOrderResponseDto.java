package com.yatang.sc.xinyi.dto.saleOrder;

import com.alibaba.fastjson.annotation.JSONField;
import com.busi.kidd.serialize.xml.LevinDateConverter;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.yatang.sc.xinyi.dto.ResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @描述: 销售订单响应D
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/23 9:30XStreamConverter
 * @版本: v1.0
 */
@Getter
@Setter
public class SaleOrderResponseDto extends ResponseDto {
    private static final long serialVersionUID = -1323185562907471524L;

    private String deliveryOrderId;//出库单仓储系统编码

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd HH:mm:ss"})
    private Date createTime;

}
