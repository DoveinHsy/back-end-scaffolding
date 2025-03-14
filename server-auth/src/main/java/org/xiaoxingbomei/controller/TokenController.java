package org.xiaoxingbomei.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.netflix.discovery.converters.Auto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.xiaoxingbomei.common.entity.response.GlobalResponse;
import org.xiaoxingbomei.constant.ApiConstant;
import org.xiaoxingbomei.service.TokenService;

import java.util.UUID;

@RestController
@Slf4j
public class TokenController
{
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TokenService tokenService;

    // ==========================================================================


    @Operation(summary = "获取token",description = "")
    @RequestMapping(name = ApiConstant.Token.getCacheToken,method = RequestMethod.POST)
    public GlobalResponse getCacheToken(@RequestBody String paramString)
    {
        GlobalResponse response = tokenService.getCacheToken(paramString);
        return response;
    }

}
