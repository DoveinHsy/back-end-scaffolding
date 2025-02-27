package org.xiaoxingbomei.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xiaoxingbomei.Enum.GlobalCodeEnum;
import org.xiaoxingbomei.annotation.RequestLimiting;
import org.xiaoxingbomei.annotation.ServiceSwitch;
import org.xiaoxingbomei.constant.ApiConstant;
import org.xiaoxingbomei.constant.Constant;
import org.xiaoxingbomei.dto.SystemAuthDto;
import org.xiaoxingbomei.service.AuthService;

import org.xiaoxingbomei.common.entity.GlobalEntity;

/**
 * 集成sa-token，权限controller
 */
@RestController
@Tag(name="权限controller",description = "集成sa-token，权限controller")
public class AuthController
{
    @Autowired
    public AuthService authService;

    @Operation(summary = "生成验证码", description = "生成验证码")
    @RequestMapping(value = ApiConstant.Auth.generateCaptcha, method = RequestMethod.POST)
    public GlobalEntity generateCaptcha(@RequestBody String paramString)
    {
        GlobalEntity ret = null;

        ret = authService.generateCaptcha(paramString);

        return ret;
    }

    @Operation(summary = "生成验证码", description = "生成验证码")
    @RequestMapping(value = ApiConstant.Auth.validateCaptcha, method = RequestMethod.POST)
    public GlobalEntity validateCaptcha(@RequestBody String paramString)
    {
        GlobalEntity ret = null;

        ret = authService.validateCaptcha(paramString);

        return ret;
    }


    @Operation(summary = "doLogin",description = "集成sa-token，登录")
    @RequestLimiting
    @ServiceSwitch(switchKey = Constant.SwitchConfigCode.XX_SWITCH)
    @RequestMapping(value = ApiConstant.Auth.doLogin, method = RequestMethod.POST)
    public GlobalEntity doLogin(@RequestBody String paramString)
    {
        GlobalEntity ret = null;

        ret = authService.doLogin(paramString);

        return ret;
    }

    @Operation(summary = "isLogin",description = "集成sa-token，验证是否已经登录")
    @RequestMapping(value = ApiConstant.Auth.isLogin, method = RequestMethod.POST)
    public GlobalEntity isLogin(@RequestBody String paramString)
    {
        GlobalEntity ret = null;

        ret = authService.isLogin(paramString);

        return ret;
    }


    @Operation(summary = "checkLogin",description = "")
    @RequestMapping(value = ApiConstant.Auth.checkLogin, method = RequestMethod.POST)
    public GlobalEntity checkLogin(@RequestBody String paramString)
    {
        GlobalEntity ret = null;

        ret = authService.checkLogin(paramString);

        return ret;
    }


    @Operation(summary = "",description = "")
    @RequestMapping(value = ApiConstant.Auth.tokenInfo, method = RequestMethod.POST)
    public GlobalEntity tokenInfo(@RequestBody String paramString)
    {
        GlobalEntity ret = null;


        ret = authService.tokenInfo(paramString);


        return ret;
    }
    @Operation(summary = "logOut",description = "登出")
    @RequestMapping(value = ApiConstant.Auth.logOut, method = RequestMethod.POST)
    public GlobalEntity logOut(@RequestBody String paramString)
    {
        GlobalEntity ret = null;


        ret = authService.logOut(paramString);


        return ret;
    }


    @Operation(summary = "getPermissionList",description = "")
    @RequestMapping(value = ApiConstant.Auth.getPermissionList, method = RequestMethod.POST)
    public GlobalEntity getPermissionList(@RequestBody String paramString)
    {
        GlobalEntity ret = null;
        // 校验参数
        SystemAuthDto.checkCommonParams(paramString);
        // 初始化
        SystemAuthDto dto = SystemAuthDto.initSystemAuthDto(paramString);
        if (null == dto)
        {
            return GlobalEntity.error(GlobalCodeEnum.ERROR.getCode(),GlobalCodeEnum.ERROR.getMessage(),"初始化参数失败","",null);
        }
        ret = authService.getPermissionList(dto);// 核心调用


        return ret;
    }

    @Operation(summary = "hasPermissionList",description = "")
    @RequestMapping(value = ApiConstant.Auth.hasPermissionList, method = RequestMethod.POST)
    public GlobalEntity hasPermissionList(@RequestBody String paramString)
    {
        GlobalEntity ret = null;
        // 校验参数
        SystemAuthDto.checkCommonParams(paramString);
        // 初始化
        SystemAuthDto dto = SystemAuthDto.initSystemAuthDto(paramString);
        if (null == dto)
        {
            return GlobalEntity.error(GlobalCodeEnum.ERROR.getCode(),GlobalCodeEnum.ERROR.getMessage(),"初始化参数失败","",null);
        }
        ret = authService.hasPermissionList(dto);// 核心调用


        return ret;
    }

    @Operation(summary = "checkPermissionList",description = "")
    @RequestMapping(value = ApiConstant.Auth.checkPermissionList, method = RequestMethod.POST)
    public GlobalEntity checkPermissionList(@RequestBody String paramString)
    {
        GlobalEntity ret = null;
        // 校验参数
        SystemAuthDto.checkCommonParams(paramString);
        // 初始化
        SystemAuthDto dto = SystemAuthDto.initSystemAuthDto(paramString);
        if (null == dto)
        {
            return GlobalEntity.error(GlobalCodeEnum.ERROR.getCode(),GlobalCodeEnum.ERROR.getMessage(),"初始化参数失败","",null);
        }
        ret = authService.checkPermissionList(dto);// 核心调用

        return ret;
    }

