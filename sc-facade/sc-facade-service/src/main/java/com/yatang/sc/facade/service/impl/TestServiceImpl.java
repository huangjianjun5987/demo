package com.yatang.sc.facade.service.impl;

import com.busi.common.datatable.PageResult;
import com.yatang.sc.common.utils.JSONUtils;
import com.yatang.sc.facade.mongo.CommonQueryRequest;
import com.yatang.sc.facade.mongo.MongoQueryTemplate;
import com.yatang.sc.facade.mongo.MongoTestBean;
import com.yatang.sc.facade.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tangqi on 2018/1/9 20:21.
 */
@Service("testService")
public class TestServiceImpl implements TestService {

    @Autowired
    private MongoQueryTemplate mongoQueryTemplate;

    @Override
    public void insert(MongoTestBean bean) {
        String json = JSONUtils.toJson(bean);
        mongoQueryTemplate.insert(json, MongoTestBean.class);
        //homeAreaRepository.insert(bean);
    }

    @Override
    public void update(MongoTestBean bean) {
        mongoQueryTemplate.update(bean.getId(), JSONUtils.toJson(bean), MongoTestBean.class);
    }

    @Override
    public void bulkInsert(List<MongoTestBean> beans) {
        String json = JSONUtils.toJson(beans);
        mongoQueryTemplate.bulkInsert(json, MongoTestBean.class);
    }

    @Override
    public void bulkDelete(List<String> beans) {
        String json = JSONUtils.toJson(beans);
        mongoQueryTemplate.bulkDelete(json, MongoTestBean.class);
    }

    @Override
    public MongoTestBean findById(String id) {
        return (MongoTestBean) mongoQueryTemplate.findById(id, MongoTestBean.class);
    }

    @Override
    public List<MongoTestBean> findAll() {
        return mongoQueryTemplate.findAll(MongoTestBean.class);
    }

    @Override
    public PageResult<List<MongoTestBean>> queryPage(CommonQueryRequest request) {
        return mongoQueryTemplate.queryPage(request, MongoTestBean.class);
    }
}
