//package org.xiaoxingbomei.Enum;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import org.xiaoxingbomei.config.Feign.FeignClient;
//import org.xiaoxingbomei.config.http.HttpClient;
//import org.xiaoxingbomei.service.Client;
//
//import java.util.Objects;
//
//@Getter
//@AllArgsConstructor
//public enum ClientEnum {
//    FEIGN("feign", new FeignClient()),
//    HTTP("http", new HttpClient());
//    private String code;
//    private Client client;
//
//    public static ClientEnum getByCode(String code) {
//        for (ClientEnum value : ClientEnum.values()) {
//            if (Objects.equals(value.getCode(), code)) {
//                return value;
//            }
//        }
//        return null;
//    }
//}