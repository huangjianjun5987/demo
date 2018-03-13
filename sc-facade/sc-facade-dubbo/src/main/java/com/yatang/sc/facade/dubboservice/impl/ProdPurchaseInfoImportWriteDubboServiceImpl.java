package com.yatang.sc.facade.dubboservice.impl;

import com.busi.common.utils.BeanConvertUtils;
import com.busi.common.utils.StringUtils;
import com.google.common.base.Throwables;
import com.yatang.sc.facade.domain.*;
import com.yatang.sc.facade.dto.prod.ProdPurchaseInfoImportDto;
import com.yatang.sc.facade.dto.prod.ProdPurchasePriceUpdateDto;
import com.yatang.sc.facade.dubboservice.ProdPurchaseInfoImportWriteDubboService;
import com.yatang.sc.facade.flow.ProdPurchaseInfoImportFlowService;
import com.yatang.sc.facade.service.ProdPurchaseInfoImportService;
import com.yatang.sc.facade.service.ProdPurchaseService;
import com.yatang.sc.facade.service.SupplierAdrService;
import com.yatang.sc.facade.service.SupplierService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.CommonsEnum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @描述: 商品采购价导入记录表writeDubbo服务实现
 * @类名: ProdPurchaseInfoImportWriteDubboServiceImpl
 * @作者: kangdong
 * @创建时间: 2017/12/6 11:10
 * @版本: v1.0
 */
