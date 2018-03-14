
package test.com.yatang.sc.purchase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.yatang.sc.order.states.OrderTypes;
import com.yatang.sc.purchase.dto.*;
import com.yatang.sc.purchase.dubboservice.PurchaseDubboService;
import com.yatang.sc.purchase.dubboservice.SplitOrderService;
import com.yatang.sc.purchase.enums.InvoiceType;
import com.yatang.sc.purchase.service.PurchaseHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * Created by qiugang on 7/10/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestPurcharseService {

    @Autowired
    PurchaseHelper purchaseHelper;

    @Autowired
    PurchaseDubboService purchaseDubboService;

    @Autowired
    SplitOrderService mSplitOrderService;
    @Test
    public void aimManyOrder(){
        for(int i=0;i<10;i++){
            testCommitOrder();
        }
    }

    @Test
    public void testUpdateInvoice(){

        String profileId = "pro3";
        Date nowTiem = new Date();

        InvoiceInfoDto invoiceInfoDto = new InvoiceInfoDto();

        invoiceInfoDto.setInvoiceId("14401");
        invoiceInfoDto.setInvoiceType(InvoiceType.valueAdded.toString());
        invoiceInfoDto.setInvoiceTitle("雅堂集团");
        invoiceInfoDto.setCompanyName("四川雅堂电子商务投资股份有限公司");
        invoiceInfoDto.setTaxpayerIdentificationNumber("NSRSBM");
        invoiceInfoDto.setRegisteredAddress("四川成都");
        invoiceInfoDto.setCompanyPhone("13452134646");
        invoiceInfoDto.setDepositBank("中国银行");
        invoiceInfoDto.setAccountNumber("123456");
        invoiceInfoDto.setEntryDatetime(nowTiem);
        invoiceInfoDto.setUpdateDatetime(nowTiem);

        try {
            purchaseDubboService.updateInvoiceInfo("900005", invoiceInfoDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCommitOrder() {
        String profileId = "pro3";
        Date nowTiem = new Date();

        /**
         * 支付方式
         */
        List<PaymentGroupDto> paymentGroupDtos = new ArrayList<PaymentGroupDto>();
        for (int l =0;l<3;l++){
            PaymentGroupDto paymentGroupDto = new PaymentGroupDto();
            paymentGroupDto.setId("paymentGroup1"+l);
            paymentGroupDto.setAmount(100d);
            paymentGroupDto.setCurrencyCode("RMB"+l);
            paymentGroupDto.setAmountAuthorized(100d);
            paymentGroupDto.setAmountCredited(100d);
            paymentGroupDto.setAmountDebited(100d);
            paymentGroupDto.setAmountCredited(100d);
            paymentGroupDto.setDebitStatus("debit"+l);
            paymentGroupDto.setType("javatype"+l);
            paymentGroupDto.setPaymentMethod("支付描述"+l);
            paymentGroupDto.setTransNum("TRANS01"+l);
            paymentGroupDto.setSpecialInstructions("特殊说明"+l);
            paymentGroupDto.setState((short) 0);
            paymentGroupDto.setStateDetail("细节"+l);
            paymentGroupDto.setPayDate(new Date());
            paymentGroupDto.setChannel("weixin");
            paymentGroupDtos.add(paymentGroupDto);
        }


        /**
         * 发票信息
         */
        InvoiceInfoDto invoiceInfoDto = new InvoiceInfoDto();
        invoiceInfoDto.setInvoiceId("invoice1");
        invoiceInfoDto.setInvoiceType(InvoiceType.valueAdded.toString());
        invoiceInfoDto.setInvoiceTitle("雅堂集团");
        invoiceInfoDto.setCompanyName("四川雅堂电子商务投资股份有限公司");
        invoiceInfoDto.setTaxpayerIdentificationNumber("NSRSBM");
        invoiceInfoDto.setRegisteredAddress("四川成都");
        invoiceInfoDto.setCompanyPhone("8888888");
        invoiceInfoDto.setDepositBank("中国银行");
        invoiceInfoDto.setAccountNumber("123456");
        invoiceInfoDto.setEntryDatetime(nowTiem);
        invoiceInfoDto.setUpdateDatetime(nowTiem);


        /**
         * 订单金额
         */
        OrderPriceInfoDto orderPriceInfoDto = new OrderPriceInfoDto();
        orderPriceInfoDto.setId("orderpirce1");
        orderPriceInfoDto.setRawSubtotal(120d);
        orderPriceInfoDto.setAmount(100d);
        orderPriceInfoDto.setCurrencyCode("RMB");
        orderPriceInfoDto.setDiscountAmount(10d);
        orderPriceInfoDto.setFinalReasonCode("终止原因");
        orderPriceInfoDto.setManualAdjustmentTotal(30d);
        orderPriceInfoDto.setShipping(10d);
        orderPriceInfoDto.setTotal(110d);
        List<PriceAdjustmentDto> adjustments = new ArrayList<PriceAdjustmentDto>();
        for (int i = 0; i < 2; i++) {
            PriceAdjustmentDto priceAdjustmentDto = new PriceAdjustmentDto();
            priceAdjustmentDto.setId("pa" + i);
            priceAdjustmentDto.setAdjustment(10d);
            priceAdjustmentDto.setManualPricingAdjustment("man" + i);
            priceAdjustmentDto.setPricingModel("pricing");
            priceAdjustmentDto.setQuantityAdjusted(2l);
            priceAdjustmentDto.setTotalAdjustment(10d);
            adjustments.add(priceAdjustmentDto);
        }
        orderPriceInfoDto.setAdjustments(adjustments);


        /**
         * 购物车商品
         */
        List<CommerceItemDto> items = new ArrayList<CommerceItemDto>();
        for (int j = 0; j < 3; j++) {
            CommerceItemDto commerceItemDto = new CommerceItemDto();
            commerceItemDto.setId("ci" + j);
            commerceItemDto.setProductId("xprod346468");
            commerceItemDto.setProductName("名称" + j);
            commerceItemDto.setProductImg("图片地址" + j);
            commerceItemDto.setSkuId("xprod346468");
            commerceItemDto.setCatalogId("catalogid" + j);
            commerceItemDto.setQuantity(2l);
            commerceItemDto.setState((short) 0);
            commerceItemDto.setStateDetail("状态描述");
            commerceItemDto.setReturnQuantity(2l);
            commerceItemDto.setSelected(true);
            commerceItemDto.setType("javatype");
            ItemPriceInfoDto itemPrice = new ItemPriceInfoDto();
            itemPrice.setId("ip" + j);
            itemPrice.setAmount(100d);
            itemPrice.setCurrencyCode("RMB");
            itemPrice.setFinalReasonCode("终止原因");
            itemPrice.setListPrice(80d);
            itemPrice.setOrderDiscountShare(20d);
            itemPrice.setQuantityDiscounted(2l);
            itemPrice.setRawTotalPrice(150d);
            itemPrice.setSalePrice(100d);

            List<PriceAdjustmentDto> adjustmentDtos = new ArrayList<PriceAdjustmentDto>();
            for (int k = 0; k < 3; k++) {
                PriceAdjustmentDto priceAdjustmentDto = new PriceAdjustmentDto();
                priceAdjustmentDto.setId("pa" + k);
                priceAdjustmentDto.setAdjustment(20d);
                priceAdjustmentDto.setAdjustmentDescription("价格变化描述");
                priceAdjustmentDto.setManualPricingAdjustment("手动调价对象");
                priceAdjustmentDto.setPricingModel("关联促销");
                priceAdjustmentDto.setQuantityAdjusted(2l);
                priceAdjustmentDto.setTotalAdjustment(20d);
                adjustmentDtos.add(priceAdjustmentDto);
            }
            itemPrice.setAdjustments(adjustmentDtos);
            commerceItemDto.setItemPrice(itemPrice);
            items.add(commerceItemDto);
        }

        ShippingGroupDto shippingGroupDto = new ShippingGroupDto();
        shippingGroupDto.setId("spg1");
        shippingGroupDto.setActualShipDate(nowTiem);
        shippingGroupDto.setDescription("EMS");
        shippingGroupDto.setShipOnDate(nowTiem);
        shippingGroupDto.setType("javatype");
        shippingGroupDto.setShippingMethod("邮政");
        shippingGroupDto.setSpecialInstructions("特殊说明");
        shippingGroupDto.setStateDetail("配送方式详情");
        shippingGroupDto.setSubmitDate(nowTiem);
        shippingGroupDto.setProvince("四川");
        shippingGroupDto.setCity("成都");
        shippingGroupDto.setDistrict("天府新区");
        shippingGroupDto.setState((short)1);
        shippingGroupDto.setDetailAddress("菁蓉中心d区a3栋雅堂集团");
        shippingGroupDto.setPostcode("610200");
        shippingGroupDto.setConsigneeName("本人");
        shippingGroupDto.setTelephone("8888888");
        shippingGroupDto.setCellphone("18788888888");
        ShippingPriceInfoDto shippingPriceInfoDto = new ShippingPriceInfoDto();
        shippingPriceInfoDto.setId("spi1");
        shippingPriceInfoDto.setAmount(100d);
        shippingPriceInfoDto.setCurrencyCode("RMB");
        shippingPriceInfoDto.setFinalReasonCode("终止原因");
        shippingPriceInfoDto.setRawShipping(110d);
        List<PriceAdjustmentDto> adjustmentsSP = new ArrayList<PriceAdjustmentDto>();
        for (int l = 0; l < 3; l++) {
            PriceAdjustmentDto priceAdjustmentDto = new PriceAdjustmentDto();
            priceAdjustmentDto.setId("pas" + l);
            priceAdjustmentDto.setAdjustment(21d);
            priceAdjustmentDto.setAdjustmentDescription("价格变化描述");
            priceAdjustmentDto.setManualPricingAdjustment("手动调价对象");
            priceAdjustmentDto.setPricingModel("关联促销");
            priceAdjustmentDto.setQuantityAdjusted(3l);
            priceAdjustmentDto.setTotalAdjustment(21d);
            adjustmentsSP.add(priceAdjustmentDto);
        }
        shippingPriceInfoDto.setAdjustments(adjustmentsSP);
        shippingGroupDto.setShippingPriceInfoDto(shippingPriceInfoDto);


        ShoppingCart sp = new ShoppingCart();
        sp.setBranchCompanyId("b1");
        sp.setStoreId("s1");
        sp.setUserId("prod1");
        OrderDto dto = new OrderDto();
        dto.setProfileId(profileId);
        dto.setBranchCompanyId("b1");
        dto.setFranchiseeId("fh1");
        dto.setFranchiseeStoreId("jm1");
        dto.setCreatedByOrderId("createbyorderid");
        dto.setOrderType(OrderTypes.NORMAL_SALE);
        dto.setState((short) 1);
        dto.setStateDetail("购物车描述");
        dto.setSubmitTime(nowTiem);
        dto.setCreationTime(nowTiem);
        dto.setCompletedTime(nowTiem);
        dto.setSalesChannel("平台");
        dto.setAgentId("a_id");
        dto.setSiteId("s_id");
        dto.setDescription("描述");
        dto.setVersion("20");
        dto.setPriceInfoDto(orderPriceInfoDto);
        dto.setItems(items);
        dto.setInvoiceInfoDto(invoiceInfoDto);
        dto.setShippingGroupDto(shippingGroupDto);
        dto.setPaymentGroupDtos(paymentGroupDtos);
        sp.setCurrentOrder(dto);
        try {
            purchaseHelper.saveShoppingCart(sp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testAddItemToShoppingCart(){
        purchaseDubboService.addItemToShoppingCart("",null);
    }

    @Test
    public void directCommitOrder(){

        DirectCommitOrderDto directCommitOrderDto = new DirectCommitOrderDto();
        directCommitOrderDto.setStoreId("A000044");
        List<DirectStoreCommerItemDto> directStoreCommerItemDtoList = new ArrayList<DirectStoreCommerItemDto>();
        DirectStoreCommerItemDto directStoreCommerItemDto = new DirectStoreCommerItemDto();
        directStoreCommerItemDto.setQuantity(1);
        directStoreCommerItemDto.setProductId("xprod324993");
        directStoreCommerItemDtoList.add(directStoreCommerItemDto);
        directCommitOrderDto.setDirectStoreCommerItemList(directStoreCommerItemDtoList);
        Response<String> rep = purchaseDubboService.directCommitOrder(directCommitOrderDto);
        System.out.println(JSONObject.toJSON(rep));

    }

    @Test
    public void testAddThridOrder(){
        String profileId = "pro3";
        Date nowTime = new Date();

        /**
         * 支付方式
         */
        List<PaymentGroupDto> paymentGroupDtos = new ArrayList<PaymentGroupDto>();
        PaymentGroupDto paymentGroupDto = new PaymentGroupDto();
        paymentGroupDto.setAmount(100d);
        paymentGroupDto.setCurrencyCode("RMB");
        paymentGroupDto.setAmountAuthorized(100d);
        paymentGroupDto.setAmountCredited(100d);
        paymentGroupDto.setAmountDebited(100d);
        paymentGroupDto.setAmountCredited(100d);
        paymentGroupDto.setDebitStatus("debit");
        paymentGroupDto.setType("javatype");
        paymentGroupDto.setPaymentMethod("支付描述");
        paymentGroupDto.setTransNum("TRANS01");
        paymentGroupDto.setSpecialInstructions("特殊说明");
        paymentGroupDto.setState((short) 0);
        paymentGroupDto.setStateDetail("细节");
        paymentGroupDto.setPayDate(new Date());
        paymentGroupDto.setChannel("weixin");
        paymentGroupDtos.add(paymentGroupDto);

        /**
         * 发票信息
         */
        InvoiceInfoDto invoiceInfoDto = new InvoiceInfoDto();
        invoiceInfoDto.setInvoiceId("invoice1");
        invoiceInfoDto.setInvoiceType(InvoiceType.valueAdded.toString());
        invoiceInfoDto.setInvoiceTitle("雅堂集团");
        invoiceInfoDto.setCompanyName("四川雅堂电子商务投资股份有限公司");
        invoiceInfoDto.setTaxpayerIdentificationNumber("NSRSBM");
        invoiceInfoDto.setRegisteredAddress("四川成都");
        invoiceInfoDto.setCompanyPhone("8888888");
        invoiceInfoDto.setDepositBank("中国银行");
        invoiceInfoDto.setAccountNumber("123456");
        invoiceInfoDto.setEntryDatetime(nowTime);
        invoiceInfoDto.setUpdateDatetime(nowTime);

        /**
         * 订单金额
         */
        OrderPriceInfoDto orderPriceInfoDto = new OrderPriceInfoDto();
        orderPriceInfoDto.setRawSubtotal(120d);
        orderPriceInfoDto.setAmount(100d);
        orderPriceInfoDto.setCurrencyCode("RMB");
        orderPriceInfoDto.setDiscountAmount(10d);
        orderPriceInfoDto.setFinalReasonCode("终止原因");
        orderPriceInfoDto.setManualAdjustmentTotal(30d);
        orderPriceInfoDto.setShipping(10d);
        orderPriceInfoDto.setTotal(110d);
        List<PriceAdjustmentDto> adjustments = new ArrayList<PriceAdjustmentDto>();
        for (int i = 0; i < 2; i++) {
            PriceAdjustmentDto priceAdjustmentDto = new PriceAdjustmentDto();
            priceAdjustmentDto.setAdjustment(10d);
            priceAdjustmentDto.setManualPricingAdjustment("man" + i);
            priceAdjustmentDto.setPricingModel("pricing");
            priceAdjustmentDto.setQuantityAdjusted(2l);
            priceAdjustmentDto.setTotalAdjustment(10d);
            adjustments.add(priceAdjustmentDto);
        }
        orderPriceInfoDto.setAdjustments(adjustments);


        /**
         * 购物车商品
         */
        List<CommerceItemDto> items = new ArrayList<CommerceItemDto>();
        for (int j = 0; j < 2; j++) {
            CommerceItemDto commerceItemDto = new CommerceItemDto();
            commerceItemDto.setProductId("60000335741");
            commerceItemDto.setProductName("名称" + j);
            commerceItemDto.setProductImg("图片地址" + j);
            commerceItemDto.setSkuId("60000335741");
            commerceItemDto.setCatalogId("catalogid" + j);
            commerceItemDto.setQuantity(2l);
            commerceItemDto.setState((short) 0);
            commerceItemDto.setStateDetail("状态描述");
            commerceItemDto.setReturnQuantity(2l);
            commerceItemDto.setSelected(true);
            commerceItemDto.setType("javatype");
            ItemPriceInfoDto itemPrice = new ItemPriceInfoDto();
            itemPrice.setAmount(100d);
            itemPrice.setCurrencyCode("RMB");
            itemPrice.setFinalReasonCode("终止原因");
            itemPrice.setListPrice(80d);
            itemPrice.setOrderDiscountShare(20d);
            itemPrice.setQuantityDiscounted(2l);
            itemPrice.setRawTotalPrice(150d);
            itemPrice.setSalePrice(100d);

            List<PriceAdjustmentDto> adjustmentDtos = new ArrayList<PriceAdjustmentDto>();
            for (int k = 0; k < 3; k++) {
                PriceAdjustmentDto priceAdjustmentDto = new PriceAdjustmentDto();
                priceAdjustmentDto.setAdjustment(20d);
                priceAdjustmentDto.setAdjustmentDescription("价格变化描述");
                priceAdjustmentDto.setManualPricingAdjustment("手动调价对象");
                priceAdjustmentDto.setPricingModel("关联促销");
                priceAdjustmentDto.setQuantityAdjusted(2l);
                priceAdjustmentDto.setTotalAdjustment(20d);
                adjustmentDtos.add(priceAdjustmentDto);
            }
            itemPrice.setAdjustments(adjustmentDtos);
            commerceItemDto.setItemPrice(itemPrice);
            items.add(commerceItemDto);
        }

        ShippingGroupDto shippingGroupDto = new ShippingGroupDto();
        shippingGroupDto.setActualShipDate(nowTime);
        shippingGroupDto.setDescription("EMS");
        shippingGroupDto.setShipOnDate(nowTime);
        shippingGroupDto.setType("javatype");
        shippingGroupDto.setShippingMethod("邮政");
        shippingGroupDto.setSpecialInstructions("特殊说明");
        shippingGroupDto.setStateDetail("配送方式详情");
        shippingGroupDto.setSubmitDate(nowTime);
        shippingGroupDto.setProvince("四川");
        shippingGroupDto.setCity("成都");
        shippingGroupDto.setDistrict("天府新区");
        shippingGroupDto.setState((short)0);
        shippingGroupDto.setDetailAddress("菁蓉中心d区a3栋雅堂集团");
        shippingGroupDto.setPostcode("610200");
        shippingGroupDto.setConsigneeName("本人");
        shippingGroupDto.setTelephone("8888888");
        shippingGroupDto.setCellphone("18788888888");
        ShippingPriceInfoDto shippingPriceInfoDto = new ShippingPriceInfoDto();
        shippingPriceInfoDto.setAmount(100d);
        shippingPriceInfoDto.setCurrencyCode("RMB");
        shippingPriceInfoDto.setFinalReasonCode("终止原因");
        shippingPriceInfoDto.setRawShipping(110d);
        List<PriceAdjustmentDto> adjustmentsSP = new ArrayList<PriceAdjustmentDto>();
        for (int l = 0; l < 3; l++) {
            PriceAdjustmentDto priceAdjustmentDto = new PriceAdjustmentDto();
            priceAdjustmentDto.setAdjustment(21d);
            priceAdjustmentDto.setAdjustmentDescription("价格变化描述");
            priceAdjustmentDto.setManualPricingAdjustment("手动调价对象");
            priceAdjustmentDto.setPricingModel("关联促销");
            priceAdjustmentDto.setQuantityAdjusted(3l);
            priceAdjustmentDto.setTotalAdjustment(21d);
            adjustmentsSP.add(priceAdjustmentDto);
        }
        shippingPriceInfoDto.setAdjustments(adjustmentsSP);
        shippingGroupDto.setShippingPriceInfoDto(shippingPriceInfoDto);


        OrderDto dto = new OrderDto();
        dto.setProfileId(profileId);
        dto.setBranchCompanyId("b1");
        dto.setFranchiseeId("jms_000521");
        dto.setOrderType(OrderTypes.ONLINE_RETAILERS_SALE);
        dto.setState((short) 0);
        dto.setStateDetail("购物车描述");
        dto.setSubmitTime(nowTime);
        dto.setCreationTime(nowTime);
        dto.setCompletedTime(null);
        dto.setSalesChannel("平台");
        dto.setAgentId("a_id");
        dto.setSiteId("s_id");
        dto.setDescription("描述");
        dto.setVersion("20");
        dto.setPriceInfoDto(orderPriceInfoDto);
        dto.setItems(items);
        dto.setInvoiceInfoDto(invoiceInfoDto);
        dto.setShippingGroupDto(shippingGroupDto);
        dto.setPaymentGroupDtos(paymentGroupDtos);
        try {
            purchaseHelper.saveThirdOrder(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testBuyItemsAgain(){
        //Response<Boolean> reponse = purchaseDubboService.buyItemsAgain("100042017122800002");
        //System.out.println(JSON.toJSONString(reponse));
    }

    @Test
    public void testSplitOrderByASN() {
        try {
            mSplitOrderService.splitOrderByASN("10000201801220000601", 220 + "");
        } catch (Exception pE) {
            pE.printStackTrace();
        }
    }
}

