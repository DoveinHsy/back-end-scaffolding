create table sys_role
(
    role_key      varchar(20)  not null comment '角色标识' primary key,
    role_name     varchar(50)  not null comment '角色名称',
    description   varchar(255) null     comment '描述',
    status        varchar(10)  null     comment '状态:0-失效 1-有效'
)
    comment '系统-角色表';