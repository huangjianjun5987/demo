package test.com.yatang.sc.order;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.busi.common.resp.Response;
import com.yatang.sc.dto.FranchiseeConditionDto;
import com.yatang.sc.dto.FranchiseeSettlementDto;
import com.yatang.sc.dto.QueryParticipateDataDto;
import com.yatang.sc.dto.WebShippingGroupDto;
import com.yatang.sc.order.dubboservice.FranchiseeSimpleQueryDubboService;
import com.yatang.sc.order.dubboservice.OrderPaymentDubboService;
import com.yatang.sc.order.dubboservice.PromotionDubboService;
import com.yatang.sc.order.dubboservice.WebOrderDubboService;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.order.service.PromotionService;
import com.yatang.sc.purchase.dto.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class TestWebQueryOrderService {

    @Autowired
    private WebOrderDubboService webOrderDubboService;

    @Autowired
    PromotionDubboService promotionDubboService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private FranchiseeSimpleQueryDubboService franchiseeSimpleQueryDubboService;



    @Test
    public void queryOrder() throws ParseException {
        File file = new File("") ;
        //FileUtils.readFileToString(file);


        SimpleDateFormat sb = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        WebQueryOrderConditionDto conditionDto = new WebQueryOrderConditionDto();
        conditionDto.setPage(1);
        conditionDto.setPageSize(10);
//        conditionDto.setSubmitStartTime(sb.parse("2017-08-04 18:41:13"));
//        conditionDto.setSubmitEndTime(sb.parse("2017-08-04 18:46:11"));
//        List<String> ids = new ArrayList<String>();
//        ids.add("10000");
//        ids.add("10001");
        conditionDto.setContainParent(0);
//        conditionDto.setBranchCompanyIds(ids);
        Response<PageResult<WebQueryOrderDto>> rep = webOrderDubboService.queryOrder(conditionDto);
        System.out.println(JSONObject.toJSONString(rep, SerializerFeature.WriteMapNullValue));

    }

    @Test
    public void deliverGoods(){

        String orderId = "11479";
        String shippingMethod = "顺风快递";
        String shippingNo = "mhd324987234";
        String userId = "pro1";
//        webOrderDubboService.deliverGoods(orderId,shippingMethod,shippingNo,userId);
    }

    @Test
    public void orderDescription(){
        String orderId = "11479";
        String description = "这个订单有点叼！";
        String userId = "pro1";
        webOrderDubboService.orderDescription(orderId,description,userId);
    }


    @Test
    public void getShippingGroupInfo(){
        String orderId = "10000201708143411";
        Response<WebShippingGroupDto> rep = webOrderDubboService.shippingGroupInfo(orderId);
        System.out.println(JSONObject.toJSONString(rep));
    }
    @Test
    public void getPrintInfo(){
        String orderId = "10000201708143411";
        Response<PrintOrderDto> rep = webOrderDubboService.getPrintInfo(orderId);
        System.out.println(JSONObject.toJSONString(rep));
    }

    @Test
    public void getOrderDetail() {
        String orderId = "12366";
        Response<OrderManageDetailDto> orderManageDetailDtoResponse = webOrderDubboService.queryOrderDeatil(orderId);
        System.out.println(JSONObject.toJSONString(orderManageDetailDtoResponse));
    }


    @Test
     public void testApproval(){
            Response<Boolean> rr = webOrderDubboService.approvalOrder("12366","pro3");
        }

    @Test
    public void testCanYu() throws ParseException {
        QueryParticipateDataDto queryParticipateDataDto = new QueryParticipateDataDto();
        queryParticipateDataDto.setPageSize(5);
        queryParticipateDataDto.setPageNum(1);
        queryParticipateDataDto.setPromoId("HD1700012");
        /*queryParticipateDataDto.setOrderId("100001708047077");*/
        String date1 = "2017-08-04 18:41:11";
        String date2 = "2017-08-21 13:38:00";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date11 = sf.parse(date1);
        Date date22 = sf.parse(date2);
        queryParticipateDataDto.setStartTime(date11);
        queryParticipateDataDto.setEndTime(date22);
        queryParticipateDataDto.setStateDetail("已取消");
        System.out.println(JSONObject.toJSONString(promotionDubboService.queryParticipateDataByCondition(queryParticipateDataDto)));
    }

    @Test
    public void queryFranchiseeSettlement(){
        FranchiseeConditionDto franchiseeConditionDto = new FranchiseeConditionDto();
        franchiseeConditionDto.setOrderId("10004201709090009");
        franchiseeConditionDto.setPage(1);
        franchiseeConditionDto.setPageSize(10);
        Response<PageResult<FranchiseeSettlementDto>> resultResponse = franchiseeSimpleQueryDubboService.queryFranchiseeSettlement(franchiseeConditionDto);
        System.out.println(JSONObject.toJSON(resultResponse));
    }

    @Test
    public void test1(){
        WebQueryOrderConditionDto w = new WebQueryOrderConditionDto();
        w.setPage(1);
        w.setPageSize(2);
        Response<PageResult<OrderEnhancedDto>> resultResponse = webOrderDubboService.getEnhancedOrderPageList(w);
        System.out.println(JSONObject.toJSON(resultResponse));

    }

    @Test
    public void testQueryOrderIndex(){
        WebQueryOrderConditionDto webQueryOrderConditionDto = new WebQueryOrderConditionDto();
        webQueryOrderConditionDto.setPage(1);
        webQueryOrderConditionDto.setPageSize(10);
        webQueryOrderConditionDto.setId("100002017121200009");
        Response<PageResult<WebQueryOrderDto>> resultResponse = webOrderDubboService.queryOrder(webQueryOrderConditionDto);
        System.out.println(JSONObject.toJSON(resultResponse));
    }

    @Test
    public void testQueryOrderProductIndex(){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = null;
        Date end = null;
        try {
            start = sf.parse("2017-11-15 00:00:00");
            end = sf.parse("2017-11-16 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        WebQueryOrderConditionDto webQueryOrderConditionDto = new WebQueryOrderConditionDto();
        webQueryOrderConditionDto.setPage(1);
        webQueryOrderConditionDto.setPageSize(20);
        webQueryOrderConditionDto.setId("100002017121300002");
//        webQueryOrderConditionDto.setOrderState("W");
//        webQueryOrderConditionDto.setSubmitStartTime(start);
//        webQueryOrderConditionDto.setSubmitEndTime(end);
        Response<PageResult<OrderEnhancedDto>> resultResponse = webOrderDubboService.getEnhancedOrderPageList(webQueryOrderConditionDto);
        System.out.println(JSONObject.toJSON(resultResponse));

    }


}
