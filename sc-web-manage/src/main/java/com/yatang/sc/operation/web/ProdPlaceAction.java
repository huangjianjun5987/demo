package com.yatang.sc.operation.web;

import com.busi.common.datatable.PageResult;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.common.staticvalue.ProdAdrEnum;
import com.yatang.sc.common.utils.JSONUtils;
import com.yatang.sc.facade.dto.ProdSpAdrSearchBoxDto;
import com.yatang.sc.facade.dto.ProdSpAdrSearchParamDto;
import com.yatang.sc.facade.dto.QueryRequestDto;
import com.yatang.sc.facade.dto.prod.place.ProdPlaceAddDto;
import com.yatang.sc.facade.dto.prod.place.ProdPlaceDto;
import com.yatang.sc.facade.dto.prod.place.ProdPlaceUpdateDto;
import com.yatang.sc.facade.dubboservice.prodplace.ProdPlaceQueryDubboService;
import com.yatang.sc.facade.dubboservice.prodplace.ProdPlaceWriteDubboService;
import com.yatang.sc.operation.util.SessionUtil;
import com.yatang.sc.operation.vo.ProdSpAdrSearchParamVo;
import com.yatang.sc.operation.vo.prod.place.ProdPlaceAddVo;
import com.yatang.sc.operation.vo.prod.place.ProdPlaceIdListVo;
import com.yatang.sc.operation.vo.prod.place.ProdPlaceUpdateVo;
import com.yatang.sc.operation.vo.prod.place.ProdPlaceVo;
import com.yatang.sc.operation.vo.prod.place.QueryProdPlaceVo;
import com.yatang.sc.web.common.excel.service.impl.ExcelExportService;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationSCService;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.biz.system.dto.UserTypeCompanyDTO;
import com.yatang.xc.mbd.biz.system.dubboservice.CompanyDubboService;
import com.yatang.xc.mbd.org.es.dto.StoreDto;
import com.yatang.xc.mbd.org.es.dubboservice.OrganizationIndexSCDubboService;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.yatang.sc.facade.common.Constants.CURRENT_USER;

/**
 * Created by tangqi on 2018/1/15 9:14.
 */
@Slf4j
@RestController
@RequestMapping("/sc/prodPlace")
public class ProdPlaceAction extends BaseAction {

    @Autowired
    private ProdPlaceWriteDubboService prodPlaceWriteDubboService;

    @Autowired
    private ProdPlaceQueryDubboService prodPlaceQueryDubboService;

    @Autowired
    private ExcelExportService excelExportService;

    @Autowired
    private OrganizationSCService organizationSCService;

    @Autowired
    private CompanyDubboService companyDubboService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationIndexSCDubboService organizationIndexSCDubboService;

