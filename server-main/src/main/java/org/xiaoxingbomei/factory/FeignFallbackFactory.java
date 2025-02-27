//package org.xiaoxingbomei.factory;
//
//import feign.hystrix.FallbackFactory;
//import lombok.extern.log4j.Log4j2;
//
//import java.lang.reflect.Proxy;
//
///**
// * feign失败回调
// */
//@Slf4j
//public class FeignFallbackFactory<T> implements FallbackFactory<T>
//{
//
//    @Override
//    public T create(Throwable cause) {
//        log.error("feign 调用异常", cause);
//        Class<?> clazz = this.getClass();
//        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
//                (proxy, method, args) -> {
//                    StringBuilder parameterBuilder = new StringBuilder();
//                    parameterBuilder.append(method.getName());
//                    parameterBuilder.append(":");
//                    //parameterBuilder.append(JacksonUtils.toJson(args));
//                    //return ApiResponse.fail(5000, parameterBuilder.toString());
//                    return null;
//                });
//    }
//}
