package com.yatang.sc.operation.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.common.staticvalue.FileType;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.operation.service.WhiteListHelper;
import com.yatang.sc.operation.util.excel.ExcelUtil2;
import com.yatang.sc.operation.util.excel.FileUtils;
import com.yatang.sc.operation.vo.*;
import com.yatang.sc.sorder.vo.ImportExcelVo;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationSCService;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.biz.org.sc.dto.ParamScPurchaseDto;
import com.yatang.xc.mbd.biz.org.sc.dto.QueryStoreScPurchaseDto;
import com.yatang.xc.mbd.biz.org.sc.dto.StorePurcaseResultDto;
import com.yatang.xc.mbd.biz.org.sc.dto.StoreScPurchaseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * by
 */

@Slf4j
@RestController
@RequestMapping(value = "/sc/sp")
public class StorePermissionAction extends BaseAction {

    @Autowired
    OrganizationSCService organizationSCService;
    @Autowired
    WarehouseLogicQueryDubboService warehouseLogicQueryDubboService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    WhiteListHelper whiteListHelper;

    /**
     * 根据条件查询白名单
     *
     * @param queryStoreScPurchaseVo
     * @return
     */
    @RequestMapping(value = "queryWhiteList", method = RequestMethod.GET)
    public Response<PageResult<StoreScPurchaseDto>> queryPageSCStorePurchase(QueryStoreScPurchaseVo queryStoreScPurchaseVo) {
        Response<PageResult<StoreScPurchaseDto>> res = new Response<PageResult<StoreScPurchaseDto>>();
        PageResult<StoreScPurchaseDto> storeScPurchaseDtoPageResult = new PageResult<StoreScPurchaseDto>();
        logger.info("StorePermissionAction 查询白名单的请求参数是:{}", JSONObject.toJSONString(queryStoreScPurchaseVo));
        QueryStoreScPurchaseDto queryStoreScPurchaseDto = BeanConvertUtils.convert(queryStoreScPurchaseVo, QueryStoreScPurchaseDto.class);
        Response<List<StoreScPurchaseDto>> storeScPurchaseResponse = organizationSCService.queryPageSCStorePurchase(queryStoreScPurchaseDto);
        logger.info("StorePermissionAction 查询白名单的结果是:{}", JSONObject.toJSONString(storeScPurchaseResponse));
        Response<Integer> countResponse = organizationSCService.queryCountSCStorePurchase(queryStoreScPurchaseDto);
        logger.info("StorePermissionAction 查询白名单的条数是:{}", JSONObject.toJSONString(countResponse));
        if (storeScPurchaseResponse == null) {
            res.setResultObject(storeScPurchaseDtoPageResult);
            res.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setSuccess(false);
            logger.info("StorePermissionAction 查询白名单调接口返回值是null");
            return res;
        }
        storeScPurchaseDtoPageResult.setData(storeScPurchaseResponse.getResultObject());
        if (countResponse != null && countResponse.getResultObject() != null) {
            storeScPurchaseDtoPageResult.setTotal((long) countResponse.getResultObject());
        }
        storeScPurchaseDtoPageResult.setPageNum(queryStoreScPurchaseDto.getPageNo());
        storeScPurchaseDtoPageResult.setPageSize(queryStoreScPurchaseDto.getPageSize());
        res.setResultObject(storeScPurchaseDtoPageResult);
        res.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        res.setCode(CommonsEnum.RESPONSE_200.getCode());
        res.setSuccess(true);
        logger.info("StorePermissionAction 查询白名单的返回给前端的数据:{}", JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 供应链白名单 上线下线
     *
     * @param paramScPurchasesVo
     * @return
     */
    @RequestMapping(value = "onlineOffline", method = RequestMethod.POST)
    @Deprecated
    public Response<Integer> onlineOffline(HttpServletRequest request, @RequestBody ParamScPurchasesVo paramScPurchasesVo) {
        logger.info("StorePermissionAction 上下线白名单的请求参数是:{}", JSONObject.toJSONString(paramScPurchasesVo));
        Response<Integer> res = new Response<Integer>();
        List<ParamScPurchaseVo> paramScPurchaseVos = JSON.parseArray(paramScPurchasesVo.getParam(), ParamScPurchaseVo.class);
        List<ParamScPurchaseDto> paramScPurchaseDtos = BeanConvertUtils.convertList(paramScPurchaseVos, ParamScPurchaseDto.class);
        logger.info("StorePermissionAction 上下线白名单的请求参数解析之后的是:{}", JSONObject.toJSONString(paramScPurchaseDtos));
        //先看是否满足上线条件
        if (CollectionUtils.isEmpty(paramScPurchaseDtos)) {
            res.setSuccess(false);
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setErrorMessage("请选择要上下线的商家");
            logger.info("要上下线的商家集合为空");
            return res;
        }
        for (ParamScPurchaseDto paramScPurchaseDto : paramScPurchaseDtos) {
            if (paramScPurchaseDto.getScPurchaseFlag() != null && paramScPurchaseDto.getScPurchaseFlag() == 1) {
                Response<Integer> jugeResponse = organizationSCService.queryJudgeSCOnline(paramScPurchaseDto.getStoreId());
                if (jugeResponse == null || jugeResponse.getResultObject() == null) {
                    logger.error("调供应链上线采购查询条件是否满足服务");
                    res.setSuccess(false);
                    res.setCode(CommonsEnum.RESPONSE_500.getCode());
                    res.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
                    return res;
                }
                if (jugeResponse.getResultObject() == 0) {
                    logger.error("商家信息不完整，请去主数据完善后上线");
                    res.setErrorMessage("商家信息不完整，请去主数据完善后上线");
                    res.setCode(CommonsEnum.RESPONSE_500.getCode());
                    res.setResultObject(0);
                    res.setSuccess(false);
                    return res;
                }
            }
        }
        Response<Integer> onlineOfflineResponse = organizationSCService.onOfflineStorePurchase(paramScPurchaseDtos);
        if (!onlineOfflineResponse.isSuccess()) {
            res.setSuccess(false);
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setErrorMessage(onlineOfflineResponse.getErrorMessage());
            return res;
        }
        res.setResultObject(onlineOfflineResponse.getResultObject());
        res.setCode(CommonsEnum.RESPONSE_200.getCode());
        res.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        res.setSuccess(true);
        return res;
    }

    /**
     * @param vo
     * @Description: 供应链白名单上线
     * @author tankejia
     * @date 2017/10/17- 15:01
     */
    @RequestMapping(value = "whiteListOnline", method = RequestMethod.POST)
    public Response<String> whiteListOnline(@Validated({GroupOne.class}) @RequestBody WhiteListOnOfflineVo vo) {
        Response<String> response = new Response<>();
        List<ParamScPurchaseDto> dtoList = new ArrayList<>();

        List<String> storeIds = vo.getStoreIds();
        if (storeIds != null && storeIds.size() > 0) {
            for (String storeId : storeIds) {
                // 判断是否满足上线条件
                Response<Integer> jugeResponse = organizationSCService.queryJudgeSCOnline(storeId);
                if (jugeResponse != null && jugeResponse.getResultObject() != null) {
                    if (jugeResponse.getResultObject() == 0) {
                        logger.error("商家信息不完整，请去主数据完善后上线");
                        response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
                        response.setCode(CommonsEnum.RESPONSE_400.getCode());
                        response.setResultObject("商家信息不完整，请去主数据完善后上线");
                        response.setSuccess(false);
                        return response;
                    }
                }

                ParamScPurchaseDto dto = new ParamScPurchaseDto();
                dto.setStoreId(storeId);
                //上线操作
                dto.setScPurchaseFlag(1);
                dto.setDeliveryWarehouseCode(vo.getWarehouseCode());
                dto.setDeliveryWarehouseName(vo.getWarehouseName());
                dtoList.add(dto);
            }
        }

        Response<Integer> onOfflineResponse = organizationSCService.onOfflineStorePurchase(dtoList);
        if (onOfflineResponse != null && onOfflineResponse.getResultObject() != null) {
            if (onOfflineResponse.getResultObject() > 0) {
                response.setResultObject("上线成功!");
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                response.setSuccess(true);
            } else {
                response.setResultObject("上线失败!");
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
                response.setSuccess(false);
            }
        }
        return response;
    }


    /**
     * @param vo
     * @Description: 供应链白名单下线
     * @author tankejia
     * @date 2017/10/17- 15:01
     */
    @RequestMapping(value = "whiteListOffline", method = RequestMethod.POST)
    public Response<String> whiteListOffline(@Validated({GroupTwo.class}) @RequestBody WhiteListOnOfflineVo vo) {
        Response<String> response = new Response<>();
        List<ParamScPurchaseDto> dtoList = new ArrayList<>();

        List<String> storeIds = vo.getStoreIds();
        if (storeIds != null && storeIds.size() > 0) {
            for (String storeId : storeIds) {
                ParamScPurchaseDto dto = new ParamScPurchaseDto();
                dto.setStoreId(storeId);
                //下线操作
                dto.setScPurchaseFlag(0);
                dto.setDeliveryWarehouseCode(vo.getWarehouseCode());
                dto.setDeliveryWarehouseName(vo.getWarehouseName());
                dtoList.add(dto);
            }
        }

        Response<Integer> onOfflineResponse = organizationSCService.onOfflineStorePurchase(dtoList);
        if (onOfflineResponse != null && onOfflineResponse.getResultObject() != null) {
            if (onOfflineResponse.getResultObject() > 0) {
                response.setResultObject("下线成功!");
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                response.setSuccess(true);
            } else {
                response.setResultObject("下线失败!");
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
                response.setSuccess(false);
            }
        }
        return response;
    }

    @RequestMapping(value = "/whiteListBatchImport", method = RequestMethod.POST)
    public Response<List<StorePurcaseResultDto>> whiteListBatchImport(MultipartFile file) {
        log.info("whiteListBatchImport start.");
        Response<List<StorePurcaseResultDto>> rep = new Response<>();
        String contentType = file.getContentType();
        if(!FileType.fileTypeMap.get("excel").contains(contentType)){
            log.error("白名单导入："+CommonsEnum.RESPONSE_10048.getName());
            rep.setErrorMessage(CommonsEnum.RESPONSE_10048.getName());
            rep.setCode(CommonsEnum.RESPONSE_10048.getCode());
            rep.setSuccess(false);
            return rep;
        }
        if (file == null || file.isEmpty()) {
            log.error("白名单导入："+CommonsEnum.RESPONSE_10024.getName());
            rep.setErrorMessage(CommonsEnum.RESPONSE_10024.getName());
            rep.setCode(CommonsEnum.RESPONSE_10024.getCode());
            rep.setSuccess(false);
            return rep;
        }
        try {
            InputStream in = file.getInputStream();
            List<List<Object>> records = FileUtils.getBankListByExcel(in, file.getOriginalFilename());
            List<ParamScPurchaseVo> paramScPurchaseVos = new ArrayList<>();
            for (int i = 1;i<records.size();i++) {
                ParamScPurchaseVo paramScPurchaseVo = new ParamScPurchaseVo();
                paramScPurchaseVo.setStoreId(records.get(i).get(0).toString());
                paramScPurchaseVo.setScPurchaseFlag(Integer.valueOf(records.get(i).get(1).toString()));
                paramScPurchaseVo.setDeliveryWarehouseCode(records.get(i).get(2).toString());
                paramScPurchaseVos.add(paramScPurchaseVo);
            }

            return whiteListHelper.whiteListBatchImport(paramScPurchaseVos);
        }catch (ParseException e){
            log.error(e.getMessage(), e);
            rep.setSuccess(false);
            rep.setCode(CommonsEnum.RESPONSE_10004.getCode());
            rep.setErrorMessage("导入数据有误，请检查");
        }catch(NotOfficeXmlFileException e) {
            log.error(e.getMessage(), e);
            rep.setSuccess(false);
            rep.setCode(CommonsEnum.RESPONSE_10004.getCode());
            rep.setErrorMessage("文件格式错误");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            rep.setSuccess(false);
            rep.setCode(CommonsEnum.RESPONSE_10004.getCode());
            rep.setErrorMessage("导入模版不一致");
        }
        log.info("whiteListBatchImport end.");
        return rep;
    }

    @RequestMapping(value = "/whiteListBatchExport", method = RequestMethod.GET)
    public void whiteListBatchExport(HttpServletResponse response, QueryStoreScPurchaseVo queryStoreScPurchaseVo) {
        log.info("whiteListBatchExport start param{}", JSON.toJSONString(queryStoreScPurchaseVo));
        queryStoreScPurchaseVo.setPageNo(1);
        queryStoreScPurchaseVo.setPageSize(Integer.MAX_VALUE);
        QueryStoreScPurchaseDto queryStoreScPurchaseDto = BeanConvertUtils.convert(queryStoreScPurchaseVo, QueryStoreScPurchaseDto.class);
        Response<List<StoreScPurchaseDto>> storeScPurchaseResponse = organizationSCService.queryPageSCStorePurchase(queryStoreScPurchaseDto);
        try {
            if (storeScPurchaseResponse != null && storeScPurchaseResponse.isSuccess() && !CollectionUtils.isEmpty(storeScPurchaseResponse.getResultObject())) {
                List<StoreScPurchaseVo> list = BeanConvertUtils.convertList(storeScPurchaseResponse.getResultObject(),StoreScPurchaseVo.class);
                ExcelUtil2.listToExcel(list, response);
            } else {
                response.setHeader("Content-type", "text/html;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<script>alert('服务器错误')</script>");
                response.getWriter().flush();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("whiteListBatchExport end.");
    }

    @RequestMapping(value="/whiteListTemplete",method = RequestMethod.GET)
    public void whiteListTemplete (HttpServletResponse response){
        List<WhiteListTempleteVo> importExcelVos = new ArrayList<>();
        WhiteListTempleteVo whiteListTempleteVo = new WhiteListTempleteVo();
        importExcelVos.add(whiteListTempleteVo);
        try{
            ExcelUtil2.listToExcel2(importExcelVos, response);
        }catch (Exception e){
            logger.error("导出异常", e);
            try {
                response.setHeader("Content-type", "text/html;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<script>alert('服务器错误')</script>");
                response.getWriter().flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }

}
