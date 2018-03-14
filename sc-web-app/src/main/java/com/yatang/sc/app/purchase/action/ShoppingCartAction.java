package com.yatang.sc.app.purchase.action;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import org.apache.commons.lang3.StringUtils;
import com.yatang.sc.app.action.AppBaseAction;
import com.yatang.sc.app.res.ActionResponse;
import com.yatang.sc.app.vo.*;
import com.yatang.sc.app.vo.imgconfig.AppConfig;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import com.yatang.sc.order.dubboservice.PromotionDubboService;
import com.yatang.sc.order.states.CommerceItemTypes;
import com.yatang.sc.purchase.dto.*;
import com.yatang.sc.purchase.dubboservice.PurchaseDubboService;
import com.yatang.xc.mbd.biz.org.dto.StoreDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @描述:
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/7/11 20:28
 * @版本:v1.0
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
    @RequestMapping(value = "/sc/cart")
public class ShoppingCartAction extends AppBaseAction{

    private final PurchaseDubboService purchaseDubboService;

    private final PromotionDubboService promotionDubboService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    AppConfig appConfig;

    @Autowired
    private ItemLocInventoryDubboService mItemLocInventoryDubboService;

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping(value="initCart",method= RequestMethod.GET)
    public Response<Boolean> initShoppingCart(HttpServletRequest request){
        Response<Boolean> response = new Response<Boolean>();
        String userId = getUserId(request);
        if(StringUtils.isEmpty(userId)){
            log.error("param store is null");
            response.setSuccess(false);
            response.setErrorMessage("用户不存在！");
            return ActionResponse.wrap(response);
        }
        String storeId = getStoreId(request);
        Response<StoreDto> storeRes = organizationService.queryStoreById(storeId);
        if(storeRes == null || !storeRes.isSuccess()){
            log.error("storeRes is null");
            response.setSuccess(false);
            response.setErrorMessage("门店不存在！");
            return ActionResponse.wrap(response);
        }
        StoreDto store = storeRes.getResultObject();
        Map initalParams = new HashMap();
        initalParams.put("storeId", storeId);
        initalParams.put("branchCompanyId", store.getBranchCompanyId());
        initalParams.put("deliveryWarehouseCode", store.getDeliveryWarehouseCode());
        response = purchaseDubboService.initalShoppingCart(userId, initalParams);
        return ActionResponse.wrap(response);
    }



    /**
     *加载购物车
     * @param request
     * @return
     */
    @RequestMapping(value = "loadCart", method = RequestMethod.GET)
    public Response<ShoppingCartVo> loadShoppingCart(@RequestParam(required = false) boolean checkInventory, HttpServletRequest request) {

        Response<ShoppingCartVo> result = new Response<ShoppingCartVo>();
        String userId = getUserId(request);
        if (StringUtils.isEmpty(userId)) {
            userId = request.getParameter("userId");
            request.getSession().setAttribute("userId", userId);
        }
        if (StringUtils.isEmpty(userId)) {
            result.setCode(CommonsEnum.RESPONSE_500.getCode());
            result.setSuccess(false);
            result.setErrorMessage("用户信息错误!");
            ActionResponse.wrap(result);
        }
        Response<ShoppingCart> response = purchaseDubboService.loadShoppingCart(userId, checkInventory);
//        log.info("loadCart:{}",JSON.toJSONString(response));
        if (response.isSuccess()) {
            ShoppingCart cart = response.getResultObject();
            if (cart != null) {
                log.info("shoppingCart--->", JSONObject.toJSONString(cart.getCurrentOrder()));
            }
            ShoppingCartVo cartVo = convert(cart);
            result.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            result.setCode(CommonsEnum.RESPONSE_200.getCode());
            result.setSuccess(true);
            result.setResultObject(cartVo);
            log.info("shoppingCart--->", JSONObject.toJSONString(cartVo));
        }
        return ActionResponse.wrap(result);
    }

