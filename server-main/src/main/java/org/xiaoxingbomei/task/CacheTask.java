//package org.xiaoxingbomei.task;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.xiaoxingbomei.utils.Exception_Utils;
//
//import java.util.concurrent.atomic.AtomicBoolean;
//
///**
// * 缓存 task
// * 1、定时检查redis连接状态
// * 2、
// */
//@Slf4j
//@Component
//public class CacheTask
//{
////    @Autowired
////    private RedisTemplate redisTemplate;
////
////    // redis连接状态
////    private final AtomicBoolean isRedisConnected = new AtomicBoolean(false);
////
////    // 查询缓存，检查redis连接状态
////    public boolean isRedisConnected()
////    {
////        return isRedisConnected.get();
////    }
////
////    // 1、定时检查redis连接状态
////    @Scheduled(fixedDelay = 20000)
////    public void checkRedisConnection()
////    {
////        try
////        {
////            // 简单的get操作来测试redis的连接
////            redisTemplate.opsForValue().get("test_redis_connect");
////
////            // 修改redis连接状态
////            isRedisConnected.set(true);
////
////             log.info("=========定时检查组件:redis client connected successfully=========");
////
////        }catch (Exception e)
////        {
////            // 打印堆栈
////            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
////
////            // 修改redis连接状态
////            isRedisConnected.set(false);
////
////            log.error("redis client connect failed");
////        }
////    }
//
//    // 可以在这里添加更多与缓存相关的定时任务
//
//    // 可以在这里添加更多与缓存相关的定时任务
//
//}
