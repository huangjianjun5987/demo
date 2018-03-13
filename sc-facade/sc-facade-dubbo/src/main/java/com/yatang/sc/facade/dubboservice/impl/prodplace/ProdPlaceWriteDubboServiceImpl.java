package com.yatang.sc.facade.dubboservice.impl.prodplace;

import com.busi.common.datatable.PageResult;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.common.utils.JSONUtils;
import com.yatang.sc.facade.domain.prodplace.ProdPlacePo;
import com.yatang.sc.facade.domain.prodplace.ProdPlaceUpdatePo;
import com.yatang.sc.facade.dto.prod.place.ProdPlaceAddDto;
import com.yatang.sc.facade.dto.prod.place.ProdPlaceDto;
import com.yatang.sc.facade.dto.prod.place.ProdPlaceUpdateDto;
import com.yatang.sc.facade.dubboservice.prodplace.ProdPlaceWriteDubboService;
import com.yatang.sc.facade.service.prodplace.ProdPlaceService;
import com.yatang.xc.mbd.biz.org.dto.QueryStoreDto;
import com.yatang.xc.mbd.biz.org.dto.StoreDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationSCService;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.org.es.dubboservice.OrganizationIndexSCDubboService;
import com.yatang.xc.mbd.org.es.sc.dto.QueryIndexScGroupStoreDto;
import com.yatang.xc.mbd.org.es.sc.dto.ScGroupStoreDto;
import com.yatang.xc.mbd.org.es.sc.dto.ScGroupStoreResultDto;
import com.yatang.xc.mbd.pi.es.dto.InternationalCodeDto;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
*@Author tangqi
*@Date 2018/1/17 9:28
*@Desc
*/
@Service("prodPlaceWriteDubboService")
public class ProdPlaceWriteDubboServiceImpl implements ProdPlaceWriteDubboService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProdPlaceService prodPlaceService;

    @Autowired
    private ProductScIndexDubboService indexDubboService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationSCService organizationSCService;

    @Autowired
    private OrganizationIndexSCDubboService organizationIndexSCDubboService;

    @Override
    public Response addProdPlace(ProdPlaceAddDto prodPlaceAddDto) {
        Response response = new Response<>();
        try {
            List<ProdPlacePo> prodPlaceList = getProdPlaceList(prodPlaceAddDto);
            prodPlaceService.addProdPlace(prodPlaceList);
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
    public Response<PageResult<List<ProdPlaceDto>>> distinct(ProdPlaceAddDto dto) {
        Response<PageResult<List<ProdPlaceDto>>> response = new Response<>();
        PageResult<List<ProdPlaceDto>> pageResult = new PageResult<>();
        try {
            List<ProdPlacePo> prodPlaceList = getProdPlaceList(dto);
            PageResult<List<ProdPlacePo>> distinct = prodPlaceService.distinct(prodPlaceList, dto.getPageNum(), dto.getPageSize());
            List<ProdPlaceDto> prodPlaceDtos = BeanConvertUtils.convertList(distinct.getResultObject(), ProdPlaceDto.class);
            pageResult.setResultObject(prodPlaceDtos);
            pageResult.setRecordsTotal(distinct.getRecordsTotal());
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

    private List<ProdPlacePo> getProdPlaceList(ProdPlaceAddDto dto) {
        List<ProdPlacePo> poList = new ArrayList<>();
        if (CollectionUtils.isEmpty(dto.getProductIds())) {
            return poList;
        }
        List<StoreDto> storeDtos = getStoreList(dto.getPlaceType(), dto.getPlaceId());
        Response<Map<String, ProductIndexDto>> mapResponse = indexDubboService.queryByProductIds(dto.getProductIds());
        Map<String, ProductIndexDto> resultObject = mapResponse.getResultObject();
        Iterator<Map.Entry<String, ProductIndexDto>> iterator = resultObject.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ProductIndexDto> entry = iterator.next();
            ProductIndexDto product = entry.getValue();
            assemblePo(dto, poList, product, storeDtos);
        }
        return poList;
    }

    private List<StoreDto> getStoreList(Integer placeType, String placeId) {
        switch (placeType) {
            case 0:
                QueryStoreDto dto = new QueryStoreDto();
                dto.setPageNo(1);
                dto.setPageSize(Integer.MAX_VALUE);
                dto.setTotalCount(Integer.MAX_VALUE);
                Response<List<StoreDto>> response = organizationService.queryStores(dto);
                if (!response.isSuccess()) {
                    logger.info("getStoreList-获取所有门店失败：{}", JSONUtils.toJson(response));
                    throw new RuntimeException("获取所有门店失败：");
                }
                return response.getResultObject();
            case 1:
                //子公司下的所有门店
                Response<List<StoreDto>> storeListRes = organizationSCService.queryStorePageBybranchCompanyId(placeId, 1, Integer.MAX_VALUE, Integer.MAX_VALUE);
                if (!storeListRes.isSuccess() || CollectionUtils.isEmpty(storeListRes.getResultObject())) {
                    logger.info("getProdPlaceList-通过子公司Id获取门店列表失败：{}", JSONUtils.toJson(placeId));
                    throw new RuntimeException("通过子公司Id获取门店列表失败：" + placeId);
                }
                return storeListRes.getResultObject();
            case 2:
                QueryIndexScGroupStoreDto groupStoreDto = new QueryIndexScGroupStoreDto();
                groupStoreDto.setExistsAreaGroup(true);
                groupStoreDto.setPageNo(1);
                groupStoreDto.setPageSize(Integer.MAX_VALUE);
                groupStoreDto.setAreaGroupId(placeId);
                Response<ScGroupStoreResultDto> resultDtoResponse = organizationIndexSCDubboService.queryScGroupStorePage(groupStoreDto);
                if (!resultDtoResponse.isSuccess()) {
                    logger.info("getProdPlaceList-通过区域组Id获取门店列表失败：{}", JSONUtils.toJson(placeId));
                    throw new RuntimeException("通过区域组Id获取门店列表失败：" + placeId);
                }
                List<ScGroupStoreDto> records = resultDtoResponse.getResultObject().getRecords();
                return convertGroupStore2Store(records);
            case 3:
                Response<StoreDto> storeRes = organizationService.queryStoreById(placeId);
                if (!storeRes.isSuccess() || storeRes.getResultObject() == null) {
                    logger.info("getProdPlaceList-通过门店Id获取门店失败：{}", JSONUtils.toJson(placeId));
                    throw new RuntimeException("通过门店Id获取门店失败：" + placeId);
                }
                return Arrays.asList(storeRes.getResultObject());
        }
        return null;
    }

    private List<StoreDto> convertGroupStore2Store(List<ScGroupStoreDto> records) {
        if (CollectionUtils.isEmpty(records)) {
            return new ArrayList<>();
        }
        List<StoreDto> storeList = new ArrayList<>();
        for (ScGroupStoreDto groupStoreDto : records) {
            StoreDto store = new StoreDto();
            store.setId(groupStoreDto.getId());
            store.setName(groupStoreDto.getName());
            store.setCode(groupStoreDto.getId());
            store.setBranchCompanyId(groupStoreDto.getBranchCompanyId());
            store.setBranchCompanyName(groupStoreDto.getBranchCompanyName());
            storeList.add(store);
        }

        return storeList;
    }

    private void assemblePo(ProdPlaceAddDto dto, List<ProdPlacePo> poList, ProductIndexDto product, List<StoreDto> storeList) {
        for (StoreDto store : storeList) {
            ProdPlacePo po = new ProdPlacePo();
            po.setLogisticsModel(dto.getLogisticsModel());
            po.setPlaceType(dto.getPlaceType());
            po.setPlaceId(dto.getPlaceId());
            //供应商
            po.setSupplierId(dto.getSupplierId());
            po.setSupplierCode(dto.getSupplierCode());
            po.setSupplierName(dto.getSupplierName());
            //供应商地点
            po.setAdrSupId(dto.getAdrSupId());
            po.setAdrSupCode(dto.getAdrSupCode());
            po.setAdrSupName(dto.getAdrSupName());

            po.setCreateDate(dto.getCreateDate());
            po.setCreateUserId(dto.getCreateUserId());
            //店铺信息
            po.setStoreId(store.getId());
            po.setStoreCode(store.getCode());
            po.setStoreName(store.getName());
            po.setBranchCompanyId(store.getBranchCompanyId());
            po.setBranchCompanyName(store.getBranchCompanyName());
            //产品信息
            po.setProductId(product.getId());
            po.setProductCode(product.getProductCode());
            po.setProductName(product.getName());
            po.setBrand(product.getBrand().getName());
            po.setFirstLevelCategoryId(product.getFirstLevelCategoryId());
            po.setFirstLevelCategoryName(product.getFirstLevelCategoryName());
            po.setSecondLevelCategoryId(product.getSecondLevelCategoryId());
            po.setSecondLevelCategoryName(product.getSecondLevelCategoryName());
            po.setThirdLevelCategoryId(product.getThirdLevelCategoryId());
            po.setThirdLevelCategoryName(product.getThirdLevelCategoryName());
            po.setFourthLevelCategoryId(product.getFirstLevelCategoryId());
            po.setFourthLevelCategoryName(product.getFourthLevelCategoryName());
            List<InternationalCodeDto> internationalCodes = product.getInternationalCodes();
            List<String> codeString = new ArrayList<>();
            for (InternationalCodeDto codeDto : internationalCodes){
                codeString.add(codeDto.getInternationalCode());
            }
            po.setInternationalCodes(codeString);
            po.setId(po.getStoreId()+"_"+po.getProductId());
            poList.add(po);
        }
    }

    @Override
    public Response update(ProdPlaceUpdateDto dto) {
        Response response = new Response();
        try {
            prodPlaceService.update(BeanConvertUtils.convert(dto, ProdPlaceUpdatePo.class));
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response bulkDelete(List<String> ids) {
        Response<ProdPlaceDto> response = new Response();
        try {
            prodPlaceService.bulkDelete(ids);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
}
