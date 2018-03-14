package com.yatang.sc.operation.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.common.utils.ConvertUtil;
import com.yatang.sc.coupon.dubboservice.CouponActivityDubbleService;
import com.yatang.sc.coupon.dubboservice.CouponsQueryDubboService;
import com.yatang.sc.coupon.dubboservice.CouponsWriteDubboService;
import com.yatang.sc.dto.CouponActivityDto;
import com.yatang.sc.dto.CouponActivityParamDto;
import com.yatang.sc.dto.CouponRecordDto;
import com.yatang.sc.dto.CouponRecordParamDto;
import com.yatang.sc.dto.CouponsDto;
import com.yatang.sc.dto.CouponsParamDto;
import com.yatang.sc.dto.GrantCouponDto;
import com.yatang.sc.operation.util.SessionUtil;
import com.yatang.sc.operation.vo.GrantCouponBatchVo;
import com.yatang.sc.operation.vo.GrantCouponVo;
import com.yatang.sc.operation.vo.coupon.CouponActivityParamVo;
import com.yatang.sc.operation.vo.coupon.CouponActivityVo;
import com.yatang.sc.operation.vo.coupon.CouponParamVo;
import com.yatang.sc.operation.vo.coupon.CouponRecordParamVo;
import com.yatang.sc.operation.vo.coupon.CouponRecordVo;
import com.yatang.sc.operation.vo.coupon.CouponsDetailVo;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.web.paramvalid.MessageConstantUtil;
import com.yatang.sc.web.paramvalid.ParamValid;
import com.yatang.sc.web.view.XlsData;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;

import static com.yatang.sc.facade.common.Constants.CURRENT_USER;

/**
 * 
 * <class description>优惠券web接口，处理优惠券相关业务
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年9月19日
 */
@RestController
@RequestMapping(value = "/sc/coupon")
public class CouponAction extends BaseAction {

	@Autowired
	private CouponActivityDubbleService			couponActivityDubbleService;
	@Autowired
	private CouponsWriteDubboService			couponsWriteDubboService;
	@Autowired
	private CouponsQueryDubboService			couponsQueryDubboService;

	private Logger								log	= LoggerFactory.getLogger(this.getClass());



	@RequestMapping(value = "/grantCoupon", method = RequestMethod.POST)
	public Response<String> grantCoupon(@RequestBody GrantCouponVo grantCouponVo, HttpSession session) {
		 //LoginInfoVO currentUser = (LoginInfoVO)
		 //session.getAttribute(CURRENT_USER);
		 //if (null == currentUser || null == currentUser.getUserId()) {
		 //log.error("获取用户信息出错，grantCouponVo={},session={}",
		 //JSON.toJSONString(grantCouponVo),
		 //JSON.toJSONString(session));
		 //return getFailResponse();
		 //}
		if (null == grantCouponVo || null == grantCouponVo.getPromoIds() || grantCouponVo.getPromoIds().length == 0
				|| null == grantCouponVo.getStoreIds() || grantCouponVo.getStoreIds().length == 0) {
			log.error("门店ID列表或者优惠券ID列表为空，门店ID列表：{}，优惠券ID列表：{}", Arrays.toString(grantCouponVo.getStoreIds()),
					Arrays.toString(grantCouponVo.getPromoIds()));
			return getFailResponse();
		}
		GrantCouponDto grantCouponDto = BeanConvertUtils.convert(grantCouponVo, GrantCouponDto.class);
		// grantCouponDto.setUserId(currentUser.getUserId());
		log.info("调用couponWriteDubbleService.grantCoupon(grantCouponDto)方法，参数为：{}", JSON.toJSONString(grantCouponDto));
		Response<String> result = couponActivityDubbleService.grantCoupon(grantCouponDto);
		log.info("调用couponWriteDubbleService.grantCoupon(grantCouponDto)方法结果为：{}", JSON.toJSONString(result));
		return result;
	}

