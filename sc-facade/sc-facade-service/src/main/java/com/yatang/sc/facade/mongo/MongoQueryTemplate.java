package com.yatang.sc.facade.mongo;

import com.busi.common.datatable.PageResult;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yatang.sc.common.utils.JSONUtils;
import com.yatang.sc.facade.mongo.assemble.AboutAssemble;
import com.yatang.sc.facade.mongo.assemble.DateAssemble;
import com.yatang.sc.facade.mongo.assemble.InAssemble;
import com.yatang.sc.facade.mongo.assemble.NumberAssemble;
import com.yatang.sc.facade.mongo.assemble.StringAssemble;
import com.yatang.sc.facade.mongo.assemble.TypeAssemble;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
*@Author tangqi
*@Date 2018/1/10 13:39
*@Desc
*/
@Component
public class MongoQueryTemplate<T> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static ObjectMapper mapper = new ObjectMapper();

    private List<TypeAssemble> assembles = new ArrayList<>();

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private StringAssemble stringAssemble;

    @Autowired
    private DateAssemble dateAssemble;

    @Autowired
    private NumberAssemble numberAssemble;

    @Autowired
    private AboutAssemble aboutAssemble;

    @Autowired
    private InAssemble inAssemble;

    public void insert(String entityJson, Class<T> clazz) {
        logger.info("MongoQueryTemplate--将要保存的单个文档：{}", entityJson);
        T object = JSONUtils.toObject(entityJson, clazz);
        if (!existCollection(clazz)) {
            logger.info("MongoQueryTemplate--新增集合：{}", clazz.getSimpleName());
            mongoTemplate.createCollection(clazz.getSimpleName());
        }
        mongoTemplate.save(object);
    }

    public void update(String entityId, String entityJson, Class<T> clazz) {
        logger.info("MongoQueryTemplate--将要更新的文档ID：{}", entityId);
        Object objectDB = findById(entityId, clazz);
        if (objectDB == null) {
            logger.error("MongoQueryTemplate--文档在数据库中不存在");
            throw new RuntimeException("文档在数据库中不存在");
        }
        T object = JSONUtils.toObject(entityJson, clazz);
        mongoTemplate.save(object);
    }

    public void delete(String entityId, Class<T> clazz) {
        logger.info("MongoQueryTemplate--将要删除的文档ID：{}", entityId);
        Object objectDB = findById(entityId, clazz);
        if (objectDB == null) {
            logger.error("MongoQueryTemplate--文档在数据库中不存在");
            throw new RuntimeException("文档在数据库中不存在");
        }
        mongoTemplate.remove(objectDB);
    }

    public void bulkInsert(String entityList, Class<T> clazz) {
        logger.info("MongoQueryTemplate--将要新增的文档ID列表：{}", entityList);
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
            List list = mapper.readValue(entityList, javaType);
            if (!existCollection(clazz)) {
                logger.info("MongoQueryTemplate--新增集合：{}", clazz.getSimpleName());
                mongoTemplate.createCollection(clazz.getSimpleName());
            }
            mongoTemplate.insertAll(list);
        } catch (Exception e) {
            logger.error("mongoQueryTemplate-批量插入数据失败：", e);
            throw new RuntimeException("批量插入数据失败", e);
        }

    }

    public void bulkDelete(String idList, Class<T> clazz) {
        logger.info("MongoQueryTemplate--将要删除的文档ID列表：{}", idList);
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, String.class);
            List<String> list = mapper.readValue(idList, javaType);

            Query query = new Query();
            query.addCriteria(Criteria.where("_id").in(list));
            mongoTemplate.findAllAndRemove(query, clazz);
        } catch (Exception e) {
            logger.error("mongoQueryTemplate-批量插入数据失败：", e.getMessage());
            throw new RuntimeException("批量删除数据失败", e);
        }
    }

    public Object findById(String id, Class<T> clazz) {
        logger.info("根据Id查询文档：{}", id);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, clazz);
    }

    public List<T> findAll(Class<T> clazz) {
        return mongoTemplate.findAll(clazz);
    }

    public PageResult<T> queryPage(CommonQueryRequest request, Class<T> clazz) {
        logger.info("多条件查询参数：{}", JSONUtils.toJson(request));
        PageRequest pageRequest = buildPageRequest(request);
        Query query = new Query();
        query.limit(pageRequest.getPageSize());
        query.skip((pageRequest.getPageNumber() - 1) * pageRequest.getPageSize());
        query.with(pageRequest.getSort());
        Map<String, Criteria> criteriaMap = new HashMap<>();
        Map queryRequestMap = JSONUtils.toMap(request.getQueryJson());
        if (queryRequestMap == null) {
            queryRequestMap = new HashMap();
        }

        for (Iterator<Map.Entry<String, Object>> requestIterator = queryRequestMap.entrySet().iterator(); requestIterator.hasNext(); ) {
            Map.Entry<String, Object> entry = requestIterator.next();
            if(entry.getValue() == null){
                continue;
            }
            executeAssemble(entry, criteriaMap);
        }

        Iterator<Map.Entry<String, Criteria>> iterator = criteriaMap.entrySet().iterator();
        while (iterator.hasNext()) {
            query.addCriteria(iterator.next().getValue());
        }
        List<T> list = mongoTemplate.find(query, clazz);
        PageResult result = new PageResult();
        result.setResultObject(list);
        result.setRecordsTotal(Integer.parseInt(new Long(mongoTemplate.count(query, clazz)).toString()));
        return result;
    }

    private void executeAssemble(Map.Entry<String, Object> entry, Map<String, Criteria> criteriaMap) {
        Iterator<TypeAssemble> iterator = assembles.iterator();
        while (iterator.hasNext()) {
            TypeAssemble assemble = iterator.next();
            if (assemble.assemble(entry, criteriaMap)) {
                break;
            }
        }
    }

    private PageRequest buildPageRequest(CommonQueryRequest request) {
        long pageNumber = request.getPageNum();
        long pageSize = request.getPageSize();
        pageNumber = pageNumber < 0 ? 0 : pageNumber;
        pageSize = pageSize < 0 ? 0 : pageSize;
        if (request.getSortParam().isEmpty()) {
            return new PageRequest((int) pageNumber, (int) pageSize);
        }
        Map<String, String> sortMap = request.getSortParam();
        Sort sort = null;
        Iterator<Map.Entry<String, String>> iterator = sortMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            Sort newSort = new Sort(Sort.Direction.fromString(entry.getValue()), entry.getKey());
            if (sort == null) {
                sort = newSort;
            } else {
                sort.and(newSort);
            }
        }
        return new PageRequest((int) pageNumber, (int) pageSize, sort);
    }

    private boolean existCollection(Class<T> clazz) {
        return mongoTemplate.collectionExists(clazz.getSimpleName());
    }

    @PostConstruct
    public void initAssemble() {
        assembles.clear();
        assembles.add(stringAssemble);
        assembles.add(dateAssemble);
        assembles.add(numberAssemble);
        assembles.add(aboutAssemble);
        assembles.add(inAssemble);
    }
}
