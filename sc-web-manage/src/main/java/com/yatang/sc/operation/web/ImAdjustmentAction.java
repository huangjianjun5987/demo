package com.yatang.sc.operation.web;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.inventory.common.PageResult;
import com.yatang.sc.inventory.dto.im.ImAdjustmentQueryListDto;
import com.yatang.sc.inventory.dto.im.ImAdjustmentQueryParamDto;
import com.yatang.sc.inventory.dto.im.ImAdjustmentReceiptDto;
import com.yatang.sc.inventory.dubboservice.ImAdjustmentQueryDubboService;
import com.yatang.sc.inventory.dubboservice.ImAdjustmentWriteDubboService;
import com.yatang.sc.operation.vo.im.ImAdjustmentListExcelModel;
import com.yatang.sc.operation.vo.im.ImAdjustmentListExcelView;
import com.yatang.sc.operation.vo.im.ImAdjustmentQueryListVo;
import com.yatang.sc.operation.vo.im.ImAdjustmentQueryParamVo;
import com.yatang.sc.operation.vo.im.ImAdjustmentViewVo;
import com.yatang.sc.web.paramvalid.MessageConstantUtil;
import com.yatang.sc.operation.vo.im.ImAdjustmentReceiptVo;
import com.yatang.sc.validgroup.DefaultGroup;

import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.yatang.sc.inventory.common.Constants.CURRENT_USER;

