package com.yatang.sc.operation.web;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.base.Strings;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.dto.WishDetailDto;
import com.yatang.sc.dto.WishListDto;
import com.yatang.sc.dto.WishListParamDto;
import com.yatang.sc.operation.vo.wish.WishDetailForExcelVo;
import com.yatang.sc.operation.vo.wish.WishListForExcelVo;
import com.yatang.sc.operation.vo.wish.WishListParamVo;
import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.web.paramvalid.MessageConstantUtil;
import com.yatang.sc.web.paramvalid.ParamValid;
import com.yatang.sc.web.view.XlsData;
import com.yatang.sc.wish.dubboService.WishBackQueryDubboService;
import com.yatang.sc.wish.dubboService.WishBackWriteDubboService;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static com.yatang.sc.facade.common.Constants.CURRENT_USER;

/**
 * @description: 心愿单 后台管理action
 * @author: yinyuxin
 * @date: 2018/1/9 10:25
 * @version: v1.0
 */
@RestController
@RequestMapping("/sc/wishBackAction")
public class WishBackAction extends BaseAction{

	@Autowired
	private WishBackWriteDubboService wishBackWriteDubboService;
	@Autowired
	private WishBackQueryDubboService wishBackQueryDubboService;

	private static final Logger LOGGER= LoggerFactory.getLogger(WishBackAction.class);

	/**
	 * 动态查询心愿单列表
	 * @param wishListParamVo
	 * @author yinyuxin
	 * @return
	 */
	@RequestMapping(value = "queryWishListsByParam",method = RequestMethod.GET)
	public Response<PageResult<WishListDto>> queryWishListsByParam(WishListParamVo wishListParamVo,HttpServletRequest request){
		LOGGER.info("wishBackAction--queryWishListsByParam()--param:"+ JSONObject.toJSONString(wishListParamVo));
		LoginInfoVO loginInfoVO = (LoginInfoVO) request.getSession().getAttribute(CURRENT_USER);
		if (null==loginInfoVO){
			Response response = new Response();
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
			return response;
		}
		List<String> branchCompanyIds=new ArrayList<>();
		if (Strings.isNullOrEmpty(wishListParamVo.getBranchCompanyId())){
			branchCompanyIds=this.queryUserCompanyIds(loginInfoVO.getUserId());
		}else {
			branchCompanyIds.add(wishListParamVo.getBranchCompanyId());
		}
		WishListParamDto wishListParamDto= BeanConvertUtils.convert(wishListParamVo,WishListParamDto.class);
		wishListParamDto.setBranchCompanyIds(branchCompanyIds);
		Response<PageResult<WishListDto>> response= wishBackQueryDubboService.queryWishListsByParam(wishListParamDto);
		return response;
	}



	/**
	 * 导出心愿单列表excel
	 * @param wishListParamVo
	 * @author yinyuxin
	 * @return
	 */
	@RequestMapping(value = "queryWishListsForExcel",method = RequestMethod.GET)
	public ModelAndView queryWishListsForExcel(WishListParamVo wishListParamVo,HttpServletRequest request){
		LOGGER.info("wishBackAction--queryWishDetailsByWishListIdAndStoreId()--param:"+ JSONObject.toJSONString(wishListParamVo));
		LoginInfoVO loginInfoVO = (LoginInfoVO) request.getSession().getAttribute(CURRENT_USER);
		if (null==loginInfoVO){
			LOGGER.error("wishBackAction--queryWishListsForExcel()--error:用户未登录");
			return null;
		}
		List<String> branchCompanyIds=new ArrayList<>();
		if (Strings.isNullOrEmpty(wishListParamVo.getBranchCompanyId())){
			branchCompanyIds=this.queryUserCompanyIds(loginInfoVO.getUserId());
		}else {
			branchCompanyIds.add(wishListParamVo.getBranchCompanyId());
		}
		WishListParamDto wishListParamDto= BeanConvertUtils.convert(wishListParamVo,WishListParamDto.class);
		wishListParamDto.setPageNum(1);
		wishListParamDto.setPageSize(Integer.MAX_VALUE);
		wishListParamDto.setBranchCompanyIds(branchCompanyIds);
		Response<PageResult<WishListDto>> response= wishBackQueryDubboService.queryWishListsByParam(wishListParamDto);
		XlsData xlsData = new XlsData("预定单列表");
		xlsData.setData(WishListForExcelVo.class, this.installWishListDataForExcel(response));
		xlsData.setPattern("YYYY-MM-dd hh:mm:ss");
		return getXlsModelAndView(xlsData);
	}



