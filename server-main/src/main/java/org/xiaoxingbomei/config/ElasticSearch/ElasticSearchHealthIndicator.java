package org.xiaoxingbomei.config.ElasticSearch;

import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * 自定义es健康检查器
 * 通过访问 /actuator/health  即可查看
 */
@Component
public class ElasticSearchHealthIndicator implements HealthIndicator
{

    private final RestHighLevelClient restHighLevelClient;

    public ElasticSearchHealthIndicator(RestHighLevelClient restHighLevelClient)
    {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Override
    public Health health()
    {
        try
        {
            Response response = restHighLevelClient.getLowLevelClient().performRequest(new Request("GET", "/"));
            if (response.getStatusLine().getStatusCode() == 200)
            {
                return Health.up().build();
            } else
            {
                return Health.down().withDetail("status", response.getStatusLine().getStatusCode()).build();
            }
        } catch (Exception e) {
            return Health.down(e).withDetail("message", "Elasticsearch连接失败").build();
        }
    }
}
