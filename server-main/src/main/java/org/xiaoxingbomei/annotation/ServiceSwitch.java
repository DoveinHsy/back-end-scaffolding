package org.xiaoxingbomei.annotation;

import java.lang.annotation.*;

/**
 * 通用开关注解
 * 使用方法：
 *
 * 需求功能已经开发测试完成，但是上线时间不确定，一般的做法是将代码备份，在需要上线的时候在合并代码重新上线
 * 这样做还是比较麻烦的  so
 * 通过自定义注解 + AOP实现一个功能开关
 *
 * 定义了一个key对应不同功效的开关,定义了一个val作为开关是否打开的标识，以及一个message作为消息提示
 */
@Documented
@Target({ElementType.METHOD})        // 作用在方法上
@Retention(RetentionPolicy.RUNTIME)  // 运行时起作用
public @interface ServiceSwitch
{
    /**
     * 业务开关的key（不同key代表不同功效的开关）
     */
    String switchKey();

    // 开关，0:关 ，1:开
    String switchVal() default "0";

    // 提示信息
    String message() default "功能已关闭";
}