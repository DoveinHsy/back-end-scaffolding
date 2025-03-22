package org.xiaoxingbomei.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xiaoxingbomei.common.entity.response.GlobalResponse;
import org.xiaoxingbomei.constant.ApiConstant;
import org.xiaoxingbomei.service.AuthService;

import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class AuthController
{
    @Autowired
    private AuthService authService;

    // =========================================================================================

    /**
     * sa-token controller 控制
     * 登录：登录统一入口
     *    根据登录类型路由不同的登录方式
     *  - 密码登录
     *  - 验证码登录
     *  - 单点登录
     */
    @Operation(summary = "登录",description = "集成sa-token，登录")
    @RequestMapping(value = ApiConstant.Auth.login, method = RequestMethod.POST)
    public GlobalResponse login(@RequestBody String paramString)
    {
        GlobalResponse ret = authService.login(paramString);
        return ret;
    }

    @Operation(summary = "是否登录",description = "集成sa-token，获取当前会话是否已经登录，返回 true false")
    @RequestMapping(value = ApiConstant.Auth.isLogin, method = RequestMethod.POST)
    public GlobalResponse isLogin(@RequestBody String paramString)
    {
        GlobalResponse ret = authService.isLogin(paramString);
        return ret;
    }

    @Operation(summary = "检测登录",description = "集成sa-token，如果未登录，则抛出异常 [NotLoginException]")
    @RequestMapping(value = ApiConstant.Auth.checkLogin, method = RequestMethod.POST)
    public GlobalResponse checkLogin(@RequestBody String paramString)
    {
        GlobalResponse ret = authService.checkLogin(paramString);
        return ret;
    }


    @Operation(summary = "登出",description = "集成sa-token,登出")
    @RequestMapping(value = ApiConstant.Auth.logout, method = RequestMethod.POST)
    public GlobalResponse logout(@RequestBody String paramString)
    {
        GlobalResponse ret = authService.logout(paramString);
        return ret;
    }

    @Operation(summary = "获取token信息",description = "集成sa-token,获取token信息")
    @RequestMapping(value = ApiConstant.Auth.getSaTokenInfo, method = RequestMethod.POST)
    public GlobalResponse getSaTokenInfo(@RequestBody String paramString)
    {
        GlobalResponse ret = authService.getSaTokenInfo(paramString);
        return ret;
    }

    @Operation(summary = "获取登录id",description = "集成sa-token,获取登录的用户id")
    @RequestMapping(value = ApiConstant.Auth.getLoginId, method = RequestMethod.POST)
    public GlobalResponse getLoginId(@RequestBody String paramString)
    {
        GlobalResponse ret = authService.getLoginId(paramString);
        return ret;
    }


    @Operation(summary = "角色-创建角色",description = "创建角色并关联对应的权限")
    @RequestMapping(value = ApiConstant.Auth.createRole, method = RequestMethod.POST)
    public GlobalResponse createRole(@RequestBody String paramString)
    {
        GlobalResponse ret = authService.createRole(paramString);
        return ret;
    }

    @Operation(summary = "角色-获取角色集合",description = "获取全部的角色列表")
    @RequestMapping(value = ApiConstant.Auth.getRole, method = RequestMethod.POST)
    public GlobalResponse getRole(@RequestBody String paramString)
    {
        GlobalResponse ret = authService.getRole(paramString);
        return ret;
    }

    @Operation(summary = "角色-授予用户角色",description = "集成sa-token,授予用户角色")
    @RequestMapping(value = ApiConstant.Auth.assignUserRoles, method = RequestMethod.POST)
    public GlobalResponse assignUserRoles(@RequestBody String paramString)
    {
        GlobalResponse response = authService.assignUserRoles(paramString);
        return response;
    }


    @Operation(summary = "权限-",description = "XXX")
    @RequestMapping(value = ApiConstant.Auth.createPermission, method = RequestMethod.POST)
    public GlobalResponse createPermission(@RequestBody String loginId)
    {
        GlobalResponse ret = authService.createPermission(loginId);
        return ret;
    }

    @Operation(summary = "权限-",description = "XXX")
    @RequestMapping(value = ApiConstant.Auth.getAllPermission, method = RequestMethod.POST)
    public GlobalResponse getAllPermission(@RequestBody String loginId)
    {
        GlobalResponse ret = authService.getAllPermission(loginId);
        return ret;
    }

    @Operation(summary = "权限-",description = "XXX")
    @RequestMapping(value = ApiConstant.Auth.assignRolePermissions, method = RequestMethod.POST)
    public GlobalResponse assignRolePermissions(@RequestBody String loginId)
    {
        GlobalResponse ret = authService.assignRolePermissions(loginId);
        return ret;
    }

    @Operation(summary = "权限-",description = "XXX")
    @RequestMapping(value = ApiConstant.Auth.getRolePermissions, method = RequestMethod.POST)
    public GlobalResponse getRolePermissions(@RequestBody String loginId)
    {
        GlobalResponse ret = authService.getRolePermissions(loginId);
        return ret;
    }

    @Operation(summary = "权限-",description = "XXX")
    @RequestMapping(value = ApiConstant.Auth.getUserPermissions, method = RequestMethod.POST)
    public GlobalResponse getUserPermissions(@RequestBody String loginId)
    {
        GlobalResponse ret = authService.getUserPermissions(loginId);
        return ret;
    }




}
