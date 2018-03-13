package com.yatang.sc.inventory.dubboservice.flow;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.inventory.domain.ImAdjustmentItemPo;
import com.yatang.sc.inventory.dto.ItemInventoryQueryParamDto;
import com.yatang.sc.inventory.dto.ItemLocSohDto;
import com.yatang.sc.inventory.dto.im.ImAdjustmentDto;
import com.yatang.sc.inventory.dto.im.ImAdjustmentReceiptDto;
import com.yatang.sc.inventory.dubboservice.ImAdjustmentWriteDubboService;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import com.yatang.sc.kidd.dto.im.ImAdjustmentResultDto;
import com.yatang.sc.kidd.dto.im.InventoryQueryDto;
import com.yatang.sc.kidd.dto.im.WarehouseProductDto;
import com.yatang.sc.kidd.service.InventoryService;
import com.yatang.xc.mbd.biz.org.dto.BranchCompanyDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @描述:同步wms库存
 * @类名:InventorySynFlowServiceImpl
 * @作者: lvheping
 * @创建时间: 2017/9/5 20:11
 * @版本: v1.0
 */
@Slf4j
@Service("inventorySynFlowService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InventorySynFlowServiceImpl implements InventorySynFlowService {

    private final InventoryService kiddInventoryService;//调用第三方服务

    private final ItemLocInventoryDubboService itemLocInventoryDubboService;//查询仓库库存信息

    private final ProductScIndexDubboService indexDubboService;// 商品查询dubboservice（供应链）

    private final ImAdjustmentWriteDubboService imAdjustmentWriteDubboService;//库存调整新增服务

    private final OrganizationService organizationService;//主数据业务

    /**
     * 根据返回数据做相关的业务操作
     *
     * @param imAdjuestmentParamentDto
     * @return
     */
    @Override
    public Response<Void> adjustmentInventoryItem(InventoryQueryDto imAdjuestmentParamentDto) {
        log.info("调用查询际链仓库信息参数 imAdjuestmentParamentDto " + imAdjuestmentParamentDto);
        Response response = new Response();
        //根据子公司code查询子公司id
        if (imAdjuestmentParamentDto==null||imAdjuestmentParamentDto.getOwnerCode()==null){
            log.info("传入参数错误 imAdjuestmentParamentDto " + imAdjuestmentParamentDto);
        }
        Response<List<BranchCompanyDto>> byBranchCompanyIdAndName = organizationService.querySimpleByBranchCompanyIdAndName(imAdjuestmentParamentDto.getOwnerCode(), null);
        log.info("查询子公司信息byBranchCompanyIdAndName "+byBranchCompanyIdAndName);
        if (!byBranchCompanyIdAndName.isSuccess()){
            log.info("查询子公司信息错误");
            response.setErrorMessage(CommonsEnum.RESPONSE_20002.getName());
            response.setCode(CommonsEnum.RESPONSE_20002.getCode());
            response.setSuccess(false);
            return response;
        }
        List<BranchCompanyDto> resultObject1 = byBranchCompanyIdAndName.getResultObject();
        if (null==resultObject1||resultObject1.size()==0){
            log.info("查询子公司信息错误");
            response.setErrorMessage(CommonsEnum.RESPONSE_20002.getName());
            response.setCode(CommonsEnum.RESPONSE_20002.getCode());
            response.setSuccess(false);
            return response;
        }
        BranchCompanyDto branchCompanyDto = resultObject1.get(0);
        if (null==branchCompanyDto||null==branchCompanyDto.getId()){
            log.info("查询子公司信息错误");
            response.setErrorMessage(CommonsEnum.RESPONSE_20002.getName());
            response.setCode(CommonsEnum.RESPONSE_20002.getCode());
            response.setSuccess(false);
            return response;
        }


        Response<ImAdjustmentResultDto> imAdjustmentResultDtoResponse = kiddInventoryService.inventoryQuery(imAdjuestmentParamentDto);
        log.info("查询库存信息 imAdjustmentResultDtoResponse " + imAdjustmentResultDtoResponse);
        if (!imAdjustmentResultDtoResponse.isSuccess()) {
            response.setErrorMessage(CommonsEnum.RESPONSE_20006.getName());
            response.setCode(CommonsEnum.RESPONSE_20006.getCode());
            response.setSuccess(false);
            return response;
        }
        ImAdjustmentResultDto data = imAdjustmentResultDtoResponse.getResultObject();
        if (data == null) {
            response.setErrorMessage(CommonsEnum.RESPONSE_20006.getName());
            response.setCode(CommonsEnum.RESPONSE_20006.getCode());
            response.setSuccess(false);
            return response;
        }
        List<WarehouseProductDto> result = data.getResult();
        log.info("查询仓库信息" + result);
        if (result == null || result.size() == 0) {
            response.setErrorMessage(CommonsEnum.RESPONSE_20006.getName());
            response.setCode(CommonsEnum.RESPONSE_20006.getCode());
            response.setSuccess(false);
            return response;
        }
        Map<String,List> map1 = new HashMap();//调增
        Map<String,List> map2 = new HashMap();//调减
        for (WarehouseProductDto warehouseProductDto : result) {
            log.info("仓库返回信息"+warehouseProductDto+"查询主数据信息的商品code"+warehouseProductDto.getItemCode());
            if (warehouseProductDto.getWarehouseCode()==null){
                response.setErrorMessage(CommonsEnum.RESPONSE_20003.getName());
                response.setCode(CommonsEnum.RESPONSE_20003.getCode());
                response.setSuccess(false);
                return response;
            }
            Response<ProductIndexDto> productDtoResponse = indexDubboService.queryByProductCode(warehouseProductDto.getItemCode());
            if (!productDtoResponse.isSuccess()||productDtoResponse==null){
                response.setErrorMessage(CommonsEnum.RESPONSE_20005.getName());
                response.setCode(CommonsEnum.RESPONSE_20005.getCode());
                response.setSuccess(false);
                return response;
            }
            ProductIndexDto resultObject = productDtoResponse.getResultObject();
            log.info("根据商品code查询商品信息"+resultObject);
            if (resultObject.getId()==null){
                response.setErrorMessage(CommonsEnum.RESPONSE_20005.getName());
                response.setCode(CommonsEnum.RESPONSE_20005.getCode());
                response.setSuccess(false);
                return response;
            }
            ItemInventoryQueryParamDto itemInventoryQueryParamDto = new ItemInventoryQueryParamDto();
            itemInventoryQueryParamDto.setLogicWareHouseCode(warehouseProductDto.getWarehouseCode());
            itemInventoryQueryParamDto.setProductId(resultObject.getId());
            Response<List<ItemLocSohDto>> listResponse = itemLocInventoryDubboService.queryItemInventoryListByParam(itemInventoryQueryParamDto);
            log.info("查询仓库库存信息" + listResponse);
            if (!listResponse.isSuccess()) {
                log.info("查询仓库库存信息失败" + listResponse);
                return BeanConvertUtils.convert(listResponse, Response.class);
            }
            List<ItemLocSohDto> resultObject2 = listResponse.getResultObject();
            if (CollectionUtils.isEmpty(resultObject2)) {
                log.info("商品在该逻辑仓中不存在" + resultObject2);
                response.setErrorMessage(CommonsEnum.RESPONSE_20007.getName());
                response.setCode(CommonsEnum.RESPONSE_20007.getCode());
                response.setSuccess(false);
                return response;
            }
            ItemLocSohDto itemLocSohDto = resultObject2.get(0);
            Long stock= itemLocSohDto.getStockOnHand();
            Long actualQty = warehouseProductDto.getActualQty()==null?0L:warehouseProductDto.getActualQty().longValue();
            Long num = actualQty- stock;//调整数量
            if (num!=0){
                //需要生成调整库存
                ImAdjustmentItemPo imAdjustmentItemPo = new ImAdjustmentItemPo();
                imAdjustmentItemPo.setProductId(resultObject.getId());//商品id
                imAdjustmentItemPo.setProductCode(resultObject.getProductCode());//商品编码
                imAdjustmentItemPo.setProductName(resultObject.getSaleName());//商品名称
                imAdjustmentItemPo.setQuantity(num);//调整数量
                imAdjustmentItemPo.setProductDate(warehouseProductDto.getProductDate());//商品生产日期
                imAdjustmentItemPo.setProductDate(warehouseProductDto.getExpireDate());//商品过期日期
                imAdjustmentItemPo.setGroups(resultObject.getFirstLevelCategoryId());//部类
                imAdjustmentItemPo.setDept(resultObject.getSecondLevelCategoryId());//大类
                imAdjustmentItemPo.setClasss(resultObject.getThirdLevelCategoryId());//中类
                imAdjustmentItemPo.setSubclass(resultObject.getFourthCategoryId());//小类
                if (num>0){
                    //库存调增5
                    if (map1.get(warehouseProductDto.getWarehouseCode())!=null){
                        List list = map1.get(warehouseProductDto.getWarehouseCode());
                        list.add(imAdjustmentItemPo);
                    }else {
                        List list = new ArrayList();
                        list.add(imAdjustmentItemPo);
                       map1.put(warehouseProductDto.getWarehouseCode(),list);
                    }
                }else {
                    //库存调减6
                    if (map2.get(warehouseProductDto.getWarehouseCode())!=null){
                        List list = map2.get(warehouseProductDto.getWarehouseCode());
                        list.add(imAdjustmentItemPo);
                    }else {
                        List list = new ArrayList();
                        list.add(imAdjustmentItemPo);
                        map2.put(warehouseProductDto.getWarehouseCode(),list);
                    }
                }
            }

        }
        //调增库存调整单（按照不同逻辑仓生成库存调整单）
        if (map1.keySet()!=null&&map1.keySet().size()>0){
            for (String warehouseCode:map1.keySet()){
                ImAdjustmentReceiptDto imAdjustmentReceiptDto = new ImAdjustmentReceiptDto();//库存调整单
                ImAdjustmentDto imAdjustment = new ImAdjustmentDto();//库存调整单主表信息
                imAdjustment.setType(5);//类型库存调增
               // imAdjustment.setStatus(0);//状态（制单状态）
                imAdjustment.setAdjustmentTime(new Date());//调整日期（获取当前系统时间）
                imAdjustment.setWarehouseCode(warehouseCode);//逻辑仓库编码
                imAdjustment.setBranchCompanyId(branchCompanyDto.getId());//子公司id
                //imAdjustment.setCreateUserName("syn");//创建人
                imAdjustmentReceiptDto.setImAdjustment(imAdjustment);//设置主表
                imAdjustmentReceiptDto.setImAdjustmentItems(map1.get(warehouseCode));//设置商品信息表
                imAdjustmentWriteDubboService.addAdjustmentReceipt(imAdjustmentReceiptDto);
            }

        }
        //调减库存调整单（按照不同逻辑仓生成库存调整单）
        if (map1.keySet()!=null&&map2.keySet().size()>0){
            for (String warehouseCode:map2.keySet()){
                ImAdjustmentReceiptDto imAdjustmentReceiptDto = new ImAdjustmentReceiptDto();//库存调整单
                ImAdjustmentDto imAdjustment = new ImAdjustmentDto();//库存调整单主表信息
                imAdjustment.setType(6);//类型库存调减
               // imAdjustment.setStatus(0);//状态（制单状态）
                imAdjustment.setAdjustmentTime(new Date());//调整日期（获取当前系统时间）
                imAdjustment.setWarehouseCode(warehouseCode);//逻辑仓库编码
                imAdjustment.setBranchCompanyId(branchCompanyDto.getId());//子公司id
                imAdjustmentReceiptDto.setImAdjustment(imAdjustment);//设置主表
                imAdjustmentReceiptDto.setImAdjustmentItems(map2.get(warehouseCode));//设置商品信息表
                imAdjustmentWriteDubboService.addAdjustmentReceipt(imAdjustmentReceiptDto);
            }

        }
       /* imAdjuestmentParamentDto.setPageNo(data.getPageNo() + 1);
        adjustmentInventoryItem(imAdjuestmentParamentDto);*/

        return response;

    }

}
