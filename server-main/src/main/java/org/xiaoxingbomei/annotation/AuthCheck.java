package org.xiaoxingbomei.annotation;

import java.lang.annotation.*;

/**
 * 权限校验注解。基于satoken框架
 * 使用方法
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck
{

}
