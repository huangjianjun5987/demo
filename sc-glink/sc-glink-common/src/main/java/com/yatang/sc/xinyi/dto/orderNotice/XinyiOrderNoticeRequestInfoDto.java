package com.yatang.sc.xinyi.dto.orderNotice;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @描述: todo 响应的类 可能会改动 todo
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/25 16:53
 * @版本: v1.0
 */
@Getter
@Setter
public class XinyiOrderNoticeRequestInfoDto implements Serializable {
    private static final long serialVersionUID = 8395792616580606910L;

    private XinyiOrderNoticeInfoDto order;

    private XinyiOrderProcessInfoDto process;//订单状态信息
}
