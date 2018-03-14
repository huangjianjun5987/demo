package com.yatang.sc.order.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.dto.ParticiPateDto;
import com.yatang.sc.dto.PromoQueryConditionDto;
import com.yatang.sc.dto.PromotionDto;
import com.yatang.sc.dto.QueryParticipateDataDto;

import java.util.List;

/**
 * @描述: 促销活动dubbo服务接口
 * @作者: tankejia
 * @创建时间: 2017/9/4-14:08 .
 * @版本: 1.0 .
 */
public interface PromotionDubboService {
    
    /**
     * @Description: 多条件查询活动列表分页显示
     * @author tankejia
     * @date 2017/9/4- 14:10
     * @param record
     */
    Response<PageResult<PromotionDto>> listPromotions(PromoQueryConditionDto record);
    
    /**
     * @Description: 根据活动id查询促销范围列表接口
     * @author tankejia
     * @date 2017/9/4- 15:17
     * @param promoId
     */
    Response<List<String>> listBranchCompany(String promoId);

    /**
     * @Description: 新增促销活动
     * @author tankejia
     * @date 2017/9/4- 15:26
     * @param dto
     */
    Response<Boolean> insertPromotion(PromotionDto dto);

    /**
     * @Description: 发布/关闭促销活动
     * @author tankejia
     * @date 2017/9/6- 16:12
     * @param dto
     */
    Response<Boolean> updatePromoStatus(PromotionDto dto);

    /**
     * @Description: 查询活动详情
     * @author tankejia
     * @date 2017/9/6- 16:12
     * @param promotionId
     */
    Response<PromotionDto> queryPromotionDetail(String promotionId);

    /**
     * 查询参与数据列表
     * @param queryParticipateDataDto
     * @return
     */
    Response<ParticiPateDto> queryParticipateDataByCondition(QueryParticipateDataDto queryParticipateDataDto);

    /**
     * 查询指定范围内可用的活动
     * @param branchCompanyId
     * @param promoType
     * @return
     */
    Response<List<PromotionDto>> listAvailablePromotions(String branchCompanyId,String promoType);

    /**
     * @Description: 查询活动详情页修改关联的门店Id
     * @author zhudanning
     * @date 2017/12/14- 10:13
     * @param dto
     */
    Response<Boolean> updateStoreId(PromotionDto dto);

    /**
     * 批量修改活动或者优惠券的状态
     * @param couponPromotionIds
     * @return
     */
    Response<Boolean> batchUpdatePromoStatus(String[] couponPromotionIds,String status);
}
