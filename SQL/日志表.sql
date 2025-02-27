# 用户通用业务日志落库的表
create table `ysx-app`.business_log_common
(
    id              varchar(80) not null comment '主键'   primary key,
    userId          varchar(20) not null comment '用户id',
    userName        varchar(20) null     comment '用户名称',
    roleId          varchar(20) null     comment '角色id',
    roleName        varchar(20) null     comment '角色名',
    orgId           varchar(20) null     comment '机构id',
    orgName         varchar(20) null     comment '机构名',
    channelId       varchar(20) null     comment '渠道id',
    channelName     varchar(20) null     comment '渠道名',
    deviceId        varchar(20) null     comment '设备id',
    deviceName      varchar(20) null     comment '设备名',
    pageId          varchar(20) null     comment '页面id',
    pageName        varchar(20) null     comment '页面名',
    moduleId        varchar(20) null     comment '模块id',
    moduleName      varchar(20) null     comment '模块名',
    operateTypeId   varchar(20) null     comment '操作类型id',
    operateTypeName varchar(20) null     comment '操作类型名',
    operateTime     varchar(20) null     comment '操作时间',
    content         text        null     comment '备注'
)
    comment '业务操作日志表';

