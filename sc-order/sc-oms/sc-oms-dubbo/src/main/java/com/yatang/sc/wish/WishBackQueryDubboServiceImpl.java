package com.yatang.sc.wish;

import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.dto.WishDetailDto;
import com.yatang.sc.dto.WishListDto;
import com.yatang.sc.dto.WishListParamDto;
import com.yatang.sc.order.domain.WishDetailPo;
import com.yatang.sc.order.domain.WishListParamPo;
import com.yatang.sc.order.domain.WishListPo;
import com.yatang.sc.order.service.WishService;
import com.yatang.sc.wish.dubboService.WishBackQueryDubboService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 心愿单 后端write接口实现类
 * @author: yinyuxin
 * @date: 2018/1/3 15:56
 * @version: v1.0
 */
@Service("wishBackQueryDubboService")
public class WishBackQueryDubboServiceImpl implements WishBackQueryDubboService{

	@Autowired
	private WishService wishService;

	private static final Logger LOGGER= LoggerFactory.getLogger(WishBackQueryDubboServiceImpl.class);

	@Override
	public Response<PageResult<WishListDto>> queryWishListsByParam(WishListParamDto wishListParamDto) {
		LOGGER.info("wishQueryDubboService-->queryWishListsByParam()-->param:"+ JSONObject.toJSONString(wishListParamDto));
		Response<PageResult<WishListDto>> response=new Response<PageResult<WishListDto>>();
		try {
			WishListParamPo wishListParamPo= BeanConvertUtils.convert(wishListParamDto,WishListParamPo.class);
			PageInfo<WishListPo> wishListPosPageInfo=wishService.queryWishListsByParam(wishListParamPo);
			if (null==wishListPosPageInfo||null==wishListPosPageInfo.getList()||wishListPosPageInfo.getList().size()<=0){
				response.setSuccess(true);
				response.setResultObject(new PageResult<WishListDto>());
				return response;
			}
			List<WishListDto> wishListDtos=BeanConvertUtils.convertList(wishListPosPageInfo.getList(),WishListDto.class);
			PageResult<WishListDto> pageResult=new PageResult<WishListDto>();
			pageResult.setData(wishListDtos);
			pageResult.setTotal(wishListPosPageInfo.getTotal());
			pageResult.setPageSize(wishListPosPageInfo.getPageSize());
			pageResult.setPageNum(wishListPosPageInfo.getPageNum());
			response.setResultObject(pageResult);
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			return response;
		} catch (Exception e) {
			LOGGER.error("wishQueryDubboService-->queryWishListsByParam()-->error:"+ ExceptionUtils.getFullStackTrace(e));
			response.setResultObject(new PageResult<WishListDto>());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			return response;
		}
	}



	@Override
	public Response<PageResult<WishDetailDto>> queryWishDetailsByWishListIdAndStoreId(WishListParamDto wishListParamDto) {
		LOGGER.info("wishQueryDubboService-->queryWishDetailsByWishListIdAndStoreId()-->param:"+ JSONObject.toJSONString(wishListParamDto));
		Response<PageResult<WishDetailDto>> response=new Response<PageResult<WishDetailDto>>();
		try {
			WishListParamPo wishListParamPo=BeanConvertUtils.convert(wishListParamDto,WishListParamPo.class);
			PageInfo<WishDetailPo> wishDetailPosPageInfo=wishService.queryWishDetailsByWishListIdAndStoreId(wishListParamPo);
			if (null==wishDetailPosPageInfo||null==wishDetailPosPageInfo.getList()||wishDetailPosPageInfo.getList().size()<=0){
				response.setSuccess(true);
				response.setResultObject(new PageResult<WishDetailDto>());
				return response;
			}
			List<WishDetailDto> wishDetailDtos=BeanConvertUtils.convertList(wishDetailPosPageInfo.getList(),WishDetailDto.class);
			//根据wishlid查询商品条码，考虑数据安全，不采用前端传值
			WishListPo wishListPo = wishService.queryWishListById(wishListParamDto.getWishListId());
			if (null!=wishListPo){
				for (WishDetailDto wishDetailDto:wishDetailDtos){
					wishDetailDto.setGbCode(wishListPo.getGbCode());
				}
			}
			PageResult<WishDetailDto> pageResult=new PageResult<WishDetailDto>();
			pageResult.setData(wishDetailDtos);
			pageResult.setTotal(wishDetailPosPageInfo.getTotal());
			pageResult.setPageSize(wishDetailPosPageInfo.getPageSize());
			pageResult.setPageNum(wishDetailPosPageInfo.getPageNum());
			response.setResultObject(pageResult);
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			return response;
		} catch (Exception e) {
			LOGGER.error("wishQueryDubboService-->queryWishDetailsByWishListIdAndStoreId()-->error:"+ ExceptionUtils.getFullStackTrace(e));
			response.setResultObject(new PageResult<WishDetailDto>());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			return response;
		}
	}
}