    @RequestMapping(path = "/addDistinct", method = RequestMethod.POST)
    public Response addProdPlaceDistinct(@Validated @RequestBody ProdPlaceAddVo vo) {
        Response response = getSuccessResponse();
        com.yatang.sc.facade.common.PageResult<ProdPlaceVo> pageResult1 = new com.yatang.sc.facade.common.PageResult<>();
        LoginInfoVO currentUser = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        ProdPlaceAddDto prodPlaceAddDto = BeanConvertUtils.convert(vo, ProdPlaceAddDto.class);
        prodPlaceAddDto.setCreateDate(new Date());
        prodPlaceAddDto.setCreateUserId(currentUser.getUserId());
        Response<PageResult<List<ProdPlaceDto>>> distinctRes = prodPlaceWriteDubboService.distinct(prodPlaceAddDto);
        if (distinctRes.isSuccess() && distinctRes.getResultObject() != null && !CollectionUtils.isEmpty(distinctRes.getResultObject().getResultObject())) {
            List<ProdPlaceVo> prodPlaceVos = BeanConvertUtils.convertList(distinctRes.getResultObject().getResultObject(), ProdPlaceVo.class);
            pageResult1.setData(prodPlaceVos);
            pageResult1.setTotal(new Long(distinctRes.getResultObject().getRecordsTotal()));
            pageResult1.setPageSize(vo.getPageSize());
            pageResult1.setPageNum(vo.getPageNum());
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
            response.setResultObject(pageResult1);
        } else if (distinctRes.isSuccess() && distinctRes.getResultObject() != null && CollectionUtils.isEmpty(distinctRes.getResultObject().getResultObject())) {
            prodPlaceWriteDubboService.addProdPlace(prodPlaceAddDto);
        } else {
            return distinctRes;
        }
        return response;
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public Response updateProdPlace(@Validated @RequestBody ProdPlaceUpdateVo vo) {
        LoginInfoVO currentUser = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        ProdPlaceUpdateDto updateDto = BeanConvertUtils.convert(vo, ProdPlaceUpdateDto.class);
        updateDto.setLastModifyDate(new Date());
        updateDto.setLastModifyUserId(currentUser.getUserId());
        Response update = prodPlaceWriteDubboService.update(updateDto);
        return update;
    }

    @RequestMapping(path = "/queryDetail", method = RequestMethod.GET)
    public Response<ProdPlaceVo> queryProdPlaceDetail(@RequestParam String id) {
        Response<ProdPlaceVo> response = new Response<>();
        Response<ProdPlaceDto> responseRes = prodPlaceQueryDubboService.queryDetail(id);
        response.setCode(responseRes.getCode());
        response.setSuccess(responseRes.isSuccess());
        response.setErrorMessage(responseRes.getErrorMessage());
        response.setResultObject(BeanConvertUtils.convert(responseRes.getResultObject(), ProdPlaceVo.class));
        return response;
    }

    @RequestMapping(path = "/bulkDelete", method = RequestMethod.POST)
    public Response bulkDelete(@RequestBody ProdPlaceIdListVo vo) {
        Response response = prodPlaceWriteDubboService.bulkDelete(vo.getIds());
        return response;
    }

    @RequestMapping(path = "/queryPage", method = RequestMethod.POST)
    public Response queryPage(@Validated @RequestBody QueryProdPlaceVo vo) {
        Response response = getSuccessResponse();

        QueryRequestDto dto = new QueryRequestDto();
        dto.setPageSize(vo.getPageSize());
        dto.setPageNum(vo.getPageNum());
        vo.setPageNum(null);
        vo.setPageSize(null);
        String json = JSONUtils.toJson(vo);
        dto.setQueryJson(json);
        Response<PageResult<List<ProdPlaceDto>>> queryRes = prodPlaceQueryDubboService.queryPage(dto);
        if (!queryRes.isSuccess()) {
            return queryRes;
        }
        PageResult<List<ProdPlaceDto>> pageResultRes = queryRes.getResultObject();
        com.yatang.sc.facade.common.PageResult<ProdPlaceVo> pageResult1 = new com.yatang.sc.facade.common.PageResult<>();
        pageResult1.setTotal(new Long(pageResultRes.getRecordsTotal()));
        List<ProdPlaceVo> prodPlaceVoList = BeanConvertUtils.convertList(pageResultRes.getResultObject(), ProdPlaceVo.class);
        pageResult1.setData(prodPlaceVoList);
        pageResult1.setPageNum(dto.getPageNum());
        pageResult1.setPageSize(dto.getPageSize());
        response.setResultObject(pageResult1);
        return response;
    }

    /**
     * @param response
     * @Description: 导出新增商品地点关系时的重复数据Excel
     * @author tankejia
     * @date 2018/1/15- 11:18
     */
    @GetMapping("exportRepeatedProdAdd")
    public void exportRepeatedProdAdd(HttpServletResponse response, @Validated ProdPlaceAddVo vo) {
        try {
            ProdPlaceAddDto prodPlaceAddDto = BeanConvertUtils.convert(vo, ProdPlaceAddDto.class);
            prodPlaceAddDto.setPageSize(Integer.MAX_VALUE);
            Response<PageResult<List<ProdPlaceDto>>> responseRes = prodPlaceWriteDubboService.distinct(prodPlaceAddDto);
            if (null != responseRes && responseRes.getResultObject() != null && CollectionUtils.isNotEmpty(responseRes
                    .getResultObject().getResultObject())) {
                for (ProdPlaceDto dto : responseRes.getResultObject().getResultObject()) {
                    dto.setLogisticsType(ProdAdrEnum.directSending.getCode().equals(dto.getLogisticsModel())
                            ? ProdAdrEnum.directSending.getName() : ProdAdrEnum.dispatching.getName());
                }
                excelExportService.excelExport("repeatedProdPlaceExcel", responseRes.getResultObject().getResultObject(), response);
            }
        } catch (Exception e) {
            log.error("repeatedProdPlaceExcel(导出商品地点关系新增时重复数据Excel出错) error --> {}", ExceptionUtils.getFullStackTrace(e));
        }
    }

    /**
     * @param paramVo
     * @Description: 根据地点类型查询其对应子公司下面的所有供应商及供应商地点值清单
     * @author tankejia
     * @date 2018/1/18- 13:53
     */
    @GetMapping("prodSpAdrSearchBox")
    public Response<com.yatang.sc.facade.common.PageResult<ProdSpAdrSearchBoxDto>> prodSpAdrSearchBox(ProdSpAdrSearchParamVo paramVo) {
        return prodPlaceQueryDubboService.prodSpAdrSearchBox(BeanConvertUtils.convert(paramVo, ProdSpAdrSearchParamDto.class));
    }

    /*
    *@Author tangqi
    *@Date 2018/1/22 11:00
    *@Desc 根据子公司ID获取门店
    */
    @RequestMapping(path = "queryStoreByCompanyId", method = RequestMethod.GET)
    public Response queryStoreByCompanyId(String branchCompanyId, @NotNull(message = "参数不能为空") Integer pageSize,
                                          @NotNull(message = "参数不能为空") Integer pageNum, String idOrName) {
        LoginInfoVO currentUser = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        //如果没有传子公司Id，就默认取当前用户子公司Id
        String updateBranchCompanyId = null;
        if (StringUtils.isEmpty(branchCompanyId)) {
            updateBranchCompanyId = currentUser.getCompanyId();
        }
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isEmpty(updateBranchCompanyId)){
            map.put("branchCompanyId", branchCompanyId);
        }else{
            map.put("branchCompanyId", updateBranchCompanyId);
        }
        if(!StringUtils.isEmpty(idOrName)){
            map.put("idOrName", idOrName);
        }
        Response<UserTypeCompanyDTO> checkUserTypeResponse = companyDubboService.findUserTypeCompanyByUserId(new Integer(currentUser.getUserId()));
        if (!checkUserTypeResponse.isSuccess()) {
            logger.error("getProdPlaceList-判断用户类型失败：{}", JSONUtils.toJson(checkUserTypeResponse));
            throw new RuntimeException("判断用户类型失败：" + JSONUtils.toJson(checkUserTypeResponse));
        }
        if (checkUserTypeResponse.getResultObject().isHeadQuarters() && StringUtils.isEmpty(branchCompanyId)) {
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Object> entry = iterator.next();
                if(StringUtils.equals(entry.getKey(), "branchCompanyId")){
                    iterator.remove();
                }
            }
        }