    @RequestMapping(value="loadCartCount",method= RequestMethod.GET)
    public Response<CartCountVo> loadCartCount(HttpServletRequest request){

        Response<CartCountVo> result = new Response<CartCountVo>();
        CartCountVo cartCountVo =new CartCountVo();
        String userId = getUserId(request);
        if(StringUtils.isEmpty(userId)){
            userId = request.getParameter("userId");
            request.getSession().setAttribute("userId",userId );
        }
        if(StringUtils.isEmpty(userId)){
            result.setCode(CommonsEnum.RESPONSE_500.getCode());
            result.setSuccess(false);
            result.setErrorMessage("用户信息错误!");
            return ActionResponse.wrap(result);
        }
        Response<ShoppingCart>  response =purchaseDubboService.loadShoppingCart(userId, false);
        if(response.isSuccess()){
            ShoppingCart cart=response.getResultObject();
            if (cart !=null &&cart.getCurrentOrder()!=null){
                cartCountVo.setCartCount(cart.getCurrentOrder().getTotalCommerceItemQty());
                //log.info("shoppingCart--->",JSONObject.toJSONString(cart.getCurrentOrder()) );
            }
            result.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            result.setCode(CommonsEnum.RESPONSE_200.getCode());
            result.setSuccess(true);
            result.setResultObject( cartCountVo);
        }
        return ActionResponse.wrap(result);
    }
    
    @RequestMapping(value="setUseCoupon",method= RequestMethod.GET)
    public Response<HashMap<String,Object>> setUseCoupon(@RequestParam boolean useCoupon,HttpServletRequest request){
        Response<HashMap<String,Object>> result = new Response<HashMap<String,Object>>();
        HashMap<String,Object> responseMap=new HashMap<String,Object>();
        String userId = getUserId(request);
        if(StringUtils.isEmpty(userId)){
            userId = request.getParameter("userId");
            request.getSession().setAttribute("userId",userId );
        }
        if(StringUtils.isEmpty(userId)){
            result.setCode(CommonsEnum.RESPONSE_500.getCode());
            result.setSuccess(false);
            result.setErrorMessage("用户信息错误!");
            return result;
        }
        Response<ShoppingCart>  response =purchaseDubboService.setUseCoupon(userId, useCoupon);
        if(response.isSuccess()){
            result.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            result.setCode(CommonsEnum.RESPONSE_200.getCode());
            result.setSuccess(true);
            result.setResultObject( responseMap);
        }
        return ActionResponse.wrap(result);
    }
    
