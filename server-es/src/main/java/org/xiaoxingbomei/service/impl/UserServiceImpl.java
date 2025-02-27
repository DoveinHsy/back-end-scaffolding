package org.xiaoxingbomei.service.impl;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.service.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService
{

    @Autowired
    RestHighLevelClient elasticClient;

    //

    @Override
    public GlobalEntity saveUserInfoToEs(List<Map<String, Object>> userListMap)
    {

        if (userListMap == null || userListMap.size() == 0)
        {
            return GlobalEntity.success("用户数据为空，无需es同步");
        }

        String index = "user_info"; // 根据需要选择合适的索引名称
        BulkRequest bulkRequest = new BulkRequest();

        // 构建批量请求
        for (Map<String, Object> userDoc : userListMap)
        {
            IndexRequest indexRequest = new IndexRequest(index)
                    .id((String) userDoc.get("id")) // 使用 id 作为 Elasticsearch 的文档 ID
                    .source(JSON.toJSONString(userDoc), XContentType.JSON);
            bulkRequest.add(indexRequest);
        }

        // 执行批量请求
        try
        {
            BulkResponse bulkResponse = elasticClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulkResponse.hasFailures()) {
                throw new RuntimeException("Elasticsearch bulk save failed: " + bulkResponse.buildFailureMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException("Elasticsearch error while saving data", e);
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        return GlobalEntity.success(resultMap,"同步es成功");
    }
}
