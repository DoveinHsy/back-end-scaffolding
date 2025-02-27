package org.xiaoxingbomei.entity;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.xiaoxingbomei.Enum.DesensitizationTypeEnum;
import org.xiaoxingbomei.annotation.Desensitization;

import java.io.IOException;
import java.util.Objects;

/**
 * 关于自定义的规则可以根据自己的需求来写工具类，这里简单使用Hutool的工具来了
 */
@AllArgsConstructor
@NoArgsConstructor
public class DesensitizationSerialize extends JsonSerializer<String> implements ContextualSerializer
{

    private DesensitizationTypeEnum type;

    private Integer start;

    private Integer end;

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException
    {
        switch (type)
        {
            // 自定义
            case CUSTOM_RULE:
                jsonGenerator.writeString(CharSequenceUtil.hide(s, start, end));
                break;
            // 身份证
            case ID_CARD:
                jsonGenerator.writeString(DesensitizedUtil.idCardNum(s, 1, 2));
                break;
            // 手机号
            case MOBILE_PHONE:
                jsonGenerator.writeString(DesensitizedUtil.mobilePhone(s));
                break;
            // 地址
            case ADDRESS:
                jsonGenerator.writeString(DesensitizedUtil.address(s, 8));
                break;
            // 银行卡脱敏
            case BANK_CARD:
                jsonGenerator.writeString(DesensitizedUtil.bankCard(s));
                break;
            default:
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty)
            throws JsonMappingException
    {
        if (beanProperty != null)
        {
            // 判断数据类型是否为String类型
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class))
            {
                // 获取定义的注解
                Desensitization desensitization = beanProperty.getAnnotation(Desensitization.class);

                // 为null
                if (desensitization == null) {
                    desensitization = beanProperty.getContextAnnotation(Desensitization.class);
                }
                // 不为null
                if (desensitization != null) {
                    // 创建定义的序列化类的实例并且返回，入参为注解定义的type,开始位置，结束位置。
                    return new DesensitizationSerialize(desensitization.type(), desensitization.start(),
                            desensitization.end());
                }
            }

            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(null);
    }

}