	/**
	 * 新增优惠券
	 *
	 * @param CouponsDetailVo
	 * @return
	 */
	@RequestMapping(value = "insertCoupons", method = RequestMethod.POST)
	public Response<Boolean> insertCoupons(
			@Validated({ GroupOne.class }) @RequestBody CouponsDetailVo CouponsDetailVo,HttpSession session) {
		log.info("------------CouponAction-->insertCoupons(),param:" + JSONObject.toJSONString(CouponsDetailVo)
				+ "------------");
		LoginInfoVO currentUser = (LoginInfoVO)
				session.getAttribute(CURRENT_USER);
		if (null == currentUser || null == currentUser.getUserId()) {
			log.error("获取用户信息出错，CouponsDetailVo={},session={}",
					JSON.toJSONString(CouponsDetailVo),
					JSON.toJSONString(session));
			return getFailResponse();
		}
		CouponsDto couponsDto = BeanConvertUtils.convert(CouponsDetailVo, CouponsDto.class);
		couponsDto.setCreateDate(new Date());
		couponsDto.setCreateUserId(currentUser.getUserId());
		return couponsWriteDubboService.insertCoupons(couponsDto);
	}



	/**
	 * 根据id查询优惠券
	 *
	 * @param promoId
	 * @return
	 */
	@RequestMapping(value = "queryCouponsById", method = RequestMethod.GET)
	public Response<CouponsDetailVo> queryCouponsById(String promoId) {
		log.info("------------CouponAction-->queryCouponsById(),param:" + JSONObject.toJSONString(promoId)
				+ "------------");
		Response<CouponsDetailVo> voResponse = new Response<>();
		Response<CouponsDto> dtoResponse = couponsQueryDubboService.queryCouponsById(promoId);
		voResponse.setResultObject(BeanConvertUtils.convert(dtoResponse.getResultObject(), CouponsDetailVo.class));
		voResponse.setSuccess(dtoResponse.isSuccess());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setCode(dtoResponse.getCode());
		return voResponse;
	}



	/**
	 * 查询优惠券列表
	 *
	 * @param couponsParamVo
	 * @return
	 */
	@RequestMapping(value = "queryCouponsList", method = RequestMethod.GET)
	public Response<PageResult<CouponsDetailVo>> queryCouponsList(CouponParamVo couponsParamVo) {
		log.info("------------CouponAction-->queryCouponsList(),param:" + JSONObject.toJSONString(couponsParamVo)
				+ "------------");
		Response<PageResult<CouponsDetailVo>> voResponse = new Response<>();
		PageResult<CouponsDetailVo> pageResult = new PageResult<>();
		Response<PageResult<CouponsDto>> dtoResponse = couponsQueryDubboService
				.queryCouponsList(BeanConvertUtils.convert(couponsParamVo, CouponsParamDto.class));
		PageResult<CouponsDto> dtoPageResult = dtoResponse.getResultObject();
		if (dtoPageResult != null) {
			List<CouponsDetailVo> data = BeanConvertUtils.convertList(dtoPageResult.getData(), CouponsDetailVo.class);
			pageResult.setData(data);
			pageResult.setPageNum(dtoPageResult.getPageNum());
			pageResult.setPageSize(dtoPageResult.getPageSize());
			pageResult.setTotal(dtoPageResult.getTotal());
		}
		voResponse.setResultObject(pageResult);
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setSuccess(dtoResponse.isSuccess());
		return voResponse;
	}

