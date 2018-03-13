package com.yatang.sc.facade.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.domain.ProdPurchaseExtPo;
import com.yatang.sc.facade.domain.ProdPurchaseInfoPo;
import com.yatang.sc.facade.domain.ProdPurchaseListPo;
import com.yatang.sc.facade.domain.ProdPurchasePriceAuditPo;
import com.yatang.sc.facade.domain.ProdPurchaseQueryParamPo;
import com.yatang.sc.facade.dto.prod.ProdPurchaseExtDto;
import com.yatang.sc.facade.dto.prod.ProdPurchaseInfoDto;
import com.yatang.sc.facade.dto.prod.ProdPurchasePriceAuditDto;
import com.yatang.sc.facade.dto.prod.ProdPurchaseQueryParamDto;
import com.yatang.sc.facade.dubboservice.ProdPurchaseQueryDubboService;
import com.yatang.sc.facade.service.ProdPurchaseService;
import com.yatang.xc.mbd.biz.system.dto.UserDTO;
import com.yatang.xc.mbd.biz.system.dubboservice.UserDubboService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @描述: 商品采购的queryDubbo服务实现
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/15 14:08
 * @版本: v1.0
 */
@Slf4j
@Service("prodPurchaseQueryDubboService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdPurchaseQueryDubboServiceImpl implements ProdPurchaseQueryDubboService {

    private final ProdPurchaseService prodPurchaseService;


    private final UserDubboService userDubboService;


    private final ProductScIndexDubboService indexDubboService;

    @Override
    public Response<ProdPurchaseExtDto> getProdPurchaseById(Long id) {

        Response<ProdPurchaseExtDto> response = new Response<>();
        try {
            log.info("start--getProdPurchaseById----id:{}", id);
            ProdPurchaseExtPo prodPurchaseExtPo = prodPurchaseService.getProdPurchaseById(id);
            if (null == prodPurchaseExtPo) {
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                return response;
            }
            //po2Dto
            ProdPurchaseExtDto prodPurchaseExtDto = BeanConvertUtils.convert(prodPurchaseExtPo, ProdPurchaseExtDto.class);

            //拼接用户信息
            if (StringUtils.isNotBlank(prodPurchaseExtDto.getCreateUserId())) {

                Response<UserDTO> createUserResp = userDubboService.findUserById(Integer.parseInt(prodPurchaseExtDto.getCreateUserId()));
                if (CommonsEnum.RESPONSE_200.getCode().equals(createUserResp.getCode())&&createUserResp.getResultObject()!=null) {

                        prodPurchaseExtDto.setCreateUserName(createUserResp.getResultObject().getEmployeeName());


                }
            }
            if (StringUtils.isNotBlank(prodPurchaseExtDto.getModifyUserId())) {
                Response<UserDTO> modifyUserResp = userDubboService.findUserById(Integer.parseInt(prodPurchaseExtDto.getModifyUserId()));
                if (CommonsEnum.RESPONSE_200.getCode().equals(modifyUserResp.getCode())&&modifyUserResp.getResultObject()!=null) {
                    prodPurchaseExtDto.setModifyUserName(modifyUserResp.getResultObject().getEmployeeName());
                }

            }
            if (StringUtils.isNotBlank(prodPurchaseExtDto.getAuditUserId())) {
                Response<UserDTO> auditUserResp = userDubboService.findUserById(Integer.parseInt(prodPurchaseExtDto.getAuditUserId()));
                if (CommonsEnum.RESPONSE_200.getCode().equals(auditUserResp.getCode())&&auditUserResp.getResultObject()!=null) {
                    prodPurchaseExtDto.setAuditUserName(auditUserResp.getResultObject().getEmployeeName());
                }
            }


            response.setResultObject(prodPurchaseExtDto);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
            log.info("end--getProdPurchaseById");
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<PageResult<ProdPurchaseExtDto>> queryProdPurchaseExtByCondition(ProdPurchaseQueryParamDto purchasePriceQueryParamDto) {
        Response<PageResult<ProdPurchaseExtDto>> response = new Response<>();
        try {
            log.info("start--queryProdPurchaseExtByCondition--------purchasePriceQueryParamDto:{}", purchasePriceQueryParamDto.toString());

            //dto
            ProdPurchaseQueryParamPo purchasePriceQueryParamPo = BeanConvertUtils.convert(purchasePriceQueryParamDto, ProdPurchaseQueryParamPo.class);
            PageInfo<ProdPurchaseExtPo> pageInfo = prodPurchaseService.queryProdPurchaseExtByCondition(purchasePriceQueryParamPo);
            List<ProdPurchaseExtPo> purchaseExtPoList = pageInfo.getList();
       /*     if (purchaseExtPoList.size() == 0) {
                response.setSuccess(true);
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                response.setResultObject(null);
                return response;
            }*/
            //po2Dto
            List<ProdPurchaseExtDto> prodPurchaseExtDtos = BeanConvertUtils.convertList(purchaseExtPoList, ProdPurchaseExtDto.class);
            //设置属性
            PageResult<ProdPurchaseExtDto> pageResult = new PageResult<>();
            pageResult.setPageNum(pageInfo.getPageNum());
            pageResult.setPageSize(pageInfo.getPageSize());
            pageResult.setTotal(pageInfo.getTotal());
            pageResult.setData(prodPurchaseExtDtos);
            response.setResultObject(pageResult);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
            log.info("end--queryProdPurchaseExtByCondition");
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<Void> checkMainSupplier(String productId, String branchCompanyId, int supplierType) {
        Response<Void> response = new Response<>();
        try {

            log.info("queryProdPurchaseExtByCondition---------start--productId:{},branchCompanyId:{},supplierType:{}", productId, branchCompanyId, supplierType);
            boolean b = prodPurchaseService.checkMainSupplier(productId, branchCompanyId, supplierType);
            if (b) {
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                response.setSuccess(true);
            } else {
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                return response;
            }
            log.info("queryProdPurchaseExtByCondition---------end");
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }


    @Override
    public Response<Map<String, List<ProdPurchaseExtDto>>> queryProdPurchaseExtVoMapByProductIds(List<String> productIds) {
        Response<Map<String, List<ProdPurchaseExtDto>>> response = new Response<>();
        Map<String, List<ProdPurchaseExtDto>> prodMapList = new ConcurrentHashMap<>();
        try {

            log.info("start--queryProdPurchaseExtByCondition--------productIds:{}", productIds);
            List<ProdPurchaseListPo> poList = prodPurchaseService.queryProdPurchaseListPoByProductIds(productIds);
            if (poList.size() == 0) {
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                return response;
            }
            for (ProdPurchaseListPo prodPurchaseListPo : poList) {
                String productId = prodPurchaseListPo.getProductId();
                List<ProdPurchaseExtDto> prodPurchaseExtDtos = BeanConvertUtils.convertList(prodPurchaseListPo.getProdPurchaseExtPos(), ProdPurchaseExtDto.class);
                //po2Dto
                prodMapList.put(productId, prodPurchaseExtDtos);
            }
            //设置属性
            response.setResultObject(prodMapList);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;

    }

    @Override
    public Response<ProdPurchaseInfoDto> getProdPurchaseByParam(ProdPurchaseQueryParamDto queryParamDto) {
        Response<ProdPurchaseInfoDto> response = new Response<>();
        try {
            log.info("start--getProdPurchaseByParam--queryParamDto:{}", JSON.toJSONString(queryParamDto));
            //dto2PO
            ProdPurchaseQueryParamPo queryParamPo = BeanConvertUtils.convert(queryParamDto, ProdPurchaseQueryParamPo.class);
            ProdPurchaseInfoPo prodPurchaseInfoPo = prodPurchaseService.getProdPurchaseByParam(queryParamPo);
       /*     if (null == prodPurchaseInfoPo) {
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                return response;
            }*/
            //po2Dto
            ProdPurchaseInfoDto prodPurchaseInfoDto = BeanConvertUtils.convert(prodPurchaseInfoPo, ProdPurchaseInfoDto.class);
            response.setResultObject(prodPurchaseInfoDto);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<List<ProdPurchaseInfoDto>> queryProdPurchaseListByParam(ProdPurchaseQueryParamDto queryParamDto) {
        log.info("start--getProdPurchaseByParam--queryParamDto:{}", JSON.toJSONString(queryParamDto));
        Response<List<ProdPurchaseInfoDto>> response = new Response<>();
        try {

            //dto2PO
            ProdPurchaseQueryParamPo queryParamPo = BeanConvertUtils.convert(queryParamDto, ProdPurchaseQueryParamPo.class);
            List<ProdPurchaseInfoPo> prodPurchaseInfoPos = prodPurchaseService.queryProdPurchaseListByParam(queryParamPo);
            //po2Dto
            List<ProdPurchaseInfoDto> prodPurchaseInfoDtos = BeanConvertUtils.convertList(prodPurchaseInfoPos, ProdPurchaseInfoDto.class);
            response.setResultObject(prodPurchaseInfoDtos);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }


    @Override
    public Response<BigDecimal> queryPurchasePriceForProdSell(String productId, String branchCompanyId) {
        log.info("prodPurchaseQueryDubboService-->queryPurchasePriceForProdSell()-->param:productId=" + productId + ",branchCompanyId=" + branchCompanyId);
        Response<BigDecimal> response = new Response<>();
        try {
            BigDecimal bigDecimal = prodPurchaseService.queryPurchasePriceForProdSell(productId, branchCompanyId);
            if (null!=bigDecimal){
                response.setResultObject(bigDecimal);
            }
            response.setSuccess(true);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            return response;
        } catch (Exception e) {
            log.error("prodPurchaseQueryDubboService-->queryPurchasePriceForProdSell()-->error:" + ExceptionUtils.getFullStackTrace(e));
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            return response;
        }
    }

    @Override
    public Response<ProdPurchasePriceAuditDto> getProdPurchasePriceAuditDtoById(Long id) {
        log.info("dubbo--getProdPurchasePriceAuditDtoById----id:{}", id);
        Response<ProdPurchasePriceAuditDto> response = new Response<>();
        try {

            //po2dto
            ProdPurchasePriceAuditPo priceAuditPo = prodPurchaseService.getProdPurchasePriceAuditDtoById(id);
            Response<ProductIndexDto> productIndexDtoResponse = indexDubboService.queryByProductId(priceAuditPo.getProductId());
            if (null == productIndexDtoResponse || null == productIndexDtoResponse.getResultObject()) {
                throw new RuntimeException("查询商品数据异常--商品id" + priceAuditPo.getProductId());
            }
            priceAuditPo.setProductCode(productIndexDtoResponse.getResultObject().getProductCode());
            priceAuditPo.setProductName(productIndexDtoResponse.getResultObject().getBrand() == null ? null : productIndexDtoResponse.getResultObject().getName());

            ProdPurchasePriceAuditDto priceAuditDto = BeanConvertUtils.convert(priceAuditPo, ProdPurchasePriceAuditDto.class);
            response.setResultObject(priceAuditDto);
            response.setSuccess(true);
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            log.error("prodPurchaseQueryDubboService-->getProdPurchasePriceAuditDtoById()-->error:{}", ExceptionUtils.getFullStackTrace(e));
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            return response;
        }
        return response;
    }
}
