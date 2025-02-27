package org.xiaoxingbomei.utils;

/**
 * es7.10.2 工具类
 */

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Elasticsearch 7.10.2 工具类
 */
public class Elasticsearch7_Utils
{

    private final RestHighLevelClient client;

    public Elasticsearch7_Utils(RestHighLevelClient client) {this.client = client;}

    /**
     * 创建索引
     *
     * @param indexName 索引名称
     * @param settings  索引设置
     * @param mappings  索引映射
     * @return 是否创建成功
     */
    public boolean createIndex(String indexName, Settings settings, String mappings) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.settings(settings);
        request.mapping(mappings, XContentType.JSON);
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 检查索引是否存在
     *
     * @param indexName 索引名称
     * @return 是否存在
     */
    public boolean exists(String indexName) throws IOException
    {
        GetIndexRequest request = new GetIndexRequest(indexName);
        return client.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 删除索引
     *
     * @param indexName 索引名称
     * @return 是否删除成功
     */
    public boolean deleteIndex(String indexName) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 获取索引信息
     *
     * @param indexName 索引名称
     * @return 索引信息
     */
    public GetIndexResponse getIndexInfo(String indexName) throws IOException {
        GetIndexRequest request = new GetIndexRequest(indexName);
        return client.indices().get(request, RequestOptions.DEFAULT);
    }

    /**
     * 插入文档
     *
     * @param indexName 索引名称
     * @param id        文档ID
     * @param document  文档内容
     * @return 插入结果
     */
    public IndexResponse insertDocument(String indexName, String id, String document) throws IOException {
        IndexRequest request = new IndexRequest(indexName).id(id).source(document, XContentType.JSON);
        return client.index(request, RequestOptions.DEFAULT);
    }

    /**
     * 获取文档
     *
     * @param indexName 索引名称
     * @param id        文档ID
     * @return 文档内容
     */
    public GetResponse getDocument(String indexName, String id) throws IOException {
        GetRequest request = new GetRequest(indexName, id);
        return client.get(request, RequestOptions.DEFAULT);
    }

    /**
     * 更新文档
     *
     * @param indexName 索引名称
     * @param id        文档ID
     * @param document  更新内容
     * @return 更新结果
     */
    public UpdateResponse updateDocument(String indexName, String id, String document) throws IOException {
        UpdateRequest request = new UpdateRequest(indexName, id).doc(document, XContentType.JSON);
        return client.update(request, RequestOptions.DEFAULT);
    }

    /**
     * 删除文档
     *
     * @param indexName 索引名称
     * @param id        文档ID
     * @return 删除结果
     */
    public DeleteResponse deleteDocument(String indexName, String id) throws IOException {
        DeleteRequest request = new DeleteRequest(indexName, id);
        return client.delete(request, RequestOptions.DEFAULT);
    }

    /**
     * 批量插入文档
     *
     * @param indexName 索引名称
     * @param documents 文档列表（ID -> 文档内容）
     * @return 批量操作结果
     */
    public BulkResponse bulkInsert(String indexName, Map<String, String> documents) throws IOException {
        BulkRequest request = new BulkRequest();
        documents.forEach((id, doc) -> request.add(new IndexRequest(indexName).id(id).source(doc, XContentType.JSON)));
        return client.bulk(request, RequestOptions.DEFAULT);
    }

    /**
     * 批量删除文档
     *
     * @param indexName 索引名称
     * @param ids       文档ID列表
     * @return 批量操作结果
     */
    public BulkResponse bulkDelete(String indexName, List<String> ids) throws IOException {
        BulkRequest request = new BulkRequest();
        ids.forEach(id -> request.add(new DeleteRequest(indexName, id)));
        return client.bulk(request, RequestOptions.DEFAULT);
    }

    /**
     * 搜索文档
     *
     * @param indexName 索引名称
     * @param field     字段名
     * @param value     字段值
     * @return 搜索结果
     */
    public SearchResponse search(String indexName, String field, String value) throws IOException {
        SearchRequest request = new SearchRequest(indexName);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery(field, value));
        request.source(sourceBuilder);
        return client.search(request, RequestOptions.DEFAULT);
    }

    /**
     * 检查文档是否存在
     *
     * @param indexName 索引名称
     * @param id        文档ID
     * @return 是否存在
     */
    public boolean documentExists(String indexName, String id) throws IOException {
        GetRequest request = new GetRequest(indexName, id);
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        return client.exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 获取索引别名
     *
     * @param indexName 索引名称
     * @return 别名信息
     */
    public GetAliasesRequest getAliases(String indexName) {
        return new GetAliasesRequest(indexName);
    }

    /**
     * 异步插入文档
     *
     * @param indexName 索引名称
     * @param id        文档ID
     * @param document  文档内容
     * @param listener  异步监听器
     */
    public void insertDocumentAsync(String indexName, String id, String document, ActionListener<IndexResponse> listener) {
        IndexRequest request = new IndexRequest(indexName).id(id).source(document, XContentType.JSON);
        client.indexAsync(request, RequestOptions.DEFAULT, listener);
    }
}