    @Operation(summary = "checkPermissionAnd",description = "")
    @RequestMapping(value = ApiConstant.Auth.checkPermissionAnd, method = RequestMethod.POST)
    public GlobalEntity checkPermissionAnd(@RequestBody String paramString)
    {
        GlobalEntity ret = null;
        // 校验参数
        SystemAuthDto.checkCommonParams(paramString);
        // 初始化
        SystemAuthDto dto = SystemAuthDto.initSystemAuthDto(paramString);
        if (null == dto)
        {
            return GlobalEntity.error(GlobalCodeEnum.ERROR.getCode(),GlobalCodeEnum.ERROR.getMessage(),"初始化参数失败","",null);
        }
        ret = authService.checkPermissionAnd(dto);// 核心调用

        return ret;
    }

    @Operation(summary = "CheckPermissionOr",description = "")
    @RequestMapping(value = ApiConstant.Auth.CheckPermissionOr, method = RequestMethod.POST)
    public GlobalEntity CheckPermissionOr(@RequestBody String paramString)
    {
        GlobalEntity ret = null;
        // 校验参数
        SystemAuthDto.checkCommonParams(paramString);
        // 初始化
        SystemAuthDto dto = SystemAuthDto.initSystemAuthDto(paramString);
        if (null == dto)
        {
            return GlobalEntity.error(GlobalCodeEnum.ERROR.getCode(),GlobalCodeEnum.ERROR.getMessage(),"初始化参数失败","",null);
        }
        ret = authService.CheckPermissionOr(dto);// 核心调用

        return ret;
    }

    @Operation(summary = "getRoleList",description = "获取角色列表")
    @RequestMapping(value = ApiConstant.Auth.getRoleList, method = RequestMethod.POST)
    public GlobalEntity getRoleList(@RequestBody String paramString)
    {
        GlobalEntity ret = null;
        // 校验参数
        SystemAuthDto.checkCommonParams(paramString);
        // 初始化
        SystemAuthDto dto = SystemAuthDto.initSystemAuthDto(paramString);
        if (null == dto)
        {
            return GlobalEntity.error(GlobalCodeEnum.ERROR.getCode(),GlobalCodeEnum.ERROR.getMessage(),"初始化参数失败","",null);
        }
        ret = authService.getRoleList(dto);// 核心调用

        return ret;
    }

    @Operation(summary = "hasRole",description = "")
    @RequestMapping(value = ApiConstant.Auth.hasRole, method = RequestMethod.POST)
    public GlobalEntity hasRole(@RequestBody String paramString)
    {
        GlobalEntity ret = null;
        // 校验参数
        SystemAuthDto.checkCommonParams(paramString);
        // 初始化
        SystemAuthDto dto = SystemAuthDto.initSystemAuthDto(paramString);
        if (null == dto)
        {
            return GlobalEntity.error(GlobalCodeEnum.ERROR.getCode(),GlobalCodeEnum.ERROR.getMessage(),"初始化参数失败","",null);
        }
        ret = authService.hasRole(dto);// 核心调用

        return ret;
    }

    @Operation(summary = "checkRole",description = "")
    @RequestMapping(value = ApiConstant.Auth.checkRole, method = RequestMethod.POST)
    public GlobalEntity checkRole(@RequestBody String paramString)
    {
        GlobalEntity ret = null;
        // 校验参数
        SystemAuthDto.checkCommonParams(paramString);
        // 初始化
        SystemAuthDto dto = SystemAuthDto.initSystemAuthDto(paramString);
        if (null == dto)
        {
            return GlobalEntity.error(GlobalCodeEnum.ERROR.getCode(),GlobalCodeEnum.ERROR.getMessage(),"初始化参数失败","",null);
        }
        ret = authService.checkRole(dto);// 核心调用

        return ret;
    }

    @Operation(summary = "checkRoleAnd",description = "")
    @RequestMapping(value = ApiConstant.Auth.checkRoleAnd, method = RequestMethod.POST)
    public GlobalEntity checkRoleAnd(@RequestBody String paramString)
    {
        GlobalEntity ret = null;
        // 校验参数
        SystemAuthDto.checkCommonParams(paramString);
        // 初始化
        SystemAuthDto dto = SystemAuthDto.initSystemAuthDto(paramString);
        if (null == dto)
        {
            return GlobalEntity.error(GlobalCodeEnum.ERROR.getCode(),GlobalCodeEnum.ERROR.getMessage(),"初始化参数失败","",null);
        }
        ret = authService.checkRoleAnd(dto);// 核心调用

        return ret;
    }

    @Operation(summary = "checkRoleOr",description = "")
    @RequestMapping(value = ApiConstant.Auth.checkRoleOr, method = RequestMethod.POST)
    public GlobalEntity checkRoleOr(@RequestBody String paramString)
    {
        GlobalEntity ret = null;
        // 校验参数
        SystemAuthDto.checkCommonParams(paramString);
        // 初始化
        SystemAuthDto dto = SystemAuthDto.initSystemAuthDto(paramString);
        if (null == dto)
        {
            return GlobalEntity.error(GlobalCodeEnum.ERROR.getCode(),GlobalCodeEnum.ERROR.getMessage(),"初始化参数失败","",null);
        }
        ret = authService.checkRoleOr(dto);// 核心调用

        return ret;
    }
}
