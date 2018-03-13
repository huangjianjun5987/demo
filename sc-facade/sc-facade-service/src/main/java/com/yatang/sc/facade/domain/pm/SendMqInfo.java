package com.yatang.sc.facade.domain.pm;

import com.busi.mq.message.MQMsg;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @描述:
 * @类名:
 * @作者: lvheping
 * @创建时间: 2017/8/8 16:34
 * @版本: v1.0
 */
@Getter
@Setter
public class SendMqInfo implements Serializable,MQMsg {

    private static final long serialVersionUID = 2234373300043184353L;

    private String				purchaseOrderNo;			// 采购单号
}
