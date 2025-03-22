package org.xiaoxingbomei.config.satoken;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
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

    // ======================================================================================

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
                .addExclude("/favicon.ico")
                // 鉴权方法：每次访问进入
                .setAuth(obj ->
                {
                    // 登录校验 -- 拦截所有路由，并排除/auth 用于开放登录
                    SaRouter.match("/**").notMatch("/auth/**").check(r -> StpUtil.checkLogin());

                    // 角色认证 -- 不同模块，校验不同的角色
                    SaRouter.match("/main/**", r -> StpUtil.checkRoleAnd("admin"));

                    // 权限认证 -- 不同模块，校验不同的权限
                    SaRouter.match("/xxx/**", r -> StpUtil.checkPermission("xxx"));

                })
                // 异常处理方法：每次setAuth函数出现异常时进入
                .setError(this::getSaResult);
    }

    // 包装权限异常响应
    private SaResult getSaResult(Throwable throwable)
    {
        if(throwable instanceof NotLoginException)
        {
            log.error("请先登录");
            return SaResult.error("请先登录");
        }
        else if (throwable instanceof NotRoleException)
        {
            log.error("您无角色进行此操作");
            return SaResult.error("您无角色进行此操作");
        }
        else if (throwable instanceof NotPermissionException)
        {
            log.error("您无权限进行此操作");
            return SaResult.error("您无权限进行此操作");
        }
        else
        {
            log.error("权限受限{}",throwable.getMessage());
            return SaResult.error("权限受限");
        }
    }

}
