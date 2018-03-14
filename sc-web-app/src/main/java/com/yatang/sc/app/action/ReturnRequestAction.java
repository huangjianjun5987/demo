package com.yatang.sc.app.action;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.app.res.ActionResponse;
import com.yatang.sc.app.vo.orderreturned.ReturnRequestQueryParamVo;
import com.yatang.sc.app.vo.imgconfig.AppConfig;
import com.yatang.sc.app.vo.orderreturned.OrderPreReturnRequestReceiptVo;
import com.yatang.sc.app.vo.orderreturned.ReturnRequestReceiptVo;
import com.yatang.sc.app.web.paramvalid.MessageConstantUtil;
import com.yatang.sc.app.web.paramvalid.ParamValid;
import com.yatang.sc.app.web.validgroup.DefaultGroup;
import com.yatang.sc.app.web.validgroup.GroupOne;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.order.dubboservice.OrderRefundDubboService;
import com.yatang.sc.purchase.dto.OrderProductListDto;
import com.yatang.sc.purchase.dto.OrderRefundPriceDto;
import com.yatang.sc.purchase.dto.returned.OrderPreReturnRequestReceiptDto;
import com.yatang.sc.purchase.dto.returned.OrderPreReturnedCalculateAmountDto;
import com.yatang.sc.purchase.dto.returned.ReturnRequestDetailDto;
import com.yatang.sc.purchase.dto.returned.ReturnRequestListDto;
import com.yatang.sc.purchase.dto.returned.ReturnRequestOrderProductListDto;
import com.yatang.sc.purchase.dto.returned.ReturnRequestQueryParamDto;
import com.yatang.sc.purchase.dto.returned.ReturnRequestReceiptDto;
import com.yatang.sc.purchase.dubboservice.ReturnRequestQueryDubboService;
import com.yatang.sc.purchase.dubboservice.ReturnRequestWriteDubboService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @描述: 换退货action类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/17 14:01
 * @版本: v1.0
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping(value = "/sc/ReturnRequest")
public class ReturnRequestAction extends AppBaseAction {

    private final ReturnRequestWriteDubboService writeDubboService;

    private final ReturnRequestQueryDubboService queryDubboService;

    private final AppConfig appConfig;

    private final OrderRefundDubboService orderRefundDubboService;

    /**
     * 新建退换货单
     *
     * @return
     */
    @RequestMapping(value = "createOrderReturnedReceipt", method = RequestMethod.POST)
    public Response<String> createOrderReturnedReceipt(@RequestBody @Validated(DefaultGroup.class) ReturnRequestReceiptVo requestReceiptVo) {


        log.info( "action--createOrderReturnedReceipt>>新增退换货单:{}", JSON.toJSONString( requestReceiptVo ) );
        //vo2dto
        ReturnRequestReceiptDto receiptDto = BeanConvertUtils.convert( requestReceiptVo, ReturnRequestReceiptDto.class );
        return writeDubboService.createOrderReturnedReceipt( receiptDto );
    }


    /**
     * 新增拒收退货单
     *
     * @return
     */
    @RequestMapping(value = "createRejectOrderReturnedReceipt", method = RequestMethod.POST)
    public Response<String> createRejectOrderReturnedReceipt(@RequestBody @Validated(GroupOne.class) ReturnRequestReceiptVo requestReceiptVo) {

        log.info( "action--createRejectOrderReturnedReceipt>>新增拒收退货单:{}", JSON.toJSONString( requestReceiptVo ) );
        //vo2dto
        ReturnRequestReceiptDto receiptDto = BeanConvertUtils.convert( requestReceiptVo, ReturnRequestReceiptDto.class );
        return writeDubboService.createRejectOrderReturnedReceipt( receiptDto );

    }