    @RequestMapping(value="getUseCoupon",method= RequestMethod.GET)
    public Response<HashMap<String,Object>> getUseCoupon(HttpServletRequest request){
        Response<HashMap<String,Object>> result = new Response<HashMap<String,Object>>();
        HashMap<String,Object> responseMap=new HashMap<String,Object>();
        String userId = getUserId(request);
        if(StringUtils.isEmpty(userId)){
            userId = request.getParameter("userId");
            request.getSession().setAttribute("userId",userId );
        }
        if(StringUtils.isEmpty(userId)){
            result.setCode(CommonsEnum.RESPONSE_500.getCode());
            result.setSuccess(false);
            result.setErrorMessage("用户信息错误!");
            return result;
        }
        Response<ShoppingCart>  response =purchaseDubboService.loadShoppingCart(userId);
        if(response.isSuccess()){
            ShoppingCart cart=response.getResultObject();
            boolean rHasSuperposePromotion = cart.getCurrentOrder().isHasNotSuperposedPromotion();
            boolean rUseCoupon = cart.getCurrentOrder().isUseCoupon();
            responseMap.put("useCoupon", rUseCoupon);
            responseMap.put("hasSuperposePromotion", rHasSuperposePromotion);
            result.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            result.setCode(CommonsEnum.RESPONSE_200.getCode());
            result.setSuccess(true);
            result.setResultObject( responseMap);
        }
        return ActionResponse.wrap(result);
    }
    /**
     * 添加商品到购物车
     * @return
     */
    @RequestMapping(value="addCartItem",method =RequestMethod.POST)
    public Response<CartCountVo> addItemToShoppingCart(@RequestBody AddItemVo addItemVo){

        Response<CartCountVo> resultResponse = new Response<CartCountVo>();
        CartCountVo cartCountVo =new CartCountVo();
        String userId = getUserId();
        AddItemDto addItemDto = BeanConvertUtils.convert(addItemVo,AddItemDto.class);
        Response<ShoppingCart> addItemResult = purchaseDubboService.addItemToShoppingCart(userId,addItemDto );
        if(!addItemResult.isSuccess()){
            resultResponse.setSuccess( false );
            resultResponse.setCode( CommonsEnum.RESPONSE_500.getCode() );
            resultResponse.setErrorMessage( addItemResult.getErrorMessage() );
            return ActionResponse.wrap(resultResponse);
        }
       //把最新的购物车返回
        ShoppingCart cart=addItemResult.getResultObject();
        if (cart !=null &&cart.getCurrentOrder()!=null){
            cartCountVo.setCartCount(cart.getCurrentOrder().getTotalCommerceItemQty());
        }
        resultResponse.setResultObject( cartCountVo );
        resultResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
        resultResponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        resultResponse.setSuccess( true );
        return ActionResponse.wrap(resultResponse);
    }
    /**
     * 从购物车从删除商品
     * @param deleteCartItemVo
     * @return
     */
    @RequestMapping(value="deleteCartItem",method=RequestMethod.POST)
    public Response<ShoppingCartVo> removeItem(@RequestBody DeleteCartItemVo deleteCartItemVo){
        Response<ShoppingCartVo> resultResponse = new Response<ShoppingCartVo>();
        String userId = getUserId();
        Response<ShoppingCart> removeItemResult = purchaseDubboService.removeItem( userId,deleteCartItemVo.getSkuId());
        if(!removeItemResult.isSuccess()){
            resultResponse.setSuccess( false );
            resultResponse.setCode( CommonsEnum.RESPONSE_500.getCode() );
            resultResponse.setErrorMessage( removeItemResult.getErrorMessage() );
            return ActionResponse.wrap(resultResponse);
        }
        //将修改购的购物车返回
        ShoppingCart shoppingCart = removeItemResult.getResultObject();
        ShoppingCartVo cartVo= simpleConvert(shoppingCart);
        resultResponse.setResultObject( cartVo );
        resultResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
        resultResponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        resultResponse.setSuccess( true );
        return ActionResponse.wrap(resultResponse);
    }

    /**
     * 修改购物车中商品的数量
     * @param updateCartItemVo
     * @return
     */
    @RequestMapping(value="updateCart",method=RequestMethod.POST)
    public Response<ShoppingCartVo> updateCommerceItem(@RequestBody UpdateCartItemVo updateCartItemVo){
        Response<ShoppingCartVo> resultResponse = new Response<ShoppingCartVo>();
        String userId = getUserId();
        Response<ShoppingCart> updateItemResult = purchaseDubboService.updateCommerceItem(userId,updateCartItemVo.getSkuId(), updateCartItemVo.getQuantity());
        if(!updateItemResult.isSuccess()){
            resultResponse.setSuccess( false );
            resultResponse.setCode( CommonsEnum.RESPONSE_500.getCode() );
            resultResponse.setErrorMessage( updateItemResult.getErrorMessage() );
            return ActionResponse.wrap(resultResponse);
        }
        //将操作后的购物车返回
        ShoppingCart shoppingCart=updateItemResult.getResultObject();
        ShoppingCartVo cartVo= simpleConvert(shoppingCart);
        resultResponse.setResultObject( cartVo );
        resultResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
        resultResponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        resultResponse.setSuccess( true );
        return ActionResponse.wrap(resultResponse);
    }

