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

    @Operation(summary = "获取权限集合",description = "集成sa-token,获取登录的用户的权限集合")
    @RequestMapping(value = ApiConstant.Auth.getPermissionList, method = RequestMethod.POST)
    public GlobalResponse getPermissionList(@RequestParam String loginId)
    {
        GlobalResponse ret = authService.getPermissionList(loginId);
        return ret;
    }


    @Operation(summary = "创建角色",description = "创建角色并关联对应的权限")
    @RequestMapping(value = ApiConstant.Auth.createRole, method = RequestMethod.POST)
    public GlobalResponse createRole(@RequestParam String paramString)
    {
        GlobalResponse ret = authService.createRole(paramString);
        return ret;
    }

    @Operation(summary = "获取角色集合",description = "集成sa-token,获取登录的用户的角色集合")
    @RequestMapping(value = ApiConstant.Auth.getRoleList, method = RequestMethod.POST)
    public List<String> getRoleList(@RequestParam String loginId)
    {
        log.info("应该是别的地方rpc这里");
        List<String> roleList = authService.getRoleList(loginId);

        return roleList;
    }

    @Operation(summary = "获取角色集合",description = "集成sa-token,获取登录的用户的角色集合")
    @RequestMapping(value = ApiConstant.Auth.getRoleListByStore, method = RequestMethod.POST)
    public GlobalResponse getRoleListByStore(@RequestBody String loginId)
    {
        List<String> roleList = authService.getRoleListByStore(loginId);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("loginId",loginId);
        resultMap.put("roleList",roleList);
        return GlobalResponse.success(resultMap,"获取登录用户的角色列表成功");
    }


//    /**
//     * RBAC-
//     */
//    @Operation(summary = "RBAC-",description = "")
//    @RequestMapping(value = ApiConstant.Auth.Xxx,method = RequestMethod.POST)
//    public GlobalResponse xxx(@RequestMapping String paramString)
//    {
//        GlobalResponse response = authService.xxx();
//        return response;
//    }


}
