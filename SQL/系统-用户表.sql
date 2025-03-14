create table sys_user
(
    user_id       varchar(36)  not null comment '用户ID' primary key, -- 改为 VARCHAR 类型，长度为 36（UUID 的标准长度）
    name          varchar(50)  not null comment '名字',
    password      varchar(50)  not null comment '密码',
    gender        varchar(10)  null     comment '性别',
    id_card       varchar(20)  null     comment '证件号码',
    phone_number  varchar(20)  null     comment '电话号码',
    mobile_number varchar(20)  null     comment '手机号码',
    user_type     varchar(20)  null     comment '用户类型:1-内部用户 2-xxx 3-xxx',
    create_time   varchar(20)  null     comment '创建时间',
    update_time   varchar(20)  null     comment '更新时间',
    description   varchar(255) null     comment '备注',
    status        varchar(10)  null     comment '状态:0-失效 1-有效'
)
    comment '系统-用户表';