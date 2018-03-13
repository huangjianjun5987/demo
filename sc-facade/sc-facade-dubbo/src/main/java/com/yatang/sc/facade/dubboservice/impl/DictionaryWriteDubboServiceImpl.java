package com.yatang.sc.facade.dubboservice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.domain.DictionaryContentPo;
import com.yatang.sc.facade.domain.DictionaryPo;
import com.yatang.sc.facade.dto.DictionaryContentDto;
import com.yatang.sc.facade.dto.DictionaryDto;
import com.yatang.sc.facade.dubboservice.DictionaryWriteDubboService;
import com.yatang.sc.facade.service.DictionaryService;

import lombok.extern.slf4j.Slf4j;

/**
 * @描述:数据字典
 * @类名:DictionaryWriteDubboServiceImpl
 * @作者: kangdong
 * @创建时间: 2017/6/8 16:13
 * @版本: v1.0
 */
@Service("dictionaryWriteDubboService")
@Slf4j
public class DictionaryWriteDubboServiceImpl implements DictionaryWriteDubboService {
	@Autowired
	private DictionaryService service;



	@Override
	public Response<Boolean> insertDictionaryInfo(DictionaryDto record) {
		if (log.isInfoEnabled()) {
			log.info("----------新增数据字典>>insertDictionaryInfo():record=" + JSON.toJSONString(record) + "----------");
		}
		Response<Boolean> response = new Response<Boolean>();
		DictionaryPo dictionaryPo = BeanConvertUtils.convert(record, DictionaryPo.class);
		Response<Boolean> responseInfo = checkDictionaryInfo(record);
		if (!responseInfo.isSuccess()) {
			return BeanConvertUtils.convert(responseInfo, Response.class);
		} else {
			if (service.insertDictionaryInfo(dictionaryPo)) {
				response.setSuccess(true);
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			} else {
				response.setSuccess(false);
				response.setCode(CommonsEnum.RESPONSE_500.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			}
		}
		return response;
	}



	@Override
	public Response<Boolean> updateDictionary(DictionaryDto record) {
		if (log.isInfoEnabled()) {
			log.info("----------更新数据字典>>updateDictionary():record=" + JSON.toJSONString(record) + "----------");
		}
		Response<Boolean> response = new Response<Boolean>();
		DictionaryPo dictionaryPo = BeanConvertUtils.convert(record, DictionaryPo.class);
		Response<Boolean> responseInfo = checkDictionaryInfo(record);
		if (!responseInfo.isSuccess()) {
			return BeanConvertUtils.convert(responseInfo, Response.class);
		} else {
			if (service.updateDictionaryInfo(dictionaryPo)) {
				response.setSuccess(true);
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			} else {
				response.setSuccess(false);
				response.setCode(CommonsEnum.RESPONSE_500.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			}
		}
		return response;
	}



	@Override
	public Response<Boolean> checkDictionaryInfo(DictionaryDto record) {
		if (log.isInfoEnabled()) {
			log.info("----------检查数据字典重复性>>checkDictionaryInfo():record=" + JSON.toJSONString(record) + "----------");
		}
		Response<Boolean> response = new Response<Boolean>();
		DictionaryPo dictionaryPo = BeanConvertUtils.convert(record, DictionaryPo.class);
		int count = service.checkByDictionaryAndCode(dictionaryPo);
		if (count > 0) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_10000.getCode());
			response.setErrorMessage("添加失败,字典名称或字典编码已存在!");
		} else {
			response.setSuccess(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
		}
		return response;
	}



	@Override
	public Response<Boolean> deleteDictionaryInfo(Integer dictionaryId) {
		if (log.isInfoEnabled()) {
			log.info("----------删除数据字典>>deleteDictionaryInfo():dictionaryId=" + dictionaryId + "----------");
		}
		Response<Boolean> response = new Response<Boolean>();
		try {
			List<DictionaryContentPo> res = service.queryDictionaryContentById(dictionaryId);
			for (DictionaryContentPo list : res) {
				service.deleteContentById(list.getId());
			}
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(service.deleteDictionaryById(dictionaryId));
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}



	@Override
	public Response<Boolean> insertDictionaryContentInfo(DictionaryContentDto record) {
		if (log.isInfoEnabled()) {
			log.info("----------添加数据字典内容>>insertDictionaryContentInfo():record=" + JSON.toJSONString(record)
					+ "----------");
		}
		Response<Boolean> response = new Response<Boolean>();
		DictionaryContentPo po = BeanConvertUtils.convert(record, DictionaryContentPo.class);
		Response<Boolean> responseInfo = checkDictionaryContentInfo(record);
		if (!responseInfo.isSuccess()) {
			return BeanConvertUtils.convert(responseInfo, Response.class);
		} else {
			if (service.insertDictionaryContentInfo(po)) {
				response.setSuccess(true);
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			} else {
				response.setSuccess(false);
				response.setCode(CommonsEnum.RESPONSE_500.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			}
		}
		return response;
	}



	@Override
	public Response<Boolean> updateContentInfo(DictionaryContentDto record) {
		if (log.isInfoEnabled()) {
			log.info("----------更新数据字典内容>>updateContentInfo():record=" + JSON.toJSONString(record) + "----------");
		}
		Response<Boolean> response = new Response<Boolean>();
		DictionaryContentPo po = BeanConvertUtils.convert(record, DictionaryContentPo.class);
		if (record.getContentName() != null) {
			Response<Boolean> responseInfo = checkDictionaryContentInfo(record);
			if (!responseInfo.isSuccess()) {
				return BeanConvertUtils.convert(responseInfo, Response.class);
			} else {
				if (service.updateContentInfo(po)) {
					response.setSuccess(true);
					response.setCode(CommonsEnum.RESPONSE_200.getCode());
					response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
				} else {
					response.setSuccess(false);
					response.setCode(CommonsEnum.RESPONSE_500.getCode());
					response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
				}
			}
		}
		return response;
	}



	@Override
	public Response<Boolean> updateState(DictionaryContentDto record) {
		if (log.isInfoEnabled()) {
			log.info("----------更新数据字典状态>>updateState():record=" + JSON.toJSONString(record) + "----------");
		}
		Response<Boolean> response = new Response<Boolean>();
		try {
			DictionaryContentPo dictionaryContentPo = BeanConvertUtils.convert(record, DictionaryContentPo.class);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(service.updateContentInfo(dictionaryContentPo));
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}



	@Override
	public Response<Boolean> checkDictionaryContentInfo(DictionaryContentDto record) {
		if (log.isInfoEnabled()) {
			log.info("----------检查数据字典内容>>checkDictionaryContentInfo():record=" + JSON.toJSONString(record)
					+ "----------");
		}
		Response<Boolean> response = new Response<Boolean>();
		DictionaryContentPo po = BeanConvertUtils.convert(record, DictionaryContentPo.class);
		int count = service.checkByContent(po);
		if (count > 0) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_10000.getCode());
			response.setErrorMessage("添加失败,字典内容名称已存在!");
		} else {
			response.setSuccess(true);
		}
		return response;
	}
}
