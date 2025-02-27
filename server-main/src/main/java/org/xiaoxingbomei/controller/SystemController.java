package org.xiaoxingbomei.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.constant.ApiConstant;
import org.xiaoxingbomei.service.SystemService;
import org.xiaoxingbomei.service.TechService;

import java.util.HashMap;

/**
 * 系统 controller
 */

@Tag(name="系统controller",description = "系统controller")
@RestController
@Slf4j
public class SystemController
{
    @Autowired
    private SystemService systemService;

    @Autowired
    private TechService  techService;

    // =================================

    @Operation(summary = "获取全部系统信息",description = "通过es或者数据库获取全部系统信息")
    @RequestMapping(value = ApiConstant.System.getSystemInfo, method = RequestMethod.POST)
    public GlobalEntity getSystemInfo(@RequestBody String paramString)
    {
        GlobalEntity ret = systemService.getSystemInfo(paramString);;
        return ret;
    }

    @Operation(summary = "批量操作系统信息",description = "通过excel导入，无则新增，有则更新")
    @RequestMapping(value = ApiConstant.System.multiHandleSystem, method = RequestMethod.POST)
    public GlobalEntity multiHandleSystem(@RequestParam MultipartFile file)
    {
        GlobalEntity ret = systemService.multiHandleSystem(file);;
        return ret;
    }


}
