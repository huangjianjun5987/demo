package test.com.yatang.sc.coupon;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.busi.common.resp.Response;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.OrderGiveCouponToStoreLog;
import com.yatang.sc.order.dubboservice.WebReturnRequestDubboService;
import com.yatang.sc.order.service.OrderGiveCouponToStoreLogService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.purchase.dto.ReturnRequestDetailDto;
import com.yatang.xc.oc.b.member.biz.core.dto.LevelPrivilegeDto;
import com.yatang.xc.oc.b.member.biz.core.dto.PrivilegeInfoDto;
import com.yatang.xc.oc.b.member.biz.core.dubboservice.PrivilegeInfoDubboService;

/**
 * @描述: 优惠券测试类
 * @类名: TestCouponDubboService
 * @作者: dengdongshan
 * @创建时间: 2017/11/1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class TestCouponDubboService {

	 @Autowired
	OrderService orderService;

	@Autowired
	private PrivilegeInfoDubboService privilegeInfoDubboService;

	@Autowired
	private WebReturnRequestDubboService webReturnRequestDubboService;
	
	@Autowired
    private OrderGiveCouponToStoreLogService couponToStoreLogService;

    @Test
    public void revertCoupons() {
        Response<String> response = orderService.revertCoupons("100002017110100120",1022841l);
        System.out.print("======"+response.getResultObject());
    }
    
    @Test
    public void testGiveCoupon() {
//      Response<String> response = orderService.giveCouponToStore("jms_000541", "A000541", 61955l);
    	Order order=new Order();
    //  order.setProfileId("jms_000044");
    	order.setProfileId("jms_000541");
    	order.setPriceInfo(61955l);
    	order.setId("100002017101100015");
    	saveOrderDiscount(order);
    	giveCoupons(order);
        
    }
    
	private void saveOrderDiscount(Order order) {
		Response<LevelPrivilegeDto> privilegeInfo = privilegeInfoDubboService.getPrivilegeInfoByMemberCode( order.getProfileId(), "QY_system_gyl" );
        if(privilegeInfo==null||privilegeInfo.getResultObject() == null||privilegeInfo.getResultObject().getPrivilegeInfoDtoList()==null||privilegeInfo.getResultObject().getPrivilegeInfoDtoList().isEmpty()){
        	return;
        }
        PrivilegeInfoDto privilegeInfoDto = privilegeInfo.getResultObject().getPrivilegeInfoDtoList().get(0);
        if (privilegeInfoDto == null) {
                return;
        }
        float discountNumeric = privilegeInfoDto.getDiscountNumeric();
		OrderGiveCouponToStoreLog orderLog=new OrderGiveCouponToStoreLog();
		orderLog.setDiscount(discountNumeric);
		orderLog.setPriceInfoId(order.getPriceInfo());
		orderLog.setCreationTime(new Date());
		couponToStoreLogService.save(orderLog);
	}
	
    private void giveCoupons(Order order) {
        Response<ReturnRequestDetailDto> returnRequestDetailDto = webReturnRequestDubboService.queryReturnRequestByOrderId( order.getId() );
        double refuseAmount = 0d;
        if (returnRequestDetailDto != null && returnRequestDetailDto.getResultObject() != null&&returnRequestDetailDto.getResultObject().getRefundAmount()!=null) {
            refuseAmount = returnRequestDetailDto.getResultObject().getRefundAmount();
        }
        //会员等级返券
        Response<String> giveCouponToStore = orderService.giveCouponToStore( order.getFranchiseeStoreId(), refuseAmount, order.getPriceInfo() );
        if (giveCouponToStore != null) {
        	System.out.print("订单确认返券： order Id-->:"+order.getId()+" give coupons->: "+giveCouponToStore.getResultObject());
        }
    }
    @Test
    public void getPri(){
    	Response<LevelPrivilegeDto> response = privilegeInfoDubboService.getPrivilegeInfoByMemberCode("jms_000541","gyl_system");
    	
    	System.out.print("======"+response.getResultObject().getArriveScore());
    }
    @Test
    public void getReturnRequest(){
		Response<ReturnRequestDetailDto> returnRequestDetailDto = webReturnRequestDubboService.queryReturnRequestByOrderId("100002017101100015");
		System.out.print("======"+returnRequestDetailDto.getResultObject().getAmount());
    }
}
