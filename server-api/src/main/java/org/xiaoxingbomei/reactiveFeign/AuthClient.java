package org.xiaoxingbomei.reactiveFeign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.xiaoxingbomei.common.entity.response.GlobalResponse;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.List;


/**
 * Auth模块  webflux rpc
 */
//@ReactiveFeignClient(name="auth")
@FeignClient("auth")
public interface AuthClient
{

    @RequestMapping(value = "/getPermissionList", method = RequestMethod.POST)
//    Mono<GlobalResponse> getPermissionList(@RequestParam String loginId);
    List<String> getPermissionList(@RequestParam String loginId);


    @RequestMapping(value = "/getRoleList", method = RequestMethod.POST)
    List<String> getRoleList(@RequestParam String loginId);


}
