package com.yatang.sc.operation.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.operation.vo.GrantCouponStoreVo;
import com.yatang.sc.operation.vo.GrantFranchiseeVo;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationSCService;
import com.yatang.xc.mbd.biz.org.sc.dto.QueryStoreScDto;
import com.yatang.xc.mbd.biz.org.sc.dto.StoreScDto;

@RestController
@RequestMapping(value = "/sc/franchisee")
public class FranchiseeAction extends BaseAction {
	private static final Logger		log	= LoggerFactory.getLogger(FranchiseeAction.class);

	@Autowired
	private OrganizationSCService	organizationSCService;



	/**
	 * 
	 * <method description> 查询需要发放优惠券的加盟商
	 *
	 * @return
	 */
	@RequestMapping(value = "/grantFranchisee", method = RequestMethod.GET)
	public Response<PageResult<GrantCouponStoreVo>> grantFranchisee(GrantFranchiseeVo grantFranchiseeVo) {
		// 调用主数据服务条件查询加盟商列表
		QueryStoreScDto queryStoreScDto = BeanConvertUtils.convert(grantFranchiseeVo, QueryStoreScDto.class);
		log.info("调用主数据服务OrganizationSCService.queryCountSCStoreInfo(QueryStoreScDto),参数为：{}",
				JSON.toJSONString(queryStoreScDto));
		Response<Integer> countResponse = organizationSCService.queryCountSCStoreInfo(queryStoreScDto);
		log.info("调用主数据服务OrganizationSCService.queryCountSCStoreInfo(QueryStoreScDto),查询结果为：{}",
				JSON.toJSONString(countResponse));
		if (null == countResponse || "500".equals(countResponse.getCode())) {
			return getFailResponse();
		}
		if (null == countResponse.getResultObject() || 0 == countResponse.getResultObject()) {
			// PageResult<GrantCouponStoreVo> pageResult = new PageResult<>();
			// pageResult.setPageNum(grantFranchiseeVo.getPageNum());
			// pageResult.setPageSize(grantFranchiseeVo.getPageSize());
			// pageResult.setTotal(countResponse.getResultObject() + 0L);
			// Response<PageResult<GrantCouponStoreVo>> response = new
			// Response<PageResult<GrantCouponStoreVo>>();
			// response.setResultObject(pageResult);
			// response.setSuccess(true);
			// response.setCode(CommonsEnum.RESPONSE_10006.getCode());
			// response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
			return getSuccessResponse();
		}
		queryStoreScDto.setPageNo(grantFranchiseeVo.getPageNum());
		queryStoreScDto.setPageSize(grantFranchiseeVo.getPageSize());
		log.info("调用主数据服务OrganizationSCService.queryPageSCStoreInfo(QueryStoreScDto),参数为：{}",
				JSON.toJSONString(queryStoreScDto));
		Response<List<StoreScDto>> storeInfoResult = organizationSCService.queryPageSCStoreInfo(queryStoreScDto);
		log.info("调用主数据服务OrganizationSCService.queryPageSCStoreInfo(QueryStoreScDto),查询结果为：{}",
				JSON.toJSONString(storeInfoResult));
		if (null == storeInfoResult || "500".equals(storeInfoResult.getCode())) {
			return getFailResponse();
		}
		List<StoreScDto> stores = storeInfoResult.getResultObject();
		List<GrantCouponStoreVo> convertList = BeanConvertUtils.convertList(stores, GrantCouponStoreVo.class);
		PageResult<GrantCouponStoreVo> pageResult = new PageResult<>();
		pageResult.setData(convertList);
		pageResult.setPageNum(grantFranchiseeVo.getPageNum());
		pageResult.setPageSize(grantFranchiseeVo.getPageSize());
		pageResult.setTotal(countResponse.getResultObject() + 0L);
		return getSuccessResponse(pageResult);
	}
}
