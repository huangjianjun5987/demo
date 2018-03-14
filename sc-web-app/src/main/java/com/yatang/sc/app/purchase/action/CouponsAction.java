package com.yatang.sc.app.purchase.action;

import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.app.action.AppBaseAction;
import com.yatang.sc.app.vo.coupon.CouponParamVo;
import com.yatang.sc.app.vo.coupon.CouponsDetailVo;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.purchase.dto.CouponsDto;
import com.yatang.sc.purchase.dto.CouponsParamDto;
import com.yatang.sc.purchase.dubboservice.CouponsDubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description:app优惠券action
 * @author: yinyuxin
 * @date: 2017/10/22 15:19
 * @version: v1.0
 */
@RestController
@RequestMapping(value = "/sc/coupons")
public class CouponsAction extends AppBaseAction{

	private static final Logger log = LoggerFactory.getLogger(PaymentAction.class);

	@Autowired
	private CouponsDubboService couponsDubboService;



	/**
	 * 查询用户自领取的优惠券列表
	 * @param couponParamVo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryPersonalCoupons",method = RequestMethod.GET)
	public Response<PageResult<CouponsDetailVo>> queryPersonalCoupons(CouponParamVo couponParamVo,HttpServletRequest request){
		log.info("------------CouponsAction-->queryPersonalCoupons(),param:{}------------",JSONObject.toJSONString(couponParamVo));
		Response<PageResult<CouponsDetailVo>> response=new Response<>();
		PageResult<CouponsDetailVo> pageResult=new PageResult<>();
		//获取当前用户所属子公司
		couponParamVo.setBranchCompanyId(this.getBranchCompanyId(request));
		couponParamVo.setStoreId(this.getStoreId(request));
		
		CouponsParamDto couponsParamDto= BeanConvertUtils.convert(couponParamVo,CouponsParamDto.class);
		//调用dubbo查询数据
		Response<PageResult<CouponsDto>> dtoResponse = couponsDubboService.queryPersonalCoupons(couponsParamDto);
		if (dtoResponse.getResultObject().getData()!=null && dtoResponse.getResultObject().getData().size()>0){
			List<CouponsDetailVo> data = BeanConvertUtils.convertList(dtoResponse.getResultObject().getData(), CouponsDetailVo.class);
			pageResult.setData(data);
			pageResult.setPageNum(dtoResponse.getResultObject().getPageNum());
			pageResult.setPageSize(dtoResponse.getResultObject().getPageSize());
			pageResult.setTotal(dtoResponse.getResultObject().getTotal());
		}
		response.setResultObject(pageResult);
		response.setCode(dtoResponse.getCode());
		response.setErrorMessage(dtoResponse.getErrorMessage());
		response.setSuccess(dtoResponse.isSuccess());
		return response;
	}



	/**
	 * 用户领取优惠券
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "receiveCoupon",method = RequestMethod.GET)
	public  Response<Integer> receiveCoupon(String id,HttpServletRequest request){
		log.info("------------CouponsAction-->receiveCoupon(),param:" + id+ "------------");
		Response<Integer> response=new Response<>();
		CouponsParamDto couponsParamDto=new CouponsParamDto();
		couponsParamDto.setStoreId(this.getStoreId(request));
		couponsParamDto.setId(id);
		return couponsDubboService.receiveCoupon(couponsParamDto);
	}
}
