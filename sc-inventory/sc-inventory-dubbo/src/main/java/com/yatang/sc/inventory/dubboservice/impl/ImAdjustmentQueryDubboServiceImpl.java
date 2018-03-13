package com.yatang.sc.inventory.dubboservice.impl;


import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.inventory.common.CommonsEnum;
import com.yatang.sc.inventory.common.PageResult;
import com.yatang.sc.inventory.domain.ImAdjustmentQueryListPo;
import com.yatang.sc.inventory.domain.ImAdjustmentQueryParamPo;
import com.yatang.sc.inventory.dto.im.ImAdjustmentQueryListDto;
import com.yatang.sc.inventory.dto.im.ImAdjustmentQueryParamDto;
import com.yatang.sc.inventory.dubboservice.ImAdjustmentQueryDubboService;
import com.yatang.sc.inventory.dubboservice.flow.InventorySynFlowService;
import com.yatang.sc.inventory.service.ImAdjustmentService;

/**
 * @描述: 库存调整查询dubbo服务实现类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/30 上午9:15
 * @版本: v1.0
 */
@Service("imAdjustmentQueryDubboService")
public class ImAdjustmentQueryDubboServiceImpl implements ImAdjustmentQueryDubboService {

	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ImAdjustmentService imAdjustmentService;
	@Autowired
	private InventorySynFlowService inventorySynFlowService;


	/**
	 * 根据传入参数分页查询库存调整单
	 *
	 * @param convert
	 * @return
	 */
	@Override
	public Response<PageResult<ImAdjustmentQueryListDto>> queryListImAdjustment(ImAdjustmentQueryParamDto convert) {
        log.info("分页查询库存调整单参数"+convert);
		Response<PageResult<ImAdjustmentQueryListDto>> resultResponse = new Response<PageResult<ImAdjustmentQueryListDto>>();
		try {
			ImAdjustmentQueryParamPo convert1 = BeanConvertUtils.convert(convert, ImAdjustmentQueryParamPo.class);
			PageInfo<ImAdjustmentQueryListPo> imAdjustmentQueryParamPoPageInfo=imAdjustmentService.queryListImAdjustment(convert1);
			List<ImAdjustmentQueryListDto> imAdjustmentQueryListDtos = BeanConvertUtils.convertList(imAdjustmentQueryParamPoPageInfo.getList(), ImAdjustmentQueryListDto.class);
			PageResult p = new PageResult();
			p.setData(imAdjustmentQueryListDtos);
			p.setTotal(imAdjustmentQueryParamPoPageInfo.getTotal());
			p.setPageSize(imAdjustmentQueryParamPoPageInfo.getPageSize());
			p.setPageNum(imAdjustmentQueryParamPoPageInfo.getPageNum());
			resultResponse.setResultObject(p);
			resultResponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			resultResponse.setSuccess(true);

		}catch (Exception e){
			resultResponse.setSuccess(false);
			resultResponse.setCode(CommonsEnum.RESPONSE_500.getCode());
			resultResponse.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return resultResponse;
	}

	/**
	 * 根据传入列表id查询库存调整详情
	 *
	 * @param id
	 * @return
	 */
	@Override
	public Response<ImAdjustmentQueryListDto> getImAdjustment(Long id) {
		log.info("查询库存调整参数"+id);
		Response<ImAdjustmentQueryListDto> resultResponse = new Response<ImAdjustmentQueryListDto>();
		try {
			ImAdjustmentQueryListPo imAdjustment = imAdjustmentService.getImAdjustment(id);
			ImAdjustmentQueryListDto convert = BeanConvertUtils.convert(imAdjustment, ImAdjustmentQueryListDto.class);
			resultResponse.setResultObject(convert);
			resultResponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			resultResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
			resultResponse.setSuccess(true);

		}catch (Exception e){
			resultResponse.setSuccess(false);
			resultResponse.setCode(CommonsEnum.RESPONSE_500.getCode());
			resultResponse.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return resultResponse;
	}

}
