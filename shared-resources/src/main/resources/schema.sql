create table cms_product_comment
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

create table db_hms_promotion
(
    id         bigint unsigned auto_increment
        primary key,
    title      varchar(255)                 not null,
    pic        varchar(255)                 null,
    start_date date                         not null,
    end_date   date                         not null,
    product_id bigint                       not null,
    status     tinyint unsigned default '0' not null
);

create table db_hms_promotion_product
(
    id         bigint unsigned auto_increment
        primary key,
    product_id bigint                       not null,
    pic        varchar(255)                 not null,
    name       varchar(255)                 not null,
    start_date date                         not null,
    end_date   date                         not null,
    status     tinyint unsigned default '0' not null
);

create table db_hms_promotion_shop
(
    id         bigint unsigned auto_increment
        primary key,
    shop_id    bigint                       not null,
    pic        varchar(255)                 not null,
    start_date date                         not null,
    end_date   date                         not null,
    name       varchar(255)                 not null,
    status     tinyint unsigned default '0' not null
);

create table db_mms_staging_message
(
    id        bigint unsigned auto_increment
        primary key,
    user_type tinyint unsigned not null,
    user_id   bigint unsigned  not null,
    msg       text             null
);

create table db_mms_staging_msg
(
    id        bigint unsigned auto_increment
        primary key,
    user_type tinyint unsigned not null,
    user_id   bigint unsigned  not null,
    group_id  bigint unsigned  not null,
    msg       text             null
);

create table db_oms_order
(
    id            bigint unsigned auto_increment
        primary key,
    order_sn      varchar(64)                  null comment '订单编号',
    product_id    bigint                       null,
    shop_id       bigint unsigned              not null,
    product_pic   varchar(500)                 null,
    product_name  varchar(200)                 null,
    product_price decimal(10, 2)               null comment '销售价格',
    real_price    decimal(10, 2)               null comment '最终价格',
    product_attr  varchar(500)                 null comment '商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]',
    member_id     bigint unsigned              not null,
    create_time   datetime                     null comment '提交时间',
    end_stage     tinyint unsigned default '5' not null,
    current_stage tinyint unsigned default '0' not null comment '当前阶段',
    status        int                          null comment '订单状态：0->待付款；1->已支付；2->已完成；4->已关闭；5->已取消；6->无效订单；',
    delete_status int              default 0   not null comment '删除状态：0->未删除；1->已删除',
    complete_time datetime                     null comment '支付时间'
)
    comment '订单' charset = utf8mb4
                   row_format = DYNAMIC;

create table db_oms_order_item
(
    id          bigint unsigned auto_increment
        primary key,
    order_id    bigint unsigned              not null,
    stage       tinyint unsigned default '1' not null comment '订单阶段',
    price       decimal(10, 2)               not null,
    pay_type    tinyint unsigned             not null comment '1-微信 2-支付宝',
    status      tinyint unsigned default '0' not null comment '订单状态：0->待付款；1->已支付；2->已完成；',
    create_time datetime                     not null
);

create table db_pms_category
(
    id            bigint unsigned auto_increment
        primary key,
    name          varchar(64)              not null,
    pic           varchar(255) default ''  not null,
    parent_id     bigint       default 0   not null,
    level         int          default 1   not null,
    sort          int unsigned default '0' not null,
    product_count bigint       default 0   not null,
    constraint name
        unique (name)
);

create table db_qrcode_validation
(
    id          bigint unsigned auto_increment
        primary key,
    content     varchar(255)         null,
    scan_status tinyint(1) default 0 not null,
    createAt    datetime             not null
);

create table db_shop_user_relation
(
    id                 bigint unsigned auto_increment
        primary key,
    user_id            bigint unsigned             not null,
    shop_id            bigint unsigned             not null,
    relation           tinyint                     not null comment '0:店长 1:客服 2:写手',
    cs_commission_rate decimal(10, 2) default 0.00 not null comment '客服佣金比例，只对客服有效',
    create_by          bigint unsigned             not null comment '创建人id',
    create_at          datetime                    not null,
    update_at          datetime                    not null
);

create table db_sms_phone_code
(
    id        bigint unsigned auto_increment
        primary key,
    phone     varchar(255)      not null,
    code      varchar(255)      not null,
    type      tinyint default 0 not null comment '用户类型',
    status    tinyint default 0 not null comment '0- 未被使用， 1- 已被使用',
    create_at datetime          not null,
    update_at datetime          not null
);