	/**
	 * 查询可发放的优惠券列表
	 *
	 * @param couponsParamVo
	 * @return
	 */
	@RequestMapping(value = "queryAliveCouponsList", method = RequestMethod.GET)
	public Response<PageResult<CouponsDetailVo>> queryAliveCouponsList(CouponParamVo couponsParamVo) {

		log.info("------------CouponAction-->queryAliveCouponsList(),param:" + JSONObject.toJSONString(couponsParamVo)
				+ "------------");
		Response<PageResult<CouponsDetailVo>> voResponse = new Response<>();
		PageResult<CouponsDetailVo> pageResult = new PageResult<>();
		Response<PageResult<CouponsDto>> dtoResponse = couponsQueryDubboService
				.queryAliveCouponsList(BeanConvertUtils.convert(couponsParamVo, CouponsParamDto.class));
		PageResult<CouponsDto> dtoPageResult = dtoResponse.getResultObject();
		if (dtoPageResult != null) {

			List<CouponsDetailVo> data = BeanConvertUtils.convertList(dtoPageResult.getData(), CouponsDetailVo.class);
			pageResult.setData(data);
			pageResult.setPageNum(dtoPageResult.getPageNum());
			pageResult.setPageSize(dtoPageResult.getPageSize());
			pageResult.setTotal(dtoPageResult.getTotal());
		}
		voResponse.setResultObject(pageResult);
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setSuccess(dtoResponse.isSuccess());
		return voResponse;
	}




	/**
	 * 查询参与数据（已使用）
	 *
	 * @param couponRecordParamVo
	 * @return
	 */
	@RequestMapping(value = "queryCouponRecordList", method = RequestMethod.GET)
	public Response<PageResult<CouponRecordVo>> queryCouponRecordList(CouponRecordParamVo couponRecordParamVo) {
		log.info("------------CouponAction-->queryCouponRecordList(),param:"
				+ JSONObject.toJSONString(couponRecordParamVo) + "------------");
		Response<PageResult<CouponRecordVo>> voResponse = new Response<>();
		PageResult<CouponRecordVo> pageResult = new PageResult<>();
		Response<PageResult<CouponRecordDto>> dtoResponse = couponsQueryDubboService
				.queryCouponRecordList(BeanConvertUtils.convert(couponRecordParamVo, CouponRecordParamDto.class));
		PageResult<CouponRecordDto> dtoPageResult = dtoResponse.getResultObject();
		if (dtoPageResult != null) {
			List<CouponRecordVo> data = BeanConvertUtils.convertList(dtoPageResult.getData(), CouponRecordVo.class);
			pageResult.setData(data);
			pageResult.setPageNum(dtoPageResult.getPageNum());
			pageResult.setPageSize(dtoPageResult.getPageSize());
			pageResult.setTotal(dtoPageResult.getTotal());
		}
		voResponse.setResultObject(pageResult);
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setSuccess(dtoResponse.isSuccess());
		return voResponse;
	}



	/**
	 * 查询参与数据（已使用）导出excel
	 *
	 * @param couponRecordParamVo
	 * @return
	 */
	@RequestMapping(value = "queryCouponRecordListExcel", method = RequestMethod.GET)
	public ModelAndView queryCouponRecordListExcel(CouponRecordParamVo couponRecordParamVo) {
		log.info("------------CouponAction-->queryCouponRecordListExcel(),param:"
				+ JSONObject.toJSONString(couponRecordParamVo) + "------------");
		Response<PageResult<CouponRecordDto>> dtoResponse = couponsQueryDubboService
				.queryCouponRecordList(BeanConvertUtils.convert(couponRecordParamVo, CouponRecordParamDto.class));
		PageResult<CouponRecordDto> dtoPageResult = dtoResponse.getResultObject();
		XlsData xlsData = new XlsData("优惠券参与数据");
		if (dtoPageResult != null) {
			List<CouponRecordVo> data = BeanConvertUtils.convertList(dtoPageResult.getData(), CouponRecordVo.class);
			transData(data);
			xlsData.setData(CouponRecordVo.class, data);
			xlsData.setPattern("yyyy-MM-dd HH:mm:ss");
		}
		return getXlsModelAndView(xlsData);
	}



