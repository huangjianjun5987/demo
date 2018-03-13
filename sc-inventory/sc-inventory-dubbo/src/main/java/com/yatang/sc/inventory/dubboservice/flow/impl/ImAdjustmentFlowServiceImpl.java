package com.yatang.sc.inventory.dubboservice.flow.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.inventory.domain.ImAdjustmentReceiptPo;
import com.yatang.sc.inventory.dto.im.ImAdjustmentDto;
import com.yatang.sc.inventory.dto.im.ImAdjustmentItemDto;
import com.yatang.sc.inventory.dto.im.ImAdjustmentReceiptDto;
import com.yatang.sc.inventory.dto.im.KiddImAdjustmentDataDto;
import com.yatang.sc.inventory.dto.im.KiddImAdjustmentItemDto;
import com.yatang.sc.inventory.dto.im.KiddImAdjustmentReceiptDto;
import com.yatang.sc.inventory.dubboservice.flow.ImAdjustmentFlowService;
import com.yatang.sc.inventory.enums.GLinkReceiptTypeConstant;
import com.yatang.sc.inventory.service.ImAdjustmentService;
import com.yatang.sc.inventory.service.ItemLocSohService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 库存调整flow服务接口实现
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/1 下午4:00
 * @版本: v1.0
 */


@Service("imAdjustmentFlowService")
public class ImAdjustmentFlowServiceImpl implements ImAdjustmentFlowService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductScIndexDubboService indexDubboService;//商品查询
    @Autowired
    private ImAdjustmentService imAdjustmentService;//库存调整服务
    @Autowired
    private WarehouseLogicQueryDubboService warehouseLogicQueryDubboService;//逻辑仓库
    @Autowired
    private ItemLocSohService itemLocSohService;//库存查询

    @Override
    public Response<Void> adjustInventoryItem(ImAdjustmentReceiptDto receiptDto) {

        Response<Void> response = new Response<>();
        try {
            log.info("flow---adjustInventoryItem--调整库存,imAdjustmentReceiptDto:{}", receiptDto);
            //dto2po
            //商品
            ImAdjustmentReceiptPo receiptPo = BeanConvertUtils.convert(receiptDto, ImAdjustmentReceiptPo.class);
            imAdjustmentService.adjustInventoryItem(receiptPo);
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
    public Response<ImAdjustmentReceiptDto> adjustImAdjustmentReceipt(KiddImAdjustmentReceiptDto kiddImAdjustmentReceipt) {
        log.info("flow---adjustImAdjustmentReceipt--数据转换,kiddImAdjustmentReceipt:{}", JSON.toJSONString(kiddImAdjustmentReceipt));
        Response<ImAdjustmentReceiptDto> response = new Response<>();

        try {
            //数据赋值
            KiddImAdjustmentDataDto data = kiddImAdjustmentReceipt.getData();
            if (null == data) {
                log.error("flow---adjustImAdjustmentReceipt--数据转换,获取仓库数据order数据为空");
                response.setCode(CommonsEnum.RESPONSE_20009.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_20009.getName());
                response.setSuccess(false);
                return response;
            }
            //1.设置调整单主表数据
            // 1.获取库存调整单类型
            //类型:0:物流丢失（WLDS）、1:仓库报溢（CKBY）、2:仓库报损（CKBS）、3:业务调增（YWTZ）、4:业务调减（YWTJ）、5:仓库同步调增（CKTBZ）、6:仓库同步调减（CKTBJ）
            String remark = data.getRemark();
            Integer gLinkReceiptType;
            switch (remark) {
                case GLinkReceiptTypeConstant.RECEIPT_TYPE_WLDS://物流丢失
                    gLinkReceiptType = 0;
                    break;
                case GLinkReceiptTypeConstant.RECEIPT_TYPE_CKBY://仓库报溢
                    gLinkReceiptType = 1;
                    break;
                case GLinkReceiptTypeConstant.RECEIPT_TYPE_CKBS://仓库报损
                    gLinkReceiptType = 2;
                    break;
                case GLinkReceiptTypeConstant.RECEIPT_TYPE_YWTZ://业务调增
                    gLinkReceiptType = 3;
                    break;
                case GLinkReceiptTypeConstant.RECEIPT_TYPE_YWTJ://业务调减
                    gLinkReceiptType = 4;
                    break;
                case GLinkReceiptTypeConstant.RECEIPT_TYPE_CKTBZ://仓库同步调增
                    gLinkReceiptType = 5;
                    break;
                case GLinkReceiptTypeConstant.RECEIPT_TYPE_CKTBJ://仓库同步调减
                    gLinkReceiptType = 6;
                    break;
                default:
                    log.error("flow---adjustImAdjustmentReceipt--际链仓库调整单类型失败,当前调整类型={}", remark);
                    response.setCode(CommonsEnum.RESPONSE_20008.getCode());
                    response.setErrorMessage(CommonsEnum.RESPONSE_20008.getName() + ",传递的调整类型为:" + remark);
                    response.setSuccess(false);
                    return response;
            }
            //主表
            ImAdjustmentDto imAdjustmentDto = new ImAdjustmentDto();
            //调整单类型转换设值
            imAdjustmentDto.setType(gLinkReceiptType);
            //仓库编码
            String warehouseCode = data.getWarehouseCode();
            //外部单据号(盘点单编码) 统一用checkOrderCode
            String outBizCode = data.getOutBizCode();


            //为空
            if (StringUtils.isBlank(warehouseCode)) {
                log.error("flow---adjustImAdjustmentReceipt--数据转换,获取仓库>>仓库编码数据为空");
                response.setCode(CommonsEnum.RESPONSE_20003.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_20003.getName());
                response.setSuccess(false);
                return response;
            } else {//本地查询
                Response<LogicWarehouseDto> logicWarehouseDtoResponse = warehouseLogicQueryDubboService.selectLogicWarehouseByPrimaryKey(warehouseCode);
                log.info("flow---adjustImAdjustmentReceipt-->根据仓库编码:{},查询结果={}", warehouseCode, JSON.toJSONString(logicWarehouseDtoResponse));
                if (!logicWarehouseDtoResponse.isSuccess()) {
                    return BeanConvertUtils.convert(logicWarehouseDtoResponse, Response.class);
                }
                LogicWarehouseDto warehouseDto = logicWarehouseDtoResponse.getResultObject();
                if (null == warehouseDto) {//数据不存在
                    log.error("flow---adjustImAdjustmentReceipt-->根据仓库编码:{},查询逻辑仓库失败", warehouseCode);
                    response.setCode(CommonsEnum.RESPONSE_20003.getCode());
                    response.setErrorMessage(CommonsEnum.RESPONSE_20003.getName());
                    response.setSuccess(false);
                    return response;
                }
                imAdjustmentDto.setBranchCompanyId(warehouseDto.getBranchCompanyId());//逻辑仓库所属子公司编号
            }
            if (StringUtils.isBlank(outBizCode)) {
                log.error("flow---adjustImAdjustmentReceipt--数据转换,获取数据>>外部单据号数据为空");
                response.setCode(CommonsEnum.RESPONSE_20004.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_20004.getName());
                response.setSuccess(false);
                return response;
            }
            imAdjustmentDto.setWarehouseCode(warehouseCode);//仓库编号
            imAdjustmentDto.setExternalBillNo(outBizCode);
            imAdjustmentDto.setAdjustmentTime(data.getCheckTime());//调整单时间

            //2.商品校验
            List<KiddImAdjustmentItemDto> items = data.getItems();//
            List<ImAdjustmentItemDto> imAdjustmentItemDtos = new ArrayList<>();
            if (0 == items.size()) {
                log.error("flow---adjustImAdjustmentReceipt--数据转换,获取仓库数据商品数据为空");
                response.setCode(CommonsEnum.RESPONSE_20005.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_20005.getName());
                response.setSuccess(false);
                return response;
            }
            for (KiddImAdjustmentItemDto item : items) {//商品遍历转换校验

                ImAdjustmentItemDto imAdjustmentItem = new ImAdjustmentItemDto();
                String itemCode = item.getItemCode();//商品编号
                Integer quantity = item.getQuantity();//盘盈盘亏商品变化量 盘盈为正数，盘亏为负数
                if (StringUtils.isEmpty(itemCode)) {//为空
                    log.error("flow---adjustImAdjustmentReceipt--数据转换,获取数据>>商品编号数据为空");
                    response.setCode(CommonsEnum.RESPONSE_20010.getCode());
                    response.setErrorMessage(CommonsEnum.RESPONSE_20010.getName());
                    response.setSuccess(false);
                    return response;
                }
                if (0 == quantity) {//为空
                    log.error("flow---adjustImAdjustmentReceipt--数据转换,获取数据>>商品编号为:{}库存调整数量为0", itemCode);
                    response.setCode(CommonsEnum.RESPONSE_20011.getCode());
                    response.setErrorMessage("商品编号为:" + itemCode + CommonsEnum.RESPONSE_20011.getName());
                    response.setSuccess(false);
                    return response;
                }
                //拼接数据际链
                //查询商品的分类情况
                Response<ProductIndexDto> productIndexDtoResponse = indexDubboService.queryByProductCode(itemCode);
                log.info("flow---adjustImAdjustmentReceipt--调整库存--调用indexDubboService根据商品编码:{}查询返回结果={}", itemCode, JSON.toJSONString(productIndexDtoResponse));
                if (!productIndexDtoResponse.isSuccess()) {
                    response.setCode(CommonsEnum.RESPONSE_20005.getCode());
                    response.setErrorMessage("商品编号为" + itemCode + CommonsEnum.RESPONSE_20005.getName());
                    response.setSuccess(false);
                    return response;
                }
                ProductIndexDto productIndexDto = productIndexDtoResponse.getResultObject();
                if (null == productIndexDtoResponse.getResultObject()) {
                    log.error("flow---adjustImAdjustmentReceipt--调整库存>>商品编码为:{},查询失败={}", itemCode, JSON.toJSONString(productIndexDto));
                    response.setCode(CommonsEnum.RESPONSE_20005.getCode());
                    response.setErrorMessage("商品编号为" + itemCode + CommonsEnum.RESPONSE_20005.getName());
                    response.setSuccess(false);
                    return response;
                }
                //从商品获取对应的属性
                imAdjustmentItem.setGroups(productIndexDto.getFirstLevelCategoryId());//部类
                imAdjustmentItem.setDept(productIndexDto.getSecondLevelCategoryId());//大类
                imAdjustmentItem.setClasss(productIndexDto.getThirdLevelCategoryId());//中类
                imAdjustmentItem.setSubclass(productIndexDto.getFourthCategoryId());//小类
                imAdjustmentItem.setProductId(productIndexDto.getId());//商品id
                imAdjustmentItem.setProductName(productIndexDto.getSaleName());//商品名称
                imAdjustmentItem.setProductCode(productIndexDto.getProductCode());//商品编号
                imAdjustmentItem.setQuantity(Long.valueOf(item.getQuantity()));//盘盈盘亏商品变化量 盘盈为正数，盘亏为负数
                //际链获取数据
                imAdjustmentItem.setProductDate(item.getProductDate());//商品生产日期
                imAdjustmentItem.setExpireDate(item.getExpireDate());//商品过期日期
                imAdjustmentItemDtos.add(imAdjustmentItem);
            }

            ImAdjustmentReceiptDto receiptDto = new ImAdjustmentReceiptDto();
            receiptDto.setImAdjustment(imAdjustmentDto);
            receiptDto.setImAdjustmentItems(imAdjustmentItemDtos);
            response.setResultObject(receiptDto);
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

}