create table db_tboms_customer_service_order
(
    id                                   bigint unsigned auto_increment
        primary key,
    shop_id                              bigint unsigned             not null comment '系统店铺id',
    integrated_order_id                  bigint unsigned             not null comment '综合订单id',
    taobao_order_no                      varchar(255)   default ''   not null comment '淘宝订单编号',
    taobao_order_state                   tinyint        default 0    not null comment '未结算/已结算',
    has_corresponding_taobao_order_state tinyint        default 0    not null comment '子订单都有相应的淘宝订单',
    order_price_amount                   decimal(10, 2) default 0.00 not null comment '客服录入订单金额',
    taobao_order_price_amount            decimal(10, 2) default 0.00 not null comment '真实订单金额',
    price_amount_right_state             tinyint        default 0    not null comment '订单金额正确',
    updater_id                           bigint unsigned             not null comment '信息更新人id',
    create_at                            datetime                    not null,
    update_at                            datetime                    not null
);

create table db_tboms_integrated_order
(
    id                                   bigint unsigned auto_increment
        primary key,
    shop_id                              bigint unsigned              not null comment '系统店铺id',
    taobao_order_no                      varchar(255)   default ''    not null comment '订单编号：第一个子订单的编号',
    order_validated_state                tinyint        default 0     not null comment '所有子订单已验证',
    taobao_order_state                   tinyint        default 0     not null comment '未结算/已结算',
    updater_id                           bigint unsigned              not null comment '信息更新人id',
    has_corresponding_taobao_order_state tinyint        default 0     not null comment '子订单都有相应的淘宝订单',
    order_price_amount                   decimal(10, 2) default 0.00  not null comment '客服录入订单金额',
    taobao_order_price_amount            decimal(10, 2) default 0.00  not null comment '真实订单金额',
    cs_commission_rate                   decimal(10, 2) default 0.00  not null comment '客服佣金比率',
    cs_commission                        decimal(10, 2) default 0.00  not null comment '客服佣金',
    price_amount_right_state             tinyint        default 0     not null comment '订单金额正确',
    profile_margin                       decimal(4, 3)  default 0.700 not null comment '利润率',
    should_pay_amount                    decimal(10, 2) default 0.00  not null comment '付给写手的总金额',
    pay_amount_right_state               tinyint        default 0     not null comment '支付给写手的金额正确',
    lock_status                          int            default 0     not null,
    order_state                          tinyint        default 0     not null,
    validation_version                   varchar(255)   default ''    not null comment '校验版本'
);

create table db_tboms_integrated_order_validation_history
(
    id                                   bigint unsigned auto_increment
        primary key,
    integrated_order_id                  bigint unsigned not null comment '综合订单id',
    order_validated_state                tinyint         not null comment '验证通过状态：0: 未验证，-1: 未通过验证，1: 通过验证',
    taobao_order_state                   tinyint         not null comment '淘宝订单状态： 0: 未成功， 1: 交易成功',
    has_corresponding_taobao_order_state tinyint         not null comment '对应的客服订单是否都有对应的淘宝订单：0: 否， 1: 是',
    price_amount_right_state             tinyint         not null comment '对应的客服订单是否所有的价格都是正确的：0: 否， 1: 是',
    pay_amount_right_state               tinyint         not null comment '支付金额是否正确： 0: 否， 1: 是',
    validation_at                        datetime        not null comment '验证时间'
)
    comment '综合订单校验历史';

create table db_tboms_taobao_order
(
    id                                 bigint unsigned auto_increment
        primary key,
    shop_id                            bigint unsigned not null comment '店铺id(该系统的店铺id)',
    taobao_shop_name                   varchar(255)    not null comment '店铺名称(淘宝店铺名)',
    taobao_order_no                    varchar(255)    not null comment '订单编号',
    taobao_pay_time                    datetime        not null comment '订单支付时间',
    taobao_buyer_actual_payment_amount decimal(10, 2)  null comment '买家实际支付金额',
    taobao_refund_time                 datetime        null comment '退款时间',
    taobao_refund_amount               decimal(10, 2)  null comment '退款金额',
    taobao_order_status                varchar(255)    null comment '结算状态：0-未结算，1-已结算',
    taobao_merchant_notes              varchar(255)    null comment '商家备注',
    created_at                         datetime        not null,
    updated_at                         datetime        not null,
    updater_id                         bigint unsigned not null comment '更新人id'
);

create table db_tboms_writer_order
(
    id                  bigint unsigned auto_increment
        primary key,
    shop_id             bigint unsigned              not null comment '系统店铺id',
    integrated_order_id bigint unsigned              not null comment '综合订单id',
    writer_id           bigint unsigned              not null comment '写手id',
    order_state         tinyint                      not null,
    should_pay          decimal(10, 2)               not null comment '应付金额',
    pay_state           tinyint unsigned default '0' not null comment '支付状态: 0-待支付，1-已支付',
    payment_order_no    varchar(255)     default ''  not null comment '支付单号',
    pay_time            datetime                     null comment '支付时间',
    updater_id          bigint unsigned              not null comment '客服id',
    create_at           datetime                     not null comment '创建时间',
    update_at           datetime                     not null comment '更新时间'
);

