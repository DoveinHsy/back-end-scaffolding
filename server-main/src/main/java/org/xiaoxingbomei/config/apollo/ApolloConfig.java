package org.xiaoxingbomei.config.apollo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Apollo配置
 */
@Configuration
public class ApolloConfig
{

    // 确保了在你的应用程序启动时，能够注入一个Config对象，以便在应用中获取 Apollo 配置信息
    @Bean(name = "customApolloConfig")
    public Config apolloConfig()
    {
        return ConfigService.getAppConfig();
    }
}