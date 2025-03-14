package org.xiaoxingbomei.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Login implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String userId;          // 登录用户的id
    private String token;           // 令牌
    private Long   tokenExpiration; // 令牌有效期

}
