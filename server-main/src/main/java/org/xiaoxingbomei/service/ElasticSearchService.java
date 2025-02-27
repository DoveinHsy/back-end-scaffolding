//package org.xiaoxingbomei.service;
//
//
//
//import org.elasticsearch.client.indices.CreateIndexResponse;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.reindex.BulkByScrollResponse;
//import org.xiaoxingbomei.entity.ElasticIndex;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * es service
// */
//public interface ElasticSearchService
//{
//    /**
//     * @description: 创建索引
//     */
//    CreateIndexResponse createIndex(String indexName);
//
//    /**
//     * @description: 判断索引, 判断其是否存在
//     */
//    boolean existIndex(String indexName);
//
//    /**
//     * @description: 删除索引
//     */
//    void deleteIndex(String indexName);
//
//    /**
//     * @description: 添加文档
//     */
//    void addDocument(ElasticIndex elasticIndex, String key, Object o);
//
//    /**
//     * @description: 根据key获得文档的信息
//     */
//    <T> T getDocument(ElasticIndex elasticIndex, String key, Class<T> clazz);
//
//    <T> List<T> multiGetDocument(ElasticIndex elasticIndex, List<String> keys, Class<T> clazz);
//
//    BulkByScrollResponse updateByQueryByWhere(ElasticIndex elasticIndex, BoolQueryBuilder boolQueryBuilder, Map<String, Object> document);
//
//    /**
//     * @description: 根据key更新文档
//     */
//    <T> void updateDocument(ElasticIndex elasticIndex, String key, T o);
//
//    /**
//     * @description: 删除文档记录
//     */
//    void deleteDocument(ElasticIndex elasticIndex, String key);
//
//    /**
//     * @description: 匹配查询
//     */
//    <T> List<T> searchQueryAll(ElasticIndex elasticIndex, BoolQueryBuilder boolBuilder, String[] fields, Class<T> clazz, String orderField, String orderType);
//
//}
