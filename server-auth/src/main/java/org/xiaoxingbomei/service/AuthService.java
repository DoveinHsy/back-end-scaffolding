package org.xiaoxingbomei.service;

import org.xiaoxingbomei.common.entity.response.GlobalResponse;

public interface AuthService
{
    GlobalResponse login         (String paramString);
    GlobalResponse isLogin       (String paramString);
    GlobalResponse checkLogin    (String paramString);
    GlobalResponse logout        (String paramString);
    GlobalResponse getSaTokenInfo(String paramString);
    GlobalResponse getLoginId(String paramString);

    GlobalResponse getPermissionList(String loginId);
    GlobalResponse getRoleList(String paramString);
}
