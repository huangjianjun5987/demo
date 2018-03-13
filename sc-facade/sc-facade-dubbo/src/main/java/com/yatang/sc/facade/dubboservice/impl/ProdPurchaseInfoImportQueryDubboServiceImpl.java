package com.yatang.sc.facade.dubboservice.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.common.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Throwables;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dao.ProdPurchaseInfoImportsDao;
import com.yatang.sc.facade.domain.*;
import com.yatang.sc.facade.dto.prod.ProdPurchaseInfoImportDto;
import com.yatang.sc.facade.dto.prod.ProdPurchaseInfoImportsDto;
import com.yatang.sc.facade.dto.prod.ProdPurchasePriceUpdateParamDto;
import com.yatang.sc.facade.dto.supplier.SpAdrBasicDto;
import com.yatang.sc.facade.dubboservice.ProdPurchaseInfoImportQueryDubboService;
import com.yatang.sc.facade.dubboservice.SupplierQueryDubboService;
import com.yatang.sc.facade.service.ProdPurchaseInfoImportService;
import com.yatang.sc.facade.service.SupplierService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 商品采购价导入记录的queryDubbo服务实现
 * @类名: ProdPurchaseInfoImportQueryDubboServiceImpl
 * @作者: kangdong
 * @创建时间: 2017/12/6 09:44
 * @版本: v1.0
 */
@Slf4j
@Service("prodPurchaseInfoImportQueryDubboService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdPurchaseInfoImportQueryDubboServiceImpl implements ProdPurchaseInfoImportQueryDubboService {

    private final ProdPurchaseInfoImportService prodPurchaseInfoImportService;

    private final ProdPurchaseInfoImportsDao prodPurchaseInfoImportsDao;

    private final SupplierService supplierService;

    private final ProductScIndexDubboService productScIndexDubboService;

    /**
     * @Description: 根据id查询商品采购价导入记录详情
     * @param id 记录id
     * @author kangdong
     * @date 2017/12/6 09:50
     * @return
     */
    @Override
    public Response<ProdPurchaseInfoImportDto> getProdPurchaseInfoImportById(Long id) {
        Response<ProdPurchaseInfoImportDto> response = new Response<>();
        try {
            log.info("start--getProdPurchaseInfoImportById----id:{}",id);
            ProdPurchaseInfoImportPo prodPurchaseInfoImportPo = prodPurchaseInfoImportService.selectByPrimaryKey(id);
            if (null == prodPurchaseInfoImportPo) {
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                return response;
            }
            //po2Dto
            ProdPurchaseInfoImportDto prodPurchaseInfoImportDto = BeanConvertUtils.convert(prodPurchaseInfoImportPo, ProdPurchaseInfoImportDto.class);
            response.setResultObject(prodPurchaseInfoImportDto);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
            log.info("end--getProdPurchaseInfoImportById");
        } catch (Exception e) {
            response.setErrorMessage(Throwables.getRootCause(e).getMessage());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<PageResult<ProdPurchaseInfoImportDto>> getProdPurchaseInfoImportByParam(ProdPurchasePriceUpdateParamDto paramDto) {
        Response<PageResult<ProdPurchaseInfoImportDto>> response = new Response<>();
        List<ProdPurchaseInfoImportDto> importDtos = new ArrayList<>();
        try {
            log.info("start--getProdPurchaseInfoImportByParam--- param: {}", paramDto.toString());
            ProdPurchasePriceUpdateParamPo paramPo = BeanConvertUtils.convert(paramDto, ProdPurchasePriceUpdateParamPo.class);
            PageInfo<ProdPurchaseInfoImportPo> pageInfo = prodPurchaseInfoImportService.getProdPurchaseInfoImportByParam(paramPo);
            List<ProdPurchaseInfoImportPo> importPos = pageInfo.getList();
            for (ProdPurchaseInfoImportPo importPo: importPos){
                ProdPurchaseInfoImportDto importDto = new ProdPurchaseInfoImportDto();
                ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
                BeanUtils.copyProperties(importDto, importPo);
                // 上传时间
                ProdPurchaseInfoImportsPo importsPo = prodPurchaseInfoImportsDao.selectByPrimaryKey(importPo.getImportsId());
                importDto.setUploadDate(importsPo.getCreateTime());
                // 供应商名称
                SupplierInfoPo supplierInfoPo = supplierService.queryById(importPo.getSpId());
                if (supplierInfoPo != null) {
                    importDto.setSpName(supplierInfoPo.getSupplierBasicInfo().getCompanyName());
                }
                // 供应商地点
                if (importPo.getSpAdrId() != null) {
                    SpAdrBasicPo spAdrBasicPo = supplierService.queryByAdrInfoId(importPo.getSpAdrId());
                    if (spAdrBasicPo != null) {
                        importDto.setSpAdrName(spAdrBasicPo.getProviderName());
                    }
                }
                importDtos.add(importDto);
            }
            PageResult<ProdPurchaseInfoImportDto> pageResult = new PageResult<>();
            pageResult.setData(importDtos);
            pageResult.setPageNum(pageInfo.getPageNum());
            pageResult.setPageSize(pageInfo.getPageSize());
            pageResult.setTotal(pageInfo.getTotal());

            response.setResultObject(pageResult);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
        } catch (Exception e) {
            response.setErrorMessage(Throwables.getRootCause(e).getMessage());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        log.info("end--getProdPurchaseInfoImportByParam");
        return response;
    }
}
