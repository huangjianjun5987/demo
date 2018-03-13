package com.yatang.sc.facade.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.Constants;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.domain.AuditFailedReasonPo;
import com.yatang.sc.facade.domain.MultiQuerySupplierPo;
import com.yatang.sc.facade.domain.QueryAvailableBranchCompanyPo;
import com.yatang.sc.facade.domain.SpAdrBasicPo;
import com.yatang.sc.facade.domain.SpSearchBoxPo;
import com.yatang.sc.facade.domain.SupplierAdrInfoPo;
import com.yatang.sc.facade.domain.SupplierBankInfoPo;
import com.yatang.sc.facade.domain.SupplierBasicInfoPo;
import com.yatang.sc.facade.domain.SupplierEnterQueryParamPo;
import com.yatang.sc.facade.domain.SupplierInfoPo;
import com.yatang.sc.facade.domain.SupplierInfoQueryListPo;
import com.yatang.sc.facade.domain.SupplierInfoQueryParamPo;
import com.yatang.sc.facade.domain.SupplierSaleRegionPo;
import com.yatang.sc.facade.domain.SupplierlicenseInfoPo;
import com.yatang.sc.facade.dto.AuditFailedReasonDto;
import com.yatang.sc.facade.dto.MultiQuerySupplierDto;
import com.yatang.sc.facade.dto.ProvinceDto;
import com.yatang.sc.facade.dto.SupplierPlaceDto;
import com.yatang.sc.facade.dto.supplier.QueryAvailableBranchCompanyDto;
import com.yatang.sc.facade.dto.supplier.SpAdrBasicDto;
import com.yatang.sc.facade.dto.supplier.SpSearchBoxDto;
import com.yatang.sc.facade.dto.supplier.SupplierAdrInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierBankInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierBasicInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierEnterQueryParamDto;
import com.yatang.sc.facade.dto.supplier.SupplierInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierInfoQueryListDto;
import com.yatang.sc.facade.dto.supplier.SupplierInfoQueryParamDto;
import com.yatang.sc.facade.dto.supplier.SupplierSaleRegionDto;
import com.yatang.sc.facade.dto.supplier.SupplierShowListDto;
import com.yatang.sc.facade.dto.supplier.SupplierlicenseInfoDto;
import com.yatang.sc.facade.dubboservice.SupplierQueryDubboService;
import com.yatang.sc.facade.enums.CommonEnum;
import com.yatang.sc.facade.service.SupplierAdrService;
import com.yatang.sc.facade.service.SupplierService;
import com.yatang.xc.oc.biz.adapter.RedisAdapterServie;
import com.yatang.xc.oc.biz.redis.dubboservice.RedisPlatform;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("supplierQueryDubboService")
public class SupplierQueryDubboServiceImpl implements SupplierQueryDubboService {

	protected final Log		log	= LogFactory.getLog(this.getClass());

	@Autowired
	private SupplierService service;
	
	@Autowired
	private SupplierAdrService adrService;

	@Autowired
	private RedisAdapterServie<String, String> redisDubboAdapterServie;//用于存放供应商和供应商地点编号