    /**
     * @param requestQueryParamVo
     * @return
     * @Description: 移动端获取退换货商品列表
     * @author kangdong
     * @date 2017/10/24 14:01
     */
    @RequestMapping(value = "queryReturnRequestList", method = RequestMethod.POST)
    public Response<PageResult<ReturnRequestListDto>> queryReturnRequestList(@RequestBody ReturnRequestQueryParamVo requestQueryParamVo) {
        log.info( "action--queryReturnRequestList>>获取退换货商品列表:{}", JSON.toJSONString( requestQueryParamVo ) );
        String userId = getUserId();
        ReturnRequestQueryParamDto param = BeanConvertUtils.convert( requestQueryParamVo, ReturnRequestQueryParamDto.class );
        param.setProfileId( userId );
        Response<PageResult<ReturnRequestListDto>> response = ActionResponse.wrap( queryDubboService.queryReturnRequestList( param ) );
        PageResult<ReturnRequestListDto> pageResult = response.getResultObject();
        if (pageResult == null) {
            return response;
        }
        List<ReturnRequestListDto> returnRequestListVos = pageResult.getData();
        if(CollectionUtils.isEmpty(returnRequestListVos)){
            return response;
        }
        for (ReturnRequestListDto returnRequest : returnRequestListVos) {
            List<OrderProductListDto> orderProductListDtos = returnRequest.getProductList();
            if (CollectionUtils.isEmpty(orderProductListDtos)) {
                continue;
            }
            for (OrderProductListDto orderProductListDto : orderProductListDtos) {
                orderProductListDto.setIconUrl(appConfig.getAppImageUrl(orderProductListDto.getIconUrl()));
            }
        }
        return response;
    }

    /**
     * @param id
     * @return
     * @Description: 退换货单详情
     * @author kangdong
     * @date 2017/10/24 14:01
     */
    @RequestMapping(value = "returnRequestDetail", method = RequestMethod.GET)
    @ParamValid
    public Response<ReturnRequestDetailDto> returnRequestDetail(@RequestParam @NotBlank(message = MessageConstantUtil.NOT_EMPTY) String id) {
        log.info( "action--returnRequestDetail>>退换货单详情:{}", id );
        //先判断订单是不是这个user的  通过orderId找到userId
        String userId = getUserId();
        Response<ReturnRequestDetailDto> dtoResponse = queryDubboService.queryReturnRequestDetailById( id, userId );
        ReturnRequestDetailDto returnRequestDetailDto = dtoResponse.getResultObject();
        if (returnRequestDetailDto == null) {
            return dtoResponse;
        }
        List<ReturnRequestOrderProductListDto> lists = returnRequestDetailDto.getProductList();
        for(ReturnRequestOrderProductListDto list:lists) {
            list.setIconUrl(appConfig.getAppImageUrl( list.getIconUrl()));
        }
        return ActionResponse.wrap( dtoResponse );
    }


    /**
     * @param amount  总价
     * @param orderId
     * @return
     * @Description: 拒收
     * @author kangdong
     */
    @RequestMapping(value = "refundPrice", method = RequestMethod.GET)
    public Response<OrderRefundPriceDto> refundPrice(Double amount, String orderId) {
        log.info( "action--rejection>>拒收操作:{},{}", amount, orderId );
        Response<OrderRefundPriceDto> orderRefundPriceDtoResponse = orderRefundDubboService.refundPrice( amount, orderId );
        return orderRefundPriceDtoResponse;
    }


    /**
     * 生成预览金额
     *
     * @param receiptVo
     * @return
     */
    @RequestMapping(value = "calculatePreOrderReturnedAmount", method = RequestMethod.POST)
    Response<OrderPreReturnedCalculateAmountDto> calculatePreOrderReturnedAmount(@RequestBody @Valid OrderPreReturnRequestReceiptVo receiptVo) {
        log.info( "action--calculatePreOrderReturnedAmount>>接收参数{}", JSON.toJSONString( receiptVo ) );
        //vo2dto
        OrderPreReturnRequestReceiptDto receiptDto = BeanConvertUtils.convert( receiptVo, OrderPreReturnRequestReceiptDto.class );
        Response<OrderPreReturnedCalculateAmountDto> response = orderRefundDubboService.calculatePreOrderReturnedAmount( receiptDto );
        return response;
    }


}
