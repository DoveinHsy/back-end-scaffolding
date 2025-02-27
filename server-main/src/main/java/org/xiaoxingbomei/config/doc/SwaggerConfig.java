package org.xiaoxingbomei.config.doc;


import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * 1. @OpenAPIDefinition全局只能定义一个，主要配置文档信息和安全配置，这里列举了常用的请求头携带token的安全配置模式
 * a.  @OpenAPIDefinition下的info属性配置文档信息
 * b.  @OpenAPIDefinition下的security配置认证方式，name属性引入自定义的认证模式
 * 2. @SecurityScheme注解就是自定义的认证模式，配置请求头携带token
 */

@OpenAPIDefinition
(
        info = @Info
        (
                title = "小型博美",
                version = "1.7.0",
                description = "main-swagger",
                contact = @Contact(name = "xianghe")
        ),
        security = @SecurityRequirement(name = "JWT"),
        externalDocs = @ExternalDocumentation
                (description = "参考文档",
                 url = "https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations"
                )
)

@SecurityScheme(type = SecuritySchemeType.HTTP, name = "相赫秘钥", scheme = "bearer", in = SecuritySchemeIn.HEADER)
@Configuration
public class SwaggerConfig
{

}
