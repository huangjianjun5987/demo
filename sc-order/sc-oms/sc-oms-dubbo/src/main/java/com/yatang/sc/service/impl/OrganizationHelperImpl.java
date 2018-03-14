package com.yatang.sc.service.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.StringUtils;
import com.yatang.sc.common.localcache.annotation.LocalCache;
import com.yatang.sc.service.OrganizationHelper;
import com.yatang.xc.mbd.biz.org.dto.BranchCompanyDto;
import com.yatang.xc.mbd.biz.org.dto.FranchiseeDto;
import com.yatang.xc.mbd.biz.org.dto.StoreDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Log
@Service("organizationHelper")
public class OrganizationHelperImpl implements OrganizationHelper {

    @Autowired
    OrganizationService organizationService;

    @Override
    @LocalCache(group = "branchCompany", maximumSize = 1000,expireAfterWrite = 20)
    public BranchCompanyDto findBranchCompanyById(String branchCompanyId) {

        log.info("start find branch company by id:" + branchCompanyId);
        if(StringUtils.isEmpty(branchCompanyId)){
            return null;
        }
        Response<BranchCompanyDto> response = organizationService.querySimpleByBranchCompanyId(branchCompanyId);
        if(response == null){
            log.warning("response is null, can't find branch company:" + branchCompanyId);
            return null;
        }
        if(response.isSuccess()){
            return response.getResultObject();
        }else{
            log.warning(response.getErrorMessage() + ",can't find branch company:" + branchCompanyId);
            return null;
        }
    }

    @Override
    public FranchiseeDto findFranchiseeById(String franchiseeId) {
        log.info("start find franchisee by id:" + franchiseeId);
        if(StringUtils.isEmpty(franchiseeId)){
            return null;
        }
        Response<FranchiseeDto> response = organizationService.querySimpleById(franchiseeId);
        if(response == null){
            log.warning("response is null, can't find franchisee:" + franchiseeId);
            return null;
        }
        if(response.isSuccess()){
            return response.getResultObject();
        }else{
            log.warning(response.getErrorMessage() + ",can't find franchisee:" + franchiseeId);
            return null;
        }
    }

    @Override
    public StoreDto findStoreById(String storeId) {
        log.info("start find storeDto by id:" + storeId);
        if(StringUtils.isEmpty(storeId)){
            return null;
        }
        Response<StoreDto> response = organizationService.queryStoreById(storeId);
        if(response == null){
            log.warning("response is null, can't find storeDto:" + storeId);
            return null;
        }
        if(response.isSuccess()){
            return response.getResultObject();
        }else{
            log.warning(response.getErrorMessage() + ",can't find storeDto:" + storeId);
            return null;
        }
    }
}