        Response response = getSuccessResponse();

        Response<Integer> countResponse = organizationIndexSCDubboService.querySCStoreByIdOrName(map);
        if (!countResponse.isSuccess()) {
            logger.error("getProdPlaceList-通过条件查询门店数量失败：{}", JSONUtils.toJson(branchCompanyId));
            throw new RuntimeException("通过条件查询门店数量失败：" + branchCompanyId);
        }
        Response<List<StoreDto>> storeListRes = organizationIndexSCDubboService.queryPageSCStoreByIdOrName(pageNum, pageSize, map);
        if (!storeListRes.isSuccess() || CollectionUtils.isEmpty(storeListRes.getResultObject())) {
            logger.error("getProdPlaceList-通过条件查询门店列表失败：{}", JSONUtils.toJson(branchCompanyId));
            throw new RuntimeException("通过条件查询门店列表失败：" + branchCompanyId);
        }
        List<Map<String, String>> maps = assembleMap(storeListRes);
        com.yatang.sc.common.PageResult<Map<String, String>> pageResult = new com.yatang.sc.common.PageResult<>();
        pageResult.setTotal(new Long(countResponse.getResultObject()));
        pageResult.setData(maps);
        pageResult.setPageNum(pageNum);
        pageResult.setPageSize(pageSize);
        response.setResultObject(pageResult);
        return response;
    }

    private List<Map<String, String>> assembleMap(Response<List<StoreDto>> storeListRes) {
        List<StoreDto> storeDtos = storeListRes.getResultObject();
        List<Map<String, String>> list = new ArrayList<>();
        for (StoreDto storeDto : storeDtos) {
            Map<String, String> map = new HashMap<>();
            map.put("id", storeDto.getId());
            map.put("code", storeDto.getCode());
            map.put("name", storeDto.getName());
            list.add(map);
        }
        return list;
    }

}
