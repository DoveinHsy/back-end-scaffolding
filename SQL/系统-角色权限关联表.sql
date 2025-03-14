create table sys_role_permission
(
    role_Key       varchar(36)  not null comment '角色id',
    permission_key varchar(36)  not null comment '权限id',
    primary key (role_Key,permission_key)
)
    comment '系统-角色权限关联表';