    /**
     *购物车中商品的选择情况
     * @param itemSelectsVo
     * @return
     */
    @RequestMapping(value="selectOrNot",method=RequestMethod.POST)
    public Response<ShoppingCartVo> selectOrNot(@RequestBody ItemSelectsVo itemSelectsVo){
        Response<ShoppingCartVo> resultResponse = new Response<ShoppingCartVo>();
        List<ItemSelectVo> itemSelectVos= JSON.parseArray(itemSelectsVo.getItemSelect(),ItemSelectVo.class);
        String userId = getUserId();
        List<ItemSelectDto> itemSelectDtos = BeanConvertUtils.convertList(itemSelectVos,ItemSelectDto.class);
        Response<ShoppingCart> selectItemResult = purchaseDubboService.selectOrNot(userId,itemSelectDtos);
        if(!selectItemResult.isSuccess()){
            resultResponse.setSuccess( false );
            resultResponse.setCode( CommonsEnum.RESPONSE_500.getCode() );
            resultResponse.setErrorMessage( selectItemResult.getErrorMessage() );
            return ActionResponse.wrap(resultResponse);
        }
        //将执行操作后的购物车返回
        ShoppingCart shoppingCart=selectItemResult.getResultObject();
        ShoppingCartVo cartVo= simpleConvert(shoppingCart);
        resultResponse.setResultObject( cartVo );
        resultResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
        resultResponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        resultResponse.setSuccess( true );
        return ActionResponse.wrap(resultResponse);
    }

    /**
     * 购物车中的结算
     * @param request
     * @return
     */
    @RequestMapping(value="moveToCheckout",method=RequestMethod.POST)
    Response<List<String>> moveToCheckout(HttpServletRequest request){
        String userId = getUserId();
        Response<List<String>> settlementOrder = purchaseDubboService.moveToCheckout(userId);
        return ActionResponse.wrap(settlementOrder);
    }

    /**
     * 购物车中的批量删除
     * @param deleteCartItemsVo
     * @return
     */
    @RequestMapping(value="batchDeletetItem",method=RequestMethod.POST)
    Response<ShoppingCartVo> batchDeletetItem(@RequestBody DeleteCartItemsVo deleteCartItemsVo){
        Response<ShoppingCartVo> resultResponse = new Response<ShoppingCartVo>();
        String userId = getUserId();
        List<DeleteCartItemVo> deleteCartItemVos= JSON.parseArray(deleteCartItemsVo.getSkuIds(),DeleteCartItemVo.class);
        List<DeleteCartItemDto> deleteCartItemDtos = BeanConvertUtils.convertList(deleteCartItemVos,DeleteCartItemDto.class);
        Response<ShoppingCart> batchDeletetItem = purchaseDubboService.batchDeletetItem(userId,deleteCartItemDtos);
        if(!batchDeletetItem.isSuccess()){
            resultResponse.setSuccess( false );
            resultResponse.setCode( CommonsEnum.RESPONSE_500.getCode() );
            resultResponse.setErrorMessage( batchDeletetItem.getErrorMessage() );
            return ActionResponse.wrap(resultResponse);
        }
        //将操作后的购物车返回
        ShoppingCart shoppingCart=batchDeletetItem.getResultObject();
        ShoppingCartVo cartVo= simpleConvert(shoppingCart);
        resultResponse.setResultObject( cartVo );
        resultResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
        resultResponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        resultResponse.setSuccess( true );
        return ActionResponse.wrap(resultResponse);
    }

    /**
     * 一键删除购物车中库存不足的商品
     * @return
     */
    @RequestMapping(value= "oneKeyToDelete",method = RequestMethod.POST)
    Response<ShoppingCartVo>  oneKeyToDelete(){
        Response<ShoppingCartVo> resultResponse = new Response<ShoppingCartVo>();
        String userId = getUserId();
        Response<ShoppingCart>  oneKeyToDelete = purchaseDubboService.oneKeyToDelete(userId);
        if(!oneKeyToDelete.isSuccess()){
            resultResponse.setSuccess( false );
            resultResponse.setCode( CommonsEnum.RESPONSE_500.getCode() );
            resultResponse.setErrorMessage( oneKeyToDelete.getErrorMessage() );
            return ActionResponse.wrap(resultResponse);
        }
        //将操作后的购物车返回
        ShoppingCart shoppingCart=oneKeyToDelete.getResultObject();
        ShoppingCartVo cartVo= simpleConvert(shoppingCart);
        resultResponse.setResultObject( cartVo );
        resultResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
        resultResponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        resultResponse.setSuccess( true );
        return ActionResponse.wrap(resultResponse);
    }

