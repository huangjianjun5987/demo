package com.yatang.sc.sorder.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xiangyonghong on 2017/7/17.
 */
@Data
public class QueryOrderVo implements Serializable{

    private String id;//订单编号
    private String profile_id;//用户id
    private Date submit_time;//下单时间
    private int state;//状态
    private String price;//订单支付金额
    private String discount;//优惠金额
    private String userName;//买家用户名
    private String actType;//活动类型
    private String payMent;//支付方式
    private String payChannel;//支付渠道
    private String last_modified_time;//最后更新时间
}
