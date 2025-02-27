package org.xiaoxingbomei.config.apollo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 引入apollo配置信息，作为全局变量
 */
@Component
@ConfigurationProperties
@Data
public class ApolloProperties
{
    @Value("${spring.datasource.localhost.jdbc-url}")
    private String jdbcUrl;

}
