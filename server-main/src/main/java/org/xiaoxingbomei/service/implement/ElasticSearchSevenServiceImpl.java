//package org.xiaoxingbomei.service.implement;
//
//import com.alibaba.druid.support.json.JSONUtils;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.google.common.collect.Lists;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
//import org.elasticsearch.action.bulk.BulkRequest;
//import org.elasticsearch.action.delete.DeleteRequest;
//import org.elasticsearch.action.get.*;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.search.ClearScrollRequest;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.search.SearchScrollRequest;
//import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.indices.CreateIndexRequest;
//import org.elasticsearch.client.indices.CreateIndexResponse;
//import org.elasticsearch.client.indices.GetIndexRequest;
//import org.elasticsearch.core.TimeValue;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.reindex.BulkByScrollResponse;
//import org.elasticsearch.index.reindex.UpdateByQueryRequest;
//import org.elasticsearch.script.Script;
//import org.elasticsearch.search.Scroll;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.sort.SortOrder;
//import org.elasticsearch.xcontent.XContentType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.xiaoxingbomei.controller.request.PaginationRequest;
//import org.xiaoxingbomei.entity.ElasticIndex;
//import org.xiaoxingbomei.service.ElasticSearchService;
//import org.xiaoxingbomei.utils.ES_Utils;
//
//import java.util.*;
//
//@Component
//@Slf4j
//public class ElasticSearchSevenServiceImpl implements ElasticSearchService
//{
//    @Autowired
//    RestHighLevelClient client;
//
//    public ElasticIndex getIndex(String indexName)
//    {
//        ElasticIndex elasticIndex;
//        if (!this.existIndex(indexName))
//        {
//            CreateIndexResponse createIndexResponse = this.createIndex(indexName);
//            elasticIndex = new ElasticIndex(createIndexResponse.index());
//        } else {
//            elasticIndex = new ElasticIndex(indexName);
//        }
//        return elasticIndex;
//    }
//
//    /**
//
//     * @description: 索引创建
//     */
//    @Override
//    public CreateIndexResponse createIndex(String indexName)
//    {
//        // 1、创建索引请求
//        CreateIndexRequest request = new CreateIndexRequest(indexName);
//        CreateIndexResponse createIndexResponse = null;
//        // 2、客户端执行请求 IndicesClient,请求后获得响应
//        try
//        {
//            createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
//        } catch (Exception e)
//        {
//            try
//            {
//                createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
//            } catch (Exception e1)
//            {
//                log.error("操作异常", e1);
//            }
//        }
//        return createIndexResponse;
//    }
//
//    /**
//     * @description: 获取索引, 判断其是否存在
//     */
//    @Override
//    public boolean existIndex(String indexName)
//    {
//        boolean flag = false;
//        try
//        {
//            GetIndexRequest request = new GetIndexRequest(indexName);
//            flag = client.indices().exists(request, RequestOptions.DEFAULT);
//        } catch (Exception e)
//        {
//            try
//            {
//                GetIndexRequest request = new GetIndexRequest(indexName);
//                flag = client.indices().exists(request, RequestOptions.DEFAULT);
//            } catch (Exception e1)
//            {
//                log.error("操作异常", e1);
//            }
//        }
//        return flag;
//    }
//
//
//    /**
//     * @description: 删除索引
//     */
//    @Override
//    public void deleteIndex(String indexName)
//    {
//        if (existIndex(indexName))
//        {
//            DeleteIndexRequest request = new DeleteIndexRequest(indexName);
//            // 删除
//            try
//            {
//                client.indices().delete(request, RequestOptions.DEFAULT);
//            } catch (Exception e) {
//                log.error("索引删除失败", e);
//            }
//        }
//    }
//
//    /**
//     * @description: 添加文档
//     */
//    @Override
//    public void addDocument(ElasticIndex elasticIndex, String key, Object o)
//    {
//        IndexRequest request = new IndexRequest(elasticIndex.getIndexName());
//        request.id(key);
//        // 将我们的数据放入请求 json
//        request.source(JSON.toJSONString(o), XContentType.JSON);
//        // 客户端发送请求 , 获取响应的结果
//        try
//        {
//            client.index(request, RequestOptions.DEFAULT);
//        } catch (Exception e)
//        {
//            try
//            {
//                client.index(request, RequestOptions.DEFAULT);
//            } catch (Exception e1)
//            {
//                log.error("新增文档异常", e1);
//            }
//        }
//    }
//
//    /**
//     * 前缀匹配，模糊匹配查询相应文档
//     */
//    public <T> List<T> searchQuery(ElasticIndex elasticIndex, QueryBuilder regexBuilder, QueryBuilder queryBuilder, Class<T> clazz)
//    {
//        SearchSourceBuilder sourceBuilder = SearchSourceBuilder.searchSource();
//        //设置查询条件
//        sourceBuilder.query(regexBuilder);
//        if (Objects.nonNull(queryBuilder))
//        {
//            sourceBuilder.query(queryBuilder);
//        }
//        sourceBuilder.from(0);
//        sourceBuilder.size(10);
//        //创建请求填入 查询条件
//        SearchRequest searchRequest = new SearchRequest(elasticIndex.getIndexName());
//        searchRequest.source(sourceBuilder);
//
//        SearchResponse searchResponse = null;
//        try
//        {
//            //执行条件查询
//            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//        } catch (Exception e)
//        {
//            log.error("es查询数据异常", e);
//        }
//        ArrayList<T> list = new ArrayList<>();
//        if (searchResponse == null)
//        {
//            return list;
//        }
//
//        SearchHits hits = searchResponse.getHits();
//        SearchHit[] searchHits = hits.getHits();
//        //将搜索结果封装到模型
//        if (searchHits != null && searchHits.length > 0)
//        {
//            for (SearchHit hit : searchResponse.getHits().getHits())
//            {
//                list.add(JSON.parseObject(hit.getSourceAsString(), clazz));
//            }
//        }
//        //返回模型集合
//        return list;
//    }
//
//    /**
//     * @description: 添加文档
//     */
//    public void batchUpdateDocument(ElasticIndex elasticIndex, Map<String, String> hashMap) {
//        // 客户端发送请求 , 获取响应的结果
//        try {
//            BulkRequest request = new BulkRequest();
//            int i = 0;
//            for (Map.Entry<String, String> entry : hashMap.entrySet()) {
//                IndexRequest indexRequest = new IndexRequest(elasticIndex.getIndexName());
//                indexRequest.id(entry.getKey());
//                indexRequest.source(entry.getValue(), XContentType.JSON);
//                request.add(indexRequest);
//                i++;
//                if (i == 2000)
//                {
//                    try
//                    {
//                        client.bulk(request, RequestOptions.DEFAULT);
//                    } catch (Exception e) {
//                        try {
//                            client.bulk(request, RequestOptions.DEFAULT);
//                        } catch (Exception e1) {
//                            log.error("批量更新文档异常", e1);
//                        }
//                    }
//                    request = new BulkRequest();
//                    i = 0;
//                }
//            }
//
//            if (i > 0) {
//                try {
//                    client.bulk(request, RequestOptions.DEFAULT);
//                } catch (Exception e) {
//                    try {
//                        client.bulk(request, RequestOptions.DEFAULT);
//                    } catch (Exception e1) {
//                        log.error("批量更新文档异常", e1);
//                    }
//                }
//            }
//        } catch (Exception e2) {
//            log.error("批量更新文档异常", e2);
//        }
//    }
//
//    /**
//     * 根据条件进行更新
//     *
//     * @param elasticIndex     索引
//     * @param boolQueryBuilder where条件
//     * @param setDocumentMap   set值
//     */
//    public BulkByScrollResponse updateByQueryByWhere(ElasticIndex elasticIndex, BoolQueryBuilder boolQueryBuilder, Map<String, Object> setDocumentMap) {
//        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest(elasticIndex.getIndexName());
//        updateByQueryRequest.setQuery(boolQueryBuilder);
//        StringBuilder script = new StringBuilder();
//        Set<String> keys = setDocumentMap.keySet();
//        for (String key : keys) {
//            String appendValue;
//            Object value = setDocumentMap.get(key);
//            if (value instanceof Number) {
//                appendValue = value.toString();
//            } else if (value instanceof String) {
//                if ("null".equals(value)) {
//                    // 置空
//                    appendValue = "null";
//                } else {
//                    appendValue = "'" + value + "'";
//                }
//            } else if (value instanceof List) {
//                appendValue = JSONUtils.toJSONString(value);
//            } else {
//                appendValue = value.toString();
//            }
//            script.append("ctx._source.").append(key).append("=").append(appendValue).append(";");
//        }
//        updateByQueryRequest.setScript(new Script(script.toString()));
//        return updateByQuery(updateByQueryRequest);
//    }
//
//    private BulkByScrollResponse updateByQuery(UpdateByQueryRequest updateByQueryRequest) {
//        try {
//            return client.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
//        } catch (Exception e) {
//            try {
//                return client.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
//            } catch (Exception e1) {
//                log.error("操作异常", e1);
//                return null;
//            }
//        }
//    }
//
//    /**
//     * @description: 根据key获得文档的信息
//     */
//    @Override
//    public <T> T getDocument(ElasticIndex elasticIndex, String key, Class<T> clazz) {
//        GetRequest request = new GetRequest(elasticIndex.getIndexName());
//        request.id(key);
//        GetResponse getResponse = null;
//        try {
//            getResponse = client.get(request, RequestOptions.DEFAULT);
//        } catch (Exception e) {
//            log.error("查询文档异常", e);
//        }
//        if (getResponse != null && getResponse.isExists())
//        {
//            JSONObject jsonObject = JSON.parseObject(getResponse.getSourceAsString());
//            return JSON.parseObject(jsonObject.getString(key), clazz);
////            return FastJSON.toObject(getResponse.getSourceAsString(), clazz);
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * @description: 根据key集合获得文档集合的信息
//     */
//
//     @Override
//     public <T> List<T> multiGetDocument(ElasticIndex elasticIndex, List<String> keys, Class<T> clazz)
//     {
//         MultiGetRequest multiGetRequest = new MultiGetRequest();
//         for (String key : keys) {
//             MultiGetRequest.Item item = new MultiGetRequest.Item(elasticIndex.getIndexName(), key);
//             multiGetRequest.add(item);
//         }
//         MultiGetResponse response = null;
//         try {
//             RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
//             builder.setHttpAsyncResponseConsumerFactory(
//                     new HttpAsyncResponseConsumerFactory
//                             //修改为500MB
//                             .HeapBufferedResponseConsumerFactory(500 * 1024 * 1024));
//             response = client.mget(multiGetRequest, builder.build());
//         } catch (Exception e)
//         {
//             log.error("文档获取异常", e);
//         }
//         if (response == null)
//         {
//             return Collections.emptyList();
//         }
//         List<T> arrayList = Lists.newArrayListWithCapacity(keys.size());
//         for (MultiGetItemResponse item : response.getResponses())
//         {
//             GetResponse getResponse = item.getResponse();
//             if (getResponse.getSourceAsString() != null)
//             {
//                 JSONObject jsonObject = JSON.parseObject(getResponse.getSourceAsString());
//                 arrayList.add(JSON.parseObject(jsonObject.toString(), clazz));
//                // arrayList.add(JacksonUtils.toObject(getResponse.getSourceAsString(), clazz));
//             }
//         }
//         return arrayList;
//     }
//
//    /**
//     * @description: 根据key更新文档
//     */
//    @Override
//    public <T> void updateDocument(ElasticIndex elasticIndex, String key, T obj)
//    {
//        IndexRequest request = new IndexRequest(elasticIndex.getIndexName());
//        request.id(key);
//        request.source(JSON.toJSONString(obj), XContentType.JSON);
//        try {
//            client.index(request, RequestOptions.DEFAULT);
//        } catch (Exception e) {
//            log.error("文档更新异常", e);
//        }
//    }
//
//    /**
//     * @description: 删除文档记录
//     */
//    @Override
//    public void deleteDocument(ElasticIndex elasticIndex, String key)
//    {
//        DeleteRequest request = new DeleteRequest(elasticIndex.getIndexName());
//        request.id(key);
//        try
//        {
//            client.delete(request, RequestOptions.DEFAULT);
//        } catch (Exception e)
//        {
//            log.error("文档删除异常", e);
//        }
//    }
//
//    /**
//     * @description: 根据key批量删除文档记录
//     */
//    public void batchDeleteDocument(ElasticIndex elasticIndex, List<String> keyList)
//    {
//        try {
//            BulkRequest request = new BulkRequest();
//            Iterator<String> it = keyList.iterator();
//            int i = 0;
//            while (it.hasNext()) {
//                String key = it.next();
//                DeleteRequest deleteRequest = new DeleteRequest(elasticIndex.getIndexName(), key);
//                request.add(deleteRequest);
//                it.remove();
//                i++;
//                if (i == 2000) {
//                    client.bulk(request, RequestOptions.DEFAULT);
//                    request = new BulkRequest();
//                    i = 0;
//                }
//            }
//            if (i > 0) {
//                client.bulk(request, RequestOptions.DEFAULT);
//            }
//        } catch (Exception e) {
//            log.error("批量删除文档异常", e);
//        }
//    }
//
//    /**
//     * @description: 利用HighLevelRestClient的深分页，根据查询结果全部获取对象
//     * 深分页的大概思路是每一页查询会给你返回一个scrollId，类似于一个游标，记录本次查询的位置，下次使用这个有游标再去查下一页的数据，所有不会出现跨度大，查询数据多导致溢出的问题，但是它也有一个弊端就是不支持跳页
//     */
//    @Override
//    public <T> List<T> searchQueryAll(ElasticIndex elasticIndex, BoolQueryBuilder boolBuilder, String[] fields, Class<T> clazz, String orderField, String orderType) {
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.query(boolBuilder);
//        // scroll滚动查询，最大1000，超过就滚动查询
//        sourceBuilder.size(1000);
//        if (fields != null && fields.length > 0) {
//            //需要返回和不返回的字段，可以是数组也可以是字符串
//            sourceBuilder.fetchSource(fields, null);
//        }
//        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
//
//        SearchRequest searchRequest = new SearchRequest(elasticIndex.getIndexName());
//
//        // 处理字段排序问题
//        dealFieldOrder(sourceBuilder, orderField, orderType);
//
//        searchRequest.source(sourceBuilder);
//        searchRequest.scroll(scroll);
//
//        SearchResponse searchResponse = null;
//        try {
//            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//        } catch (Exception e) {
//            try {
//                searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//            } catch (Exception e1) {
//                log.error("操作异常", e1);
//            }
//        }
//        ArrayList<T> list = new ArrayList<>();
//        if (searchResponse == null) {
//            return list;
//        }
//        String scrollId = searchResponse.getScrollId();
//        SearchHits hits = searchResponse.getHits();
//        SearchHit[] searchHits = hits.getHits();
//        while (searchHits != null && searchHits.length > 0) {
//            for (SearchHit hit : searchResponse.getHits().getHits()) {
//                list.add(JSON.parseObject(hit.getSourceAsString(), clazz));
//            }
//            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
//            scrollRequest.scroll(scroll);
//            try {
//                searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
//            } catch (Exception e) {
//                try {
//                    searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
//                } catch (Exception e1) {
//                    log.error("滚动查询异常", e1);
//                }
//            }
//            scrollId = searchResponse.getScrollId();
//            searchHits = searchResponse.getHits().getHits();
//        }
//        // 清除滚屏
//        if (scrollId != null) {
//            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
//            clearScrollRequest.addScrollId(scrollId);
//            try {
//                client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
//            } catch (Exception e) {
//                try {
//                    client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
//                } catch (Exception e1) {
//                    log.error("清除滚动id异常", e1);
//                }
//            }
//        }
//        return list;
//    }
//
//    /**
//     * @description: 采用ES深分页, 获取全部KEY 根据查询条件，获取文档KEY集合
//     */
//    public List<String> searchQueryKey(ElasticIndex elasticIndex, BoolQueryBuilder boolBuilder) {
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        // scroll滚动查询，最大1000，超过就滚动查询
//        sourceBuilder.size(1000);
//        //设置只返回索引
//        sourceBuilder.fetchSource(false);
//        //设置查询条件
//        sourceBuilder.query(boolBuilder);
//
//        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
//
//        SearchRequest searchRequest = new SearchRequest(elasticIndex.getIndexName());
//        searchRequest.source(sourceBuilder);
//        searchRequest.scroll(scroll);
//        SearchResponse searchResponse;
//
//        ArrayList<String> list = new ArrayList<>();
//        try {
//            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//            String scrollId = searchResponse.getScrollId();
//            SearchHit[] searchHits = searchResponse.getHits().getHits();
//            while (searchHits != null && searchHits.length > 0) {
//                for (SearchHit hit : searchResponse.getHits().getHits()) {
//                    list.add(hit.getId());
//                }
//                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
//                scrollRequest.scroll(scroll);
//                searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
//                scrollId = searchResponse.getScrollId();
//                searchHits = searchResponse.getHits().getHits();
//            }
//            // 清除滚屏
//            if (scrollId != null) {
//                ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
//                clearScrollRequest.addScrollId(scrollId);
//                client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
//            }
//        } catch (Exception e) {
//            log.error("滚动查询异常", e);
//        }
//        return list;
//    }
//
//    /**
//     * @description: 根据查询条件，获取文档KEY集合
//     */
//    public long searchQueryCount(ElasticIndex elasticIndex, BoolQueryBuilder boolBuilder)
//    {
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.trackTotalHits(true);
//        sourceBuilder.size(0);//指定返回 0 条记录
//        sourceBuilder.query(boolBuilder);
//        SearchRequest searchRequest = new SearchRequest(elasticIndex.getIndexName());
//        searchRequest.source(sourceBuilder);
//
//        SearchResponse searchResponse = null;
//        try {
//            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//        } catch (Exception e) {
//            try {
//                searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//            } catch (Exception e1) {
//                log.error("查询异常", e1);
//            }
//        }
//        if (searchResponse != null) {
//            return searchResponse.getHits().getTotalHits().value;
//        } else {
//            return 0;
//        }
//    }
//
//    /**
//     * 不分页查询-after的方式
//     * 不受max_result_window限制，但是只能一直向下翻页，不能向上翻页
//     */
//    public <T> List<T> searchQueryAfter(ElasticIndex elasticIndex, BoolQueryBuilder boolBuilder, Class<T> clazz,
//                                        String orderField, String orderType) throws Exception {
//        return searchQueryAfterByPageSize(elasticIndex, boolBuilder, clazz, null, null, orderField, orderType);
//    }
//
//    /**
//     * 分页查询数据
//     */
//    public <T> List<T> searchQueryAfterByPageSize(ElasticIndex elasticIndex, BoolQueryBuilder boolBuilder, Class<T> clazz,
//                                                  Integer pageSize, String orderField, String orderType) throws Exception {
//        return searchQueryAfterByPageSize(elasticIndex, boolBuilder, clazz, pageSize, null, orderField, orderType);
//    }
//
//    /**
//     * 分页查询数据
//     */
//    public <T> List<T> searchQueryAfterByPageSize(ElasticIndex elasticIndex, BoolQueryBuilder boolBuilder, Class<T> clazz,
//                                                  Integer pageSize, Object afterIdentifier, String orderField, String orderType) throws Exception {
//        log.info("--------------------使用search_after搜索文档---------------------");
//        //1.创建 SearchRequest搜索请求
//        SearchRequest searchRequest = new SearchRequest(elasticIndex.getIndexName());
//        //2.创建 SearchSourceBuilder条件构造。
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//
//        //设置查询大小
//        // 第几页
//        searchSourceBuilder.from(0);
//        if (pageSize != null) {
//            searchSourceBuilder.size(pageSize);
//        } else {
//            searchSourceBuilder.size(10);
//        }
//        //生成DSL查询语句
//        searchSourceBuilder.query(boolBuilder);
//        // 处理字段排序问题
//        dealFieldOrder(searchSourceBuilder, orderField, orderType);
//
//        if (null != afterIdentifier) {
//            // 设置searchAfter的最后一个排序值
//            searchSourceBuilder.searchAfter(new Object[]{afterIdentifier});
//        }
//        // 将 SearchSourceBuilder 添加到 SearchRequest中
//        searchRequest.source(searchSourceBuilder);
//        // 执行查询
//        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//        // 5.解析查询结果
//        SearchHits hits = searchResponse.getHits();
//        ArrayList<T> list = new ArrayList<>();
//        hits.forEach(p -> list.add(JSON.parseObject(p.getSourceAsString(), clazz)));
//        return list;
//    }
//
//
//
//    /**
//     * 组装数据+获取scrollId
//     */
//    private <T> String combinationEs(String scrollId, SearchResponse searchResponse, PaginationRequest PaginationRequest, Class<T> clazz, List<T> list, Scroll scroll) {
//        SearchHits hits = searchResponse.getHits();
//        SearchHit[] searchHits = hits.getHits();
//        int currDataNum = 0;
//        while (searchHits != null && searchHits.length > 0) {
//            for (SearchHit hit : searchResponse.getHits().getHits()) {
//                if (PaginationRequest != null) {
//                    ++currDataNum;
//                    if (isStartExecute(PaginationRequest, currDataNum)) {
//                        list.add(JSON.parseObject(hit.getSourceAsString(), clazz));
//                    }
//                    if (!isEnd(PaginationRequest, currDataNum)) {
//                        return scrollId;
//                    }
//                } else {
//                    list.add(JSON.parseObject(hit.getSourceAsString(), clazz));
//                }
//            }
//            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
//            scrollRequest.scroll(scroll);
//            try {
//                searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
//            } catch (Exception e) {
//                try {
//                    searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
//                } catch (Exception e1) {
//                    log.error("滚动查询异常", e1);
//                }
//            }
//            scrollId = searchResponse.getScrollId();
//            searchHits = searchResponse.getHits().getHits();
//        }
//        return scrollId;
//    }
//
//    /**
//     * 开始+执行的记录
//     */
//    private boolean isStartExecute(PaginationRequest PaginationRequest, int currDataNum) {
//        return currDataNum >= ((PaginationRequest.getPageNumber() - 1) * PaginationRequest.getPageSize() + 1);
//    }
//
//    /**
//     * 终止的记录
//     */
//    private boolean isEnd(PaginationRequest PaginationRequest, int currDataNum) {
//        return currDataNum < ((PaginationRequest.getPageNumber() - 1) * PaginationRequest.getPageSize()) + PaginationRequest.getPageSize();
//    }
//
//    /**
//     * 清除滚屏
//     */
//    private void clearScroll(String scrollId) {
//        if (scrollId != null) {
//            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
//            clearScrollRequest.addScrollId(scrollId);
//            try {
//                client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
//            } catch (Exception e) {
//                try {
//                    client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
//                } catch (Exception e1) {
//                    log.error("清除滚动id异常", e1);
//                }
//            }
//        }
//    }
//
//    /**
//     * 处理字段排序问题
//     * <pre>
//     *     默认以id进行排序
//     *     如果传递排序字段，那么先以字段排序为主
//     * </pre>
//     */
//    private void dealFieldOrder(SearchSourceBuilder searchSourceBuilder, String orderField, String orderType)
//    {
//        // 如果走到默认排序字段：defaultOrder，将不进行自定义排序
//        if (Objects.equals("defaultOrder", orderField))
//        {
//            return;
//        }
//        if (StringUtils.isNotBlank(orderField)) {
//            orderField = ES_Utils.getKeyWord(orderField);
//            if (SortOrder.ASC.toString().equalsIgnoreCase(orderType)) {
//                searchSourceBuilder.sort(orderField, SortOrder.ASC);
//            } else
//            {
//                searchSourceBuilder.sort(orderField, SortOrder.DESC);
//            }
//        } else
//        {
//            searchSourceBuilder.sort(ES_Utils.getKeyWord("createDateTime"), SortOrder.DESC);
//        }
//        searchSourceBuilder.sort(ES_Utils.getKeyWord("id"), SortOrder.ASC);
//    }
//}
