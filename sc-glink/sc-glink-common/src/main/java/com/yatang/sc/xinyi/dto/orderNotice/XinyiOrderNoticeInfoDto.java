package com.yatang.sc.xinyi.dto.orderNotice;

import com.thoughtworks.xstream.annotations.XStreamAliasType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @描述: 订单通知dto
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/25 16:49
 * @版本: v1.0
 */
@Getter
@Setter
@XStreamAliasType("order")
public class XinyiOrderNoticeInfoDto implements Serializable {


    private static final long serialVersionUID = -5571906155016676888L;

    private String orderCode;//单据编号

    private String orderId;//仓储系统单据号

    private String orderType;//逻辑仓库编码 传PTCK=普通出库单

    private String warehouseCode;//仓库编码


}