@Slf4j
@Service("prodPurchaseInfoImportWriteDubboService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdPurchaseInfoImportWriteDubboServiceImpl implements ProdPurchaseInfoImportWriteDubboService {

	@Autowired
	private ProdPurchaseInfoImportService prodPurchaseInfoImportService;

	@Autowired
	private ProdPurchaseService prodPurchaseService;

	@Autowired
	private SupplierService service;

	@Autowired
	private SupplierAdrService adrService;

	@Autowired
	private ProductScIndexDubboService productScIndexDubboService;

	@Autowired
	private ProdPurchaseInfoImportFlowService prodPurchaseInfoImportFlowService;

	/**
	 * @Description: 导入商品采购价导入记录列表
	 * @param prodPurchasePriceUpdateDto
	 * @author kangdong
	 * @date 2017/12/6 11:24
	 * @return
	 */
	@Override
	public Response<Long> prodPurchaseListImport(List<ProdPurchasePriceUpdateDto> prodPurchasePriceUpdateDto, String userId, List<String> branchCompanyIds) {
		Response<Long> response = new Response<>();
		try {
			log.info("start----prodPurchaseListImport--prodPurchasePriceUpdateDto:{},userId:{}",prodPurchasePriceUpdateDto,userId);

			List<ProdPurchaseInfoImportPo> prodPurchaseInfoImportPos = new ArrayList<ProdPurchaseInfoImportPo>();
			for (ProdPurchasePriceUpdateDto list : prodPurchasePriceUpdateDto) {
				ProdPurchaseInfoImportPo prodPurchaseInfoImportPo = new ProdPurchaseInfoImportPo();
				ProdPurchaseInfoPo prodPurchaseInfoPo= null;

				//供应商地点编码: 1、检查供应商地点和供应商是否有关联关系；2、检查商品和供应商地点的关系
				SupplierInfoPo supplierInfoPo = service.queryBySpNo(list.getSupplierNo());
				SupplierAdrInfoPo supplierAdrInfoPo =  adrService.queryByProviderNo(list.getSupplierAddressNo());
				if(!Objects.equals(supplierInfoPo,null)) {
					prodPurchaseInfoImportPo.setSpId(supplierInfoPo.getId());
				}
				if(!Objects.equals(supplierAdrInfoPo,null)) {
					prodPurchaseInfoImportPo.setSpAdrId(supplierAdrInfoPo.getId());
				}
				//通过商品编码查询商品
				ProductIndexDto productIndexDto = null;
				Response<ProductIndexDto> indexDtoResponse = productScIndexDubboService.queryByProductCode(list.getProductNo());
				if(indexDtoResponse.isSuccess()) {
					productIndexDto = indexDtoResponse.getResultObject();
					if (!Objects.equals(productIndexDto, null)) {
						prodPurchaseInfoImportPo.setProductInformation(productIndexDto.getSaleName());
						prodPurchaseInfoImportPo.setProductId(productIndexDto.getId());
					}
				}
				if(!Objects.equals(supplierInfoPo,null) && !Objects.equals(supplierAdrInfoPo,null) && !Objects.equals(productIndexDto,null)) {
					ProdPurchaseQueryParamPo paramPo = new ProdPurchaseQueryParamPo();
					paramPo.setProductId(productIndexDto.getId());
					paramPo.setSpId(String.valueOf(supplierInfoPo.getId()));
					paramPo.setSpAdrId(String.valueOf(supplierAdrInfoPo.getId()));
					//通过商品id查询采购
					prodPurchaseInfoPo = prodPurchaseService.getProdPurchaseByParam(paramPo);
					if(!Objects.equals(prodPurchaseInfoPo,null)) {
						prodPurchaseInfoImportPo.setPriceId(prodPurchaseInfoPo.getId());
					}
				}
				//验证判断
				String information = checkImport(list,supplierInfoPo, supplierAdrInfoPo, prodPurchaseInfoPo, branchCompanyIds);

				//处理结果:0:错误;1:已验证;2:已提交
				if (!Objects.equals(information, null)) {
					prodPurchaseInfoImportPo.setHandleResult(0);
					prodPurchaseInfoImportPo.setHandleInformation(information);
				} else {
					prodPurchaseInfoImportPo.setHandleResult(1);
				}
				prodPurchaseInfoImportPo.setLineNumber(list.getLineNum());
				prodPurchaseInfoImportPo.setSpNo(list.getSupplierNo());
				prodPurchaseInfoImportPo.setSpAdrNo(list.getSupplierAddressNo());
				prodPurchaseInfoImportPo.setNewestPrice(list.getPrice());
				prodPurchaseInfoImportPo.setProductCode(list.getProductNo());
				prodPurchaseInfoImportPos.add(prodPurchaseInfoImportPo);
			}
			ProdPurchaseInfoImportsPo prodPurchaseInfoImportsPo = new ProdPurchaseInfoImportsPo();
			prodPurchaseInfoImportsPo.setCreateUserId(userId);
			prodPurchaseInfoImportsPo.setImports(prodPurchaseInfoImportPos);
			Long insertSuccess = prodPurchaseInfoImportService.insertImportList(prodPurchaseInfoImportsPo);
			if(!Objects.equals(insertSuccess,null)) {
				response.setSuccess(true);
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
				response.setResultObject(insertSuccess);
			}else {
				response.setSuccess(false);
				response.setCode(CommonsEnum.RESPONSE_10025.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_10025.getName());
			}
			log.info("end----prodPurchaseListImport--prodPurchasePriceUpdateDto:{}");
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(Throwables.getRootCause(e).getMessage());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	/**
	 * @Description 验证判断
	 * @param list
	 * @return
	 */
	private String checkImport(ProdPurchasePriceUpdateDto list, SupplierInfoPo supplierInfoPo,SupplierAdrInfoPo supplierAdrInfoPo,ProdPurchaseInfoPo prodPurchaseInfoPo, List<String> branchCompanyIds) {
		//检查子公司编码是否存在
		if(StringUtils.isEmpty(list.getSupplierNo())) {
			return "供应商编码必输，不能为空";
		}
		if(StringUtils.isEmpty(list.getSupplierAddressNo())) {
			return "供应商地点编码必输，不能为空";
		}
		if(StringUtils.isEmpty(list.getProductNo())) {
			return "商品编码错误";
		}
		if(CollectionUtils.isNotEmpty(branchCompanyIds) && !branchCompanyIds.contains(prodPurchaseInfoPo.getBranchCompanyId())){
			return "子公司在不在当前登录用户所管理的子公司里面";
		}
		if(Objects.equals(list.getPrice(),null)) {
			return "商品最新进价必填，不能为空";
		}
		//最新售价>0
		if(list.getPrice().compareTo(BigDecimal.ZERO)==-1) {
			return "商品最新进价不能为负数";
		}
		if(Objects.equals(list.getPrice(),prodPurchaseInfoPo.getNewestPrice())) {
			return "变价单和采购价不能相同";
		}
		if(Objects.equals(supplierInfoPo,null)) {
			return "此商品和供应商没有采购关系，请检查";
		}
		if(Objects.equals(supplierAdrInfoPo,null)) {
			return "此商品和供应商地点没有采购关系，请检查";
		}
		if(!Objects.equals(supplierInfoPo,null) && !Objects.equals(supplierAdrInfoPo,null)) {
			//验证1、检查供应商地点和供应商是否有关联关系
			if (!Objects.equals(supplierInfoPo.getId(), supplierAdrInfoPo.getParentId())) {
				return "此商品和供应商没有采购关系，请检查";
			}
		}
		//验证、检查商品和供应商或供应商地点是否存在关系
		if(Objects.equals(prodPurchaseInfoPo,null)) {
			return "此商品和供应商或供应商地点没有采购关系，请检查";
		}
		//商品编码：检查商品和供应商地点的关系
//		if(Objects.equals(prodPurchaseInfoPos,null)) {
//			return "此商品和供应商地点没有采购关系，请检查";
//		}
		return null;
	}


	/**
	 * 提交采购变价单
	 * @return
	 */
	@Override
	public Response<Long> updateProdPurchaseInfoImport(Long id, String userId) {
		return prodPurchaseInfoImportFlowService.updateProdPurchase(id, userId);
	}
}
