//package org.xiaoxingbomei.config.doc;
//
//import com.google.common.collect.Maps;
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import lombok.extern.slf4j.Slf4j;
//import org.springdoc.core.GroupedOpenApi;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Map;
//
///**
// * 文档配置
// */
//@Configuration
//@Slf4j
//public class SpringDocConfig {
//
//    @Bean
//    public OpenAPI openMainAPI() {
//        return new OpenAPI()
//                .components(getComponents())
//                // // 2. 再在这里添加上Swagger要使用的安全策略 - addList()中写上对应的key
//                .addSecurityItem(
//                        new SecurityRequirement().addList("tokenScheme").
//                                addList("x-gateway-security-user").
//                                addList("x-gateway-security-org"))
//                .info(new Info().title("相赫—swagger")
//                        .description("swagger接口文档")
//                        .version("4.1.2")
//                        .license(new License().name("com/xianghe/xianghe").url("http://www.xianghe.com")));
//    }
//
//    /**
//     * 先在组件中注册安全策略
//     */
//    private Components getComponents() {
//        Map<String, SecurityScheme> securitySchemeMap = Maps.newHashMapWithExpectedSize(3);
//        // 第一个参数是key值，后面是初始化一个安全策略的参数
//        securitySchemeMap.put("tokenScheme", new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name("token").description("Authorization：token认证"));
//        securitySchemeMap.put("x-gateway-security-user", new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name("x-gateway-security-user").description("用户信息，案例：eyJ1c2VyTm0iOiLnrqHnkIblkZgiLCJyb2xlQ29kZXMiOlsiQURNSU5JU1RSQVRPUiIsIkRBVEFfR09WRVJOQU5DRV9ST0xFIiwiU1REX01HVF9ERVBUX1JPTEUiLCJTVERfREVGX0RFUFRfUk9MRSIsIkRTVF9ST0xFIl0sIm9yZyI6eyJvcmdDZCI6Ik9SR18wMDAxIiwib3JnTm0iOiLmgLvooYwifSwgImlkIjoyMzAsImxvZ2luQWNjdCI6ImFkbWluIiwidGVuYW50SWQiOiIxIn0="));
//        securitySchemeMap.put("x-gateway-security-org", new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name("x-gateway-security-org").description("机构信息eyJjb2RlIjoiODAiLCJwYXJlbnRDb2RlIjoiOTk5OTk5IiwibmFtZSI6IuihjOmihuWvvCIsImlkIjozNCwicGFyZW50SWQiOjk5OTk5OX0="));
//        return new Components().securitySchemes(securitySchemeMap);
//    }
//
//    /**
//     * 数据
//     */
//    @Bean
//    public GroupedOpenApi qltyApi() {
//        return GroupedOpenApi.builder().group("相赫-sys-管理")
//                .packagesToScan("com.xianghe.xianghe.sys")
//                .build();
//    }
//}