//package org.xiaoxingbomei.config.Feign;
//
//import com.alibaba.fastjson.JSON;
//import com.netflix.ribbon.RequestTemplate;
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.springframework.beans.factory.ObjectFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//
///**
// * 配置feign解析器
// */
//public class FeignConfig implements RequestInterceptor
//{
//
//    /**
//     * messageConverters
//     **/
//    @Autowired
//    private ObjectFactory<HttpMessageConverters> messageConverters;
//
//    @Override
//    public void apply(RequestTemplate requestTemplate)
//    {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (attributes != null) {
//            HttpServletRequest request = attributes.getRequest();
//            // token
//            // requestTemplate.header("user", request.getHeader("user"));
//        } else {
//            Long tenantId = (Long) ThreadLocalUtil.get("tenantId");
//            if(tenantId==null && SecurityUserHelper.get()!=null) {
//                tenantId = SecurityUserHelper.get().getTenantId();
//            }
//            SecurityUser build = SecurityUser.builder().tenantId(tenantId).build();
//            String info = Base64.encodeBase64URLSafeString(JSON.toJSONString(build).getBytes());
//            requestTemplate.header("x-gateway-security-user", info);
//        }
//    }
//    /**
//     * feign编码设置
//     *
//     * @return 返回结果
//     */
//    @Bean
//    public Decoder feignDecoder() {
//        return new ResponseEntityDecoder(new SpringDecoder(feignHttpMessageConverter()));
//    }
//
//
//    /**
//     * 设置feign支持的格式
//     *
//     * @return 返回结果
//     */
//    public ObjectFactory<HttpMessageConverters> feignHttpMessageConverter() {
//        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
//        jsonConverter.setSupportedMediaTypes(
//                Arrays.asList(
//                        MediaType.APPLICATION_JSON,
//                        MediaType.APPLICATION_FORM_URLENCODED,
//                        MediaType.TEXT_PLAIN, MediaType.TEXT_HTML,
//                        MediaType.APPLICATION_OCTET_STREAM
//                ));
//        HttpMessageConverters httpMessageConverters = new HttpMessageConverters(jsonConverter);
//        return () -> httpMessageConverters;
//    }
//
//    /**
//     * feignFormEncoder
//     *
//     * @return Encoder
//     */
//    @Bean
//    public Encoder feignFormEncoder() {
//        return new SpringFormEncoder(new SpringEncoder(messageConverters));
//    }
//}
