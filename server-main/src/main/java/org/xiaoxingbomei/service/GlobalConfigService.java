package org.xiaoxingbomei.service;

import org.xiaoxingbomei.common.entity.GlobalEntity;

/**
 * 配置config
 */
public interface GlobalConfigService
{
    /**
     * apollo
     */
    GlobalEntity getApolloConfig();
    GlobalEntity getApolloValueByKey(String paramString);


    /**
     * nacos
     */
    GlobalEntity getNaocsConfig();


    /**
     * Openfeign
     */
    GlobalEntity getOpenFeignConfig();


    /**
     * reactive feign
     * @return
     */
    GlobalEntity getReactiveFeignConfig();

    /**
     * log4j2
     */
    GlobalEntity getLog4j2Config();

    /**
     * mybatis
     */
    GlobalEntity getMybatisConfig();

    /**
     * redis
     */
    GlobalEntity getRedisConfig();

    /**
     * elasticsearch
     */
    GlobalEntity getElasticSearchConfig();

    /**
     * sa-token
     */
    GlobalEntity getSaTokenConfig();

}
