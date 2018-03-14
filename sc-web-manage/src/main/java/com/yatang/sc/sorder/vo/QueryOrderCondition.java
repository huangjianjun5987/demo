package com.yatang.sc.sorder.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xiangyonghong on 2017/7/17.
 */
@Data
public class QueryOrderCondition implements Serializable{

    private int state;//0全部，1待付款，2待发货，3待收货，4已完成，5已取消
    private int payment;//0全部，1快捷支付，2体验店支付
    private int noType;//0订单号，1商品SKU，2商品名称，3买家门店编号
    private Date payStartTime;
    private Date payEndTime;
    private Date orderStartTime;
    private Date orderEndTime;
    private int page;
    private int pageSize;
}
