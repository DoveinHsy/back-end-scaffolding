package org.xiaoxingbomei.entity;

import lombok.Data;

@Data
public class SysUser
{
    private String userId;      // 主键
    private String name;        // 名字
    private String password;    // 密码
    private String gender;      // 性别
    private String idCard;      // 证件号码
    private String phoneNumber; // 电话号码
    private String mobileNumber;// 手机号码
    private String userType;    // 用户类型:1-内部用户 2-xxx 3-xxx
    private String createTime;  // 创建时间
    private String updateTime;  // 更新时间
    private String description; // 备注
    private String status;      // 状态:0-失效 1-有效
}
