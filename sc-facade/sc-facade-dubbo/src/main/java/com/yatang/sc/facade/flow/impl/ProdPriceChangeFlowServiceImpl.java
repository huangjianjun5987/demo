package com.yatang.sc.facade.flow.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.common.staticvalue.ProdPriceChangeTypeEnum;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.domain.ProdPriceChangePo;
import com.yatang.sc.facade.domain.ProdPriceChangeQueryPo;
import com.yatang.sc.facade.domain.SpAdrBasicPo;
import com.yatang.sc.facade.domain.SupplierAdrInfoPo;
import com.yatang.sc.facade.domain.SupplierBasicInfoPo;
import com.yatang.sc.facade.domain.SupplierInfoPo;
import com.yatang.sc.facade.dto.prod.ProdPriceChangeListResultDto;
import com.yatang.sc.facade.dto.prod.ProdPriceChangeQueryDto;
import com.yatang.sc.facade.flow.ProdPriceChangeFlowService;
import com.yatang.sc.facade.service.ProdPriceChangeService;
import com.yatang.sc.facade.service.SupplierAdrService;
import com.yatang.sc.facade.service.SupplierService;
import com.yatang.xc.mbd.biz.org.dto.BranchCompanyDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.biz.system.dto.UserDTO;
import com.yatang.xc.mbd.biz.system.dubboservice.UserDubboService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;

/**
 * 
 * <class description>商品价格变化Flow实现类
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年12月7日
 */
@Service
public class ProdPriceChangeFlowServiceImpl implements ProdPriceChangeFlowService {
	private static final Logger			log			= LoggerFactory.getLogger(ProdPriceChangeFlowServiceImpl.class);
	private static final String			DELIMITER	= "-";
	@Autowired
	private ProdPriceChangeService		priceChangeService;
	@Autowired
	private SupplierService				supplierService;
	@Autowired
	private SupplierAdrService			supplierAdrService;
	@Autowired
	private OrganizationService			organizationService;
	@Autowired
	private ProductScIndexDubboService	productScIndexDubboService;
	@Autowired
	private UserDubboService			userDubboService;



