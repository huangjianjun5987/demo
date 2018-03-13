package com.yatang.sc.facade.dubboservice.impl.prodplace;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.datatable.PageResult;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.facade.domain.prodplace.ProdPlacePo;
import com.yatang.sc.facade.domain.ProdSpAdrSearchBoxPo;
import com.yatang.sc.facade.domain.ProdSpAdrSearchParamPo;
import com.yatang.sc.facade.dto.prod.place.ProdPlaceDto;
import com.yatang.sc.facade.dto.ProdSpAdrSearchBoxDto;
import com.yatang.sc.facade.dto.ProdSpAdrSearchParamDto;
import com.yatang.sc.facade.dto.QueryRequestDto;
import com.yatang.sc.facade.dubboservice.prodplace.ProdPlaceQueryDubboService;
import com.yatang.sc.facade.dubboservice.SupplierQueryDubboService;
import com.yatang.sc.facade.mongo.CommonQueryRequest;
import com.yatang.sc.facade.service.prodplace.ProdPlaceService;
import com.yatang.sc.facade.service.SupplierAdrService;
import com.yatang.xc.mbd.biz.org.dc.dto.StoreOrgDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationDCDubboService;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationSCService;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.biz.org.sc.dto.QueryStoreAreaGroupDto;
import com.yatang.xc.mbd.biz.org.sc.dto.StoreAreaGroupDto;
import com.yatang.xc.mbd.biz.org.sc.dto.StoreAreaGroupResultDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//import com.yatang.sc.facade.common.PageResult;

