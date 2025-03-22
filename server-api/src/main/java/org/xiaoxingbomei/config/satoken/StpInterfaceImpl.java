package org.xiaoxingbomei.config.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.xiaoxingbomei.common.entity.LoginInfo;
import org.xiaoxingbomei.reactiveFeign.AuthClient;
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

        // 因为在auth的登录处 已经做了缓存预热，理论不会走到这里
        log.info("No cached permissions, returning empty list");
        return Collections.emptyList();
    }

    /**
     * 返回此 loginId 的角色列表
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType)
    {
        log.info("getRoleList start for loginId: {}", loginId);
        // 从分布式session中获取用户登录信息
        LoginInfo loginInfo = (LoginInfo) StpUtil.getSessionByLoginId(loginId).get((String) loginId);
        List<String> roleList = loginInfo.getRoleList();
        log.info("get session roleList: {}", roleList);
        return roleList;
    }

}