/**
 * @描述: 库存调整Action类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/30 上午9:29
 * @版本: v1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/sc/imAdjustment")
public class ImAdjustmentAction {

    private final ImAdjustmentQueryDubboService queryDubboService;

    private final ImAdjustmentWriteDubboService writeDubboService;

    private final WarehouseLogicQueryDubboService warehouseLogicQueryDubboService;


    /**saveItemAd
     * 新增库存调整单
     *
     * @param imAdjustmentReceipt 库存调整单
     * @return
     */
    @RequestMapping(value = "/addAdjustmentReceipt", method = RequestMethod.POST)
    public Response<Void> addAdjustmentReceipt(@RequestBody @Validated({DefaultGroup.class}) ImAdjustmentReceiptVo imAdjustmentReceipt) {
        Response<Void> response = new Response<>();
        if (log.isInfoEnabled()) {
            log.info("action--addAdjustmentReceipt--新增库存调整单>>imAdjustmentReceipt:{}", imAdjustmentReceipt);
        }
//        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
//        if (loginInfoVO == null || loginInfoVO.getUserId() == null) {
//            response = new Response<>();
//            response.setCode(CommonsEnum.RESPONSE_401.getCode());
//            response.setSuccess(false);
//            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
//            return response;
//        }

        //vo2Dto
        imAdjustmentReceipt.getImAdjustment().setCreateUserId("1l");
        ImAdjustmentReceiptDto imAdjustmentReceiptDto = BeanConvertUtils.convert(imAdjustmentReceipt, ImAdjustmentReceiptDto.class);

        response= writeDubboService.addAdjustmentReceipt(imAdjustmentReceiptDto);
        //生成订单号
        //vo2po
        return response;


    }
    /**
     * 根据传入条件分页查询库存调整列表
     * @param imAdjustmentQueryParamVo
     * @return
     */
    @RequestMapping(value = "/queryListImAdjustment",method = RequestMethod.GET)
    public Response<PageResult<ImAdjustmentViewVo>> queryListImAdjustment(ImAdjustmentQueryParamVo imAdjustmentQueryParamVo, HttpSession session){
        log.info("分页查询库存调整列表 imAdjustmentQueryParamVo"+imAdjustmentQueryParamVo);
        //获取当前子公司信息信息
        LoginInfoVO attribute = (LoginInfoVO) session.getAttribute(CURRENT_USER);
        log.info("当前登录人信息 attribute "+attribute);
        if (attribute == null || attribute.getCompanyId() == null) {
            Response response = new Response();
            response.setCode(CommonsEnum.RESPONSE_401.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
            return response;
        }
        imAdjustmentQueryParamVo.setBranchCompanyId(attribute.getCompanyId());
        if (attribute.getLoginName()!=null&&attribute.getLoginName().equals("admin")){
            imAdjustmentQueryParamVo.setBranchCompanyId(null);
        }
        ImAdjustmentQueryParamDto convert = BeanConvertUtils.convert(imAdjustmentQueryParamVo, ImAdjustmentQueryParamDto.class);
        Response<PageResult<ImAdjustmentQueryListDto>> resultResponse = queryDubboService.queryListImAdjustment(convert);
        if (!resultResponse.isSuccess()){
            log.info("查询失败"+resultResponse);
            return BeanConvertUtils.convert(resultResponse,Response.class);
        }
        PageResult<ImAdjustmentQueryListDto> resultObject = resultResponse.getResultObject();
        if (resultObject==null||resultObject.getData()==null){
            log.info("查询失败"+resultResponse);
            return BeanConvertUtils.convert(resultResponse,Response.class);
        }
        List<ImAdjustmentQueryListDto> data = resultObject.getData();
        List<ImAdjustmentQueryListVo> imAdjustmentQueryListVos = BeanConvertUtils.convertList(data, ImAdjustmentQueryListVo.class);
        for (ImAdjustmentQueryListVo listVo:imAdjustmentQueryListVos){
            String warehouseCode = listVo.getWarehouseCode();
            log.info("查询逻辑仓库信息参数"+warehouseCode);
            Response<LogicWarehouseDto> logicWarehouseDtoResponse = warehouseLogicQueryDubboService.selectLogicWarehouseByPrimaryKey(warehouseCode);
            if (!logicWarehouseDtoResponse.isSuccess()){
                log.info("查询逻辑仓库信息失败"+logicWarehouseDtoResponse);
            }
            LogicWarehouseDto resultObject1 = logicWarehouseDtoResponse.getResultObject();
            if (resultObject1!=null){
                listVo.setWarehouseName(resultObject1.getWarehouseName());//设置仓库名称
            }else {

                log.info("查询逻辑仓库为空"+logicWarehouseDtoResponse);
            }

        }
        List<ImAdjustmentViewVo> imAdjustmentViewVos = BeanConvertUtils.convertList(imAdjustmentQueryListVos, ImAdjustmentViewVo.class);
        PageResult convert2 = BeanConvertUtils.convert(resultObject, PageResult.class);
        convert2.setData(imAdjustmentViewVos);
        Response convert1 = BeanConvertUtils.convert(resultResponse, Response.class);
        convert1.setResultObject(convert2);
        return convert1;

    }

    /**
     * 根据列表id查询库存调整单详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getImAdjustment",method = RequestMethod.GET)
    public Response<ImAdjustmentQueryListVo> getImAdjustment(@NotNull(message = MessageConstantUtil.NOT_EMPTY) Long id){
        log.info("查询详情参数 id"+id);
        Response<ImAdjustmentQueryListDto> imAdjustmentQueryListVoResponse = queryDubboService.getImAdjustment(id);
        if (!imAdjustmentQueryListVoResponse.isSuccess()){
            log.info("查询详情失败imAdjustmentQueryListVoResponse "+imAdjustmentQueryListVoResponse);
            return BeanConvertUtils.convert(imAdjustmentQueryListVoResponse,Response.class);
        }
        Response convert = BeanConvertUtils.convert(imAdjustmentQueryListVoResponse, Response.class);
        ImAdjustmentQueryListVo convert1 = BeanConvertUtils.convert(imAdjustmentQueryListVoResponse.getResultObject(), ImAdjustmentQueryListVo.class);
        String warehouseCode = convert1.getWarehouseCode();
        log.info("查询逻辑仓库信息参数"+warehouseCode);
        Response<LogicWarehouseDto> logicWarehouseDtoResponse = warehouseLogicQueryDubboService.selectLogicWarehouseByPrimaryKey(warehouseCode);
        if (!logicWarehouseDtoResponse.isSuccess()){
            log.info("查询逻辑仓库信息失败"+logicWarehouseDtoResponse);
        }
        LogicWarehouseDto resultObject1 = logicWarehouseDtoResponse.getResultObject();
        if (resultObject1!=null){
            convert1.setWarehouseName(resultObject1.getWarehouseName());//设置仓库名称
        }else {

            log.info("查询逻辑仓库为空"+logicWarehouseDtoResponse);
        }
        convert.setResultObject(convert1);
        return convert;
    }

    /**
     * 导出库存调整单列表
     * @param imAdjustmentQueryParamVo
     * @param session
     * @return
     */
    @RequestMapping(value = "/exportListImAdjustment", method = RequestMethod.GET)
    public ModelAndView exportListImAdjustment(ImAdjustmentQueryParamVo imAdjustmentQueryParamVo,HttpSession session) {
        log.info("导出库存列表参数 imAdjustmentQueryParamVo"+imAdjustmentQueryParamVo);
        //获取当前子公司信息信息
        LoginInfoVO attribute = (LoginInfoVO) session.getAttribute(CURRENT_USER);
        if (attribute == null || attribute.getCompanyId() == null) {
            log.info("获取当前用户信息失败"+attribute);
            throw new RuntimeException();
        }
        imAdjustmentQueryParamVo.setBranchCompanyId(attribute.getCompanyId());
        if (attribute.getLoginName()!=null&&attribute.getLoginName().equals("admin")){
            imAdjustmentQueryParamVo.setBranchCompanyId(null);
        }
        ImAdjustmentQueryParamDto convert = BeanConvertUtils.convert(imAdjustmentQueryParamVo, ImAdjustmentQueryParamDto.class);
        convert.setPageNum(1);
        convert.setPageSize(Integer.MAX_VALUE);
        Response<PageResult<ImAdjustmentQueryListDto>> resultResponse = queryDubboService.queryListImAdjustment(convert);
        if (!resultResponse.isSuccess()){
            log.info("查询失败"+resultResponse);
            throw new RuntimeException();
        }
        PageResult<ImAdjustmentQueryListDto> resultObject = resultResponse.getResultObject();
        if (resultObject==null){
            log.info("查询失败"+resultResponse);
            throw new RuntimeException();
        }
        List<ImAdjustmentQueryListDto> data = resultObject.getData();
        List<ImAdjustmentQueryListVo> imAdjustmentQueryListVos = BeanConvertUtils.convertList(data, ImAdjustmentQueryListVo.class);
        for (ImAdjustmentQueryListVo listVo:imAdjustmentQueryListVos){
            String warehouseCode = listVo.getWarehouseCode();
            log.info("查询逻辑仓库信息参数"+warehouseCode);
            Response<LogicWarehouseDto> logicWarehouseDtoResponse = warehouseLogicQueryDubboService.selectLogicWarehouseByPrimaryKey(warehouseCode);
            if (!logicWarehouseDtoResponse.isSuccess()){
                log.info("查询逻辑仓库信息失败"+logicWarehouseDtoResponse);
            }
            LogicWarehouseDto resultObject1 = logicWarehouseDtoResponse.getResultObject();
            if (resultObject1!=null){
                listVo.setWarehouseName(resultObject1.getWarehouseName());//设置仓库名称
            }else {
                log.info("查询逻辑仓库为空"+logicWarehouseDtoResponse);
            }

        }
        List<ImAdjustmentListExcelModel> models =
                ImAdjustmentListExcelModel.ofList(imAdjustmentQueryListVos);
        ImAdjustmentListExcelView excelView = new ImAdjustmentListExcelView(models);
        return new ModelAndView(excelView);
    }



}