create table db_ums_online
(
    id              bigint unsigned auto_increment
        primary key,
    user_id         bigint unsigned  not null,
    user_type       tinyint unsigned not null,
    last_login_time datetime         not null
);

create table mms_chat_group
(
    id          bigint auto_increment
        primary key,
    shop_id     bigint                  null,
    create_time datetime                not null,
    avatar      varchar(255) default '' not null,
    name        varchar(255)            not null
);

create table mms_chat_group_user
(
    id             bigint auto_increment
        primary key,
    group_id       bigint   not null,
    user_id        bigint   not null,
    user_type      tinyint  not null,
    last_read_time datetime null
);

create table mms_chat_message
(
    id            bigint auto_increment
        primary key,
    group_user_id bigint            not null,
    create_time   datetime          not null,
    status        tinyint default 0 not null,
    message       varchar(255)      not null,
    nickname      varchar(255)      not null,
    avatar        varchar(255)      not null,
    type          tinyint default 1 not null
);

create table oms_order
(
    id               bigint auto_increment
        primary key,
    order_sn         varchar(64)     null comment '订单编号',
    product_id       bigint          null,
    shop_id          bigint unsigned not null,
    product_pic      varchar(500)    null,
    product_name     varchar(200)    null,
    product_price    decimal(10, 2)  null comment '销售价格',
    product_quantity int             null comment '购买数量',
    real_price       decimal(10, 2)  null comment '最终价格',
    product_attr     varchar(500)    null comment '商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]',
    member_id        bigint          not null,
    create_time      datetime        null comment '提交时间',
    pay_type         int             null comment '支付方式：1->支付宝；2->微信',
    status           int             null comment '订单状态：0->待付款；1->已支付；2->已完成；4->已关闭；5->已取消；6->无效订单；',
    delete_status    int default 0   not null comment '删除状态：0->未删除；1->已删除',
    payment_time     datetime        null comment '支付时间',
    modify_time      datetime        not null
)
    comment '订单' charset = utf8mb4
                   row_format = DYNAMIC;

create table pms_product
(
    id             bigint auto_increment
        primary key,
    name           varchar(200)    not null,
    description    text            null comment '商品描述',
    shop_id        bigint          not null comment '店铺id',
    pic            varchar(255)    null,
    album_pics     varchar(255)    null comment '画册图片，连产品图片限制为5张，以逗号分割',
    delete_status  int             null comment '删除状态：0->未删除；1->已删除',
    publish_status int             null comment '上架状态：0->下架；1->上架',
    sort           int             null comment '排序',
    sale           int             null comment '销量',
    price          decimal(10, 2)  null,
    original_price decimal(10, 2)  null comment '市场价',
    stock          int             null comment '库存',
    low_stock      int             null comment '库存预警值',
    unit           varchar(16)     null comment '单位',
    keywords       text            not null comment '关键字',
    category_id    bigint unsigned not null
)
    comment '商品信息' charset = utf8mb3
                       row_format = DYNAMIC;

create table sms_shop
(
    id          bigint auto_increment
        primary key,
    name        varchar(255)  not null,
    pic         varchar(255)  not null,
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

create table ums_admin
(
    id        bigint auto_increment
        primary key,
    username  varchar(64)             null,
    password  varchar(64)             null,
    uuid      varchar(255)            not null,
    phone     varchar(255)            not null,
    icon      varchar(500)            null comment '头像',
    email     varchar(100)            null comment '邮箱',
    nick_name varchar(200)            null comment '昵称',
    note      varchar(500)            null comment '备注信息',
    create_at datetime                null comment '创建时间',
    login_at  datetime                null comment '最后登录时间',
    status    int          default 1  null comment '帐号启用状态：0->禁用；1->启用',
    type      tinyint      default 0  not null comment '0-员工，1->商家',
    roles     varchar(200) default '' not null comment 'MERCHANT：商家, WRITER：写手, CUSTOMER_SERVICE：客服'
)
    comment '后台用户表' charset = utf8mb3
                         row_format = DYNAMIC;

create table ums_admin_permission_relation
(
    id            bigint auto_increment
        primary key,
    admin_id      bigint null,
    permission_id bigint null
)
    comment '后台用户和权限关系表(除角色中定义的权限以外的加减权限)' charset = utf8mb3
                                                                     row_format = DYNAMIC;

create table ums_member
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

create table ums_permission
(
    id          bigint auto_increment
        primary key,
    name        varchar(100) null comment '名称',
    value       varchar(200) null comment '权限值',
    status      int          null comment '启用状态；0->禁用；1->启用',
    create_time datetime     null comment '创建时间'
)
    comment '后台用户权限表' charset = utf8mb3
                             row_format = DYNAMIC;

create table ums_product_collection
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

