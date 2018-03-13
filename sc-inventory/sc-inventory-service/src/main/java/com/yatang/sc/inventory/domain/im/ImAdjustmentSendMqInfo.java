package com.yatang.sc.inventory.domain.im;

import com.busi.mq.message.MQMsg;
import com.yatang.sc.inventory.domain.ImAdjustmentReceiptPo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @描述: 库存调整sendMQInfo
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/1 上午10:52
 * @版本: v1.0
 */
@Getter
@Setter
@ToString
public class ImAdjustmentSendMqInfo implements Serializable, MQMsg {
    private static final long serialVersionUID = -2367282381160577755L;


    private ImAdjustmentReceiptPo imAdjustmentReceipt;//库存调整单
}
