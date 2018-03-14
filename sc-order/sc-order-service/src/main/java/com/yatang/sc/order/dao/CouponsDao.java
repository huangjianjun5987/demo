package com.yatang.sc.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yatang.sc.order.domain.CouponsParamPo;
import com.yatang.sc.order.domain.CouponsPo;

public interface CouponsDao {
    int deleteByPrimaryKey(String promoId);

    int insert(CouponsPo record);

    int insertSelective(CouponsPo record);

    CouponsPo selectByPrimaryKey(String promoId);

    int updateByPrimaryKeySelective(CouponsPo record);

    int updateByPrimaryKey(CouponsPo record);

    int updateUsedQty(String id);

    int revertUsedQty(String id);
    
    int revertGrantQty(String id);
    /******************************mybatis模板外自定义的dao*******************************************/

    /**
     * 查询优惠券列表
     * @param couponsParamPo
     * @return
     */
    List<CouponsPo> queryList(CouponsParamPo couponsParamPo);
    
	/**
	 * 查询返券类型的优惠券
	 * @return
	 */
    List<CouponsPo>  queryToGiveCoupons();
    /**
     * 查询可发放的优惠券列表
     * @param couponsParamPo
     * @return
     */
    List<CouponsPo> queryAliveCouponsList(CouponsParamPo couponsParamPo);



    /**
     * 根据id查询优惠券详情
     * @param id
     * @return
     */
    CouponsPo queryById(String id);
    
    /**
     * 
    * <method description>更新已发放数量
    * @author zhoubaiyun
    * @param id
    * @param currentGrant
    * @return
     */
    int updateGrantQty(@Param("id") String id,@Param("currentGrant")Integer currentGrant);
    
    
}