    /**
     *把购物车转换成前端需要的
     * @return
     */
    public  ShoppingCartVo convert(ShoppingCart cart){
        ShoppingCartVo cartVo=new ShoppingCartVo();
        cartVo.setAmount(cart.getCurrentOrder().getPriceInfoDto().getAmount());
        cartVo.setDiscountAmount(cart.getCurrentOrder().getPriceInfoDto().getDiscountAmount());
        cartVo.setManualAdjustmentTotal(cart.getCurrentOrder().getPriceInfoDto().getManualAdjustmentTotal());
        cartVo.setRawSubtotal(cart.getCurrentOrder().getPriceInfoDto().getRawSubtotal());
        cartVo.setItemListCount(cart.getCurrentOrder().getItemListCount());
        List<CommerceItemAppVo> rItemList = new ArrayList<CommerceItemAppVo>();
        if (cart != null && cart.getCurrentOrder() != null){
            List<CommerceItemDto> items = cart.getCurrentOrder().getItems();
            for(CommerceItemDto item : items){
                if(CommerceItemTypes.PROMOTION.equals(item.getType())){
                    continue;
                }
                rItemList.add(BeanConvertUtils.convert(item,CommerceItemAppVo.class));
            }
            cartVo.setItems(rItemList);
        }
        cartVo.setCartCount(cart.getCurrentOrder().getTotalCommerceItemQty());
        cartVo.setSelectCount(cart.getCurrentOrder().getSelectedCommerceItemQty());
        cartVo.setSelectAll(cart.getCurrentOrder().getSelectAll());
        return cartVo;

    }

    /**
     * 供选择、修改、删除使用
     * @param cart
     * @return
     */
    public  ShoppingCartVo simpleConvert(ShoppingCart cart){
        ShoppingCartVo cartVo=new ShoppingCartVo();
        cartVo.setAmount(cart.getCurrentOrder().getPriceInfoDto().getAmount());
        cartVo.setDiscountAmount(cart.getCurrentOrder().getPriceInfoDto().getDiscountAmount());
        cartVo.setManualAdjustmentTotal(cart.getCurrentOrder().getPriceInfoDto().getManualAdjustmentTotal());
        cartVo.setRawSubtotal(cart.getCurrentOrder().getPriceInfoDto().getRawSubtotal());
        cartVo.setItemListCount(cart.getCurrentOrder().getItemListCount());
        cartVo.setCartCount(cart.getCurrentOrder().getTotalCommerceItemQty());
        cartVo.setSelectCount(cart.getCurrentOrder().getSelectedCommerceItemQty());
        cartVo.setSelectAll(cart.getCurrentOrder().getSelectAll());
        return cartVo;

    }

    @RequestMapping(value = "/getListAvailablePromotions" , method = RequestMethod.GET)
    public Response<List<com.yatang.sc.dto.PromotionDto>> getListAvailablePromotions(String branchCompanyId, String promoType){
        return promotionDubboService.listAvailablePromotions(branchCompanyId,promoType);
    }


    @RequestMapping(value="/buyItemsAgain",method = RequestMethod.POST)
    public Response<Boolean> buyItemsAgain(@RequestBody BuyItemsAgainVo buyItemsAgainVo){
        log.info("/buyItemsAgain,orderId:{}",buyItemsAgainVo.getOrderId());
        return purchaseDubboService.buyItemsAgain(buyItemsAgainVo.getOrderId(),getUserId());
    }

}
