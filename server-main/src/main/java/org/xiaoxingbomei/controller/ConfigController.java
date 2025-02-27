package org.xiaoxingbomei.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xiaoxingbomei.constant.ApiConstant;
import org.xiaoxingbomei.service.GlobalConfigService;

import org.xiaoxingbomei.common.entity.GlobalEntity;


@RestController
@Tag(name = "配置Controller",description = "springboot集成各种框架的配置")
public class ConfigController
{

    @Autowired
    private GlobalConfigService configService;



    @Operation(summary = "获取apollo配置信息",description = "获取apollo配置信息")
    @RequestMapping(value = ApiConstant.Config.getApolloConfig,method = RequestMethod.POST)
    public GlobalEntity getApolloConfig()
    {
        GlobalEntity apolloConfigInfo = configService.getApolloConfig();

        return apolloConfigInfo;

    }

    @Operation(summary = "获取apollo配置信息",description = "获取apollo配置信息")
    @RequestMapping(value = ApiConstant.Config.getApolloValueByKey,method = RequestMethod.POST)
    public GlobalEntity getApolloValueByKey(@RequestBody String paramString)
    {
        GlobalEntity apolloConfigInfo = configService.getApolloValueByKey(paramString);

        return apolloConfigInfo;

    }


}
