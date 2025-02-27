//package org.xiaoxingbomei.aspect;
//
//import cn.hutool.core.util.StrUtil;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.xiaoxingbomei.annotation.RequestDebounceLock;
//import org.xiaoxingbomei.exception.CustomException;
//
//import java.lang.reflect.Method;
//
///**
// * 防抖AOP的实现
// */
//@Aspect
//@Configuration
//@Order(2)
//public class RequestDebounceLockAspect
//{
//
//    private RedissonClient redissonClient;
//
//
//    @Autowired
//    public RequestDebounceLockAspect(RedissonClient redissonClient) {
//        this.redissonClient = redissonClient;
//    }
//
//    @Around("execution(public * * (..)) && @annotation(org.xiaoxingbomei.annotation.RequestDebounceLock)")
//    public Object interceptor(ProceedingJoinPoint joinPoint) {
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        Method method = methodSignature.getMethod();
//        RequestDebounceLock requestLock = method.getAnnotation(RequestDebounceLock.class);
//        if (StrUtil.isEmpty(requestLock.prefix())) {
//            throw new CustomException(ErrorCode.QUIVER_REDIS_PREFIX_ERROR.getCode(), ErrorCode.QUIVER_REDIS_PREFIX_ERROR.getMessage());
//        }
//        // 获取自定义key
//        final String lockKey = RequestKeyGenerator.getLockKey(joinPoint);
//        // 使用Redisson分布式锁的方式判断是否重复提交
//        RLock lock = redissonClient.getLock(lockKey);
//        boolean isLocked = false;
//        try {
//            // 获取锁
//            isLocked = lock.tryLock();
//            // 没有拿到锁说明已经有了请求了
//            if (!isLocked) {
//                throw new CustomException(ErrorCode.QUIVER_FREQUENT.getCode(), ErrorCode.QUIVER_FREQUENT.getMessage());
//            }
//            // 拿到锁后设置过期时间
//            lock.lock(requestLock.expire(), requestLock.timeUnit());
//            try {
//                return joinPoint.proceed();
//            } catch (Throwable throwable) {
//                throw new CustomException(ErrorCode.ERR_SYSTEM.getCode(), ErrorCode.ERR_SYSTEM.getMessage());
//            }
//        } catch (Exception e) {
//            throw new CustomException(ErrorCode.QUIVER_FREQUENT.getCode(), ErrorCode.QUIVER_FREQUENT.getMessage());
//        } finally {
//            // 释放锁
//            if (isLocked && lock.isHeldByCurrentThread()) {
//                lock.unlock();
//            }
//        }
//    }
//}