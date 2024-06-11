create table if not exists cms_product_comment
(
    id              int auto_increment
        primary key,
    product_id      bigint                  not null,
    shop_id         bigint                  not null comment '店铺id',
    member_id       bigint                  not null comment '会员id',
    member_nickname varchar(255)            not null comment '会员昵称',
    nickname_status int          default 0  not null comment '是否需要匿名；0->匿名,1->不匿名',
    comment         varchar(255)            not null,
    pics            varchar(255) default '' not null,
    create_time     datetime                not null,
    delete_status   int          default 0  not null comment '删除状态；0->不删除，1->删除'
)
    comment '商品评论表' charset = utf8mb4
                         row_format = DYNAMIC;

create table if not exists mms_chat
(
    id                 bigint auto_increment
        primary key,
    admin_id           bigint        not null,
    member_id          bigint        not null,
    content            text          not null,
    admin_read_status  int default 0 not null comment '店长已读状态。1-已读，0-未读',
    member_read_status int default 0 not null comment '店长已读状态。1-已读，0-未读',
    create_time        datetime      not null
)
    comment '信息表' charset = utf8mb4
                     row_format = DYNAMIC;

create table if not exists oms_order
(
    id               bigint auto_increment
        primary key,
    order_sn         varchar(64)    null comment '订单编号',
    product_id       bigint         null,
    product_pic      varchar(500)   null,
    product_name     varchar(200)   null,
    product_price    decimal(10, 2) null comment '销售价格',
    product_quantity int            null comment '购买数量',
    real_price       decimal(10, 2) null comment '最终价格',
    product_attr     varchar(500)   null comment '商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]',
    member_id        bigint         not null,
    create_time      datetime       null comment '提交时间',
    pay_type         int            null comment '支付方式：1->支付宝；2->微信',
    status           int            null comment '订单状态：0->待付款；1->已支付；2->已完成；4->已关闭；5->已取消；6->无效订单；',
    delete_status    int default 0  not null comment '删除状态：0->未删除；1->已删除',
    payment_time     datetime       null comment '支付时间',
    modify_time      datetime       not null
)
    comment '订单' charset = utf8mb4
                   row_format = DYNAMIC;

create table if not exists pms_product
(
    id             bigint auto_increment
        primary key,
    name           varchar(200)   not null,
    description    text           null comment '商品描述',
    shop_id        bigint         not null comment '店铺id',
    pic            varchar(255)   null,
    album_pics     varchar(255)   null comment '画册图片，连产品图片限制为5张，以逗号分割',
    delete_status  int            null comment '删除状态：0->未删除；1->已删除',
    publish_status int            null comment '上架状态：0->下架；1->上架',
    sort           int            null comment '排序',
    sale           int            null comment '销量',
    price          decimal(10, 2) null,
    original_price decimal(10, 2) null comment '市场价',
    stock          int            null comment '库存',
    low_stock      int            null comment '库存预警值',
    unit           varchar(16)    null comment '单位',
    keywords       text           not null comment '关键字'
)
    comment '商品信息' charset = utf8mb4
                       row_format = DYNAMIC;

create table if not exists sms_shop
(
    id          bigint auto_increment
        primary key,
    name        varchar(255)  not null,
    owner_id    bigint        not null comment '店铺拥有者id，必须是adminId',
    status      int default 1 not null comment '状态，-1注销，0关店，1开业',
    create_time datetime      not null comment '创建时间',
    update_time datetime      not null comment '更新时间',
    constraint name_uindex
        unique (name) comment '店铺名唯一',
    constraint owner_id_unique
        unique (owner_id) comment '用户唯一'
)
    comment '店铺';

create table if not exists ums_admin
(
    id          bigint auto_increment
        primary key,
    username    varchar(64)   null,
    password    varchar(64)   null,
    icon        varchar(500)  null comment '头像',
    email       varchar(100)  null comment '邮箱',
    nick_name   varchar(200)  null comment '昵称',
    note        varchar(500)  null comment '备注信息',
    create_time datetime      null comment '创建时间',
    login_time  datetime      null comment '最后登录时间',
    status      int default 1 null comment '帐号启用状态：0->禁用；1->启用'
)
    comment '后台用户表' charset = utf8mb3
                         row_format = DYNAMIC;

create table if not exists ums_admin_permission_relation
(
    id            bigint auto_increment
        primary key,
    admin_id      bigint null,
    permission_id bigint null
)
    comment '后台用户和权限关系表(除角色中定义的权限以外的加减权限)' charset = utf8mb4
                                                                     row_format = DYNAMIC;

create table if not exists ums_member
(
    id          bigint auto_increment
        primary key,
    username    varchar(64)  null comment '用户名',
    password    varchar(64)  null comment '密码',
    nickname    varchar(64)  null comment '昵称',
    phone       varchar(64)  null comment '手机号码',
    status      int          null comment '帐号启用状态:0->禁用；1->启用',
    create_time datetime     null comment '注册时间',
    icon        varchar(500) null comment '头像',
    gender      int          null comment '性别：0->未知；1->男；2->女',
    birthday    date         null comment '生日',
    city        varchar(64)  null comment '所做城市',
    job         varchar(100) null comment '职业',
    source_type int          null comment '用户来源',
    constraint idx_phone
        unique (phone),
    constraint idx_username
        unique (username)
)
    comment '会员表' charset = utf8mb4
                     row_format = DYNAMIC;

create table if not exists ums_permission
(
    id          bigint auto_increment
        primary key,
    name        varchar(100) null comment '名称',
    value       varchar(200) null comment '权限值',
    status      int          null comment '启用状态；0->禁用；1->启用',
    create_time datetime     null comment '创建时间'
)
    comment '后台用户权限表' charset = utf8mb4
                             row_format = DYNAMIC;

create table if not exists ums_product_collection
(
    id          bigint auto_increment
        primary key,
    member_id   bigint        not null comment '会员id',
    product_id  bigint        not null comment '商品id',
    shop_id     bigint        not null comment '店铺id',
    create_time datetime      not null comment '收藏时间',
    status      int default 1 not null comment '删除状态：1->未删除；0->已删除',
    constraint ums_product_collection_product_id_member_id_status_uindex
        unique (product_id, member_id, status)
)
    comment '会员收藏的商品' charset = utf8mb4
                             row_format = DYNAMIC;


