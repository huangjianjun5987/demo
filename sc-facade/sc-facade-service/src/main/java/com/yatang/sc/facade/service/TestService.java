package com.yatang.sc.facade.service;


import com.busi.common.datatable.PageResult;
import com.yatang.sc.facade.mongo.CommonQueryRequest;
import com.yatang.sc.facade.mongo.MongoTestBean;

import java.util.List;

/**
 * Created by tangqi on 2018/1/9 20:20.
 */
public interface TestService {

    void insert(MongoTestBean bean);

    void update(MongoTestBean bean);

    void bulkInsert(List<MongoTestBean> beans);

    void bulkDelete(List<String> beans);

    MongoTestBean findById(String id);

    List<MongoTestBean> findAll();

    PageResult<List<MongoTestBean>> queryPage(CommonQueryRequest request);
}
