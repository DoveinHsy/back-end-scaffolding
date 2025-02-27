package org.xiaoxingbomei.config.nacos;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 引入nacos配置信息，作为全局变量
 */
@Component
@ConfigurationProperties
@Data
public class NacosConfig
{

}
