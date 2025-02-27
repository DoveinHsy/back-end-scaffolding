//package org.xiaoxingbomei.aspect;
//
//import lombok.Value;
//import lombok.extern.log4j.Log4j2;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.xiaoxingbomei.annotation.DistributeLock;
//
//import java.util.Map;
//
//@Aspect
//@Component
//@Slf4j
//public class RedisLockAspect {
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Value("${damp.redis.env}")
//    private String redisEnv;
//
//    @Around(value = "@annotation(distributeLock)")
//    public Object doAround(ProceedingJoinPoint joinPoint, DistributeLock distributeLock) {
//        String key = AnnotationResolver.newInstance().resolver(joinPoint, distributeLock.key()) + "";
//        key = redisEnv + "_" + key;
//        String keyValue = "";
//        try {
//            Map<String, Object> threadLocalMap = ThreadLocalUtil.get();
//            Object value = threadLocalMap.get(key);
//            if (Objects.nonNull(value)) {
//                return joinPoint.proceed();
//            }
//            keyValue = getLock(key, distributeLock.timeout(), distributeLock.timeUnit());
//            if (StringUtils.isEmpty(keyValue)) {
//                // 获取锁失败。
//                return ApiResponse.fail("已获取锁，请勿频繁操作");
//            }
//            ThreadLocalUtil.set(key, keyValue);
//            // 获取锁成功
//            return joinPoint.proceed();
//        } catch (Throwable throwable) {
//            return ApiResponse.fail("系统异常");
//        } finally {
//            // 释放锁。
//            unLock(key, keyValue);
//            ThreadLocalUtil.clear(key);
//        }
//    }