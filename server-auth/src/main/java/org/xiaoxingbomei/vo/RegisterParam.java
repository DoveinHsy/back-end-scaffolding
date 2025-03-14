package org.xiaoxingbomei.vo;

import lombok.Data;

@Data
public class RegisterParam
{
    private String phoneNumber; // 电话号
    private String captcha;     // 验证码
    private String inviteCode;  // 邀请码
}