	/**
	 * 查询参与数据（未使用或作废记录）
	 *
	 * @param couponActivityParamVo
	 * @return
	 */
	@RequestMapping(value = "queryCouponActivityActiveList", method = RequestMethod.GET)
	public Response<PageResult<CouponActivityVo>> queryCouponActivityActiveList(
			CouponActivityParamVo couponActivityParamVo) {

		log.info("------------CouponAction-->queryCouponActivityActiveList(),param:"
				+ JSONObject.toJSONString(couponActivityParamVo) + "------------");

		Response<PageResult<CouponActivityVo>> voResponse = new Response<>();
		PageResult<CouponActivityVo> pageResult = new PageResult<>();
		Response<PageResult<CouponActivityDto>> dtoResponse = couponsQueryDubboService.queryCouponActivityActiveList(
				BeanConvertUtils.convert(couponActivityParamVo, CouponActivityParamDto.class));
		PageResult<CouponActivityDto> dtoPageResult = dtoResponse.getResultObject();
		if (dtoPageResult != null) {
			List<CouponActivityVo> data = BeanConvertUtils.convertList(dtoPageResult.getData(), CouponActivityVo.class);
			pageResult.setData(data);
			pageResult.setPageNum(dtoPageResult.getPageNum());
			pageResult.setPageSize(dtoPageResult.getPageSize());
			pageResult.setTotal(dtoPageResult.getTotal());
		}
		voResponse.setResultObject(pageResult);
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setSuccess(dtoResponse.isSuccess());
		return voResponse;
	}



	/**
	 * 查询参与数据（未使用）导出excel
	 *
	 * @param couponActivityParamVo
	 * @return
	 */
	@RequestMapping(value = "queryCouponActivityActiveListExcel", method = RequestMethod.GET)
	public ModelAndView queryCouponActivityActiveListExcel(CouponActivityParamVo couponActivityParamVo) {
		log.info("------------CouponAction-->queryCouponActivityActiveListExcel(),param:"
				+ JSONObject.toJSONString(couponActivityParamVo) + "------------");
		Response<PageResult<CouponActivityDto>> dtoResponse = couponsQueryDubboService.queryCouponActivityActiveList(
				BeanConvertUtils.convert(couponActivityParamVo, CouponActivityParamDto.class));
		PageResult<CouponActivityDto> dtoPageResult = dtoResponse.getResultObject();
		XlsData xlsData = new XlsData("优惠券参与数据");
		if (dtoPageResult != null) {
			List<CouponActivityVo> data = BeanConvertUtils.convertList(dtoPageResult.getData(), CouponActivityVo.class);
			xlsData.setData(CouponActivityVo.class, data);
			xlsData.setPattern("yyyy-MM-dd HH:mm:ss");
		}
		return getXlsModelAndView(xlsData);
	}

	/**
	 * 批量发放优惠券
	 * @param grantCouponVo
	 * @return
	 */
	@RequestMapping(value = "/grantMutiQtyCoupon", method = RequestMethod.POST)
	public Response<String> grantMutiQtyCoupon(@RequestBody GrantCouponBatchVo grantCouponVo) {
		Response<String> result = null;
		String[] storeIds = grantCouponVo.getStoreIds();
		Map<String, Integer> grantParam = grantCouponVo.getCouponParam();
		Set<String> couponIdSet = grantParam.keySet();
		String[] couponIds = new String[couponIdSet.size()];
		for(String coupon: couponIdSet){
			int i = 0;
			couponIds[i++] = coupon;
		}
		GrantCouponDto grantCouponDto = new GrantCouponDto();
		grantCouponDto.setStoreIds(storeIds);
		grantCouponDto.setPromoIds(couponIds);
		log.info("调用couponWriteDubbleService.grantCoupon(grantCouponDto)方法，参数为：{}", JSON.toJSONString(grantCouponDto));
		for (Map.Entry<String, Integer> entry: grantParam.entrySet()){
			result = couponActivityDubbleService.grantMutiQtyCoupon(storeIds, entry.getKey(), entry.getValue());
		}
		log.info("调用couponWriteDubbleService.grantCoupon(grantCouponDto)方法结果为：{}", JSON.toJSONString(result));
		return result;
	}