	/**
	 * 根据心愿单id 和门店id查询心愿单明细清单
	 * @param wishListParamVo
	 * @author yinyuxin
	 * @return
	 */
	@RequestMapping(value = "queryWishDetailsByWishListIdAndStoreId",method = RequestMethod.GET)
	public Response<PageResult<WishDetailDto>> queryWishDetailsByWishListIdAndStoreId(@Validated(DefaultGroup.class) WishListParamVo wishListParamVo,HttpServletRequest request){
		LOGGER.info("wishBackAction--queryWishDetailsByWishListIdAndStoreId()--param:"+ JSONObject.toJSONString(wishListParamVo));
		LoginInfoVO loginInfoVO = (LoginInfoVO) request.getSession().getAttribute(CURRENT_USER);
		if (null==loginInfoVO){
			Response response = new Response();
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
			return response;
		}
		WishListParamDto wishListParamDto= BeanConvertUtils.convert(wishListParamVo,WishListParamDto.class);
		Response<PageResult<WishDetailDto>> resultResponse=wishBackQueryDubboService.queryWishDetailsByWishListIdAndStoreId(wishListParamDto);
		return resultResponse;
	}



	/**
	 * 根据心愿单id 和门店id查询心愿单明细清单（导出excel）
	 * @param wishListParamVo
	 * @author yinyuxin
	 * @return
	 */
	@RequestMapping(value = "queryWishDetailsForExcel",method = RequestMethod.GET)
	public ModelAndView queryWishDetailsForExcel(@Validated(DefaultGroup.class) WishListParamVo wishListParamVo,HttpServletRequest request){
		LOGGER.info("wishBackAction--queryWishDetailsForExcel()--param:"+ JSONObject.toJSONString(wishListParamVo));
		LoginInfoVO loginInfoVO = (LoginInfoVO) request.getSession().getAttribute(CURRENT_USER);
		if (null==loginInfoVO){
			LOGGER.error("wishBackAction--queryWishListsForExcel()--error:用户未登录");
			return null;
		}
		WishListParamDto wishListParamDto= BeanConvertUtils.convert(wishListParamVo,WishListParamDto.class);
		wishListParamDto.setPageNum(1);
		wishListParamDto.setPageSize(Integer.MAX_VALUE);
		Response<PageResult<WishDetailDto>> resultResponse=wishBackQueryDubboService.queryWishDetailsByWishListIdAndStoreId(wishListParamDto);
		XlsData xlsData = new XlsData("预定单明细");
		xlsData.setData(WishDetailForExcelVo.class, this.installWishDetailsDataForExcel(resultResponse));
		xlsData.setPattern("YYYY-MM-dd hh:mm:ss");
		return getXlsModelAndView(xlsData);
	}



	/**
	 * 完成或关闭心愿单（会触发小超B端服务，通知用户处理结果）
	 * @param wishListParamVo owishListId 心愿单主键 status状态，init:待处理， complete:已完成， close：关闭
	 * @author yinyuxin
	 * @return
	 */
	@RequestMapping(value = "comleteOrCloseWishList",method = RequestMethod.POST)
	public Response<Boolean> comleteOrCloseWishList(@Validated(DefaultGroup.class)@RequestBody WishListParamVo wishListParamVo){
		LOGGER.info("wishBackAction--comleteOrCloseWishList()--param:{}",JSONObject.toJSONString(wishListParamVo));
		Response<Boolean> dtoResponse = wishBackWriteDubboService.comleteOrCloseWishList(wishListParamVo.getWishListId(), wishListParamVo.getStatus());
		return dtoResponse;
	}



	/**
	 * 心愿单列表excel组装数据
	 * @param resultResponse
	 * @author yinyuxin
	 * @return
	 */
	public List<WishListForExcelVo> installWishListDataForExcel(Response<PageResult<WishListDto>> resultResponse){
		List<WishListForExcelVo> data=new ArrayList<>();
		if (resultResponse.isSuccess()&&null !=resultResponse.getResultObject()&& CollectionUtils.isNotEmpty(resultResponse.getResultObject().getData())){
			data=BeanConvertUtils.convertList(resultResponse.getResultObject().getData(),WishListForExcelVo.class);
			for (WishListForExcelVo wishListForExcelVo:data){
				wishListForExcelVo.setBranchCompanyName(wishListForExcelVo.getBranchCompanyId()+"-"+wishListForExcelVo.getBranchCompanyName());
				wishListForExcelVo.setStatus("init".equals(wishListForExcelVo.getStatus())?"未开始":("complete".equals(wishListForExcelVo.getStatus())?"到货处理":"无货处理"));
			}
		}
		return data;
	}



	/**
	 * 心愿单列表excel组装数据
	 * @param resultResponse
	 * @author yinyuxin
	 * @return
	 */
	public List<WishDetailForExcelVo> installWishDetailsDataForExcel(Response<PageResult<WishDetailDto>> resultResponse){
		List<WishDetailForExcelVo> data=new ArrayList<>();
		if (resultResponse.isSuccess()&&null !=resultResponse.getResultObject()&& CollectionUtils.isNotEmpty(resultResponse.getResultObject().getData())){
			data=BeanConvertUtils.convertList(resultResponse.getResultObject().getData(),WishDetailForExcelVo.class);
		}
		return data;
	}
}
