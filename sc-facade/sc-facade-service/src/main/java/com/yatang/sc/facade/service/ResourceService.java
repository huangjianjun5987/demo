package com.yatang.sc.facade.service;

import java.util.List;
import java.util.Set;

import com.yatang.sc.facade.domain.ResourcePo;

/**
 * @描述: 资源权限服务.
 * @作者: huangjianjun
 * @创建时间: 2017年6月8日 下午9:39:52 .
 */
public interface ResourceService {


    ResourcePo findOne(Long resourceId);
    
    List<ResourcePo> queryAllRes();

    List<ResourcePo> readLeftMenuOrButtonByIdAndLoginName(Long resParentId, String loginName);

    Boolean isRelatedWithRole(Long id);

    boolean deleteRes(List<Long> ids);

    boolean updateRes(ResourcePo resourcePo);

    boolean createRes(ResourcePo resourcePo);

    List<ResourcePo> queryResByIds(Set<Long> resIds);

    List<ResourcePo> makeTree(Long resParentId, List<ResourcePo> pos);
}
