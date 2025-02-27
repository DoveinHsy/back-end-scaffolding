package org.xiaoxingbomei.service;


import org.xiaoxingbomei.dto.SystemAuthDto;

import org.xiaoxingbomei.common.entity.GlobalEntity;

/**
 * 权限 Service
 */
public interface AuthService
{


    public GlobalEntity doLogin(String paramString);

    public GlobalEntity generateCaptcha(String paramString);
    public GlobalEntity validateCaptcha(String paramString);

    public GlobalEntity isLogin(String paramString);
    public GlobalEntity checkLogin(String paramString);
    public GlobalEntity tokenInfo(String paramString);
    public GlobalEntity logOut(String paramString);

    public GlobalEntity getPermissionList(SystemAuthDto dto);
    public GlobalEntity hasPermissionList(SystemAuthDto dto);
    public GlobalEntity checkPermissionList(SystemAuthDto dto);
    public GlobalEntity checkPermissionAnd(SystemAuthDto dto);
    public GlobalEntity CheckPermissionOr(SystemAuthDto dto);

    public GlobalEntity getRoleList(SystemAuthDto dto);
    public GlobalEntity hasRole(SystemAuthDto dto);
    public GlobalEntity checkRole(SystemAuthDto dto);
    public GlobalEntity checkRoleAnd(SystemAuthDto dto);
    public GlobalEntity checkRoleOr(SystemAuthDto dto);

}