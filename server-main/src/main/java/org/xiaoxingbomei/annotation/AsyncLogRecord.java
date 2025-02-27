package org.xiaoxingbomei.annotation;

import org.xiaoxingbomei.Enum.OperateTypeEnum;

import java.lang.annotation.*;

/**
 * 异步落库日志
 * 使用方法：将此注解用于落库日志的操作service之上，即可实现异步落库
 * 实现方式：
 */
@Target(ElementType.METHOD) // 注解只能用于方法
@Retention(RetentionPolicy.RUNTIME) // 修饰注解的生命周期
@Documented
public @interface AsyncLogRecord
{

    String value() default "";

    // 请求描述
    String description() default "";

    // 操作类型
    OperateTypeEnum operationType() default OperateTypeEnum.OTHER;

}