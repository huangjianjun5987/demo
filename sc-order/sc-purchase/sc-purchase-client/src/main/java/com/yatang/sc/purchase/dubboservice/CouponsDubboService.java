package com.yatang.sc.purchase.dubboservice;

import java.util.List;

import com.busi.common.resp.Response;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.purchase.dto.CouponActivityRecordDto;
import com.yatang.sc.purchase.dto.CouponsDto;
import com.yatang.sc.purchase.dto.CouponsParamDto;

/**
 * @描述: 优惠券
 * @类名: CouponDubboService
 * @作者: kangdong
 * @创建时间: 2017/9/19 11:26
 * @版本: v1.0
 */
public interface CouponsDubboService {

    /**
     * @Description: 我的优惠券是否使用
     * @date 2017/9/19- 14:13
     * @param storeId
     * @param state
     * @return
     */
    public Response<List<CouponActivityRecordDto>> queryStoreCouponActivities(String storeId, String state);

    /**
     * @Description: 已过期的我的优惠券领取记录
     * @date 2017/9/19- 14:13
     * @param storeId
     * @return
     */
    public Response<List<CouponActivityRecordDto>> queryOverdueCouponActivityItems(String storeId);

    /**
     * @Description: 查询可用的优惠券
     * @date 2017/9/19- 14:13
     * @param storeId
     * @return
     */
    public Response<PageInfo<CouponActivityRecordDto>> queryAvailableCouponActivities(String userId, String storeId,int pageNum);

    /**
     * 查询领取方式为用户领取的优惠券
     * @param couponsParamDto
     * @author yinyuxn
     * @return
     */
    Response<PageResult<CouponsDto>> queryPersonalCoupons(CouponsParamDto couponsParamDto);

    /**
     * 用户领取优惠券
     * @param couponsParamDto
     * @author yinyuxn
     * @return
     */
    Response<Integer> receiveCoupon(CouponsParamDto couponsParamDto);

    /*
     *@描述:结算后返券金额
     *@作者:tangqi
     *@时间:2017/11/24 13:31
     */
    Response<Double> calcCouponValue(String orderId);

    /*
     *@描述:结算前返券金额（显示）
     *@作者:tangqi
     *@时间:2017/11/29 11:51
     */
    Response<Double> calcGiveCoupon(Double price, String userId);
}
