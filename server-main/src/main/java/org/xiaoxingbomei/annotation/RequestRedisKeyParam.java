package org.xiaoxingbomei.annotation;

import java.lang.annotation.*;

/**
 * 自定义分布式锁key的参数注解
 * 使用方法：
 *
 * 不是每个参数都需要拼接成key，所以自定义一个注解，在需要拼接的参数上添加注解即可
 */
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequestRedisKeyParam
{

}
