package com.yatang.sc.facade.dubboservice.impl;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.domain.ZiptoftpInfoPo;
import com.yatang.sc.facade.dto.ZiptoftpInfoDto;
import com.yatang.sc.facade.dubboservice.ZiptoftpInfoDubboService;
import com.yatang.sc.facade.service.ZiptoftpInfoService;

@Service("ziptoftpInfoDubboService")
public class ZiptoftpInfoDubboServiceImpl implements ZiptoftpInfoDubboService {
	private static final Logger	log	= LoggerFactory.getLogger(ZiptoftpInfoDubboServiceImpl.class);
	@Autowired
	private ZiptoftpInfoService	ZiptoftpInfoService;



	@Override
	public Response<Integer> insert(ZiptoftpInfoDto ziptoftpInfoDto) {
		Response<Integer> response = new Response<>();
		log.info("新增zip上传信息，ziptoftpInfoDto:{}", JSON.toJSONString(ziptoftpInfoDto));
		try {
			ZiptoftpInfoPo ziptoftpInfoPo = BeanConvertUtils.convert(ziptoftpInfoDto, ZiptoftpInfoPo.class);
			ziptoftpInfoPo.setUploadTime(new Date());
			ZiptoftpInfoService.insert(ziptoftpInfoPo);
			log.info("新增成功，返回主键为：{}", ziptoftpInfoPo.getId());
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(ziptoftpInfoPo.getId());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}



	@Override
	public Response<ZiptoftpInfoDto> selectByPrimaryKey(Integer id) {
		log.info("根据主键查询zip上传信息，id:{}", id);
		Response<ZiptoftpInfoDto> response = new Response<>();
		try {
			ZiptoftpInfoPo ziptoftpInfoPo = ZiptoftpInfoService.selectByPrimaryKey(id);
			log.info("根据主键查询zip上传信息，查询结果:{}", JSON.toJSONString(ziptoftpInfoPo));
			if (null == ziptoftpInfoPo) {
				response.setCode(CommonsEnum.RESPONSE_10006.getCode());
				response.setSuccess(false);
				response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
				return response;
			}
			ZiptoftpInfoDto ziptoftpInfoDto = BeanConvertUtils.convert(ziptoftpInfoPo, ZiptoftpInfoDto.class);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(ziptoftpInfoDto);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}



	@Override
	public Response<Boolean> deleteByPrimaryKey(Integer id) {
		log.info("根据主键删除zip上传信息，id:{}", id);
		Response<Boolean> response = new Response<>();
		try {
			int result = ZiptoftpInfoService.deleteByPrimaryKey(id);
			log.info("根据主键删除zip上传信息，删除结果:{}", result);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(true);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}



	@Override
	public Response<PageResult<ZiptoftpInfoDto>> selectZiptoftpInfoList(ZiptoftpInfoDto ziptoftpInfoDto,
			Integer pageNum, Integer pageSize) {
		log.info("根据条件：{}分页查询列表，pageNum:{},pageSize:{}", JSON.toJSONString(ziptoftpInfoDto), pageNum, pageSize);
		Response<PageResult<ZiptoftpInfoDto>> response = new Response<>();
		try {
			PageInfo<ZiptoftpInfoPo> pageInfo = ZiptoftpInfoService.selectZiptoftpInfoList(
					BeanConvertUtils.convert(ziptoftpInfoDto, ZiptoftpInfoPo.class), pageNum, pageSize);
			log.info("根据条件分页查询列表，查询结果:{}", JSON.toJSONString(pageInfo));
			PageResult<ZiptoftpInfoDto> pageResult = new PageResult<>();
			pageResult.setPageNum(pageInfo.getPageNum());
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setTotal(pageInfo.getTotal());
			pageResult.setData(BeanConvertUtils.convertList(pageInfo.getList(), ZiptoftpInfoDto.class));
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(pageResult);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}



	@Override
	public Response<Integer> updateIfAbsent(ZiptoftpInfoDto ziptoftpInfoDto) {
		Response<Integer> response = new Response<>();
		log.info("更新zip上传信息，ziptoftpInfoDto:{}", JSON.toJSONString(ziptoftpInfoDto));
		try {
			if (null == ziptoftpInfoDto || null == ziptoftpInfoDto.getId()) {
				response.setCode(CommonsEnum.RESPONSE_500.getCode());
				response.setSuccess(true);
				response.setErrorMessage("更新必须有ID");
				response.setResultObject(0);
			}
			ZiptoftpInfoPo oldZiptoftpInfoPo = ZiptoftpInfoService.selectByPrimaryKey(ziptoftpInfoDto.getId());
			if (null == oldZiptoftpInfoPo) {
				response.setCode(CommonsEnum.RESPONSE_500.getCode());
				response.setSuccess(true);
				response.setErrorMessage("没有旧记录ID=" + ziptoftpInfoDto.getId());
				response.setResultObject(0);
			}
			ZiptoftpInfoPo ziptoftpInfoPo = BeanConvertUtils.convert(ziptoftpInfoDto, ZiptoftpInfoPo.class);
			ziptoftpInfoPo.setUploadTime(new Date());
			ZiptoftpInfoService.updateByPrimaryKey(ziptoftpInfoPo);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(ziptoftpInfoDto.getId());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}

}
