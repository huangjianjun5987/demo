package com.yatang.sc.app.purchase.action;


import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.app.action.AppBaseAction;
import com.yatang.sc.app.res.ActionResponse;
import com.yatang.sc.app.vo.wish.SaveWishVo;
import com.yatang.sc.app.vo.wish.ScanCodeVo;
import com.yatang.sc.app.vo.wish.WishListConditionVo;
import com.yatang.sc.app.vo.wish.WishListsVo;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.purchase.dto.wish.*;
import com.yatang.sc.purchase.dubboservice.WishQueryDubboService;
import com.yatang.sc.purchase.dubboservice.WishWriteDubboService;
import com.yatang.xc.mbd.biz.org.dto.StoreDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @描述:
 * @类名:
 * @作者:xiangyonghong
 * @创建时间:2018/01/02 17:28
 * @版本:v1.0
 */
@RestController
@RequestMapping(value = "/sc/wish")
public class WishAction extends AppBaseAction {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    WishQueryDubboService wishQueryDubboService;

    @Autowired
    WishWriteDubboService wishWriteDubboService;

    @Autowired
    OrganizationService organizationService;


    /**
     * 扫码查询想预定的商品
     * @param scanCodeVo
     * @param request
     * @return
     */
    @RequestMapping(value = "/barCode",method = RequestMethod.GET)
    public Response<WishListsVo> scanCode(ScanCodeVo scanCodeVo, HttpServletRequest request){
        log.info("/sc/wish/barCode.");
        String storeId = getStoreId(request);
        Response<WishListsVo> response = new Response<>();
        Response<StoreDto> storeRes = organizationService.queryStoreById(storeId);
        if(storeRes == null || !storeRes.isSuccess()){
            log.error("storeRes is null");
            response.setSuccess(false);
            response.setErrorMessage("门店不存在！");
            return ActionResponse.wrap(response);
        }
        StoreDto store = storeRes.getResultObject();
        ScanCodeDto scanCodeDto = BeanConvertUtils.convert(scanCodeVo,ScanCodeDto.class);
        scanCodeDto.setUserId(getUserId());
        scanCodeDto.setWareHouseCode(store.getDeliveryWarehouseCode());
        scanCodeDto.setBranchCompanyId(getBranchCompanyId(request));
        Response<WishListsDto> responseDto = wishQueryDubboService.scanCode(scanCodeDto);
        WishListsVo wishListsVo = BeanConvertUtils.convert(responseDto.getResultObject(),WishListsVo.class);
        response.setSuccess(responseDto.isSuccess());
        response.setCode(responseDto.getCode());
        response.setErrorMessage(responseDto.getErrorMessage());
        response.setResultObject(wishListsVo);
        return ActionResponse.wrap(response);
    }

    /**
     * 保存想预定的商品
     * @param saveWishLists
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveWishLists",method = RequestMethod.POST)
    public Response<Boolean> saveWishLists(@RequestBody List<SaveWishVo> saveWishLists,HttpServletRequest request){
        log.info("/sc/wish/saveWish,param:{}", JSON.toJSONString(saveWishLists));
        List<SaveWishListsDto> saveWishListsDto = BeanConvertUtils.convertList(saveWishLists,SaveWishListsDto.class);
        SaveWishDto saveWishDto = new SaveWishDto();
        saveWishDto.setFranchiserId(getUserId());
        saveWishDto.setBranchCompanyId(getBranchCompanyId(request));
        saveWishDto.setStoreId(getStoreId());
        saveWishDto.setSaveWishListsDtoList(saveWishListsDto);
        return ActionResponse.wrap(wishWriteDubboService.saveWishLists(saveWishDto));
    }

    /**
     * 查询我预定的商品
     * @return
     */
    @RequestMapping(value = "/wishLists",method = RequestMethod.GET)
    public Response<PageResult<WishListsVo>> wishLists(WishListConditionVo conditionVo){
        log.info("/sc/wish/wishLists.");
        Response<PageResult<WishListsVo>> response = new Response<>();
        WishListConditionDto wishListConditionDto = BeanConvertUtils.convert(conditionVo,WishListConditionDto.class);
        wishListConditionDto.setFranchiserId(getUserId());
        Response<PageResult<WishListsDto>> responseDto = wishQueryDubboService.wishLists(wishListConditionDto);
        List<WishListsVo> wishListsVoList = BeanConvertUtils.convertList(responseDto.getResultObject().getData(),WishListsVo.class);
        PageResult<WishListsVo> pageResult = new PageResult<>();
        pageResult.setData(wishListsVoList);
        pageResult.setPageNum(responseDto.getResultObject().getPageNum());
        pageResult.setPageSize(responseDto.getResultObject().getPageSize());
        pageResult.setTotal(responseDto.getResultObject().getTotal());
        response.setCode(responseDto.getCode());
        response.setSuccess(responseDto.isSuccess());
        response.setErrorMessage(responseDto.getErrorMessage());
        response.setResultObject(pageResult);
        return ActionResponse.wrap(response);
    }

    /**
     * 查询我预定的已经到货的商品
     * @param request
     * @return
     */
    @RequestMapping(value = "/wishRealizedList",method = RequestMethod.GET)
    public Response<PageResult<WishListsVo>> wishRealizedList(WishListConditionVo conditionVo,HttpServletRequest request){
        log.info("/sc/wish/wishRealizedList.");
        Response<PageResult<WishListsVo>> response = new Response<>();
        String storeId = getStoreId(request);
        Response<StoreDto> storeRes = organizationService.queryStoreById(storeId);
        if(storeRes == null || !storeRes.isSuccess()){
            log.error("storeRes is null");
            response.setSuccess(false);
            response.setErrorMessage("门店不存在！");
            return ActionResponse.wrap(response);
        }
        StoreDto store = storeRes.getResultObject();
        WishListConditionDto wishListConditionDto = BeanConvertUtils.convert(conditionVo,WishListConditionDto.class);
        wishListConditionDto.setFranchiserId(getUserId());
        wishListConditionDto.setWareHouseCode(store.getDeliveryWarehouseCode());
        wishListConditionDto.setFranchiserId(store.getFranchiseeId());
        wishListConditionDto.setBranchCompanyId(store.getBranchCompanyId());
        Response<PageResult<WishListsDto>> responseDto = wishQueryDubboService.wishRealizedList(wishListConditionDto);
        List<WishListsVo> wishListsVoList = BeanConvertUtils.convertList(responseDto.getResultObject().getData(),WishListsVo.class);
        PageResult<WishListsVo> pageResult = new PageResult<>();
        pageResult.setData(wishListsVoList);
        pageResult.setPageNum(responseDto.getResultObject().getPageNum());
        pageResult.setPageSize(responseDto.getResultObject().getPageSize());
        pageResult.setTotal(responseDto.getResultObject().getTotal());
        response.setCode(responseDto.getCode());
        response.setErrorMessage(responseDto.getErrorMessage());
        response.setSuccess(responseDto.isSuccess());
        response.setResultObject(pageResult);
        return ActionResponse.wrap(response);
    }


}
