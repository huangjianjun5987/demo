package com.yatang.sc.web.paramvalid;

/**
 * @描述:
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/6 15:43
 * @版本: v1.0
 */
public class MessageConstantUtil {
    private MessageConstantUtil() {
    }

    public static final String NOT_EMPTY="不能为空";
    public static final String AD_PLAN_STATUS_RANGE ="必须是整数 0:停用,1:启用";
    public static final String STATUS_RANGE ="必须是整数0或者1";
    public static final String ID_RANGE="区间范围为[0,整数的最大值:2的32次方-1]";
}
