package com.yatang.sc.facade.mongo.assemble;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Map;

/**
 * Created by tangqi on 2018/1/10 17:22.
 */
public interface TypeAssemble {

    boolean assemble(Map.Entry<String, Object> entry, Map<String, Criteria> criteriaMap);
}
