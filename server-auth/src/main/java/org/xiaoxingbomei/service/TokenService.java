package org.xiaoxingbomei.service;


import org.xiaoxingbomei.common.entity.response.GlobalResponse;

public interface TokenService
{

    GlobalResponse getCacheToken(String paramString);

}
