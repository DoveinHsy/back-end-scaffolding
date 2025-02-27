package org.xiaoxingbomei.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.constant.ApiConstant;
import org.xiaoxingbomei.service.LogService;
import org.xiaoxingbomei.vo.BusinessLogCommon;
import org.xiaoxingbomei.vo.SystemLogCommon;

@Tag(name = "日志controller",description = "日志落库controller")
@RestController
public class LogController
{

    @Autowired
    private LogService logService;

    @Operation(summary = "通用业务日志落库",description = "通用业务日志落库")
    @RequestMapping(value = ApiConstant.Log.insertBusinessLogCommon,method = RequestMethod.POST)
    public GlobalEntity insertSystemLogCommon(@RequestBody BusinessLogCommon businessLogCommon)
    {
        GlobalEntity ret;

        businessLogCommon = BusinessLogCommon.initBusinessLogCommon(businessLogCommon);

        ret = logService.insertBusinessLogCommon(businessLogCommon);

        return ret;
    }

    @Operation(summary = "通用系统日志落库",description = "通用系统日志落库")
    @RequestMapping(value = ApiConstant.Log.insertSystemLogCommon,method = RequestMethod.POST)
    public GlobalEntity insertSystemLogCommon(@RequestBody SystemLogCommon systemLogCommon)
    {
        GlobalEntity ret;

        ret = logService.insertSystemLogCommon(systemLogCommon);

        return ret;
    }

}
