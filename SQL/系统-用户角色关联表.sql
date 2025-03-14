create table sys_user_role
(
    user_id   varchar(36)  not null comment '用户id',
    role_key  varchar(36)  not null comment '角色id',
    primary key (user_id,role_key)
)
    comment '系统-用户角色关联表';