package com.yatang.sc.facade.dubboservice.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.domain.DictionaryContentPo;
import com.yatang.sc.facade.domain.DictionaryPo;
import com.yatang.sc.facade.dto.DictionaryContentDto;
import com.yatang.sc.facade.dto.DictionaryDto;
import com.yatang.sc.facade.dubboservice.DictionaryQueryDubboService;
import com.yatang.sc.facade.service.DictionaryService;

import lombok.extern.slf4j.Slf4j;

/**
 * @描述:数据字典
 * @类名:DictionaryQueryDubboServiceImpl
 * @作者: kangdong
 * @创建时间: 2017/6/8 11:47
 * @版本: v1.0
 */
@Service("dictionaryQueryDubboService")
@Slf4j
public class DictionaryQueryDubboServiceImpl implements DictionaryQueryDubboService {
	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private DictionaryService service;



	@Override
	public Response<PageResult<DictionaryDto>> findByPage(String keyword, Integer pageNum, Integer pageSize) {
		if (log.isInfoEnabled()) {
			log.info("----------查询数据字典列表>>findByPage():keyword=" + keyword + ",pageNum=" + pageNum + ",pageSize="
					+ pageSize + "----------");
		}
		Response<PageResult<DictionaryDto>> response = new Response<PageResult<DictionaryDto>>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("keyword", keyword);
			map.put("pageSize", pageSize);
			map.put("pageNum", pageNum);
			PageInfo<DictionaryPo> pageInfo = service.findByPage(map);
			List<DictionaryPo> pos = pageInfo.getList();
			List<DictionaryDto> dtos = BeanConvertUtils.convertList(pos, DictionaryDto.class);
			PageResult<DictionaryDto> page = new PageResult<DictionaryDto>();
			page.setPageNum(pageInfo.getPageNum());
			page.setPageSize(pageInfo.getPageSize());
			page.setTotal(pageInfo.getTotal());
			page.setData(dtos);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setResultObject(page);
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	public Response<DictionaryDto> queryById(Integer dictionaryId) {
		if (log.isInfoEnabled()) {
			log.info("----------查询数据字典详情>>queryById():dictionaryId=" + dictionaryId + "----------");
		}
		Response<DictionaryDto> response = new Response<DictionaryDto>();
		try {
			DictionaryPo po = service.queryById(dictionaryId);
			DictionaryDto dictionaryDto = BeanConvertUtils.convert(po, DictionaryDto.class);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setResultObject(dictionaryDto);
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	public Response<List<DictionaryContentDto>> queryDictionaryContentList(Integer dictionaryId) {
		if (log.isInfoEnabled()) {
			log.info("----------查询数据字典内容>>queryDictionaryContentList():dictionaryId=" + dictionaryId + "----------");
		}
		Response<List<DictionaryContentDto>> response = new Response<>();
		try {
			List<DictionaryContentPo> dictionarycontent = service.queryDictionaryContentById(dictionaryId);
			List<DictionaryContentDto> DictionaryContentDto = BeanConvertUtils.convertList(dictionarycontent,
					DictionaryContentDto.class);
			response.setResultObject(DictionaryContentDto);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<List<DictionaryContentDto>> queryDictionContentForSelect(String dictionaryCode) {
		if (log.isInfoEnabled()) {
			log.info("----------查询数据字典下拉框>>queryDictionContentForSelect():dictionaryCode=" + dictionaryCode
					+ "----------");
		}
		Response<List<DictionaryContentDto>> response = new Response<List<DictionaryContentDto>>();
		List<DictionaryContentDto> list = new ArrayList<DictionaryContentDto>();
		try {
			// 1：通过字段名称查询系统级字典
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("keyword", dictionaryCode);
			map.put("dictionaryType", 1);
			PageInfo<DictionaryPo> data = service.findByPage(map);
			if (data != null && data.getList() != null) {
				if (data.getList().size() != 1) {
					// 查出0个或者多个字典
					response.setErrorMessage("字典为空或重复，查询失败");
				} else {
					// 2:获取字典的id，查询所有的字典内容对象
					DictionaryPo dictionaryPo = data.getList().get(0);
					List<DictionaryContentPo> listPo = service.queryDictionaryContentById(dictionaryPo.getId());
					list = BeanConvertUtils.convertList(listPo, DictionaryContentDto.class);
				}
			}
			response.setResultObject(list);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
}
