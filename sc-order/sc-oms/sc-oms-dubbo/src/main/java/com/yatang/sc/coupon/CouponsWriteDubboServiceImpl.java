package com.yatang.sc.coupon;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.coupon.dubboservice.CouponsWriteDubboService;
import com.yatang.sc.dto.CouponsDto;
import com.yatang.sc.dto.PromotionDto;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.order.domain.CouponsPo;
import com.yatang.sc.order.domain.PromotionPo;
import com.yatang.sc.order.service.CouponsService;
import com.yatang.sc.order.service.PromotionService;
import com.yatang.sc.util.IdGeneratorUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: yinyuxin
 * @date: 2017/9/19 18:10
 * @version: v1.0
 */
@Service("couponsWriteDubboService")
public class CouponsWriteDubboServiceImpl implements CouponsWriteDubboService{

	private Logger log= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CouponsService couponsService;
	@Autowired
	private PromotionService promotionService;
	@Autowired
	private IdGeneratorUtil idGeneratorUtil;

	@Override
	public Response<Boolean> insertCoupons(CouponsDto couponsDto) {
		log.info("------------couponsWriteDubboService-->insertCoupons(),param:"+ JSONObject.toJSONString(couponsDto)+"------------");
		Response<Boolean> response=new Response<Boolean>();
		try {
			//0:验证参数是否为空
			if (null==couponsDto){
				throw new RuntimeException("参数为null");
			}
			//1：获取主键id
			String id=idGeneratorUtil.getIdGeneratorId("J");
			if (StringUtils.isBlank(id)||StringUtils.isEmpty(id)){
				throw new RuntimeException("主键id获取错误");
			}
			couponsDto.setId(id);
			//2: 新增促销记录
			PromotionPo promotionPo= BeanConvertUtils.convert(couponsDto,PromotionPo.class);
			promotionPo.setType(1);
			promotionPo.setDiscountType("stander");
			promotionPo.setPromotionType("order_promo");
			Boolean flag=promotionService.insertPromotion(promotionPo);
			if (!flag){
				throw new RuntimeException("插入促销表记录失败");
			}
			//3: 新增优惠券记录
			CouponsPo couponsPo=BeanConvertUtils.convert(couponsDto,CouponsPo.class);
			//3.1：初始值，避免null值影响后面的业务
			couponsPo.setUsedQty(0l);
			couponsPo.setGrantQty(0l);
			couponsService.insertSelective(couponsPo);
			response.setSuccess(true);
			response.setResultObject(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			return response;
		} catch (Exception e) {
			response.setSuccess(false);
			response.setResultObject(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.info("------------couponsWriteDubboService-->insertCoupons(),error:"+e.toString()+"------------");
			return response;
		}
	}
}
