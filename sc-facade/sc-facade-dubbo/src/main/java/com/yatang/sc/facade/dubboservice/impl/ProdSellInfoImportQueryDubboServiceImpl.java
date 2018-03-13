package com.yatang.sc.facade.dubboservice.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Throwables;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dao.ProdSellInfoImportsDao;
import com.yatang.sc.facade.domain.ProdSellInfoImportPo;
import com.yatang.sc.facade.domain.ProdSellInfoImportsPo;
import com.yatang.sc.facade.domain.ProdSellPriceUpdateParamPo;
import com.yatang.sc.facade.dto.prod.ProdSellInfoImportDto;
import com.yatang.sc.facade.dto.prod.ProdSellInfoImportsDto;
import com.yatang.sc.facade.dto.prod.ProdSellPriceUpdateParamDto;
import com.yatang.sc.facade.dubboservice.ProdSellInfoImportQueryDubboService;
import com.yatang.sc.facade.service.ProdSellInfoImportService;
import com.yatang.xc.mbd.biz.org.dto.BranchCompanyDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
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
 * @描述: 商品售价导入记录的queryDubbo服务实现
 * @类名: ProdPurchaseInfoImportQueryDubboServiceImpl
 * @作者: kangdong
 * @创建时间: 2017/12/6 09:44
 * @版本: v1.0
 */
@Slf4j
@Service("prodSellInfoImportQueryDubboService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdSellInfoImportQueryDubboServiceImpl implements ProdSellInfoImportQueryDubboService {

    private final ProdSellInfoImportService prodSellInfoImportService;

    private final ProdSellInfoImportsDao prodSellInfoImportsDao;

    private final OrganizationService organizationService;

    /**
     * @Description: 根据id查询商品售价导入记录详情
     * @param id 记录id
     * @author kangdong
     * @date 2017/12/6 09:50
     * @return
     */
    @Override
    public Response<ProdSellInfoImportDto> getProdSellInfoImportById(Long id) {
        Response<ProdSellInfoImportDto> response = new Response<>();
        try {
            log.info("start--getProdPurchaseInfoImportById----id:{}",id);
            ProdSellInfoImportPo prodSellInfoImportPo  = prodSellInfoImportService.selectByPrimaryKey(id);
            if (null == prodSellInfoImportPo) {
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                return response;
            }
            //po2Dto
            ProdSellInfoImportDto prodSellInfoImportDto = BeanConvertUtils.convert(prodSellInfoImportPo, ProdSellInfoImportDto.class);
            response.setResultObject(prodSellInfoImportDto);
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
    public Response<PageResult<ProdSellInfoImportDto>> getProdSellInfoImportByParam(ProdSellPriceUpdateParamDto paramDto) {
        Response<PageResult<ProdSellInfoImportDto>> response = new Response<>();
        List<ProdSellInfoImportDto> importDtos = new ArrayList<>();
        try {
            log.info("start--getProdSellInfoImportByParam--- param:{}", paramDto.toString());
            ProdSellPriceUpdateParamPo paramPo = BeanConvertUtils.convert(paramDto, ProdSellPriceUpdateParamPo.class);
            PageInfo<ProdSellInfoImportPo> page = prodSellInfoImportService.getProdSellInfoImportByParam(paramPo);
            List<ProdSellInfoImportPo> importPos = page.getList();
            for (ProdSellInfoImportPo importPo: importPos){
                ProdSellInfoImportDto importDto = new ProdSellInfoImportDto();
                ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
                BeanUtils.copyProperties(importDto, importPo);

                // 上传时间
                ProdSellInfoImportsPo importsPo = prodSellInfoImportsDao.selectByPrimaryKey(importPo.getImportsId());
                importDto.setUploadDate(importsPo.getCreateTime());
                // 子公司名称
                Response<BranchCompanyDto> branchCompanyDtoResponse = organizationService.querySimpleByBranchCompanyId(importPo.getBranchCompanyId());
                BranchCompanyDto branchCompany = branchCompanyDtoResponse.getResultObject();
                if (branchCompany != null) {
                    importDto.setBranchCompanyName(branchCompany.getName());
                }
                importDtos.add(importDto);
            }

            PageResult<ProdSellInfoImportDto> pageResult = new PageResult<>();
            pageResult.setPageNum(page.getPageNum());
            pageResult.setPageSize(page.getPageSize());
            pageResult.setTotal(page.getTotal());
            pageResult.setData(importDtos);
            response.setResultObject(pageResult);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
        } catch (Exception e) {
            response.setErrorMessage(Throwables.getRootCause(e).getMessage());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        log.info("end--getProdSellInfoImportByParam");
        return response;
    }
}
