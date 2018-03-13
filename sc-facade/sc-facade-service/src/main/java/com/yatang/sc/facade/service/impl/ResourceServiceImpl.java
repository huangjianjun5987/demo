package com.yatang.sc.facade.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.yatang.sc.facade.dao.RoleResourceDao;
import com.yatang.sc.facade.domain.UserPo;
import com.yatang.sc.facade.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yatang.sc.facade.dao.ResourceDao;
import com.yatang.sc.facade.domain.ResourcePo;
import com.yatang.sc.facade.service.ResourceService;
import org.springframework.util.CollectionUtils;

/**
 * @描述: 资源服务类.
 * @作者: liuxiaokun
 * @创建时间: 2018年1月16日 下午2:38:22 .
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;
    
    @Autowired
    private UserService userService;

    @Autowired
    private RoleResourceDao roleResourceDao;


    @Override
    public ResourcePo findOne(Long resourceId) {
        return resourceDao.selectByPrimaryKey(resourceId);
    }

    @Override
    public List<ResourcePo> queryAllRes() {
        List<ResourcePo> resourcePos = resourceDao.findAll();
        if(CollectionUtils.isEmpty(resourcePos)){
            return Collections.EMPTY_LIST;
        }
        List<ResourcePo> result = makeTree(0L, resourcePos);

        /*ToDo 按钮权限*/

        return result;
    }

    @Override
    public List<ResourcePo> readLeftMenuOrButtonByIdAndLoginName(Long resParentId, String loginName) {

        UserPo userPo = userService.findByUsername(loginName);
        if(userPo == null){
            return Collections.EMPTY_LIST;
        }

        List<ResourcePo> pos = resourceDao.queryPermissionsByRoleIds(userPo.getRoleIds());
        if(CollectionUtils.isEmpty(pos)){
            return Collections.EMPTY_LIST;
        }
        List<ResourcePo> result = makeTree(resParentId, pos);

        return result;
    }

    @Override
    public Boolean isRelatedWithRole(Long id) {
        return roleResourceDao.countRelatedResWithRole(id) > 0;
    }

    @Override
    public boolean deleteRes(List<Long> ids) {
        for(Long id : ids){
            recursionDeleteChildren(id);
            roleResourceDao.deleteResRoleRelation(id);
            resourceDao.deleteByPrimaryKey(id);
        }
        return true;
    }

    private void recursionDeleteChildren(Long parentId) {
        List<ResourcePo> children = resourceDao.selectByParentId(parentId);
        if(children.size() == 0){
            return;
        }
        for(ResourcePo child : children){
            resourceDao.deleteByPrimaryKey(child.getId());
            roleResourceDao.deleteResRoleRelation(child.getId());
            recursionDeleteChildren(child.getId());
        }
    }

    @Override
    public boolean updateRes(ResourcePo resourcePo) {
        resourcePo.setUpdateTime(new Date());
        return resourceDao.updateByPrimaryKeySelective(resourcePo) > 0;
    }

    @Override
    public boolean createRes(ResourcePo resourcePo) {
        resourcePo.setAvailable(true);
        resourcePo.setCreateTime(new Date());
        resourcePo.setUpdateTime(new Date());
        if(resourcePo.getSequence() == null){
            List<ResourcePo> children = resourceDao.selectByParentId(resourcePo.getParentId());
            sortBySequence(children);
            if(children.size() == 0){
                resourcePo.setSequence(1);
            }else{
                resourcePo.setSequence(children.get(children.size()-1).getSequence()+1);
            }
        }
        return resourceDao.insertSelective(resourcePo) > 0;
    }

    @Override
    public List<ResourcePo> queryResByIds(Set<Long> resIds) {
        return resourceDao.queryResByIds(resIds);
    }

    /**
     * 形成一个2层的树型结构。如：menu > page 或者 page > button
     * @param resParentId
     * @param pos
     * @return
     */
    @Override
    public List<ResourcePo> makeTree(Long resParentId, List<ResourcePo> pos) {

        List<ResourcePo> result = new ArrayList<>();
        //拿到和父Id匹配的记录
        for(int i = pos.size()-1; i>-1; i--){
            if(pos.get(i).getParentId() == resParentId){
                result.add(pos.get(i));
                pos.remove(i);
            }
        }
        sortBySequence(result);
        //拿到子记录
        for(ResourcePo po : result){
            List<ResourcePo> subResult = new ArrayList<>();
            for(int i = pos.size()-1; i>-1; i--){
                if(po.getId() == pos.get(i).getParentId()){
                    subResult.add(pos.get(i));
                    pos.remove(i);
                    po.setSubRes(subResult);
                }
            }
            sortBySequence(subResult);
        }

        return result;
    }

    private void sortBySequence(List<ResourcePo> result) {
        Collections.sort(result, new Comparator<ResourcePo>() {
            @Override
            public int compare(ResourcePo o1, ResourcePo o2) {
                return o1.getSequence() > o2.getSequence() ? 1 : -1;
            }
        });
    }


}
