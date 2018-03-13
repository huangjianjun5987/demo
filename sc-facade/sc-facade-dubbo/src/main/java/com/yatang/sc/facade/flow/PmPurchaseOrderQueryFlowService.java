package com.yatang.sc.facade.flow;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderExtDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderInfoDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderItemDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderItemQueryParamDto;
/**
 * @描述: 商品采购单的flowservice接口定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/26 10:27
 * @版本: v1.0
 */
public interface PmPurchaseOrderQueryFlowService {

    /**
     * 根据查询条件查询 采购单iem信息
     *
     * @param queryParamDto 查询条件（productId,spId,spAdrId）
     * @return
     */
    Response<PmPurchaseOrderItemDto> getNewPmPurchaseOrderItem(PmPurchaseOrderItemQueryParamDto queryParamDto);


    /**
     * 校验订单的有效性质(供应商是否有效,商品是否有效,供应商地点是否有效,采购关系是否有效,判定商品采购数量和采购内装数之间的关系)
     * @param pmPurchaseOrderExtDto
     * @return
     */
    Response<PmPurchaseOrderExtDto> checkAndResetPmPurchaseOrderExt(PmPurchaseOrderExtDto pmPurchaseOrderExtDto);

    /**
     * 校验草稿状态订单的有效性质并返回数据(供应商是否有效,商品是否有效,供应商地点是否有效,采购关系是否有效,判定商品采购数量和采购内装数之间的关系)
     * @param pmPurchaseOrderInfoDto
     * @return
     */
    Response<PmPurchaseOrderInfoDto> checkAndResetPmPurchaseOrderInfo(PmPurchaseOrderInfoDto pmPurchaseOrderInfoDto);

    /**
     * 校验预计送货日期是否等于订单创建日期+供应商提前期
     * @param convert
     * @return
     */
    Response<PmPurchaseOrderDto> auditPurchaseOrderInfo(PmPurchaseOrderDto convert);
}
