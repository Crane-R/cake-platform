create table cake_platform.address
(
    address_id bigint auto_increment comment '收货地址'
        primary key,
    content    varchar(255) null comment '内容',
    user_id    bigint       not null comment '所属用户'
)
    comment '地址';

create table cake_platform.admin
(
    admin_id bigint auto_increment comment '管理员id'
        primary key,
    code     varchar(50) not null comment '管理员码'
)
    comment '管理员表';

create table cake_platform.cake
(
    cake_id     bigint auto_increment
        primary key,
    name        varchar(100)  null comment '蛋糕名称',
    price       decimal(7, 2) null comment '价格',
    description varchar(255)  null comment '描述',
    merchant_id bigint        not null comment '逻辑外键，商家id'
)
    comment '蛋糕表';

create table cake_platform.customer
(
    customer_id bigint auto_increment comment '顾客id'
        primary key,
    nickname    varchar(50)    null comment '昵称',
    age         int            null comment '年龄',
    gender      int default -1 null comment '性别，0女1男'
)
    comment '顾客表';

create table cake_platform.merchant
(
    merchant_id  bigint auto_increment comment '商家id'
        primary key,
    code         varchar(50)  not null comment '商家码，系统生成',
    name         varchar(50)  null comment '商家名称',
    contact_info varchar(255) null comment '联系信息',
    address      varchar(255) not null comment '地址'
)
    comment '商家';

create table cake_platform.`order`
(
    order_id    varchar(50)   not null comment '系统生成的订单号'
        primary key,
    order_date  datetime      not null comment '下单日期',
    total_price decimal(7, 2) not null comment '总价',
    status      int default 0 not null invisible comment '状态，0进行中1已完成（收货了）',
    user_id     bigint        not null comment '所属用户'
)
    comment '订单';

create table cake_platform.order_item
(
    order_item_id bigint auto_increment comment '订单项id'
        primary key,
    cake_id       bigint      not null comment '对应的蛋糕id',
    order_id      varchar(50) not null comment '所属的订单编号',
    quantity      int         not null comment '该种蛋糕的订购数量'
)
    comment '订单项';

create table cake_platform.user
(
    user_id        bigint auto_increment comment '用户id'
        primary key,
    username       varchar(50)   not null comment '用户名称，用户输入或者系统生成',
    phone          varchar(50)   null comment '电话号码',
    email          varchar(255)  not null comment '电子邮件',
    identity       int default 0 not null comment '身份，0顾客1商家2管理员',
    identity_index bigint        null comment '身份索引，对应身份信息的主键',
    is_delete      int default 0 not null comment '是否删除，0否1是'
);

create index user_username_index
    on cake_platform.user (username);

