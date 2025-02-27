package org.xiaoxingbomei.annotation;

import java.lang.annotation.*;

/**
 * 放在Controller层，用于做通用的controller日志输出
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerLog { }