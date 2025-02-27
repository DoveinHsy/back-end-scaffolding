//package org.xiaoxingbomei.config.Feign;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.TypeReference;
//import io.swagger.v3.oas.models.responses.ApiResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//import org.xiaoxingbomei.Enum.HttpMethodEmun;
//import org.xiaoxingbomei.security.SecurityUser;
//import org.xiaoxingbomei.security.SecurityUserHelper;
//import org.xiaoxingbomei.service.Client;
//import org.xiaoxingbomei.utils.Spring_Utils;
//import org.xiaoxingbomei.utils.String_Utils;
//
//import java.util.Map;
//
//@Slf4j
//public class FeignClient implements Client {
//
//    private HttpMethodEmun httpMethodEmun;
//
//    @Override
//    public void setMethod(HttpMethodEmun httpMethodEmun) {
//        this.httpMethodEmun = httpMethodEmun;
//    }
//
//    @Override
//    public Object send(Object... arms) {
//        RestTemplate restTemplate = Spring_Utils.getBean("restTemplate");
//        SecurityUser securityUser = SecurityUserHelper.get();
//        switch (httpMethodEmun) {
//            case GET:
//                break;
//            case POST:
//
//                break;
//            case DELETE:
//                break;
//            case PUT:
//                String url = arms[0].toString();
//                String bizId = ((String) arms[1]);
//                String json = ((String) arms[2]);
//                if (StringUtils.isBlank(json)) {
//                    String replace = url.replace("{id}", bizId);
//                    log.info("回调接口{}", replace);
//                    restTemplate.put(replace, null);
//                } else {
//                    Map<String, Object> map = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
//                    });
//                    map.put("id", bizId);
//                    map.put("optUserId", securityUser.getId());
//                    map.put("tenantId", securityUser.getTenantId());
//                    HttpHeaders headers = new HttpHeaders();
//                    headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
//                    HttpEntity<String> entity = new HttpEntity<>(JSON.toJSONString(map), headers);
//                    headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
//                    ResponseEntity<ApiResponse> response = restTemplate.exchange(url, HttpMethod.PUT, entity, ApiResponse.class);
//                    ApiResponse body = response.getBody();
////                    if(body.getCode()!= ExceptionCode.OK.getCode()){
////                        throw new YTRuntimeException("回调接口异常");
////                    }
//                }
//                break;
//            default:
//
//
//        }
//        return null;
//    }
//}