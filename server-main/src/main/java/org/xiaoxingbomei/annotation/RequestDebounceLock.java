package org.xiaoxingbomei.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 自定义防抖注解
 * 使用方法：
 *
 * 一个注解实现接口防抖：
 * 有一个用户保存接口，由于用户手抖或者网络抖动，保存用户接口被多次请求这种情况就是一个防抖的典型案例，
 * 如果不加控制那么同一用户可能存在多条数据的情况。
 * 通常前端会做防抖设计，防止用户多次点击，但是因为网络原因引起的问题仅仅靠前端处理是不行的，后端也需要进行防抖设计。
 *
 * 那些接口需要防抖：如果一个接口在短时间内被频繁调用，而这些调用中有很大一部分是不必要的。这些接口就可以进行防抖处理
 * - 表单提交、保存类接口
 * - 搜索输入框和表单输入框需要请求后端，用户的连续输入可能会频繁触发接口请求
 *
 * 如何确定请求是重复的：
 * 可以根据在规定时间内的请求方法和请求参数确认，比如保存用户方法，在一秒内多次保存用户名为**张三**手机号是**13333333333**
 * 这个用户就可以确定请求是重复的。
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequestDebounceLock
{
    /**
     * redis锁前缀(接口方法名)
     */
    String prefix() default "";

    /**
     * redis锁过期时间 默认2秒
     */
    int expire() default 2;

    /**
     * redis锁过期时间单位 默认单位为秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * redis key分隔符
     */
    String delimiter() default "@";
}
