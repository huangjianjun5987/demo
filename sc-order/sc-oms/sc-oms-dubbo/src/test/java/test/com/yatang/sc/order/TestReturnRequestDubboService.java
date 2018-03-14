package test.com.yatang.sc.order;

import com.busi.common.resp.Response;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.order.dubboservice.WebReturnRequestDubboService;
import com.yatang.sc.purchase.dto.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: Web退换货测试类
 * @类名: TestReturnRequestDubboService
 * @作者: kangdong
 * @创建时间: 2017/10/23 15:29
 * @版本: v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class TestReturnRequestDubboService {

    @Autowired
    private WebReturnRequestDubboService rervice;

    @Test
    public void getReturnRequestList(){
        ReturnRequestQueryParamDto po = new ReturnRequestQueryParamDto();
        po.setOrderId("1000020171012000362");
        po.setPageNum(1);
        po.setPageSize(2);
        //po.setEndCreateTime(new Date());
        Response<PageResult<ReturnRequestListDto>> returnRequestList = rervice.queryReturnRequestList(po);
        PageResult<ReturnRequestListDto> returnRequestListDtoPageResult = returnRequestList.getResultObject();
        System.out.print("======"+returnRequestListDtoPageResult.getData());
    }

    @Test
    public void returnRequestDetail() {
        Response<ReturnRequestDetailDto> response = rervice.returnRequestDetail("100002017101200036T01");
        System.out.print("======"+response.getResultObject());
    }

    @Test
    public void update() {
        List<ReturnQuantityDto> list = new ArrayList<ReturnQuantityDto>();
        ReturnRequestDescriptionDto dto = new ReturnRequestDescriptionDto();
        dto.setReturnId("100042017112100003T01");
        dto.setUserId("jms_000811");
        dto.setDescription("这是备注信息");

        ReturnQuantityDto returnQuantity1 = new ReturnQuantityDto();
        returnQuantity1.setId((long) 218);
        returnQuantity1.setReturnQuantity((long) 122);//133 //r:558.60,ref:578.60,a:678.60

        ReturnQuantityDto returnQuantity2 = new ReturnQuantityDto();
        returnQuantity2.setId((long) 219);
        returnQuantity2.setReturnQuantity((long) 5);//10 //r:120.00
        list.add(returnQuantity1);
        list.add(returnQuantity2);
        dto.setItems(list);

        Response<Boolean> response = rervice.updateDescriptionAndQuantity(dto);
        System.out.print("======"+response.getResultObject().booleanValue());
    }
}
