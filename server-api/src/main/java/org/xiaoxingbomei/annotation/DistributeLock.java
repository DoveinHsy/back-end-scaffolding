package org.xiaoxingbomei.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;


/**
 * 分布式
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributeLock {
    //key 为分布式锁的 key 值
    String key();

    //timeout 为锁的超时时间，默认为 5
    long timeout() default 1;

    //timeUnit 为超时时间的单位，默认为分钟
    TimeUnit timeUnit() default TimeUnit.MINUTES;
}