	@Override
	public Response<SupplierInfoDto> queryById(String id) {
		Response<SupplierInfoDto> response = new Response<SupplierInfoDto>();
		try {
			SupplierInfoPo po = service.queryById(id);
			if (po == null) {
				response.setCode(CommonsEnum.RESPONSE_10006.getCode());
				response.setSuccess(false);
				response.setResultObject(null);
				response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
				return response;
			}
			SupplierInfoDto dto = BeanConvertUtils.convert(po, SupplierInfoDto.class);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
			response.setResultObject(dto);
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@Override
	public Response<SupplierInfoDto> queryMainById(String id) {
		Response<SupplierInfoDto> response = new Response<>();
		try {
			SupplierInfoPo po = service.queryMainById(id);
			if (po != null) {
				SupplierInfoDto dto = BeanConvertUtils.convert(po, SupplierInfoDto.class);
				response.setResultObject(dto);
				response.setSuccess(true);
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			} else {
				response.setResultObject(null);
				response.setSuccess(false);
				response.setCode(CommonsEnum.RESPONSE_400.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}


	/**
	 * @Description: 查询供应商地点信息
	 * @author kangdong
	 * @date 2017年7月18日 下午20:10:25
	 * @param id
	 */
	@Override
	public Response<SupplierPlaceDto> queryProviderPlaceInfo(Integer id) {
		Response<SupplierPlaceDto> response = new Response<SupplierPlaceDto>();
		try {
            SupplierPlaceDto supplierPlaceDto = new SupplierPlaceDto();
			SupplierAdrInfoPo supplierAdrInfoPo = service.queryProviderPlace(id);
			if(supplierAdrInfoPo == null) {
			    return response;
            }
			SupplierAdrInfoDto supplierAdrInfoDto = BeanConvertUtils.convert(supplierAdrInfoPo, SupplierAdrInfoDto.class);
			SupplierInfoPo supplierInfoPo = service.queryById(supplierAdrInfoPo.getParentId());
			SupplierInfoDto supplierInfoDto = BeanConvertUtils.convert(supplierInfoPo, SupplierInfoDto.class);

			supplierPlaceDto.setSupplierAdrInfo(supplierAdrInfoDto);
			supplierPlaceDto.setSupplierInfo(supplierInfoDto);
			if (supplierPlaceDto == null) {
				response.setCode(CommonsEnum.RESPONSE_10006.getCode());
				response.setSuccess(false);
				response.setResultObject(null);
				response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
				return response;
			}
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
			response.setResultObject(supplierPlaceDto);
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@Override
	public Response<SupplierAdrInfoDto> queryMainAddById(Integer id) {
		Response<SupplierAdrInfoDto> response = new Response<>();
		try {
			SupplierAdrInfoPo po = adrService.queryMainAddById(id);
			if (po != null) {
				SupplierAdrInfoDto dto = BeanConvertUtils.convert(po, SupplierAdrInfoDto.class);
				response.setResultObject(dto);
				response.setSuccess(true);
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			} else {
				response.setResultObject(null);
				response.setSuccess(false);
				response.setCode(CommonsEnum.RESPONSE_400.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}


	/**
	 * @Description: 查询供应商修改前修改后的信息
	 * @author kangdong
	 * @date 2017年7月19日 下午10:10:35
	 * @param spId
	 */
	public Response<ArrayList> queryEditBeforeAfter(String spId) {
		Response<ArrayList> response = new Response<ArrayList>();
		try {
			response.setResultObject(service.queryBeforeAndAfter(spId));
			response.setSuccess(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}

    /**
     * @Description: 查询供应商地点所属区域列表
     * @author kangdong
     * @date 2017年7月29日 下午10:10:35
     * @param id
     */
    @Override
    public Response<List<ProvinceDto>> querySupplierPlaceRegion(String id) {
        Response<List<ProvinceDto>> response = new Response<>();
        try{
            SupplierInfoPo po = service.queryById(id);
            SupplierSaleRegionPo saleRegionPo = po.getSaleRegionInfo();
            if(saleRegionPo != null) {
				JSONArray bigAreas = JSONArray.parseArray(saleRegionPo.getJson());
				List<ProvinceDto> list = new ArrayList<ProvinceDto>(bigAreas.size());
				for (int i=0;i< bigAreas.size();i++) {
					JSONObject jsonArray = bigAreas.getJSONObject(i);
					JSONArray regions = (JSONArray) jsonArray.get("regions");
					for(int n=0;n<regions.size();n++) {
						ProvinceDto provinceDto = new ProvinceDto();
						JSONObject job = regions.getJSONObject(n);
						//if(job.get("regionType").equals("2")) {
						provinceDto.setCode(job.get("code").toString());
						provinceDto.setName(job.get("regionName").toString());
						list.add(provinceDto);
						//}
					}
				}
				response.setSuccess(true);
				response.setResultObject(list);
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			}else{
				response.setSuccess(true);
				response.setResultObject(null);
				response.setCode(CommonsEnum.RESPONSE_10006.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
			}
        }catch (Exception e){
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

	/**
	 * @Description: 查询供应商入驻申请分页信息列表
	 * @author huangjianjun
	 * @date 2017年5月15日 下午1:42:22
	 * @param supplierEnterQueryParamDto
	 */
	@Override
	public Response<PageResult<SupplierShowListDto>> querySettledRequestListPage(
			SupplierEnterQueryParamDto supplierEnterQueryParamDto) {
		Response<PageResult<SupplierShowListDto>> response = new Response<>();
		try {
			SupplierEnterQueryParamPo convert = BeanConvertUtils.convert(supplierEnterQueryParamDto,
					SupplierEnterQueryParamPo.class);

			PageInfo<SupplierInfoPo> pageInfo = service.querySettledRequestListPage(convert);
			List<SupplierInfoDto> dtoList = BeanConvertUtils.convertList(pageInfo.getList(), SupplierInfoDto.class);
			List<SupplierShowListDto> queryProviderEnterlistVos = new ArrayList<>();
			for (SupplierInfoDto ss : dtoList) {
				SupplierShowListDto que = new SupplierShowListDto();
				que.setId(ss.getId());
				que.setCompanyName(ss.getSupplierBasicInfo().getCompanyName());
				que.setSpNo(ss.getSupplierBasicInfo().getSpNo());
				// que.setSpRegNo(ss.getSupplierBasicInfo().getSpRegNo());
				que.setStatus(ss.getStatus());
				queryProviderEnterlistVos.add(que);
			}
			PageResult<SupplierShowListDto> pageResult = new PageResult<>();
			pageResult.setPageNum(pageInfo.getPageNum());
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setTotal(pageInfo.getTotal());
			pageResult.setData(queryProviderEnterlistVos);
			response.setSuccess(true);
			response.setResultObject(pageResult);
			return response;
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage("查询供应商入驻申请列表错误");
			response.setSuccess(false);
			log.error(ExceptionUtils.getFullStackTrace(e));
			return response;
		}
	}



	@Override
	public Response<PageResult<SupplierShowListDto>> querySettledListPage(MultiQuerySupplierDto record) {
		Response<PageResult<SupplierShowListDto>> response = new Response();

		try {
			MultiQuerySupplierPo po = BeanConvertUtils.convert(record, MultiQuerySupplierPo.class);
			PageInfo<SupplierInfoPo> pageInfo = service.querySettledListPage(po);
			List<SupplierShowListDto> dtos = new ArrayList<>();

			for (SupplierInfoPo ss : pageInfo.getList()) {
				SupplierShowListDto dto = new SupplierShowListDto();
				dto.setId(ss.getId());
				dtos.add(dto);
			}

			PageResult<SupplierShowListDto> pageResult = new PageResult<>();
			pageResult.setPageNum(pageInfo.getPageNum());
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setTotal(pageInfo.getTotal());
			pageResult.setData(dtos);
			response.setSuccess(true);
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
	public Response<PageResult<SupplierInfoQueryListDto>> querySettledList(SupplierInfoQueryParamDto param) {
		Response<PageResult<SupplierInfoQueryListDto>> response = new Response();

		SupplierInfoQueryParamPo po = BeanConvertUtils.convert(param, SupplierInfoQueryParamPo.class);

		PageInfo<SupplierInfoQueryListPo> pageInfo = service.querySettledList(po);
		List<SupplierInfoQueryListDto> dtos = BeanConvertUtils.convertList(pageInfo.getList(),
				SupplierInfoQueryListDto.class);

		PageResult<SupplierInfoQueryListDto> pageResult = new PageResult<>();
		pageResult.setPageNum(pageInfo.getPageNum());
		pageResult.setPageSize(pageInfo.getPageSize());
		pageResult.setTotal(pageInfo.getTotal());
		pageResult.setData(dtos);
		response.setSuccess(true);
		response.setResultObject(pageResult);

		return response;
	}



	@Override
	public Response<PageResult<SupplierInfoQueryListDto>> queryManageList(SupplierInfoQueryParamDto param) {
		Response<PageResult<SupplierInfoQueryListDto>> response = new Response();

		SupplierInfoQueryParamPo po = BeanConvertUtils.convert(param, SupplierInfoQueryParamPo.class);
		po.setStatuses(Lists.newArrayList(1, 2, 3));

		PageInfo<SupplierInfoQueryListPo> pageInfo = service.querySettledList(po);
		List<SupplierInfoQueryListDto> dtos = BeanConvertUtils.convertList(pageInfo.getList(),
				SupplierInfoQueryListDto.class);

		PageResult<SupplierInfoQueryListDto> pageResult = new PageResult<>();
		pageResult.setPageNum(pageInfo.getPageNum());
		pageResult.setPageSize(pageInfo.getPageSize());
		pageResult.setTotal(pageInfo.getTotal());
		pageResult.setData(dtos);
		response.setSuccess(true);
		response.setResultObject(pageResult);

		return response;
	}



	@Override
	public Response<PageResult<SupplierShowListDto>> querySettledEditRequestListPage(MultiQuerySupplierDto record) {
		Response<PageResult<SupplierShowListDto>> response = new Response();
		try {
			MultiQuerySupplierPo po = BeanConvertUtils.convert(record, MultiQuerySupplierPo.class);
			PageInfo<SupplierInfoPo> pageInfo = service.querySettledListPage(po);
			List<SupplierShowListDto> dtos = new ArrayList<>();

			for (SupplierInfoPo ss : pageInfo.getList()) {
				SupplierShowListDto dto = new SupplierShowListDto();
				// dto.setSpRegNo(ss.getSupplierBasicInfo().getSpRegNo());
				dto.setCompanyName(ss.getSupplierBasicInfo().getCompanyName());
			}
			PageResult<SupplierShowListDto> pageResult = new PageResult<>();
			pageResult.setPageNum(pageInfo.getPageNum());
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setTotal(pageInfo.getTotal());
			pageResult.setData(dtos);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
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
	public Response<PageInfo<Map<String, Object>>> supplierSaleRegionList(Map<String, Object> paramMap) {
		Response<PageInfo<Map<String, Object>>> response = new Response<>();

		response.setSuccess(true);
		// response.setResultObject(service.supplierSaleRegionList(paramMap));

		return response;
	}



	@Override
	public Response<List<SupplierSaleRegionDto>> supplierSaleRegions(String spNo) {
		Response<List<SupplierSaleRegionDto>> response = new Response<>();

		// List<SupplierSaleRegionDto> dtos = supplierSaleRegions(spNo,
		// SupplierSaleRegionPo.ROOT_CODE);

		response.setSuccess(true);
		// response.setResultObject(dtos);

		return response;
	}



	@Override
	public Response<Void> checkSupplierBasicInfo(SupplierBasicInfoDto supplierBasicInfoDto) {
		if (log.isInfoEnabled()) {
			log.info("---------- <<校验公司名/供应商编号是否重复>> checkSupplierBasicInfo(SupplierBasicInfoDto record): record="
					+ JSON.toJSONString(supplierBasicInfoDto)  + "----------");
		}
		Response<Void> response = new Response<>();
		try {
			// dto2po
			SupplierBasicInfoPo basicPo = BeanConvertUtils.convert(supplierBasicInfoDto, SupplierBasicInfoPo.class);
			List<SupplierBasicInfoPo> supplierBankInfoPoList = service.checkSupplierBasicInfo(basicPo);
			if (supplierBankInfoPoList.size() == 0) {
				response.setSuccess(true);
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
				return response;
			} else {
				for (SupplierBasicInfoPo supplierBasicInfoPo : supplierBankInfoPoList) {
					if (supplierBasicInfoPo.getCompanyName().equals(supplierBasicInfoDto.getCompanyName())) {// 公司名称判重
						response.setCode(CommonsEnum.RESPONSE_10007.getCode());
						response.setErrorMessage(CommonsEnum.RESPONSE_10007.getName());
						return response;
					}
					if (supplierBasicInfoPo.getSpNo().equals(supplierBasicInfoDto.getSpNo())) {// //供应商编号判重
						response.setCode(CommonsEnum.RESPONSE_10008.getCode());
						response.setErrorMessage(CommonsEnum.RESPONSE_10008.getName());
						return response;
					}
				}
			}
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<Void> checkSupplierBankInfoByBankAccount(SupplierBankInfoDto dto) {
		if (log.isInfoEnabled()) {
			log.info("---------- <<校验银行账号是否重复>> checkSupplierBankInfoByBankAccount(SupplierBankInfoDto record): record="
					+ JSON.toJSONString(dto)  + "----------");
		}
		Response<Void> response = new Response<>();
		try {
			SupplierBankInfoPo po = BeanConvertUtils.convert(dto, SupplierBankInfoPo.class);

			List<SupplierBankInfoPo> list = service.checkSupplierBankInfoByBankAccount(po);
			if (list.size() == 0) {
				response.setSuccess(true);
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
				return response;
			} else {
				response.setCode(CommonsEnum.RESPONSE_10012.getCode());
				response.setSuccess(false);
				response.setErrorMessage(CommonsEnum.RESPONSE_10012.getName());
				return response;
			}
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<Void> checkSupplierLicenseInfoByRegistLicenceNo(SupplierlicenseInfoDto dto) {
		if (log.isInfoEnabled()) {
			log.info("---------- <<校验营业执照号是否重复>> checkSupplierLicenseInfoByRegistLicenceNo(SupplierlicenseInfoDto record): record="
					+ JSON.toJSONString(dto)  + "----------");
		}
		Response<Void> response = new Response<>();
		try {
			SupplierlicenseInfoPo po = BeanConvertUtils.convert(dto, SupplierlicenseInfoPo.class);

			List<SupplierlicenseInfoPo> list = service.checkSupplierLicenseInfoByRegistLicenceNo(po);
			if (list.size() == 0) {
				response.setSuccess(true);
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
				return response;
			} else {
				response.setCode(CommonsEnum.RESPONSE_10013.getCode());
				response.setSuccess(false);
				response.setErrorMessage(CommonsEnum.RESPONSE_10013.getName());
				return response;
			}
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}


	/**
	 * 根据传入供应商ID查询审核失败原因
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Response<AuditFailedReasonDto> findAuditFailedReason(String id) {
		Response<AuditFailedReasonDto> response = new Response<>();
		try {
			AuditFailedReasonPo auditFailedReasonPo = service.findAuditFailedReason(id);
			AuditFailedReasonDto convert = BeanConvertUtils.convert(auditFailedReasonPo, AuditFailedReasonDto.class);
			response.setResultObject(convert);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public  Response<String> getSupplierNo(String type) {
		Response<String> response = new Response<String>();
		try {
			String no = "";
			if (CommonEnum.SP.getName().equals(type)) {
				no = redisDubboAdapterServie.get(RedisPlatform.common,Constants.SUPPLIER_NO, String.class);
				if(StringUtils.isNotBlank(no)){
					no = String.valueOf((Long.parseLong(no) + 1L));
				}else{
					no = CommonEnum.SP_CONF_VAL.getName();
				}
				redisDubboAdapterServie.set(RedisPlatform.common,Constants.SUPPLIER_NO, no);
			}else{
				no = redisDubboAdapterServie.get(RedisPlatform.common,Constants.SUPPLIER_ADR_NO, String.class);
				if(StringUtils.isNotBlank(no)){
					no = String.valueOf((Long.parseLong(no) + 1L));
				}else{
					no = CommonEnum.SP_ADR_CONF_VAL.getName();
				}
				redisDubboAdapterServie.set(RedisPlatform.common,Constants.SUPPLIER_ADR_NO, no);
			}
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setResultObject(no);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}


	@Override
	public Response<PageResult<SpSearchBoxDto>> supplierSearchBox(String condition, Integer pageNum, Integer pageSize) {
		Response<PageResult<SpSearchBoxDto>> response = new Response<PageResult<SpSearchBoxDto>>();
		try {
			PageInfo<SpSearchBoxPo> pageInfo = service.supplierSearchBox(condition, pageNum, pageSize);
			List<SpSearchBoxDto> dtoList = BeanConvertUtils.convertList(pageInfo.getList(), SpSearchBoxDto.class);
			PageResult<SpSearchBoxDto> pageResult = new PageResult<SpSearchBoxDto>();
			pageResult.setPageNum(pageInfo.getPageNum());
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setTotal(pageInfo.getTotal());
			pageResult.setData(dtoList);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setResultObject(pageResult);
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage("查询供应商入驻申请列表错误");
			response.setSuccess(false);
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}




	@Override
	public Response<PageResult<SpSearchBoxDto>> supplierAdrSearchBox(String pId, List<String> branchCompanyIds, String condition, Integer pageNum, Integer pageSize) {
		Response<PageResult<SpSearchBoxDto>> response = new Response<>();
		try {
			PageInfo<SpSearchBoxPo> pageInfo = adrService.supplierSearchBox(pId,branchCompanyIds,condition, pageNum, pageSize);
			List<SpSearchBoxDto> dtoList = BeanConvertUtils.convertList(pageInfo.getList(), SpSearchBoxDto.class);
			PageResult<SpSearchBoxDto> pageResult = new PageResult<SpSearchBoxDto>();
			pageResult.setPageNum(pageInfo.getPageNum());
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setTotal(pageInfo.getTotal());
			pageResult.setData(dtoList);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setResultObject(pageResult);
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setSuccess(false);
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}


	/**
	 * @Description: 根据地点ID查询供应商地点基本信息
	 * @author kangdong
	 * @date 2017年8月1日 晚上20:10:25
	 * @param id
	 */
	public Response<SpAdrBasicDto> queryByAdrInfoId(Integer id) {
		Response<SpAdrBasicDto> response = new Response<SpAdrBasicDto>();
		try {
			SpAdrBasicPo spAdrBasicPo = service.queryByAdrInfoId(id);
			SpAdrBasicDto spAdrBasicDto = BeanConvertUtils.convert(spAdrBasicPo,SpAdrBasicDto.class);
			response.setResultObject(spAdrBasicDto);
			response.setSuccess(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}

	@Override
	public Response<List<String>> queryUselessOrgId(QueryAvailableBranchCompanyDto dto) {
		Response<List<String>> response = new Response<>();
		try {
			QueryAvailableBranchCompanyPo po = BeanConvertUtils.convert(dto, QueryAvailableBranchCompanyPo.class);
			List<String > list = adrService.queryUselessOrgId(po);
			response.setResultObject(list);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}

	@Override
	public Response<Void> checkSupplierAddNo(SpAdrBasicDto dto) {
		if (log.isInfoEnabled()) {
			log.info("---------- <<校验供应商地点编号>> checkSupplierAddNo(SpAdrBasicDto dto): dto="
					+ JSON.toJSONString(dto)  + "----------");
		}
		Response<Void> response = new Response<>();
		try {
			SpAdrBasicPo po = BeanConvertUtils.convert(dto, SpAdrBasicPo.class);

			List<SpAdrBasicPo> list = adrService.checkSupplierAddNo(po);
			if (list.size() == 0) {
				response.setSuccess(true);
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
				return response;
			} else {
				response.setCode(CommonsEnum.RESPONSE_10035.getCode());
				response.setSuccess(false);
				response.setErrorMessage(CommonsEnum.RESPONSE_10035.getName());
				return response;
			}
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@Override
	public Response<PageResult<SupplierInfoQueryListDto>> queryExportManageList(SupplierInfoQueryParamDto supplierInfoQueryParamDto) {

		if (log.isInfoEnabled()) {
			log.info("---------- <<查询供应商管理列表的导出结果>> queryExportManageList(SupplierInfoQueryParamDto supplierInfoQueryParamDto): supplierInfoQueryParamDto="
					+ JSON.toJSONString(supplierInfoQueryParamDto)  + "----------");
		}

		Response<PageResult<SupplierInfoQueryListDto>> response = new Response();
		try {
			formatDate(supplierInfoQueryParamDto);
			SupplierInfoQueryParamPo po = BeanConvertUtils.convert(supplierInfoQueryParamDto, SupplierInfoQueryParamPo.class);
			po.setStatuses(Lists.newArrayList(1, 2, 3));

			PageInfo<SupplierInfoQueryListPo> pageInfo = service.queryExportManageList(po);
			List<SupplierInfoQueryListDto> dtos = BeanConvertUtils.convertList(pageInfo.getList(),
					SupplierInfoQueryListDto.class);

			PageResult<SupplierInfoQueryListDto> pageResult = new PageResult<>();
			pageResult.setPageNum(pageInfo.getPageNum());
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setTotal(pageInfo.getTotal());
			pageResult.setData(dtos);
			response.setSuccess(true);
			response.setResultObject(pageResult);
		}
		catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}

		return response;
	}

	private void formatDate(SupplierInfoQueryParamDto supplierInfoQueryParamDto) throws ParseException {
		if(null != supplierInfoQueryParamDto.getSettledDate()){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			String dateNowStr = sdf.format(supplierInfoQueryParamDto.getSettledDate());
			supplierInfoQueryParamDto.setSettledDate(sdf.parse(dateNowStr));
		}
	}
}
