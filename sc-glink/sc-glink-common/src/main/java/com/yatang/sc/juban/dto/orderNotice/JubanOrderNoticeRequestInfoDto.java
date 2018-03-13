package com.yatang.sc.juban.dto.orderNotice;

import java.io.Serializable;

import com.yatang.sc.xinyi.dto.orderNotice.XinyiOrderNoticeInfoDto;
import com.yatang.sc.xinyi.dto.orderNotice.XinyiOrderProcessInfoDto;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: todo 响应的类 可能会改动 todo
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/25 16:53
 * @版本: v1.0
 */
@Getter
@Setter
public class JubanOrderNoticeRequestInfoDto implements Serializable {
    private static final long serialVersionUID = 8395792616580606910L;

    private JubanOrderNoticeInfoDto order;

    private JubanOrderProcessInfoDto process;//订单状态信息
}
