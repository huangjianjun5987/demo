package test.com.yatang.sc.franchisee;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.busi.common.datatable.TableDataResult;
import com.busi.common.resp.Response;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.dto.*;
import com.yatang.sc.order.dubboservice.FranchiseeSimpleQueryDubboService;
import com.yatang.xc.dc.biz.facade.dubboservice.sc.DataCenterScDubboService;
import com.yatang.xc.dc.biz.facade.dubboservice.sc.dto.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class TestFranchiseeService {

    @Autowired
    private FranchiseeSimpleQueryDubboService franchiseeSimpleQueryDubboService;
    @Autowired
    DataCenterScDubboService dataCenterScDubboService;

    @Test
    public void testQueryOrderProductIndex(){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        FranchiseeConditionDto franchiseeConditionDto = new FranchiseeConditionDto();
        franchiseeConditionDto.setCompleteDateStart("2017-11-15 00:00:00");
        franchiseeConditionDto.setCompleteDateEnd("2017-11-16 23:59:59");
        Response<PageResult<FranchiseeSettlementDto>> resultResponse = franchiseeSimpleQueryDubboService.queryFranchiseeSettlement(franchiseeConditionDto);
        System.out.println(JSONObject.toJSON(resultResponse));
    }

    @Test
    public void testQueryOrderIndex(){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        FranchiseeConditionDto franchiseeConditionDto = new FranchiseeConditionDto();
//        franchiseeConditionDto.setOrderId("100001708088030");
        Date payStart = null;
        Date payEnd = null;
        Date refundStart = null;
        Date refundEnd = null;

        try {
//            franchiseeConditionDto.setOrderId("100002017120100002");
//            payStart = sf.parse("2017-08-01 00:00:00");
//            payEnd = sf.parse("2017-12-01 23:59:59");
//            refundStart = sf.parse("2017-08-01 00:00:00");
//            refundEnd = sf.parse("2017-12-01 23:59:59");
//            franchiseeConditionDto.setPayDateStart(payStart);
//            franchiseeConditionDto.setPayDateEnd(payEnd);
//            franchiseeConditionDto.setRefundDateStart(refundStart);
//            franchiseeConditionDto.setRefundDateEnd(refundEnd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Response<PageResult<FranchiseePaymentDto>> resultResponse = franchiseeSimpleQueryDubboService.queryFranchiseePayment(franchiseeConditionDto);
        System.out.println(JSONObject.toJSON(resultResponse));

    }

    @Test
    public void testIndexOrderItem(){
        QueryOrderItemDto queryOrderItemDto = new QueryOrderItemDto();
        queryOrderItemDto.setId("100042017120700009");
        queryOrderItemDto.setPageNum(1);
        queryOrderItemDto.setPageSize(20);
        Response<TableDataResult<OrderItemDto>> rep = dataCenterScDubboService.queryOrderItem(queryOrderItemDto);
        System.out.println(JSON.toJSONString(rep));
    }
    @Test
    public void testIndexOrder(){
        QueryOrderListDto queryOrderItemDto = new QueryOrderListDto();
        queryOrderItemDto.setPageNum(1);
        queryOrderItemDto.setPageSize(20);
        Response<TableDataResult<OrderListDto>> rep = dataCenterScDubboService.queryOrderList(queryOrderItemDto);
        System.out.println(JSON.toJSONString(rep));
    }

    @Test
    public void exportOrderList(){
        QueryOrderListExportDto queryOrderListExportDto = new QueryOrderListExportDto();
        queryOrderListExportDto.setId("100001708088030");
        queryOrderListExportDto.setPageNum(1);
        queryOrderListExportDto.setPageSize(Integer.MAX_VALUE);
        Response<TableDataResult<OrderListDto>> rep = dataCenterScDubboService.exportOrderList(queryOrderListExportDto);
        System.out.println(JSON.toJSONString(rep));
    }

    @Test
    public void testQueryFranchiseePayment(){
        FranchiseeConditionDto conditionDto = new FranchiseeConditionDto();
        conditionDto.setPage(1);
        conditionDto.setPageSize(2000);
        conditionDto.setPayDateStart("2017-10-01 00:00:00");
        conditionDto.setPayDateEnd("2018-01-01 23:59:59");
        Response<PageResult<FranchiseePaymentDto>> reponse = franchiseeSimpleQueryDubboService.queryFranchiseePayment(conditionDto);
        System.out.println(JSON.toJSONString(reponse));
    }

    @Test
    public void testQueryFranchiseeSettlement(){
        FranchiseeConditionDto conditionDto = new FranchiseeConditionDto();
        List<String> companyIds = new ArrayList<String>();
        companyIds.add("10000");
        companyIds.add("10004");
        conditionDto.setPage(1);
        conditionDto.setPageSize(2000);
        conditionDto.setPayDateStart("2017-08-01 00:00:00");
        conditionDto.setPayDateEnd("2018-01-01 23:59:59");
        Response<PageResult<FranchiseeSettlementDto>> reponse = franchiseeSimpleQueryDubboService.queryFranchiseeSettlement(conditionDto);
        System.out.println(JSON.toJSONString(reponse));
    }


}
