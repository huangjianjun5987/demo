package com.yatang.sc.juban.dto.orderCancel;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @描述: 销售订单取消ReqDTO
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/23 14:21
 * @版本: v1.0
 */
@Getter
@Setter
public class JubanOrderCancelRequestDto implements Serializable{
    private static final long serialVersionUID = 890899570690828491L;

    private String warehouseCode;//逻辑仓库编码 必填
    private String ownerCode;// 货主编码 子公司ID
    private String orderCode;//单据编码
    private String orderId;//仓储系统单据编码

    private String orderType;//单据类型
    private String cancelReason;//取消原因


}
