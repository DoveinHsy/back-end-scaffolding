package org.xiaoxingbomei.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.xiaoxingbomei.common.Enum.GlobalCodeEnum;
import org.xiaoxingbomei.common.entity.response.GlobalResponse;
import org.xiaoxingbomei.common.utils.Request_Utils;
import org.xiaoxingbomei.service.AuthService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService
{
    @Override
    public GlobalResponse login(String paramString)
    {
        // 1、解析前端参数
        String loginType = Request_Utils.getParam(paramString, "loginType");

        // 2、登录方式路由
        if(StringUtils.equals("1", loginType))  // 密码登录
        {
            GlobalResponse ret = loginByPassword(paramString);
            return ret;
        }

        if(StringUtils.equals("2", loginType))  // 验证码登录
        {
            loginByXxx(paramString);
        }

        if(StringUtils.equals("3",loginType))   // 单点登录
        {
            loginBySso(paramString);
        }

        return null;
    }

    // 登录-密码
    private GlobalResponse loginByPassword(String paramString)
    {
        // 1、接收前端参数
        String userId   = Request_Utils.getParam(paramString, "userId");
        String password = Request_Utils.getParam(paramString, "password");

        // 此处用代码替代真实情况下数据库比对操作
        if(StringUtils.equals("ysx",userId) & StringUtils.equals("ysx123",password))
        {
            StpUtil.login(userId);
            return GlobalResponse.success(GlobalCodeEnum.SUCCESS.getCode(), GlobalCodeEnum.SUCCESS.getMessage(),"登录成功","",null);
        }

        return GlobalResponse.error("登录失败");
    }

    // 登录-验证码
    private GlobalResponse loginByXxx(String paramString)
    {
        return null;
    }

    // 登录-单点登录
    private GlobalResponse loginBySso(String paramString)
    {
        return null;
    }


    @Override
    public GlobalResponse isLogin(String paramString)
    {
        boolean loginResult = StpUtil.isLogin();
        return GlobalResponse.success(loginResult);
    }

    @Override
    public GlobalResponse checkLogin(String paramString)
    {
        StpUtil.checkLogin();
        return GlobalResponse.success("已登录");
    }

    @Override
    public GlobalResponse logout(String paramString)
    {
        if(StpUtil.isLogin())
        {
            StpUtil.logout();
            return GlobalResponse.success("已登出");
        }
        return GlobalResponse.success("未登录，无需登出");
    }


    @Override
    public GlobalResponse getSaTokenInfo(String paramString)
    {
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return GlobalResponse.success(tokenInfo,"获取sa-token信息");
    }

    @Override
    public GlobalResponse getLoginId(String paramString)
    {
        Object loginId = StpUtil.getLoginId();
        return GlobalResponse.success(loginId,"获取登录用户id");
    }


    @Override
    public GlobalResponse getPermissionList(String loginId)
    {
        List<String> permissionList = StpUtil.getPermissionList(loginId);
        return GlobalResponse.success(permissionList,loginId);
    }

    @Override
    public GlobalResponse getRoleList(String loginId)
    {
        List<String> roleList = StpUtil.getRoleList(loginId);
        return GlobalResponse.success(roleList,loginId);
    }
}
