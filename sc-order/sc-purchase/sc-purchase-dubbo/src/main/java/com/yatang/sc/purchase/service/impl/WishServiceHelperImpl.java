package com.yatang.sc.purchase.service.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.common.lock.RedisDistributedLockFactory;
import com.yatang.sc.common.staticvalue.WishStatus;
import com.yatang.sc.order.domain.WishDetailPo;
import com.yatang.sc.order.domain.WishListPo;
import com.yatang.sc.order.service.WishService;
import com.yatang.sc.product.service.ProductHelper;
import com.yatang.sc.purchase.dto.wish.SaveWishDto;
import com.yatang.sc.purchase.dto.wish.SaveWishListsDto;
import com.yatang.sc.purchase.service.WishServiceHelper;
import com.yatang.xc.mbd.biz.org.dto.StoreDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("wishServiceHelper")
public class WishServiceHelperImpl implements WishServiceHelper {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    ProductHelper productHelper;

    @Autowired
    WishService wishService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    private RedisDistributedLockFactory mRedisDistributedLockFactory;

    @Override
    @Transactional
    public boolean saveWishLists(SaveWishDto saveWishDto) {
        log.info("saveWishLists,param:{}", JSON.toJSONString(saveWishDto));
        List<String> barCodes = new ArrayList<>();
        for (SaveWishListsDto saveWishListsDto : saveWishDto.getSaveWishListsDtoList()){
            barCodes.add(saveWishListsDto.getBarCode());
        }
        StoreDto storeDto = null;
        Response<StoreDto> storeDtoResponse = organizationService.queryStoreById(saveWishDto.getStoreId());
        if(storeDtoResponse == null || !storeDtoResponse.isSuccess() || storeDtoResponse.getResultObject() == null){
            return false;
        }
        storeDto = storeDtoResponse.getResultObject();
        Date nowTime = new Date();
        for (SaveWishListsDto saveWishListsDto : saveWishDto.getSaveWishListsDtoList()){
            String lockPath = "/wish_lists_lock_" + saveWishDto.getBranchCompanyId()+"_"+saveWishListsDto.getBarCode();
            RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
            try{
                rLock.lock();
                saveOrUpdateWishLists(saveWishListsDto,barCodes,saveWishDto,nowTime,storeDto);
            }catch (Exception e){
                log.error("saveWishLists,param:{}",JSON.toJSONString(saveWishDto),e);
                return false;
            }finally {
                rLock.unlock();
            }
        }
        return true;
    }

    private void saveOrUpdateWishLists(SaveWishListsDto saveWishListsDto, List<String> barCodes,SaveWishDto saveWishDto,Date nowTime,StoreDto storeDto) {
        Map<String,Object> cd = new HashMap<>();
        cd.put("barCode",saveWishListsDto.getBarCode());
        cd.put("branchCompanyId",saveWishDto.getBranchCompanyId());
        WishListPo wishListPo = wishService.findWishListByCondition(cd);
        if(wishListPo != null){
            log.info("saveWishLists,wishList exits.");
            Map<String,Object> condition = new HashMap<>();
            condition.put("wishListId",wishListPo.getId());
            condition.put("storeId",saveWishDto.getStoreId());
            long count = wishService.queryWishDetailCountByCondition(condition);
            if(count > 0){
                log.info("wishDetail exist,wishListId:{},storeId:{},",wishListPo.getId(),saveWishDto.getStoreId());
                return;
            }
            wishListPo.setTotalQuantity(wishListPo.getTotalQuantity() + saveWishListsDto.getQuantity());
            wishService.updateWish(wishListPo);
        }else{
            log.info("saveWishLists,wishList not exits.");
            Map<String,ProductIndexDto> products = productHelper.findProductByInternationalCodes(barCodes);
            ProductIndexDto productIndexDto = products.get(saveWishListsDto.getBarCode());
            wishListPo = new WishListPo();
            wishListPo.setGbCode(saveWishListsDto.getBarCode());
            wishListPo.setTotalQuantity(saveWishListsDto.getQuantity());
            wishListPo.setBranchCompanyId(saveWishDto.getBranchCompanyId());
            wishListPo.setBranchCompanyName(storeDto.getBranchCompanyName());
            wishListPo.setCreateTime(nowTime);
            wishListPo.setStatus(WishStatus.init.getCode());
            if(productIndexDto != null){
                wishListPo.setProductCode(productIndexDto.getProductCode());
                wishListPo.setProductName(productIndexDto.getSaleName());
                wishListPo.setProductImg(productIndexDto.getThumbnailImage());
            }
            wishService.saveWishListPo(wishListPo);

        }
        WishDetailPo wishDetailPo = new WishDetailPo();
        wishDetailPo.setWishListId(wishListPo.getId());
        wishDetailPo.setCreateTime(nowTime);
        wishDetailPo.setStoreId(saveWishDto.getStoreId());
        wishDetailPo.setStoreName(storeDto.getName());
        wishDetailPo.setFranchiserId(saveWishDto.getFranchiserId());
        wishDetailPo.setQuantity(saveWishListsDto.getQuantity());
        wishService.saveWishDetail(wishDetailPo);
    }
}
