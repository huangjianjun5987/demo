package com.yatang.sc.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @描述: 订单状态
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/25 16:28
 * @版本: v1.0
 */
public enum KiddOrderStatusType {

    ACCEPT("ACCEPT", "仓库接单"),//仓库接单==》订单状态更新为待出库
    READY("READY", "待提货"),//待提货==》订单状态更新为待收货
    SENDCAR("SENDCAR", "已派车"),//已派车==>订单状态不变
    SIGNED("SIGNED", "正常签收"),//正常签收==》订单状态更新为已签收
    XINYI_SIGN("SIGN", "正常签收"),//正常签收==》订单状态更新为已签收
    REFUSE("REFUSE", "拒签"),//拒签==》未送达
    REJECT("REJECT", "仓库拒单"),//仓库拒单==》订单商品不足，拒单状态的销售订单需要再次调用订单新增接口传送 ;
    CANCELED("CANCELED", "取消成功"),
    CANCELEDFAIL("CANCELEDFAIL", "取消失败"),

    UNKNOWN("UNKNOWN", "未知状态");


    private String code;
    private String desc;

    KiddOrderStatusType(String pCode, String pDesc) {
        code = pCode;
        desc = pDesc;
    }

    public static KiddOrderStatusType parse(String code) {
        if (StringUtils.isEmpty(code)) {
            return UNKNOWN;
        }
        if (ACCEPT.code.equals(code)) {
            return ACCEPT;
        }
        if (READY.code.equals(code)) {
            return READY;
        }
        if (SENDCAR.code.equals(code)) {
            return SENDCAR;
        }
        if (CANCELED.code.equals(code)) {
            return CANCELED;
        }
        if (CANCELEDFAIL.code.equals(code)) {
            return CANCELEDFAIL;
        }
        if (SIGNED.code.equals(code)) {
            return SIGNED;
        }
        if (REFUSE.code.equals(code)) {
            return REFUSE;
        }
        if (REJECT.code.equals(code)) {
            return REJECT;
        }
        return UNKNOWN;
    }

    public static int getOrder(KiddOrderStatusType pKiddOrderStatusType) {
        if (ACCEPT.equals(pKiddOrderStatusType)) {
            return 10;
        }
        if (REJECT.equals(REJECT)) {
            return 12;
        }
        if (READY.equals(READY)) {
            return 20;
        }
        if (CANCELED.equals(CANCELED)) {
            return 21;
        }
        if (CANCELEDFAIL.equals(CANCELEDFAIL)) {
            return 22;
        }
        if (SENDCAR.equals(SENDCAR)) {
            return 25;
        }
        if (SIGNED.equals(SIGNED)) {
            return 30;
        }
        if (REFUSE.equals(REFUSE)) {
            return 40;
        }

        return 0;
    }
}
