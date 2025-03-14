create table customer
(
    id            varchar(36)  not null comment '用户ID' primary key, -- 改为 VARCHAR 类型，长度为 36（UUID 的标准长度）
    name          varchar(50)  not null comment '名字',
    gender        varchar(10)  null     comment '性别',
    id_card        varchar(20)  null     comment '证件号码',
    phone_number   varchar(20)  null     comment '电话号码',
    mobile_number  varchar(20)  null     comment '手机号码',
    address       varchar(255) null     comment '地址',
    nationality   varchar(100) null     comment '国籍',
    country       varchar(100) null     comment '国家',
    state         varchar(100) null     comment '省份',
    city          varchar(100) null     comment '市',
    bankCard      varchar(20)  null     comment '银行卡',
    occupation    varchar(100) null     comment '职业',
    companyName   varchar(100) null     comment '公司名称',
    maritalStatus varchar(20)  null     comment '婚姻状况',
    userType      varchar(20)  null     comment '用户类型:1-内部用户 2-对私客户 3-对公客户',
    createTime    varchar(20)  null     comment '创建时间',
    updateTime    varchar(20)  null     comment '更新时间',
    status        varchar(10)  null     comment '状态:0-失效 1-有效'
)
    comment '用户表';