	@Override
	public Response<PageResult<ProdPriceChangeListResultDto>> queryProdPriceChangeList(
			ProdPriceChangeQueryDto prodPriceChangeQueryDto) {
		log.info("ProdPriceChangeFlowServiceImpl.queryProdPriceChangeList()请求参数为：{}",
				JSON.toJSONString(prodPriceChangeQueryDto));
		Response<PageResult<ProdPriceChangeListResultDto>> response = new Response<>();
		if (null == prodPriceChangeQueryDto || null == prodPriceChangeQueryDto.getPageNum()
				|| null == prodPriceChangeQueryDto.getPageSize()) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage("请求参数不合格：" + JSON.toJSONString(prodPriceChangeQueryDto));
			return response;
		}
		try {
			ProdPriceChangeQueryPo prodPriceChangeQueryPo = BeanConvertUtils.convert(prodPriceChangeQueryDto,
					ProdPriceChangeQueryPo.class);
			PageInfo<ProdPriceChangePo> pageInfo = priceChangeService
					.selectProdPriceChangePoList(prodPriceChangeQueryPo);
			List<ProdPriceChangePo> ProdPriceChangePoList = pageInfo.getList();
			PageResult<ProdPriceChangeListResultDto> pageResult = new PageResult<>();
			List<ProdPriceChangeListResultDto> prodPriceChangeListResultDtoList = new ArrayList<>();
			pageResult.setData(prodPriceChangeListResultDtoList);
			if (null == ProdPriceChangePoList || ProdPriceChangePoList.isEmpty()) {
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setSuccess(true);
				response.setErrorMessage("查询结果为空");
				response.setResultObject(pageResult);
				return response;
			}
			pageResult.setPageNum(pageInfo.getPageNum());
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setTotal(pageInfo.getTotal());
			for (ProdPriceChangePo prodPriceChangePo : ProdPriceChangePoList) {
				log.info("商品价格改变记录为：{}", JSON.toJSONString(prodPriceChangePo));
				ProdPriceChangeListResultDto prodPriceChangeListResultDto = BeanConvertUtils.convert(prodPriceChangePo,
						ProdPriceChangeListResultDto.class);
				prodPriceChangeListResultDtoList.add(prodPriceChangeListResultDto);
				if (null != prodPriceChangePo.getChangeType()) {
					// 根据类型获取详细数据
					if (ProdPriceChangeTypeEnum.sell.getCode() == prodPriceChangePo.getChangeType()) {
						// 变价类型为售价变更时，子公司信息不为空；
						if (null != prodPriceChangePo.getBranchCompanyId()) {
							// 填充分公司信息
							fillBranchCompanyInfo(prodPriceChangePo, prodPriceChangeListResultDto);
						}
						prodPriceChangeListResultDto.setChangeTypeName(ProdPriceChangeTypeEnum.sell.getName());
						if(null==prodPriceChangePo.getGrossProfitMargin()){
							prodPriceChangeListResultDto.setGrossProfitMargin("0%");
						}else{
							prodPriceChangeListResultDto.setGrossProfitMargin(prodPriceChangePo.getGrossProfitMargin()+"%");
						}
						prodPriceChangeListResultDto.setPercentage(null);
					} else if (ProdPriceChangeTypeEnum.purchase.getCode() == prodPriceChangePo.getChangeType()) {
						// 变价类型为采购进价变更时，供应商信息不为空；
						if (null != prodPriceChangePo.getSpId()) {
							// 填充供应商信息
							fillSp(prodPriceChangePo, prodPriceChangeListResultDto);
						}
						// 变价类型为采购进价变更时，供应商地点信息不为空；
						if (null != prodPriceChangePo.getSpAdrId()) {
							// 填充供应商地点信息
							fillSpAdrInfo(prodPriceChangePo, prodPriceChangeListResultDto);
						}
						prodPriceChangeListResultDto.setChangeTypeName(ProdPriceChangeTypeEnum.purchase.getName());
						if(null == prodPriceChangePo.getPercentage()){
							prodPriceChangeListResultDto.setPercentage("0%");
						}else{
							prodPriceChangeListResultDto.setPercentage(prodPriceChangePo.getPercentage() + "%");
						}
						prodPriceChangeListResultDto.setGrossProfitMargin(null);
					} else {
						log.info("未知的价格改变类型：{}" + prodPriceChangePo.getChangeType());
					}
				}
				// 查询并设置商品信息
				if (null != prodPriceChangePo.getProductId()) {
					fillProductInfo(prodPriceChangePo, prodPriceChangeListResultDto);
				}
				if (null != prodPriceChangePo.getCreateUserId()) {
					fillUserInfo(prodPriceChangePo, prodPriceChangeListResultDto);
				}

			}
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



	private void fillUserInfo(ProdPriceChangePo prodPriceChangePo,
			ProdPriceChangeListResultDto prodPriceChangeListResultDto) {
		try {
			Response<UserDTO> response = userDubboService
					.findUserById(Integer.parseInt(prodPriceChangePo.getCreateUserId()));
			log.info("根据用户ID={}查询用户信息为：{}", JSON.toJSONString(response));
			if (null != response && CommonsEnum.RESPONSE_200.getCode().equals(response.getCode())
					&& null != response.getResultObject()) {
				String employeeName = response.getResultObject().getEmployeeName();
				prodPriceChangeListResultDto.setCreateUserName(cleanNull(employeeName));
			}
		} catch (Exception e) {
			log.info("商品价格改变记录查询时，查询用户信息出错，出错信息：{}", ExceptionUtils.getFullStackTrace(e));
		}
	}



	private void fillBranchCompanyInfo(ProdPriceChangePo prodPriceChangePo,
			ProdPriceChangeListResultDto prodPriceChangeListResultDto) {
		try {
			Response<BranchCompanyDto> branchCompanyDtoResponse = organizationService
					.querySimpleByBranchCompanyId(prodPriceChangePo.getBranchCompanyId());
			log.info("根据分公司ID={}查询分公司信息为：{}", JSON.toJSONString(branchCompanyDtoResponse));
			if (null != branchCompanyDtoResponse
					&& CommonsEnum.RESPONSE_200.getCode().equals(branchCompanyDtoResponse.getCode())
					&& null != branchCompanyDtoResponse.getResultObject()) {
				BranchCompanyDto branchCompanyDto = branchCompanyDtoResponse.getResultObject();
				prodPriceChangeListResultDto.setBranchCompanyCodeAndName(
						cleanNull(branchCompanyDto.getId()) + DELIMITER + cleanNull(branchCompanyDto.getName()));
			}
		} catch (Exception e) {
			log.info("商品价格改变记录查询时，查询分公司信息出错，出错信息：{}", ExceptionUtils.getFullStackTrace(e));
		}
	}



	private void fillSp(ProdPriceChangePo prodPriceChangePo,
			ProdPriceChangeListResultDto prodPriceChangeListResultDto) {
		try {
			SupplierInfoPo supplierInfoPo = supplierService.queryById(prodPriceChangePo.getSpId());
			SupplierBasicInfoPo supplierBasicInfoPo = supplierInfoPo.getSupplierBasicInfo();
			log.info("根据供应商ID={}查询到供应商编码为：{}，供应商名称为：{}", prodPriceChangePo.getSpId(), supplierBasicInfoPo.getSpNo(),
					supplierBasicInfoPo.getCompanyName());
			prodPriceChangeListResultDto.setSpCodeAndName(cleanNull(supplierBasicInfoPo.getSpNo()) + DELIMITER
					+ cleanNull(supplierBasicInfoPo.getCompanyName()));
		} catch (Exception e) {
			log.info("商品价格改变记录查询时，查询供应商数据出错，出错信息：{}", ExceptionUtils.getFullStackTrace(e));
		}
	}



	private void fillSpAdrInfo(ProdPriceChangePo prodPriceChangePo,
			ProdPriceChangeListResultDto prodPriceChangeListResultDto) {
		try {
			int spAdrId = Integer.parseInt(prodPriceChangePo.getSpAdrId());
			SupplierAdrInfoPo supplierAdrInfoPo = supplierAdrService.queryById(spAdrId);
			SpAdrBasicPo spAdrBasic = supplierAdrInfoPo.getSpAdrBasic();
			log.info("根据供应商地点ID={}查询到供应商地点编码为：{}，供应商地点名称为：{}", prodPriceChangePo.getSpAdrId(),
					spAdrBasic.getProviderNo(), spAdrBasic.getProviderNo());
			prodPriceChangeListResultDto.setSpAdrCodeAndName(
					cleanNull(spAdrBasic.getProviderNo()) + DELIMITER + cleanNull(spAdrBasic.getProviderName()));
		} catch (Exception e) {
			log.info("商品价格改变记录查询时，查询供应商地点数据出错，出错信息：{}", ExceptionUtils.getFullStackTrace(e));
		}
	}



	private void fillProductInfo(ProdPriceChangePo prodPriceChangePo,
			ProdPriceChangeListResultDto prodPriceChangeListResultDto) {
		try {
			Response<ProductIndexDto> productIndexDtoResponse = productScIndexDubboService
					.queryByProductId(prodPriceChangePo.getProductId());
			log.info("根据商品ID={}查询商品详情为：{}",prodPriceChangePo.getProductId(),JSON.toJSONString(productIndexDtoResponse));
			if (null != productIndexDtoResponse
					&& CommonsEnum.RESPONSE_200.getCode().equals(productIndexDtoResponse.getCode())
					&& null != productIndexDtoResponse.getResultObject()) {
				ProductIndexDto productIndexDto = productIndexDtoResponse.getResultObject();
				prodPriceChangeListResultDto
						.setFirstLevelCategoryName(cleanNull(productIndexDto.getFirstLevelCategoryName()));
				prodPriceChangeListResultDto
						.setSecondLevelCategoryName(cleanNull(productIndexDto.getSecondLevelCategoryName()));
				prodPriceChangeListResultDto
						.setThirdLevelCategoryName(cleanNull(productIndexDto.getThirdLevelCategoryName()));
				prodPriceChangeListResultDto
						.setFourthLevelCategoryName(cleanNull(productIndexDto.getFourthLevelCategoryName()));
				//12-01-08nwx确定取销售名称而不是文档写的商品描述
				prodPriceChangeListResultDto.setProductCodeAndDesc(cleanNull(productIndexDto.getProductCode())
						+ DELIMITER + cleanNull(productIndexDto.getSaleName()));
			}
		} catch (Exception e) {
			log.info("商品价格改变记录查询时，查询商品信息出错，出错信息：{}", ExceptionUtils.getFullStackTrace(e));
		}
	}



	private String cleanNull(Object object) {
		if (null == object) {
			return "";
		}
		return object.toString();
	}
}
