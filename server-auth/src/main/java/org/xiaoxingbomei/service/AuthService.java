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

    GlobalResponse createRole(String paramString);   // 创建角色
    GlobalResponse getRole   (String paramString);   // 查询角色（根据用户查或者查询全部）
    GlobalResponse updateRole(String paramString);   // 修改角色
    GlobalResponse deleteRole(String paramString);   // 删除角色
    GlobalResponse assignUserRoles(String paramString);// 给用户授予角色

    GlobalResponse createPermission     (String paramString); //
    GlobalResponse getAllPermission     (String paramString); //
    GlobalResponse assignRolePermissions(String paramString); //
    GlobalResponse getRolePermissions   (String paramString); //
    GlobalResponse getUserPermissions   (String paramString); //





}