	/**
	 * @Description: 优惠券批量作废
	 * @author tankejia
	 * @date 2017/10/31- 12:37
	 * @param couponActivityIds
	 */
	@ParamValid
	@RequestMapping(value = "cancelCoupons", method = RequestMethod.GET)
	public Response<String> cancelCoupons(@NotBlank(message = MessageConstantUtil.NOT_EMPTY) String couponActivityIds) {
		Response<String> response = new Response<>();
		// 获取当前用户信息
		LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
		if (loginInfoVO == null || loginInfoVO.getUserId() == null) {
			response.setCode(CommonsEnum.RESPONSE_401.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
			return response;
		}
		return couponActivityDubbleService.cancelCoupons(ConvertUtil.strArrToLongArr(couponActivityIds.split(",")),
				loginInfoVO.getUserId());
	}

	/**
	 * 已使用参数数据excel字符替换
	 *
	 * @param list
	 */
	private void transData(List<CouponRecordVo> list) {
		for (CouponRecordVo couponRecordVo : list) {
			// 订单状态 W:待审核 M:待人工审核 A:已审核 Q:已取消 C:已完成 SP:已拆单
			// 配送状态 DCL:待处理 WCS:未传送 DCK:待出库 WJS:仓库拒收 DSH:待收货 YQS:已签收 WSD:未送达
			// QXZ:取消中 QX:取消送货 CGWDH:库存不足
			// 支付状态 WZF:未支付 YZF:已支付 TKD:退款待审核 TKQ:退款待确认 YTK:已退款 QXFK:取消付款
			// GSN:公司内
			switch (couponRecordVo.getOrderState()) {
			case "W":
				couponRecordVo.setOrderState("待审核");
				break;
			case "M":
				couponRecordVo.setOrderState("待人工审核");
				break;
			case "A":
				couponRecordVo.setOrderState("已审核");
				break;
			case "Q":
				couponRecordVo.setOrderState("已取消");
				break;
			case "C":
				couponRecordVo.setOrderState("已完成");
				break;
			case "SP":
				couponRecordVo.setOrderState("已拆单");
				break;
			default:
				couponRecordVo.setOrderState("状态有误");
			}
			switch (couponRecordVo.getShippingState()) {
			case "DCL":
				couponRecordVo.setShippingState("待处理");
				break;
			case "WCS":
				couponRecordVo.setShippingState("未传送");
				break;
			case "DCK":
				couponRecordVo.setShippingState("待出库");
				break;
			case "WJS":
				couponRecordVo.setShippingState("仓库拒收");
				break;
			case "DSH":
				couponRecordVo.setShippingState("待收货");
				break;
			case "YQS":
				couponRecordVo.setShippingState("已签收");
				break;
			case "WSD":
				couponRecordVo.setShippingState("未送达");
				break;
			case "QXZ":
				couponRecordVo.setShippingState("取消中");
				break;
			case "QX":
				couponRecordVo.setShippingState("取消送货");
				break;
			case "CGWDH":
				couponRecordVo.setShippingState("库存不足");
				break;
			default:
				couponRecordVo.setShippingState("状态有误");
			}
			switch (couponRecordVo.getPaymentState()) {
			case "WZF":
				couponRecordVo.setPaymentState("未支付");
				break;
			case "YZF":
				couponRecordVo.setPaymentState("已支付");
				break;
			case "TKD":
				couponRecordVo.setPaymentState("退款待审核");
				break;
			case "TKQ":
				couponRecordVo.setPaymentState("退款待确认");
				break;
			case "YTK":
				couponRecordVo.setPaymentState("已退款");
				break;
			case "QXFK":
				couponRecordVo.setPaymentState("取消付款");
				break;
			case "GSN":
				couponRecordVo.setPaymentState("公司内");
				break;
			default:
				couponRecordVo.setPaymentState("状态有误");
			}
		}
	}
}
