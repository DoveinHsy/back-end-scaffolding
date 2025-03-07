package org.xiaoxingbomei.config.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.xiaoxingbomei.reactiveFeign.AuthClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 自定义权限验证接口扩展
 *
 * 数据获取：先从Redis缓存获取，缓存无数据则通过ReactiveFeign调用auth服务
 *
 */
@Component
@Slf4j
public class StpInterfaceImpl implements StpInterface
{
    @Autowired
    private RedisTemplate redisTemplate; // Redis 缓存

    @Autowired
    private AuthClient authClient; // reactivefeign 客户端

    private final Scheduler scheduler =  Schedulers.boundedElastic(); // 用于阻塞操作的线程池

    // =======================================================


    @Override
    public List<String> getPermissionList(Object loginId, String loginType)
    {
        log.info("getPermissionList start for loginId: {}", loginId);
        String cacheKey = "PERMISSION:" + loginId;
        Object cached = redisTemplate.opsForValue().get(cacheKey);
        if (cached instanceof List)
        {
            log.info("Returning cached permissions: {}", cached);
            return (List<String>) cached;
        }

        // 异步更新缓存
        authClient.getPermissions(loginId.toString())
                .doOnNext(response ->
                {
                    log.info("Fetched permissions: {}", response.getData());
                    redisTemplate.opsForValue().set(cacheKey, response.getData(), 3600, TimeUnit.SECONDS);
                })
                .doOnError(e -> log.error("Failed to fetch permissions: {}", e.getMessage()))
                .subscribeOn(scheduler)
                .subscribe();

        // 因为在auth的登录处 已经做了缓存预热，理论不会走到这里
        log.info("No cached permissions, returning empty list");
        return Collections.emptyList();
    }

    // 异步方法供过滤器调用
//    public Mono<List<String>> getPermissionsAsync(String loginId)
//    {
//        String cacheKey = "PERMISSION:" + loginId;
//        Object cached = redisTemplate.opsForValue().get(cacheKey);
//        if (cached instanceof List)
//        {
//            return Mono.just((List<String>) cached);
//        }
//        return authClient.getPermissions(loginId)
//                .doOnNext(permissions -> redisTemplate.opsForValue().set(cacheKey, permissions, 3600, TimeUnit.SECONDS))
//                .onErrorResume(e -> {
//                    log.error("Failed to fetch permissions: {}", e.getMessage());
//                    return Mono.just(Collections.emptyList());
//                });
//    }

//    @Override
//    public List<String> getRoleList(Object loginId, String loginType) {
//        // 类似逻辑，优先缓存，后 RPC
//        String cacheKey = "ROLE:" + loginId;
//        List<String> roles = (List<String>) redisTemplate.opsForValue().get(cacheKey);
//        if (roles != null && !roles.isEmpty()) {
//            return roles;
//        }
//
//        return authClient.get(String.valueOf(loginId))
//                .switchIfEmpty(Mono.just(Collections.emptyList()))
//                .doOnNext(rolesList -> redisTemplate.opsForValue().set(cacheKey, rolesList))
//                .block();
//    }

//    /**
//     * 返回此 loginId 权限列表
//     */
//    @Override
//    public List<String> getPermissionList(Object loginId, String loginType)
//    {
//        return Collections.emptyList();
//    }
//
    /**
     * 返回此 loginId 的角色列表
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType)
    {
        return Collections.emptyList();
    }

}
