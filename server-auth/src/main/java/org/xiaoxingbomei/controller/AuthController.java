package org.xiaoxingbomei.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xiaoxingbomei.common.entity.response.GlobalResponse;
import org.xiaoxingbomei.constant.ApiConstant;
import org.xiaoxingbomei.service.AuthService;

@RestController
public class AuthController
{
    @Autowired
    private AuthService authService;

    // =========================================================================================

    /**
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
    @Operation(summary = "获取权限集合",description = "集成sa-token,获取登录的用户的权限集合")
    @RequestMapping(value = ApiConstant.Auth.getPermissionList, method = RequestMethod.POST)
    public GlobalResponse getPermissionList(@RequestParam String loginId)
    {
        GlobalResponse ret = authService.getPermissionList(loginId);
        return ret;
    }


    @Operation(summary = "获取角色集合",description = "集成sa-token,获取登录的用户的角色集合")
    @RequestMapping(value = ApiConstant.Auth.getRoleList, method = RequestMethod.POST)
    public GlobalResponse getRoleList(@RequestParam String loginId)
    {
        GlobalResponse ret = authService.getRoleList(loginId);
        return ret;
    }



}
