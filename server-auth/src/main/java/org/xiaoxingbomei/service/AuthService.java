package org.xiaoxingbomei.service;

import org.xiaoxingbomei.common.entity.response.GlobalResponse;

import java.util.List;

public interface AuthService
{

    // ==========================satoken==========================
    GlobalResponse register      (String paramString);
    GlobalResponse login         (String paramString); // 通过sa-token
    GlobalResponse isLogin       (String paramString); // 通过sa-token
    GlobalResponse checkLogin    (String paramString); // 通过sa-token
    GlobalResponse logout        (String paramString); // 通过sa-token
    GlobalResponse getSaTokenInfo(String paramString); // 通过sa-token
    GlobalResponse getLoginId    (String paramString); // 通过sa-token


    GlobalResponse createRole       (String paramString);   // 创建角色
    List<String> getRoleList      (String loginId);  // 获取用户的所有角色
    List<String> getRoleListByStore      (String loginId);  // 获取用户的所有角色
    GlobalResponse getPermissionList(String loginId);  // 获取用户的所有权限

    GlobalResponse assignUserRoles(String paramString); // 给用户授予角色

}
