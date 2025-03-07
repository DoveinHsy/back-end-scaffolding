package org.xiaoxingbomei.config.satoken;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * sa-token全局过滤器
 */
@Configuration
@Slf4j
public class SatokenConfig
{

    @Autowired
    private StpInterfaceImpl stpInterface;

    /**
     * 注册 sa-token 全局过滤器（网关统一鉴权）
     */
    @Bean
    public SaReactorFilter getSaReactorFilter()
    {
        return new SaReactorFilter()
                // 拦截地址
                .addInclude("/**")    /* 拦截全部path */
                // 开放地址
                .addExclude("/favicon.ico","/auth/login")
                // 鉴权方法：每次访问进入
                .setAuth(obj ->
                {
                    String token = StpUtil.getTokenValue();
                    log.info("当前请求的 Token: {}", token);
                    if (token == null || !StpUtil.isLogin())
                    {
                        throw new NotLoginException("Token无效", "login", token);
                    }

                    // 登录校验 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
                    SaRouter.match("/**", "/auth/login", r -> StpUtil.checkLogin());

                    //
                    SaRouter.match("/auth/**", r -> StpUtil.checkPermission("auth"));

                    // 更多匹配 ...  */

                })
                // 异常处理方法：每次setAuth函数出现异常时进入
                .setError(e ->
                {
                    log.info("SaReactorFilter error", e);
                    return SaResult.error(e.getMessage());
                })
                ;
    }

}
