package com.yatang.sc.service;

import com.yatang.xc.mbd.biz.org.dto.BranchCompanyDto;
import com.yatang.xc.mbd.biz.org.dto.FranchiseeDto;
import com.yatang.xc.mbd.biz.org.dto.StoreDto;

public interface OrganizationHelper {

    BranchCompanyDto findBranchCompanyById(String branchCompanyId);

    FranchiseeDto findFranchiseeById(String franchiseeId);

    StoreDto findStoreById(String storeId);
}
