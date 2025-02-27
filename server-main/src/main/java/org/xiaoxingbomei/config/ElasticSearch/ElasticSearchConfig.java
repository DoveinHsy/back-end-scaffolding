package org.xiaoxingbomei.config.ElasticSearch;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * elasticsearch 7.10.2 配置类
 */
@Configuration
public class ElasticSearchConfig
{

    @Value("${apollo.elasticsearch.hostname}")
    private String hostName;

    @Value("${apollo.elasticsearch.port}")
    private String port;

    @Value("${apollo.elasticsearch.scheme}")
    private String scheme; // http 或 https

    private static final String USERNAME = ""; // 如果开启认证，填入用户名
    private static final String PASSWORD = ""; // 如果开启认证，填入密码

    /**
     * 配置 RestHighLevelClient，用于连接 Elasticsearch 7.10.2
     */
    @Bean
    public RestHighLevelClient restHighLevelClient()
    {
        // 配置凭证（如果 Elasticsearch 开启了安全认证）
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        if (!USERNAME.isEmpty() && !PASSWORD.isEmpty()) {
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(USERNAME, PASSWORD));
        }

        // 配置 RestClientBuilder
        RestClientBuilder builder = RestClient.builder(
                new HttpHost(hostName, Integer.parseInt(port), scheme)
        ).setHttpClientConfigCallback(httpAsyncClientBuilder -> {
            if (!USERNAME.isEmpty() && !PASSWORD.isEmpty()) {
                httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
            return httpAsyncClientBuilder;
        });

        // 创建并返回 RestHighLevelClient
        return new RestHighLevelClient(builder);
    }
}
