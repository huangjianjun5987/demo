package test.com.yatang.sc.purchase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.dto.PromoCategoriesDto;
import com.yatang.sc.dto.PromoProductDto;
import com.yatang.sc.dto.PromotionRuleDto;
import com.yatang.sc.order.states.CommerceItemTypes;
import com.yatang.sc.promotion.dto.EachConditionGiveOnceDto;
import com.yatang.sc.promotion.dto.GiveRuleConditionsDto;
import com.yatang.sc.promotion.dto.OrderRuleDto;
import com.yatang.sc.promotion.dto.PurchaseConditionsRuleDto;
import com.yatang.sc.promotion.dto.RuleConditionsDto;
import com.yatang.sc.purchase.calculator.OrderDiscountPriceCalculator;
import com.yatang.sc.purchase.dto.CommerceItemDto;
import com.yatang.sc.purchase.dto.ItemPriceInfoDto;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.dto.OrderPriceInfoDto;
import com.yatang.sc.purchase.exception.PricingException;
import com.yatang.sc.purchase.order.PricingModel;


/**
 * @描述:
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/7/11 10:39
 * @版本:v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class UserOrderDiscountPriceService {

    @Resource(name = "userOrderDiscountPriceCalculator")
    private OrderDiscountPriceCalculator userOrderDiscountPriceCalculator;

    @Resource(name = "orderDiscountPriceCalculator")
    private OrderDiscountPriceCalculator orderDiscountPriceCalculator;
    @Test
    public void price(){
        OrderPriceInfoDto priceInfo = new OrderPriceInfoDto();
        OrderDto orderDto = new OrderDto();
        Map contentMap = new HashMap();
        contentMap.put("franchiseeId","jms_000044");
        orderDto.setProfileId("jms_000044");
        priceInfo.setAmount(4860.39);
        userOrderDiscountPriceCalculator.price(priceInfo,orderDto,null,contentMap);
        System.out.println(JSON.toJSON(priceInfo));
    }
    
   

   
    public PricingModel assembleNO_RULE_DISCOUNTAMOUNT(){
		return assembleNewPromotions("new",getNotUseConditionRule(OrderRuleDto.PreferentialWay.DISCOUNTAMOUNT.getName()));
    }
    
    @Test
    public void promotion_NO_RULE_DISCOUNTAMOUNT_Test() throws PricingException{
        OrderPriceInfoDto priceInfo = new OrderPriceInfoDto();
        OrderDto order = new OrderDto();
        order.setProfileId("jms_000044");
        priceInfo.setAmount(1200);
        List<CommerceItemDto> items = assembleItems();
        order.setItems(items);
        order.setPriceInfoDto(priceInfo);
        List<PricingModel> pricingModel=new ArrayList<PricingModel>();
        //pricingModel.add(assembleOldPromotions("old"));
        pricingModel.add(assembleNO_RULE_DISCOUNTAMOUNT());
		orderDiscountPriceCalculator.price(order,pricingModel,assembleContentMap());
		System.out.println("########################################"+priceInfo.getAmount()+"########################################");
		System.out.println(JSON.toJSON(priceInfo));
		Assert.assertTrue(priceInfo.getAmount()==1197D);
		Assert.assertTrue(order.getItems().get(0).getItemPrice().getOrderDiscountShare()==0.75D);
    }

    
    public PricingModel assembleNO_RULE_PERCENTAGE(){
		return assembleNewPromotions("new",getNotUseConditionRule(OrderRuleDto.PreferentialWay.PERCENTAGE.getName()));
    }
    
    @Test
    public void promotion_NO_RULE_PERCENTAGE_Test() throws PricingException{
        List<PricingModel> pricingModel=new ArrayList<PricingModel>();
        //pricingModel.add(assembleOldPromotions("old"));
        pricingModel.add(assembleNO_RULE_PERCENTAGE());
        OrderDto assembleOrder = assembleOrder();
		orderDiscountPriceCalculator.price(assembleOrder,pricingModel, assembleContentMap());
		System.out.println("########################################"+assembleOrder.getPriceInfoDto().getAmount()+"########################################");
        System.out.println(JSON.toJSON(assembleOrder.getPriceInfoDto()));
		Assert.assertTrue(assembleOrder.getPriceInfoDto().getAmount()==1164D);
		Assert.assertTrue(assembleOrder.getItems().get(0).getItemPrice().getOrderDiscountShare()==9D);
    }
    
    
    public PricingModel assemblePURCHASE_CONDITIONS_DISCOUNTAMOUNT_CATEGORY_AMOUNT(){
		return assembleNewPromotions("new",getPurchaseConditionsRule(OrderRuleDto.PreferentialWay.DISCOUNTAMOUNT.getName(),RuleConditionsDto.PurchaseType.CATEGORY.getName(),RuleConditionsDto.ConditionType.AMOUNT.getName()));
    }
    
    @Test
    public void promotion_PURCHASE_CONDITIONS_DISCOUNTAMOUNT_CATEGORY_AMOUNT_Test() throws PricingException{
        List<PricingModel> pricingModel=new ArrayList<PricingModel>();
        //pricingModel.add(assembleOldPromotions("old"));
        pricingModel.add(assemblePURCHASE_CONDITIONS_DISCOUNTAMOUNT_CATEGORY_AMOUNT());
        OrderDto assembleOrder = assembleOrder();
		orderDiscountPriceCalculator.price(assembleOrder,pricingModel, assembleContentMap());
		System.out.println("########################################"+assembleOrder.getPriceInfoDto().getAmount()+"########################################");
        System.out.println(JSON.toJSON(assembleOrder.getPriceInfoDto()));
		Assert.assertTrue(assembleOrder.getPriceInfoDto().getAmount()==1197D);
		Assert.assertTrue(assembleOrder.getItems().get(0).getItemPrice().getOrderDiscountShare()==3D);
    }
    
    public PricingModel assemblePURCHASE_CONDITIONS_DISCOUNTAMOUNT_ALL_AMOUNT(){
		return assembleNewPromotions("new",getPurchaseConditionsRule(OrderRuleDto.PreferentialWay.DISCOUNTAMOUNT.getName(),RuleConditionsDto.PurchaseType.ALL.getName(),RuleConditionsDto.ConditionType.AMOUNT.getName()));
    }
    
    @Test
    public void promotion_PURCHASE_CONDITIONS_DISCOUNTAMOUNT_ALL_AMOUNT_Test() throws PricingException{
        List<PricingModel> pricingModel=new ArrayList<PricingModel>();
        //pricingModel.add(assembleOldPromotions("old"));
        pricingModel.add(assemblePURCHASE_CONDITIONS_DISCOUNTAMOUNT_ALL_AMOUNT());
        OrderDto assembleOrder = assembleOrder();
		orderDiscountPriceCalculator.price(assembleOrder,pricingModel, assembleContentMap());
		System.out.println("########################################"+assembleOrder.getPriceInfoDto().getAmount()+"########################################");
        System.out.println(JSON.toJSON(assembleOrder.getPriceInfoDto()));
		Assert.assertTrue(assembleOrder.getPriceInfoDto().getAmount()==1197D);
		Assert.assertTrue(assembleOrder.getItems().get(0).getItemPrice().getOrderDiscountShare()==1D);
    }
    
    
    public PricingModel assemblePURCHASE_CONDITIONS_DISCOUNTAMOUNT_CATEGORY_QUANTITY(){
		return assembleNewPromotions("new",getPurchaseConditionsRule(OrderRuleDto.PreferentialWay.DISCOUNTAMOUNT.getName(),RuleConditionsDto.PurchaseType.CATEGORY.getName(),RuleConditionsDto.ConditionType.QUANTITY.getName()));
    }
    
    @Test
    public void promotion_PURCHASE_CONDITIONS_DISCOUNTAMOUNT_CATEGORY_QUANTITY_Test() throws PricingException{
        List<PricingModel> pricingModel=new ArrayList<PricingModel>();
        //pricingModel.add(assembleOldPromotions("old"));
        pricingModel.add(assemblePURCHASE_CONDITIONS_DISCOUNTAMOUNT_CATEGORY_QUANTITY());
        OrderDto assembleOrder = assembleOrder();
		orderDiscountPriceCalculator.price(assembleOrder,pricingModel, assembleContentMap());
		System.out.println("########################################"+assembleOrder.getPriceInfoDto().getAmount()+"########################################");
        System.out.println(JSON.toJSON(assembleOrder.getPriceInfoDto()));
		Assert.assertTrue(assembleOrder.getPriceInfoDto().getAmount()==1197D);
		Assert.assertTrue(assembleOrder.getItems().get(0).getItemPrice().getOrderDiscountShare()==3D);
    }
    
    public PricingModel assemblePURCHASE_CONDITIONS_DISCOUNTAMOUNT_ALL_QUANTITY(){
		return assembleNewPromotions("new",getPurchaseConditionsRule(OrderRuleDto.PreferentialWay.DISCOUNTAMOUNT.getName(),RuleConditionsDto.PurchaseType.ALL.getName(),RuleConditionsDto.ConditionType.QUANTITY.getName()));
    }
    
    @Test
    public void promotion_PURCHASE_CONDITIONS_DISCOUNTAMOUNT_ALL_QUANTITY_Test() throws PricingException{
        List<PricingModel> pricingModel=new ArrayList<PricingModel>();
        //pricingModel.add(assembleOldPromotions("old"));
        pricingModel.add(assemblePURCHASE_CONDITIONS_DISCOUNTAMOUNT_ALL_QUANTITY());
        OrderDto assembleOrder = assembleOrder();
		orderDiscountPriceCalculator.price(assembleOrder,pricingModel, assembleContentMap());
		System.out.println("########################################"+assembleOrder.getPriceInfoDto().getAmount()+"########################################");
        System.out.println(JSON.toJSON(assembleOrder.getPriceInfoDto()));
		Assert.assertTrue(assembleOrder.getPriceInfoDto().getAmount()==1197D);
		Assert.assertTrue(assembleOrder.getItems().get(0).getItemPrice().getOrderDiscountShare()==1D);
    }
    
    
    
    public PricingModel assemblePURCHASE_CONDITIONS_DISCOUNTAMOUNT_PRODUCT_QUANTITY(){
		return assembleNewPromotions("new",getPurchaseConditionsRule(OrderRuleDto.PreferentialWay.DISCOUNTAMOUNT.getName(),RuleConditionsDto.PurchaseType.PRODUCT.getName(),RuleConditionsDto.ConditionType.QUANTITY.getName()));
    }
    
    @Test
    public void promotion_PURCHASE_CONDITIONS_DISCOUNTAMOUNT_PRODUCT_QUANTITY_Test() throws PricingException{
        List<PricingModel> pricingModel=new ArrayList<PricingModel>();
        //pricingModel.add(assembleOldPromotions("old"));
        pricingModel.add(assemblePURCHASE_CONDITIONS_DISCOUNTAMOUNT_PRODUCT_QUANTITY());
        OrderDto assembleOrder = assembleOrder();
		orderDiscountPriceCalculator.price(assembleOrder,pricingModel, assembleContentMap());
		System.out.println("########################################"+assembleOrder.getPriceInfoDto().getAmount()+"########################################");
        System.out.println(JSON.toJSON(assembleOrder.getPriceInfoDto()));
		Assert.assertTrue(assembleOrder.getPriceInfoDto().getAmount()==1197D);
		Assert.assertTrue(assembleOrder.getItems().get(0).getItemPrice().getOrderDiscountShare()==3D);

    }
    
    
    public PricingModel assemblePURCHASE_CONDITIONS_DISCOUNTAMOUNT_PRODUCT_AMOUNT(){
		return assembleNewPromotions("new",getPurchaseConditionsRule(OrderRuleDto.PreferentialWay.DISCOUNTAMOUNT.getName(),RuleConditionsDto.PurchaseType.PRODUCT.getName(),RuleConditionsDto.ConditionType.AMOUNT.getName()));
    }
    
    @Test
    public void promotion_PURCHASE_CONDITIONS_DISCOUNTAMOUNT_PRODUCT_AMOUNT_Test() throws PricingException{
        List<PricingModel> pricingModel=new ArrayList<PricingModel>();
        //pricingModel.add(assembleOldPromotions("old"));
        pricingModel.add(assemblePURCHASE_CONDITIONS_DISCOUNTAMOUNT_PRODUCT_AMOUNT());
        OrderDto assembleOrder = assembleOrder();
		orderDiscountPriceCalculator.price(assembleOrder,pricingModel, assembleContentMap());
		System.out.println("########################################"+assembleOrder.getPriceInfoDto().getAmount()+"########################################");
        System.out.println(JSON.toJSON(assembleOrder.getPriceInfoDto()));
		Assert.assertTrue(assembleOrder.getPriceInfoDto().getAmount()==1197D);
		Assert.assertTrue(assembleOrder.getItems().get(0).getItemPrice().getOrderDiscountShare()==3D);

    }
    
    
    public PricingModel assemblePURCHASE_CONDITIONS_PERCENTAGE_CATEGORY_AMOUNT(){
		return assembleNewPromotions("new",getPurchaseConditionsRule(OrderRuleDto.PreferentialWay.PERCENTAGE.getName(),RuleConditionsDto.PurchaseType.CATEGORY.getName(),RuleConditionsDto.ConditionType.AMOUNT.getName()));
    }
    
    @Test
    public void promotion_PURCHASE_CONDITIONS_PERCENTAGE_CATEGORY_AMOUNT_Test() throws PricingException{
        List<PricingModel> pricingModel=new ArrayList<PricingModel>();
        //pricingModel.add(assembleOldPromotions("old"));
        pricingModel.add(assemblePURCHASE_CONDITIONS_PERCENTAGE_CATEGORY_AMOUNT());
        OrderDto assembleOrder = assembleOrder();
		orderDiscountPriceCalculator.price(assembleOrder,pricingModel, assembleContentMap());
		System.out.println("########################################"+assembleOrder.getPriceInfoDto().getAmount()+"########################################");
        System.out.println(JSON.toJSON(assembleOrder.getPriceInfoDto()));
		Assert.assertTrue(assembleOrder.getPriceInfoDto().getAmount()==1191D);
		Assert.assertTrue(assembleOrder.getItems().get(0).getItemPrice().getOrderDiscountShare()==9D);

    }
    
    public PricingModel assemblePURCHASE_CONDITIONS_PERCENTAGE_ALL_AMOUNT(){
		return assembleNewPromotions("new",getPurchaseConditionsRule(OrderRuleDto.PreferentialWay.PERCENTAGE.getName(),RuleConditionsDto.PurchaseType.ALL.getName(),RuleConditionsDto.ConditionType.AMOUNT.getName()));
    }
    
    @Test
    public void promotion_PURCHASE_CONDITIONS_PERCENTAGE_ALL_AMOUNT_Test() throws PricingException{
        List<PricingModel> pricingModel=new ArrayList<PricingModel>();
        //pricingModel.add(assembleOldPromotions("old"));
        pricingModel.add(assemblePURCHASE_CONDITIONS_PERCENTAGE_ALL_AMOUNT());
        OrderDto assembleOrder = assembleOrder();
		orderDiscountPriceCalculator.price(assembleOrder,pricingModel, assembleContentMap());
		System.out.println("########################################"+assembleOrder.getPriceInfoDto().getAmount()+"########################################");
        System.out.println(JSON.toJSON(assembleOrder.getPriceInfoDto()));
		Assert.assertTrue(assembleOrder.getPriceInfoDto().getAmount()==1173D);
		Assert.assertTrue(assembleOrder.getItems().get(0).getItemPrice().getOrderDiscountShare()==9D);

    }
    
    public PricingModel assemblePURCHASE_CONDITIONS_PERCENTAGE_CATEGORY_QUANTITY(){
		return assembleNewPromotions("new",getPurchaseConditionsRule(OrderRuleDto.PreferentialWay.PERCENTAGE.getName(),RuleConditionsDto.PurchaseType.CATEGORY.getName(),RuleConditionsDto.ConditionType.QUANTITY.getName()));
    }
    
    @Test
    public void promotion_PURCHASE_CONDITIONS_PERCENTAGE_CATEGORY_QUANTITY_Test() throws PricingException{
        List<PricingModel> pricingModel=new ArrayList<PricingModel>();
        //pricingModel.add(assembleOldPromotions("old"));
        pricingModel.add(assemblePURCHASE_CONDITIONS_PERCENTAGE_CATEGORY_QUANTITY());
        OrderDto assembleOrder = assembleOrder();
		orderDiscountPriceCalculator.price(assembleOrder,pricingModel, assembleContentMap());
		System.out.println("########################################"+assembleOrder.getPriceInfoDto().getAmount()+"########################################");
        System.out.println(JSON.toJSON(assembleOrder.getPriceInfoDto()));
		Assert.assertTrue(assembleOrder.getPriceInfoDto().getAmount()==1191D);
		Assert.assertTrue(assembleOrder.getItems().get(0).getItemPrice().getOrderDiscountShare()==9D);
    }
    
    public PricingModel assemblePURCHASE_CONDITIONS_PERCENTAGE_ALL_QUANTITY(){
		return assembleNewPromotions("new",getPurchaseConditionsRule(OrderRuleDto.PreferentialWay.PERCENTAGE.getName(),RuleConditionsDto.PurchaseType.ALL.getName(),RuleConditionsDto.ConditionType.QUANTITY.getName()));
    }
    
    @Test
    public void promotion_PURCHASE_CONDITIONS_PERCENTAGE_ALL_QUANTITY_Test() throws PricingException{
        List<PricingModel> pricingModel=new ArrayList<PricingModel>();
        //pricingModel.add(assembleOldPromotions("old"));
        pricingModel.add(assemblePURCHASE_CONDITIONS_PERCENTAGE_ALL_QUANTITY());
        OrderDto assembleOrder = assembleOrder();
		orderDiscountPriceCalculator.price(assembleOrder,pricingModel, assembleContentMap());
		System.out.println("########################################"+assembleOrder.getPriceInfoDto().getAmount()+"########################################");
        System.out.println(JSON.toJSON(assembleOrder.getPriceInfoDto()));
		Assert.assertTrue(assembleOrder.getPriceInfoDto().getAmount()==1173D);
		Assert.assertTrue(assembleOrder.getItems().get(0).getItemPrice().getOrderDiscountShare()==9D);
    }
    
    public PricingModel assemblePURCHASE_CONDITIONS_PERCENTAGE_PRODUCT_QUANTITY(){
		return assembleNewPromotions("new",getPurchaseConditionsRule(OrderRuleDto.PreferentialWay.PERCENTAGE.getName(),RuleConditionsDto.PurchaseType.PRODUCT.getName(),RuleConditionsDto.ConditionType.QUANTITY.getName()));
    }
    
    @Test
    public void promotion_PURCHASE_CONDITIONS_PERCENTAGE_PRODUCT_QUANTITY_Test() throws PricingException{
        List<PricingModel> pricingModel=new ArrayList<PricingModel>();
        //pricingModel.add(assembleOldPromotions("old"));
        pricingModel.add(assemblePURCHASE_CONDITIONS_PERCENTAGE_PRODUCT_QUANTITY());
        OrderDto assembleOrder = assembleOrder();
		orderDiscountPriceCalculator.price(assembleOrder,pricingModel, assembleContentMap());
		System.out.println("########################################"+assembleOrder.getPriceInfoDto().getAmount()+"########################################");
        System.out.println(JSON.toJSON(assembleOrder.getPriceInfoDto()));
		Assert.assertTrue(assembleOrder.getPriceInfoDto().getAmount()==1191D);
		Assert.assertTrue(assembleOrder.getItems().get(0).getItemPrice().getOrderDiscountShare()==9D);
    }
    
    public PricingModel assemblePURCHASE_CONDITIONS_PERCENTAGE_PRODUCT_AMOUNT(){
		return assembleNewPromotions("new",getPurchaseConditionsRule(OrderRuleDto.PreferentialWay.PERCENTAGE.getName(),RuleConditionsDto.PurchaseType.PRODUCT.getName(),RuleConditionsDto.ConditionType.AMOUNT.getName()));
    }
    
    @Test
    public void promotion_PURCHASE_CONDITIONS_PERCENTAGE_PRODUCT_AMOUNT_Test() throws PricingException{
        List<PricingModel> pricingModel=new ArrayList<PricingModel>();
        //pricingModel.add(assembleOldPromotions("old"));
        pricingModel.add(assemblePURCHASE_CONDITIONS_PERCENTAGE_PRODUCT_AMOUNT());
        OrderDto assembleOrder = assembleOrder();
		orderDiscountPriceCalculator.price(assembleOrder,pricingModel, assembleContentMap());
		System.out.println("########################################"+assembleOrder.getPriceInfoDto().getAmount()+"########################################");
        System.out.println(JSON.toJSON(assembleOrder.getPriceInfoDto()));
		Assert.assertTrue(assembleOrder.getPriceInfoDto().getAmount()==1191D);
		Assert.assertTrue(assembleOrder.getItems().get(0).getItemPrice().getOrderDiscountShare()==9D);

    }
    
    
    
    public PricingModel assemblePURCHASE_CONDITIONS_FIXEDPRICE_PRODUCT_QUANTITY(){
		return assembleNewPromotions("new",getPurchaseConditionsRule(OrderRuleDto.PreferentialWay.FIXEDPRICE.getName(),RuleConditionsDto.PurchaseType.PRODUCT.getName(),RuleConditionsDto.ConditionType.QUANTITY.getName()));
    }
    
    @Test
    public void promotion_PURCHASE_CONDITIONS_FIXEDPRICE_PRODUCT_QUANTITY_Test() throws PricingException{
        List<PricingModel> pricingModel=new ArrayList<PricingModel>();
        //pricingModel.add(assembleOldPromotions("old"));
        pricingModel.add(assemblePURCHASE_CONDITIONS_FIXEDPRICE_PRODUCT_QUANTITY());
        OrderDto assembleOrder = assembleOrder();
		orderDiscountPriceCalculator.price(assembleOrder,pricingModel, assembleContentMap());
		System.out.println("########################################"+assembleOrder.getPriceInfoDto().getAmount()+"########################################");
        System.out.println(JSON.toJSON(assembleOrder.getPriceInfoDto()));
		Assert.assertTrue(assembleOrder.getPriceInfoDto().getAmount()==909D);
		Assert.assertTrue(assembleOrder.getItems().get(0).getItemPrice().getOrderDiscountShare()==291D);
    }
    
    public PricingModel assemblePURCHASE_CONDITIONS_FIXEDPRICE_PRODUCT_AMOUNT(){
		return assembleNewPromotions("new",getPurchaseConditionsRule(OrderRuleDto.PreferentialWay.FIXEDPRICE.getName(),RuleConditionsDto.PurchaseType.PRODUCT.getName(),RuleConditionsDto.ConditionType.AMOUNT.getName()));
    }
    
    @Test
    public void promotion_PURCHASE_CONDITIONS_FIXEDPRICE_PRODUCT_AMOUNT_Test() throws PricingException{
        List<PricingModel> pricingModel=new ArrayList<PricingModel>();
        //pricingModel.add(assembleOldPromotions("old"));
        pricingModel.add(assemblePURCHASE_CONDITIONS_FIXEDPRICE_PRODUCT_AMOUNT());
        OrderDto assembleOrder = assembleOrder();
		orderDiscountPriceCalculator.price(assembleOrder,pricingModel, assembleContentMap());
		System.out.println("########################################"+assembleOrder.getPriceInfoDto().getAmount()+"########################################");
        System.out.println(JSON.toJSON(assembleOrder.getPriceInfoDto()));
		Assert.assertTrue(assembleOrder.getPriceInfoDto().getAmount()==909D);
		Assert.assertTrue(assembleOrder.getItems().get(0).getItemPrice().getOrderDiscountShare()==291D);

    }
    
    
    public PricingModel assemblePURCHASE_CONDITIONS_GIVESAMEPRODUCT_PRODUCT_QUANTITY(){
		return assembleNewPromotions("new",getPurchaseConditionsRule(OrderRuleDto.PreferentialWay.GIVESAMEPRODUCT.getName(),RuleConditionsDto.PurchaseType.PRODUCT.getName(),RuleConditionsDto.ConditionType.QUANTITY.getName()));
    }
    
    @Test
    public void promotion_PURCHASE_CONDITIONS_GIVESAMEPRODUCT_PRODUCT_QUANTITY_Test() throws PricingException{
        List<PricingModel> pricingModel=new ArrayList<PricingModel>();
        //pricingModel.add(assembleOldPromotions("old"));
        pricingModel.add(assemblePURCHASE_CONDITIONS_GIVESAMEPRODUCT_PRODUCT_QUANTITY());
        OrderDto assembleOrder = assembleOrder();
		orderDiscountPriceCalculator.price(assembleOrder,pricingModel, assembleContentMap());
		System.out.println("########################################"+assembleOrder.getPriceInfoDto().getAmount()+"########################################");
        System.out.println(JSON.toJSON(assembleOrder.getPriceInfoDto()));
		Assert.assertTrue(assembleOrder.getPriceInfoDto().getAmount()==1200D);
		Assert.assertTrue(assembleOrder.getItems().get(0).getItemPrice().getOrderDiscountShare()==300D);
		for (CommerceItemDto commerceItemDto : assembleOrder.getItems()) {
			if(CommerceItemTypes.PROMOTION.equals(commerceItemDto.getType())){
				Assert.assertTrue(commerceItemDto.getQuantity()==3L);

			}
		}

    }
    
    
    public PricingModel assemblePURCHASE_CONDITIONS_GIVESAMEPRODUCT_PRODUCT_AMOUNT(){
		return assembleNewPromotions("new",getPurchaseConditionsRule(OrderRuleDto.PreferentialWay.GIVESAMEPRODUCT.getName(),RuleConditionsDto.PurchaseType.PRODUCT.getName(),RuleConditionsDto.ConditionType.AMOUNT.getName()));
    }
    
    @Test
    public void promotion_PURCHASE_CONDITIONS_GIVESAMEPRODUCT_PRODUCT_AMOUNT_Test() throws PricingException{
        List<PricingModel> pricingModel=new ArrayList<PricingModel>();
        //pricingModel.add(assembleOldPromotions("old"));
        pricingModel.add(assemblePURCHASE_CONDITIONS_GIVESAMEPRODUCT_PRODUCT_AMOUNT());
        OrderDto assembleOrder = assembleOrder();
		orderDiscountPriceCalculator.price(assembleOrder,pricingModel, assembleContentMap());
		System.out.println("########################################"+assembleOrder.getPriceInfoDto().getAmount()+"########################################");
        System.out.println(JSON.toJSON(assembleOrder.getPriceInfoDto()));
		Assert.assertTrue(assembleOrder.getPriceInfoDto().getAmount()==1200D);
		for (CommerceItemDto commerceItemDto : assembleOrder.getItems()) {
			if(CommerceItemTypes.PROMOTION.equals(commerceItemDto.getType())){
				Assert.assertTrue(commerceItemDto.getQuantity()==3L);

			}
		}
    }
    
    public PricingModel assembleEachConditionGiveOnce_AMOUNT(){
		return assembleNewPromotions("new",getEachConditionGiveOnce(RuleConditionsDto.ConditionType.AMOUNT.getName()));
    }
    
    @Test
    public void promotion_EachConditionGiveOnce_AMOUNT_Test() throws PricingException{
        List<PricingModel> pricingModel=new ArrayList<PricingModel>();
        //pricingModel.add(assembleOldPromotions("old"));
        pricingModel.add(assembleEachConditionGiveOnce_AMOUNT());
        OrderDto assembleOrder = assembleOrder();
		orderDiscountPriceCalculator.price(assembleOrder,pricingModel, assembleContentMap());
		System.out.println("########################################"+assembleOrder.getPriceInfoDto().getAmount()+"########################################");
        System.out.println(JSON.toJSON(assembleOrder.getPriceInfoDto()));
		Assert.assertTrue(assembleOrder.getPriceInfoDto().getAmount()==1200D);
		for (CommerceItemDto commerceItemDto : assembleOrder.getItems()) {
			if(CommerceItemTypes.PROMOTION.equals(commerceItemDto.getType())){
				Assert.assertTrue(commerceItemDto.getQuantity()==450L);
			}
		}
    }
    
    public PricingModel assembleEachConditionGiveOnce_QUANTITY(){
  		return assembleNewPromotions("new",getEachConditionGiveOnce(RuleConditionsDto.ConditionType.QUANTITY.getName()));
      }
      
      @Test
    public void promotion_EachConditionGiveOnce_QUANTITY_Test() throws PricingException{
          List<PricingModel> pricingModel=new ArrayList<PricingModel>();
          //pricingModel.add(assembleOldPromotions("old"));
          pricingModel.add(assembleEachConditionGiveOnce_QUANTITY());
          OrderDto assembleOrder = assembleOrder();
  		orderDiscountPriceCalculator.price(assembleOrder,pricingModel, assembleContentMap());
  		System.out.println("########################################"+assembleOrder.getPriceInfoDto().getAmount()+"########################################");
          System.out.println(JSON.toJSON(assembleOrder.getPriceInfoDto()));
  		Assert.assertTrue(assembleOrder.getPriceInfoDto().getAmount()==1200D);
  		for (CommerceItemDto commerceItemDto : assembleOrder.getItems()) {
  			if(CommerceItemTypes.PROMOTION.equals(commerceItemDto.getType())){
  				Assert.assertTrue(commerceItemDto.getQuantity()==3L);
  			}
  		}
      }
    private OrderDto  assembleOrder(){
    	OrderPriceInfoDto priceInfo = new OrderPriceInfoDto();
        OrderDto order = new OrderDto();
        order.setProfileId("jms_000044");
        priceInfo.setAmount(1200);
        List<CommerceItemDto> items = assembleItems();
        order.setItems(items);
        order.setPriceInfoDto(priceInfo);
		return order;
    }

	private Map assembleContentMap() {
		Map contentMap = new HashMap();
        contentMap.put("franchiseeId","jms_000044");
        contentMap.put("storeId","A000992");
        contentMap.put("branchCompanyId", "10000");
       // contentMap.put("getAvailablePromotions","A000992");
		return contentMap;
	}
	private List<CommerceItemDto> assembleItems() {
		List<CommerceItemDto> items=new ArrayList<CommerceItemDto>();
        CommerceItemDto item=new CommerceItemDto();
        item.setId("testItem");
        item.setCatalogId("xcate5");
        item.setProductId("50503");
        item.setProductCode("1000047");
        item.setQuantity(3);
        item.setSaleQuantity(3L);
        item.setSelected(true);
        ItemPriceInfoDto itemPrice =new ItemPriceInfoDto();
        itemPrice.setAmount(300);
        itemPrice.setId("tsetitemPriceinfo");
        itemPrice.setSalePrice(100);
        item.setItemPrice(itemPrice);
        items.add(item);
        
        
        CommerceItemDto item2=new CommerceItemDto();
        item2.setId("testItem");
        item2.setCatalogId("xcate6");
        item2.setProductId("60000887630");
        item2.setProductCode("1000060");
        item2.setQuantity(3);
        item.setSaleQuantity(3L);
        item2.setSelected(true);
        ItemPriceInfoDto itemPrice2 =new ItemPriceInfoDto();
        itemPrice2.setAmount(900);
        itemPrice2.setId("tsetitemPriceinfo");
        itemPrice2.setSalePrice(300);
        item2.setItemPrice(itemPrice2);
        items.add(item2);
        
		return items;
	}
    
    
    
    
    
    public PricingModel assembleNewPromotions(String key,PromotionRuleDto promotionRule){
    	PricingModel assembleOldPromotions = assembleOldPromotions(key);
    	assembleOldPromotions.setPromotionRule(promotionRule);
    	return assembleOldPromotions;
    } 
    
    
    
    public  PromotionRuleDto getNotUseConditionRule(String way){
    	PromotionRuleDto promotionRule =new PromotionRuleDto ();
    	promotionRule.setUseConditionRule(false);
    	OrderRuleDto orderRule=new OrderRuleDto();
    	orderRule.setPreferentialWay(way);
    	orderRule.setPreferentialValue(new BigDecimal(3));
    	promotionRule.setOrderRule(orderRule);
    	return promotionRule;
    }
    
    public  PromotionRuleDto getPurchaseConditionsRule(String way,String purchaseType,String conditionType){
    	PromotionRuleDto promotionRule =new PromotionRuleDto ();
    	promotionRule.setUseConditionRule(true);
    	promotionRule.setRuleName(PromotionRuleDto.RuleName.PURCHASECONDITION.getName());
    	PurchaseConditionsRuleDto purchaseCondition=new PurchaseConditionsRuleDto();
    	RuleConditionsDto condition =new RuleConditionsDto();
    	condition.setConditionType(conditionType);
    	condition.setConditionValue(new BigDecimal(2));
    	
    	condition.setPurchaseType(purchaseType);
    	
    	PromoCategoriesDto promoCategories =new PromoCategoriesDto();
    	promoCategories.setCategoryId("xcate5");
    	promoCategories.setCategoryLevel(1);
    	promoCategories.setCategoryName("xcate5");
    	condition.setPromoCategories(promoCategories);
    	
    	PromoProductDto promoProduct = new PromoProductDto();
    	promoProduct.setProductId("50503");
    	promoProduct.setProductName("50503");
    	condition.setPromoProduct(promoProduct);
    	
    	
        OrderRuleDto rule=new OrderRuleDto();
        rule.setPreferentialWay(way);
        rule.setPreferentialValue(new BigDecimal(3));

        purchaseCondition.setRule(rule);
        purchaseCondition.setCondition(condition);
    	promotionRule.setPurchaseConditionsRule(purchaseCondition);
    	return promotionRule;
    }
    
    public  PromotionRuleDto getEachConditionGiveOnce(String conditionType){
    	PromotionRuleDto promotionRule =new PromotionRuleDto ();
    	promotionRule.setUseConditionRule(true);
    	promotionRule.setRuleName(PromotionRuleDto.RuleName.EACHCONDITIONGIVEONCE.getName());
    	EachConditionGiveOnceDto eachConditionGiveOnce=new EachConditionGiveOnceDto();
    	RuleConditionsDto condition =new RuleConditionsDto();
    	condition.setPurchaseType(RuleConditionsDto.PurchaseType.PRODUCT.getName());
    	PromoProductDto promoProduct = new PromoProductDto();
    	promoProduct.setProductId("50503");
    	promoProduct.setProductName("50503");
    	condition.setPromoProduct(promoProduct);
    	condition.setConditionType(conditionType);
    	condition.setConditionValue(new BigDecimal(2));

        List<RuleConditionsDto> conditions=new ArrayList<RuleConditionsDto>();
        conditions.add(condition);
        eachConditionGiveOnce.setConditions(conditions);
        
        GiveRuleConditionsDto giveRuleCondition = new GiveRuleConditionsDto ();
        
        giveRuleCondition.setPromoProduct(promoProduct);
        giveRuleCondition.setPurchaseType(RuleConditionsDto.PurchaseType.PRODUCT.getName());
        
        OrderRuleDto rule=new OrderRuleDto();
        rule.setPreferentialWay(OrderRuleDto.PreferentialWay.GIVESAMEPRODUCT.getName());
        rule.setPreferentialValue(new BigDecimal(3));
        giveRuleCondition.setRule(rule);
        eachConditionGiveOnce.setGiveRuleCondition(giveRuleCondition);
        
    	promotionRule.setEachConditionGiveOnce(eachConditionGiveOnce);
    	return promotionRule;
    }
    
    
    public PricingModel assembleOldPromotions(String key){
        PricingModel model = new PricingModel();
        model.setId("test"+key);
        model.setType(0);
        model.setPromotionName("promotionName"+key);
        model.setDiscountType("stander");
        model.setStatus("released");
        model.setStartDate(new Date());
        model.setEndDate(new Date(System.currentTimeMillis()+1000000));
        model.setSuperposeUserDiscount(true);
        model.setSuperposePromo(true);
        model.setQuanifyAmount(20);
        model.setDiscount(5);
        model.setPriority(1L);    
        
        List<String> companies = new ArrayList<String>();
        companies.add("10000");
        companies.add("10003");
        model.setCompanies(companies);
        
        List<String> category = new ArrayList<String>();
        category.add("xcate5");
        model.setCategories1(category);
        List<String> category2 = new ArrayList<String>();
        category2.add("xcate35");
        model.setCategories2(category2);
        List<String> category3 = new ArrayList<String>();
        category3.add("xcate116");
        model.setCategories3(category3);
       // model.setCategories4(category);

       String[] stores = "A000992,A000902,A000993".split(",");
       model.setStores(CollectionUtils.arrayToList(stores));
        return model;
    }

}