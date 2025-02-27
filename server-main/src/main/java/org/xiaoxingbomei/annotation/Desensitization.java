package org.xiaoxingbomei.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.xiaoxingbomei.Enum.DesensitizationTypeEnum;
import org.xiaoxingbomei.entity.DesensitizationSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  脱敏注解
 *  使用方法：
 * `@JsonSerialize(using = DesensitizationSerialize.class)`用于指定在序列化时应该使用哪个自定义序列化器类,
 *  需要和下面的注解搭配使用`DesensitizationSerialize`我们自定义的序列化器才会生效
 *
 *  springboot的web项目,默认使用jackson进行序列化，可以通过自定义序列化实现数据脱敏
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizationSerialize.class)
public @interface Desensitization
{

    /**
     * 在自定义类型的时候，start和end生效
     */
    DesensitizationTypeEnum type() default DesensitizationTypeEnum.CUSTOM_RULE;

    /**
     * 开始位置（包含）
     */
    int start() default 0;

    /**
     * 结束位置（不包含）
     */
    int end() default 0;

}
