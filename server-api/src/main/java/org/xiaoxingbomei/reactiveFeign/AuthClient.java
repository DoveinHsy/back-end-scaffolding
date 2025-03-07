package org.xiaoxingbomei.reactiveFeign;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.xiaoxingbomei.common.entity.response.GlobalResponse;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;



@ReactiveFeignClient(name="auth")
public interface AuthClient
{

    @RequestMapping(value = "/getPermissionList", method = RequestMethod.POST)
    Mono<GlobalResponse> getPermissions(@RequestParam String loginId);

}