/*
*@Author tangqi
*@Date 2018/1/17 9:23
*@Desc
*/
@Service("prodPlaceQueryDubboService")
public class ProdPlaceQueryDubboServiceImpl implements ProdPlaceQueryDubboService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProdPlaceService prodPlaceService;

    @Autowired
    private ProductScIndexDubboService indexDubboService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private SupplierQueryDubboService supplierQueryDubboService;

    @Autowired
    private SupplierAdrService supplierAdrService;

    @Autowired
    private OrganizationDCDubboService organizationDCDubboService;

    @Autowired
    private OrganizationSCService organizationSCService;

    @Override
    public Response<PageResult<List<ProdPlaceDto>>> queryPage(QueryRequestDto dto) {
        Response<PageResult<List<ProdPlaceDto>>> response = new Response<>();
        PageResult<List<ProdPlaceDto>> pageResult = new PageResult();
        List<ProdPlaceDto> list = new ArrayList<>();
        try {
            PageResult<List<ProdPlacePo>> pageRes = prodPlaceService.queryPage(BeanConvertUtils.convert(dto, CommonQueryRequest.class));
            List<ProdPlacePo> prodPlacePoList = pageRes.getResultObject();
            for (ProdPlacePo po : prodPlacePoList) {
                ProdPlaceDto prodPlaceDto = convertPo2Dto(po);
                list.add(prodPlaceDto);
            }
            pageResult.setResultObject(list);
            pageResult.setRecordsTotal(pageRes.getRecordsTotal());
            response.setResultObject(pageResult);
            response.setSuccess(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
        } catch (Exception e) {
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response<ProdPlaceDto> queryDetail(String id) {
        Response<ProdPlaceDto> response = new Response();
        try {
            ProdPlacePo prodPlacePo = prodPlaceService.queryDetail(id);
            ProdPlaceDto prodPlaceDto = convertPo2Dto(prodPlacePo);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
            response.setResultObject(prodPlaceDto);
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    private ProdPlaceDto convertPo2Dto(ProdPlacePo po) {
        return BeanConvertUtils.convert(po, ProdPlaceDto.class);
        /*ProdPlaceDto dto = new ProdPlaceDto();
        BeanUtils.copyProperties(po, dto);
        Response<ProductIndexDto> ProductDtoResponse = indexDubboService.queryByProductId(po.getProductId());
        ProductIndexDto product = ProductDtoResponse.getResultObject();
        if (product == null) {
            logger.error("ProdPlaceQueryDubboServiceImpl--convertPo2Dto:商品信息为空:{}", JSONUtils.toJson(ProductDtoResponse));
            throw new RuntimeException("商品信息为空");
        }
        dto.setProductCode(product.getProductCode());
        dto.setProductName(product.getName());
        dto.setFirstLevelCategoryName(product.getFirstLevelCategoryName());
        dto.setSecondLevelCategoryName(product.getSecondLevelCategoryName());
        dto.setThirdLevelCategoryName(product.getThirdLevelCategoryName());
        dto.setFourthLevelCategoryName(product.getFourthLevelCategoryName());
        dto.setBrand(product.getBrand().getName());

        Response<StoreDto> storeDtoResponse = organizationService.queryStoreById(po.getStoreId());
        StoreDto store = storeDtoResponse.getResultObject();
        if (store == null) {
            logger.error("ProdPlaceQueryDubboServiceImpl--convertPo2Dto:店铺信息为空:{}", JSONUtils.toJson(storeDtoResponse));
            throw new RuntimeException("店铺信息为空");
        }
        dto.setBranchCompanyId(store.getBranchCompanyId());
        dto.setBranchCompanyName(store.getBranchCompanyName());
        dto.setStoreCode(store.getCode());
        dto.setStoreName(store.getName());

        Response<SupplierInfoDto> supplierInfoDtoResponse = supplierQueryDubboService.queryById(po.getSupplierId());
        SupplierInfoDto supplier = supplierInfoDtoResponse.getResultObject();
        if (supplier == null) {
            logger.error("ProdPlaceQueryDubboServiceImpl--convertPo2Dto:供应商信息为空:{}", JSONUtils.toJson(supplierInfoDtoResponse));
            throw new RuntimeException("供应商信息为空");
        }
        dto.setSupplierCode(supplier.getSupplierBasicInfo().getSpNo());
        dto.setSupplierName(supplier.getSupplierBasicInfo().getCompanyName());

        Response<SupplierPlaceDto> supplierPlaceDtoResponse = supplierQueryDubboService.queryProviderPlaceInfo(new Integer(po.getAdrSupId()));
        SupplierPlaceDto supplierPlace = supplierPlaceDtoResponse.getResultObject();
        if (supplierPlace == null) {
            logger.error("ProdPlaceQueryDubboServiceImpl--convertPo2Dto:供应商地点信息为空:{}", JSONUtils.toJson(supplierPlaceDtoResponse));
            throw new RuntimeException("供应商地点信息为空");
        }
        dto.setAdrSupCode(supplierPlace.getSupplierAdrInfo().getSpAdrBasic().getProviderNo());
        dto.setAdrSupName(supplierPlace.getSupplierAdrInfo().getSpAdrBasic().getProviderName());
        return dto;*/
    }

    @Override
    public Response<List<ProdPlaceDto>> queryDirectDeliveryProduct(String storeId, List<String> productIds) {
        if (logger.isInfoEnabled()) {
            logger.info("----- 批量查询物流模式为直送的商品地点关系>> queryDirectDeliveryProduct(): 门店id: {}; 商品id: {}",
                    storeId, JSONObject.toJSONString(productIds));
        }

        Response<List<ProdPlaceDto>> response = new Response<>();

        try {
            PageResult<List<ProdPlacePo>> pageResult = prodPlaceService.queryPage(convertQueryParam(storeId, productIds));
            if (pageResult == null || CollectionUtils.isEmpty(pageResult.getResultObject())) {
                response.setSuccess(true);
                response.setResultObject(null);
                return response;
            }

            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
            response.setResultObject(BeanConvertUtils.convertList(pageResult.getResultObject(), ProdPlaceDto.class));
        } catch (Exception e) {
            logger.error("-----批量查询物流模式为直送的商品地点关系>> queryDirectDeliveryProduct(),error: {}",
                    ExceptionUtils.getFullStackTrace(e));
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
        }
        return response;
    }

    @Override
    public Response<com.yatang.sc.facade.common.PageResult<ProdSpAdrSearchBoxDto>> prodSpAdrSearchBox(ProdSpAdrSearchParamDto paramDto) {
        if (logger.isInfoEnabled()) {
            logger.info("----- 商品地点查询供应商值清单>> prodSpAdrSearchBox(): param: {}", JSONObject.toJSONString(paramDto));
        		}

        Response<com.yatang.sc.facade.common.PageResult<ProdSpAdrSearchBoxDto>> response = new Response<>();
        ProdSpAdrSearchParamPo paramPo = BeanConvertUtils.convert(paramDto, ProdSpAdrSearchParamPo.class);

        try {
            PageInfo<ProdSpAdrSearchBoxPo> pageInfo = placeTypeHandler(paramPo);

            if (pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())) {
                response.setSuccess(true);
                response.setResultObject(null);
                return response;
            }

            com.yatang.sc.facade.common.PageResult<ProdSpAdrSearchBoxDto> pageResult = new com.yatang.sc.facade.common.PageResult<>();
            pageResult.setPageNum(pageInfo.getPageNum());
            pageResult.setPageSize(pageInfo.getPageSize());
            pageResult.setTotal(pageInfo.getTotal());
            pageResult.setData(BeanConvertUtils.convertList(pageInfo.getList(), ProdSpAdrSearchBoxDto.class));
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setResultObject(pageResult);
        } catch (Exception e) {
            logger.error("-----商品地点查询供应商值清单>> prodSpAdrSearchBox(),error: ", ExceptionUtils.getFullStackTrace(e));
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
        }
        return response;
    }

    private PageInfo<ProdSpAdrSearchBoxPo> placeTypeHandler(ProdSpAdrSearchParamPo paramPo){
        List<String> branchCompanyIds = new ArrayList<>();

        switch (paramPo.getPlaceType()) {
            case 1:
                // 根据子公司查询
                branchCompanyIds.add(paramPo.getPlaceId());
                paramPo.setBranchCompanyIds(branchCompanyIds);
                return supplierAdrService.prodSpAdrSearchBox(paramPo);
            case 2:
                // 根据区域组查询
                QueryStoreAreaGroupDto areaGroupDto= new QueryStoreAreaGroupDto();
                areaGroupDto.setAreaGroupIdOrName(paramPo.getPlaceId());
                Response<StoreAreaGroupResultDto> resultDtoResponse = organizationSCService.queryPageStoreAreaGroup(areaGroupDto);
                if (null == resultDtoResponse || null == resultDtoResponse.getResultObject()) {
                    logger.error("prodSpAdrSearchBox --根据区域组编号查询区域组出错！区域组编号: {}; 查询结果: {}",
                            paramPo.getPlaceId(), JSON.toJSONString(resultDtoResponse));
                    throw new RuntimeException("根据区域组编号查询区域组出错！");
                }
                for (StoreAreaGroupDto groupDto : resultDtoResponse.getResultObject().getRecords()) {
                    branchCompanyIds.add(groupDto.getBranchCompanyId());
                }
                paramPo.setBranchCompanyIds(branchCompanyIds);
                return supplierAdrService.prodSpAdrSearchBox(paramPo);
            case 3:
                // 根据门店查询
                Response<StoreOrgDto> orgDtoResponse = organizationDCDubboService.queryStoreOrgById(paramPo.getPlaceId());
                if (null == orgDtoResponse || null == orgDtoResponse.getResultObject()) {
                    logger.error("prodSpAdrSearchBox --根据门店id查询其所属子公司出错！门店id: {}, 查询结果: {}",
                            paramPo.getPlaceId(), JSON.toJSONString(orgDtoResponse));
                    throw new RuntimeException("根据门店id查询其所属子公司出错！");
                }
                branchCompanyIds.add(orgDtoResponse.getResultObject().getBranchCompanyId());
                paramPo.setBranchCompanyIds(branchCompanyIds);
                return supplierAdrService.prodSpAdrSearchBox(paramPo);
            default:
                //其他情况
        }
        return null;
    }

    private CommonQueryRequest convertQueryParam(String storeId, List<String> productIds) {
        if(CollectionUtils.isEmpty(productIds)) {
            logger.error("queryDirectDeliveryProduct()-->请求参数有误！传入的商品id集合为空");
            throw new RuntimeException("queryDirectDeliveryProduct()-->请求参数有误！传入的商品id集合为空");
        }
        Response<StoreOrgDto> orgDtoResponse = organizationDCDubboService.queryStoreOrgById(storeId);
        if (null == orgDtoResponse || null == orgDtoResponse.getResultObject()) {
            logger.error("queryDirectDeliveryProduct -- 根据门店id查询其所属子公司出错！门店id: {}; 查询结果: {}",
                    storeId, JSON.toJSONString(orgDtoResponse));
            throw new RuntimeException("根据门店id查询其所属子公司出错！");
        }
        CommonQueryRequest queryRequest = new CommonQueryRequest();
        queryRequest.setPageSize(Integer.MAX_VALUE);
        queryRequest.setQueryJson("{\"storeId\":\""+ storeId +"\",\"$in_productId\":"+ JSON.toJSONString(productIds) +"," +
                "\"logisticsModel\":0,\"branchCompanyId\":\""+ orgDtoResponse.getResultObject().getBranchCompanyId()+"\"}");
        return queryRequest;
    }

}
