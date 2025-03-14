package org.xiaoxingbomei.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.xiaoxingbomei.common.Enum.AuthErrorCode;
import org.xiaoxingbomei.common.entity.exception.AuthException;
import org.xiaoxingbomei.common.entity.response.GlobalResponse;
import org.xiaoxingbomei.common.utils.Request_Utils;
import org.xiaoxingbomei.service.TokenService;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.xiaoxingbomei.common.CacheConstant.CACHE_KEY_SEPARATOR;

@Service
public class TokenServiceImpl implements TokenService
{

    private static final String TOKEN_PREFIX = "token:";
    private final RedisTemplate redisTemplate;

    public TokenServiceImpl(@Qualifier("redisTemplate") RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    // =========================================================================================

    @Override
    public GlobalResponse getCacheToken(String paramString)
    {
        // 获取前端参数
        String scene = Request_Utils.getParam(paramString, "scene");

        // 通过判断sa-token是否登录，获取缓存中token
        if(StpUtil.isLogin())
        {
            String tokenValue = UUID.randomUUID().toString(); //
            String tokenKey   = TOKEN_PREFIX + CACHE_KEY_SEPARATOR + scene + CACHE_KEY_SEPARATOR + tokenValue;
            redisTemplate.opsForValue().set(tokenKey, tokenValue,30, TimeUnit.MINUTES);
            return GlobalResponse.success("根据场景获取缓存中的令牌成功");
        }
        throw new AuthException(AuthErrorCode.USER_NOT_LOGIN);
    }
}
