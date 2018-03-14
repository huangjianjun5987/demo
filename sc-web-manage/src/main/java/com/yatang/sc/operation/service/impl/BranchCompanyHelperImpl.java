package com.yatang.sc.operation.service.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.StringUtils;
import com.yatang.sc.common.localcache.annotation.LocalCache;
import com.yatang.sc.operation.service.BranchCompanyHelper;
import com.yatang.xc.mbd.biz.org.dto.BranchCompanyDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by liuxiaokun on 11/12/2017.
 */
@Service("branchCompanyHelper")
public class BranchCompanyHelperImpl implements BranchCompanyHelper {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrganizationService organizationService;

    @Override
    @LocalCache(group = "companyCacheById", expireAfterWrite = 10, maximumSize = 500)
    public BranchCompanyDto queryBranchCompanyById(String branchCompanyId) {
        logger.info("start finding branch company by id:" + branchCompanyId);
        if (StringUtils.isEmpty(branchCompanyId)) {
            return null;
        }
        Response<BranchCompanyDto> response = organizationService.querySimpleByBranchCompanyId(branchCompanyId);
        if (response == null) {
            logger.warn("response is null, can't find branch company with id:" + branchCompanyId);
            return null;
        }
        if (response.isSuccess()) {
            return response.getResultObject();
        } else {
            logger.warn(response.getErrorMessage() + ",can't find branch company with id:" + branchCompanyId);
            return null;
        }
    }
}
