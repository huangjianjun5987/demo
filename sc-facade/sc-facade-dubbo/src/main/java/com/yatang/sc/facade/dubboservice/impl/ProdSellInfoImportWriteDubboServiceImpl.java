package com.yatang.sc.facade.dubboservice.impl;

import com.yatang.sc.facade.domain.*;
import com.yatang.sc.facade.dto.prod.ProdSellPriceUpdateDto;
import com.yatang.sc.facade.dubboservice.ProdSellInfoImportWriteDubboService;
import com.yatang.sc.facade.flow.ProdPurchaseInfoImportFlowService;
import com.yatang.sc.facade.service.*;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busi.common.resp.Response;
import com.google.common.base.Throwables;
import com.yatang.sc.facade.common.CommonsEnum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @描述: 商品售价导入记录writeDubbo服务实现
 * @类名: ProdSellInfoImportWriteDubboServiceImpl
 * @作者: kangdong
 * @创建时间: 2017/12/6 14:28
 * @版本: v1.0
 */
@Slf4j
@Service("prodSellInfoImportWriteDubboService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdSellInfoImportWriteDubboServiceImpl implements ProdSellInfoImportWriteDubboService {
	@Autowired
	private final ProdSellInfoImportService prodSellInfoImportService;
	@Autowired
	private GoodPriceService goodPriceService;

	@Autowired
	private SupplierService service;

	@Autowired
	private SupplierAdrService adrService;

	@Autowired
	private ProductScIndexDubboService productScIndexDubboService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private ProdPurchaseService prodPurchaseService;

	@Autowired
	private ProdPurchaseInfoImportFlowService prodPurchaseInfoImportFlowService;

	/**
	 * @Description: 导入商品售价导入记录列表
	 * @param prodSellPriceUpdateDto
	 * @param userId
	 * @author kangdong
	 * @date 2017/12/6 11:24
	 * @return
	 */
	@Override
	public Response<Long> prodSellListImport(List<ProdSellPriceUpdateDto> prodSellPriceUpdateDto, String userId, List<String> branchCompanyIds) {
		Response<Long> response = new Response<>();
		try {
			log.info("start----prodSellListImport--prodSellPriceUpdateDto:{},userId:{}",prodSellPriceUpdateDto,userId);
			List<ProdSellInfoImportPo> prodSellInfoImportPos = new ArrayList<ProdSellInfoImportPo>();
			for(ProdSellPriceUpdateDto list:prodSellPriceUpdateDto) {
				Integer[] interval = null;
				SellPriceInfoPo sellPriceInfoPo = null;
				ProdSellInfoImportPo prodSellInfoImportPo = new ProdSellInfoImportPo();
				Response<ProductIndexDto> indexDtoResponse = productScIndexDubboService.queryByProductCode(list.getProductNo());
				if(indexDtoResponse.isSuccess()) {
					ProductIndexDto productIndexDto = indexDtoResponse.getResultObject();
					if(!Objects.equals(productIndexDto,null)) {
						prodSellInfoImportPo.setProductId(productIndexDto.getId());
						prodSellInfoImportPo.setProductInformation(productIndexDto.getSaleName());
						// 根据商品查询价格
						GoodsSellPriceQueryParamPo goodsSellPriceQueryParamPo = new GoodsSellPriceQueryParamPo();
						goodsSellPriceQueryParamPo.setProductId(productIndexDto.getId());
						goodsSellPriceQueryParamPo.setBranchCompanyId(list.getBranchCompanyId());
						sellPriceInfoPo = goodPriceService.getGoodsSellPriceInfo(goodsSellPriceQueryParamPo);
					}
				}

				//设置区间和验证区间值
				if(StringUtils.isNotBlank(list.getInterval())) {
					String[] intervals = list.getInterval().split("-");
					interval = new Integer[intervals.length];
					for(int i=0;i<interval.length;i++) {
						interval[i] = Integer.parseInt(intervals[i]);
					}

					if(interval.length==2) {
						prodSellInfoImportPo.setStartNumber(interval[0]);
						prodSellInfoImportPo.setEndNumber(interval[1]);
						// 获取price id
						setPriceId(sellPriceInfoPo, prodSellInfoImportPo, interval);
					}
				}

				//验证
				String information = checkImport(list, sellPriceInfoPo, interval, branchCompanyIds);
				//处理结果:0:错误;1:已验证;2:已提交
				if (!Objects.equals(information, null)) {
					prodSellInfoImportPo.setHandleResult(0);
					prodSellInfoImportPo.setHandleInformation(information);
				} else {
					prodSellInfoImportPo.setHandleResult(1);
				}

				prodSellInfoImportPo.setLineNumber(list.getLineNum());
				prodSellInfoImportPo.setBranchCompanyId(list.getBranchCompanyId());
				prodSellInfoImportPo.setProductCode(list.getProductNo());
				prodSellInfoImportPo.setSection(list.getInterval());
				prodSellInfoImportPo.setNewestPrice(list.getPrice());

				prodSellInfoImportPos.add(prodSellInfoImportPo);
			}
			//存入数据库
			ProdSellInfoImportsPo prodPurchaseInfoImportsPo = new ProdSellInfoImportsPo();
			prodPurchaseInfoImportsPo.setCreateUserId(userId);
			prodPurchaseInfoImportsPo.setImports(prodSellInfoImportPos);
			Long insertSuccess = prodSellInfoImportService.insertSellImportList(prodPurchaseInfoImportsPo);
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
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(Throwables.getRootCause(e).getMessage());
			response.setSuccess(false);
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	/**
	 * @Description 验证判断
	 * @param list
	 * @param sellPriceInfoPo
	 * @param interval
	 * @return
	 */
	private String checkImport(ProdSellPriceUpdateDto list,SellPriceInfoPo sellPriceInfoPo, Integer[] interval, List<String> branchCompanyIds) {
		//检查子公司编码是否存在
		if(StringUtils.isEmpty(list.getBranchCompanyId())) {
			return "子公司必输，不能为空";
		}
		if(CollectionUtils.isNotEmpty(branchCompanyIds) && !branchCompanyIds.contains(list.getBranchCompanyId())) {
			return "子公司在不在当前登录用户所管理的子公司里面";
		}
		//销售数量区间
		if(StringUtils.isEmpty(list.getInterval())) {
			return "请检查商品销售数量区间";
		}
		//商品编码
		if(StringUtils.isEmpty(list.getProductNo())) {
			return "商品编码错误";
		}
		//最新售价
		if(Objects.equals(list.getPrice(),null)) {
			return "商品最新进价必填，不能为空";
		}
		//最新售价>0
		if(list.getPrice().compareTo(BigDecimal.ZERO)==-1) {
			return "商品最新进价不能为负数";
		}
		if (Objects.equals(sellPriceInfoPo, null)) {
			return "子公司和商品不存在销售关系";
		}
		if(Objects.equals(sellPriceInfoPo.getAuditStatus(), 1)) {
			return "已提交审核不能修改";
		}
		if (interval.length != 2) {
			return "商品销售数量区间格式不正确";
		}
		boolean result = false;
		boolean isPrice = false;
		//验证区间数
		for (SellSectionPricePo price : sellPriceInfoPo.getSellSectionPrices()) {
			if (Objects.equals(price.getStartNumber(), interval[0]) && Objects.equals(price.getEndNumber(), interval[1])) {
				if(!Objects.equals(price.getPrice(),list.getPrice())) {
					isPrice = true;//表示价格不相等
				}
				result = true;
				break;
			}
		}
		//表示导入售价区间与商品价格区间表的区间不相等
		if(Objects.equals(result,false)) {
			return "请检查商品销售数量区间";
		}
		//价格相等时
		if(Objects.equals(isPrice,false)) {
			return "变价单和销售价不能相同。";
		}
		return null;
	}

	/**
	 * @Description 设置PriceId
	 * @param sellPriceInfoPo
	 * @param prodSellInfoImportPo
	 * @param interval
	 * @return
	 */
	private ProdSellInfoImportPo setPriceId(SellPriceInfoPo sellPriceInfoPo, ProdSellInfoImportPo prodSellInfoImportPo, Integer[] interval) {
		if(!Objects.equals(sellPriceInfoPo,null) && !Objects.equals(sellPriceInfoPo.getAuditStatus(),1)) {
			for (SellSectionPricePo price : sellPriceInfoPo.getSellSectionPrices()) {
				if (Objects.equals(price.getStartNumber(), interval[0]) && Objects.equals(price.getEndNumber(), interval[1])) {
					prodSellInfoImportPo.setPriceId(sellPriceInfoPo.getId());//prodSellInfoImportPo.setPriceId(price.getId());
				}
			}
		}
		return prodSellInfoImportPo;
	}

	@Override
	public Response<Long> updateProdSellInfoImport(Long id, String userId) {
		return prodPurchaseInfoImportFlowService.updateProdSell(id, userId);
	}
}
