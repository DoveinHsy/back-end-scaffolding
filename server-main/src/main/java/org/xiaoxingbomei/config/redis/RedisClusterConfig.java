package org.xiaoxingbomei.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.xiaoxingbomei.config.redis.FastJsonRedisSerializer;

import java.time.Duration;

/**
 * redis配置类
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableCaching
@Slf4j
public class RedisClusterConfig extends CachingConfigurerSupport {


    /**
     * 自定义redis的序列化配置
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 使用 String 序列化器序列化 Redis 的 key
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        // 使用 FastJson 序列化器序列化 Redis 的 value
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }


//    @Bean
//    @SuppressWarnings("all")
//    public RedisTemplate<String, Object> redisClusterTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
//        template.setConnectionFactory(factory);
//        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
//        // 全局开启AutoType，不建议使用
//        // ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
//        // 建议使用这种方式，小范围指定白名单
//        // 设置值（value）的序列化采用FastJsonRedisSerializer
//        template.setValueSerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(new StringRedisSerializer());
//        // 设置键（key）的序列化采用StringRedisSerializer。
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.afterPropertiesSet();
//        return template;
//    }

//    /**
//     * 自定义缓存管理器
//     * @param factory   自动注入spring容器中的jedisConnectionFactory
//     * @return
//     */
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory factory) {
//        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
//        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
////        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        // 配置序列化（解决乱码的问题）
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                // 7 天缓存过期
//                .entryTtl(Duration.ofDays(7))
//                // key序列化
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
//                // 值序列化
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer))
//                // 不缓存空值
//                .disableCachingNullValues();
//        // 通过连接工厂构建缓存管理器
//        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
//                // 注入配置
//                .cacheDefaults(config)
//                .build();
//        return cacheManager;
//    }

//    @Override
//    public CacheErrorHandler errorHandler(){
//        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler(){
//
//            @Override
//            public void handleCacheGetError(RuntimeException exception,
//                                            Cache cache, Object key) {
//                log.error("get cache failed: error-msg:{},key:{}",exception,key );
//            }
//            @Override
//            public void handleCachePutError(RuntimeException exception,
//                                            Cache cache, Object key, Object value) {
//                log.error("update cache failed: error-msg:{},key:{}",exception,key );
//            }
//            @Override
//            public void handleCacheEvictError(RuntimeException exception,
//                                              Cache cache, Object key) {
//                log.error("delete cache failed: error-msg:{},key:{}",exception,key );
//            }
//            @Override
//            public void handleCacheClearError(RuntimeException exception,
//                                              Cache cache) {
//                log.error("clear cache failed: error-msg:{}",exception);
//            }
//        };
//        return cacheErrorHandler;
//    }

}