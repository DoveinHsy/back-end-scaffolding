package org.xiaoxingbomei.entity;

import lombok.Data;

@Data
public class SysPermission
{
    private String permissionKey ; // 权限标识
    private String permissionName; // 权限名称
    private String parentId      ; // 父权限id，支持树形结构
    private String permissionType; // 权限类型：1-菜单 2-按钮
    private String description   ; // 描述
    private String status        ; // 状态:0-失效 1-有效
}
