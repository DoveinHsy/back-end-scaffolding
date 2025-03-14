create table sys_permission
(
    permission_key   varchar(20)  not null comment '权限标识 primary key',
    permission_name  varchar(50)  not null comment '权限名称',
    parent_id        varchar(50)  not null comment '父权限id，支持树形结构',
    permission_type  varchar(50)  not null comment '权限类型：1-菜单 2-按钮',
    description      varchar(255) null     comment '描述',
    status           varchar(10)  null     comment '状态:0-失效 1-有效'
)
    comment '系统-权限表';