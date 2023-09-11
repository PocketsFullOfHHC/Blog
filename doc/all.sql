select `id`, `name`, `password` from `test`;

# 测试表
drop table if exists `test`;
create table `test`
(
    `id`       bigint not null comment 'id',
    `name`     varchar(50) comment '名称',
    `password` varchar(50) comment '密码',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4
    comment ='测试';
insert into `test` (id, name, password)
VALUES (1, '测试', '123456');

# 用户表
drop table if exists `user`;
create table `user`
(
    `id` bigint not null comment 'id',
    `username` varchar(100) not null comment '用户名',
    `name` varchar(100) not null comment '昵称',
    `password` varchar(100) not null comment '密码',
    `avatar` varchar(100) not null default 'default.png',
    primary key (`id`),
    unique key `username_unique` (`username`)
) engine = innodb
  default charset = utf8mb4
    comment ='用户表';
insert into `user` (user.id, user.username, user.name, user.password, user.avatar)
values (1, 'test', '测试', 'test','1.png');


#帖子表
drop table if exists `blog`;
create table blog
(
    id bigint not null comment 'id',
    author_id bigint not null comment '作者id',
    publish_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '发布日期',
    content mediumtext not null,
    vote_num int default 0 null,
    constraint blog_pk
        primary key (id),
    constraint blog_user_fk
        foreign key (author_id) references user (id)
)engine = innodb
 default charset = utf8mb4
    comment '帖子表';

alter table blog add circle_id bigint null;

# 增加字段
alter table blog
    add oppose_num int default 0 null;
alter table blog
    add comment_num int default 0 null;

# 删除字段
ALTER TABLE blog DROP COLUMN circle_id;

insert into `blog` (id, author_id, content)
values (1, 1, 'carr');
insert into `blog` (id, author_id, content)
values (2, 1, '这是我发布的第一篇blog!');

# 增加图片字段
alter table blog add photo varchar(100) null default 'default.png';

#用户信息表
drop table if exists `user_info`;
create table user_info
(
    id bigint not null,
    self_intro varchar(2000) null,
    age int null,
    gender varchar(20) null,
    circles mediumtext null,
    managed_circles mediumtext null,
    tags mediumtext null,
    status bool null,
    constraint user_info_pk
        primary key (id)
) engine = innodb
  default charset = utf8mb4
    comment '用户信息表';

#兴趣圈表
drop table if exists `circle`;
create table circle
(
    id bigint not null,
    circle_name varchar(100) not null comment '兴趣圈名字',
    intro mediumtext null comment '介绍',
    manager_id bigint not null comment '管理者id',
    created_time timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '创建时间',
    tags mediumtext null comment '兴趣圈标签',
    constraint circle_pk
        primary key (id),
    constraint circle_user_id_fk
        foreign key (manager_id) references user (id)
) engine = innodb
  default charset = utf8mb4
    comment '兴趣圈表';

# 用户-兴趣圈表
drop table if exists `user_circle`;
create table user_circle
(
    id bigint not null comment 'id',
    circle_id bigint null,
    user_id bigint null,
    manager_id bigint null,
    constraint user_circle_pk
        primary key (id),
    constraint user_circle_circle_id_fk
        foreign key (circle_id) references circle (id),
    constraint user_circle_user_id_fk
        foreign key (user_id) references user (id)
) engine = innodb
  default charset = utf8mb4
    comment '用户-兴趣圈表';

# 关注表
drop table if exists `follow`;
create table follow
(
    id bigint not null,
    like_id bigint not null,
    beliked_id bigint not null,
    `follow _time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null comment '关注时间',
    constraint follow_pk
        primary key (id),
    constraint follow_user_id_fk
        foreign key (like_id) references user (id),
    constraint follow_user_id_fk_2
        foreign key (beliked_id) references user (id)
)
    comment '关注表';

#兴趣圈帖子表
drop table if exists `circle_blog`;
create table circle_blog
(
    id bigint not null comment 'id',
    author_id bigint not null comment '作者id',
    circle_id bigint null,
    publish_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
    content mediumtext not null,
    vote_num int default 0 null,
    oppose_num int default 0 null,
    comment_num int default 0 null,
    constraint circle_blog_pk
        primary key (id),
    constraint circle_blog_user_fk
        foreign key (author_id) references user (id)
)engine = innodb
 default charset = utf8mb4
    comment '兴趣圈帖子表';

# 评论表
drop table if exists `comment`;
create table comment
(
    id bigint not null comment '评论id',
    blog_id bigint not null comment 'blog id',
    commentator_id bigint not null comment '评论人id',
    content varchar(1000) not null comment '评论内容',
    comment_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '评论时间',
    constraint comment_pk
        primary key (id),
    constraint comment_blog_id_fk
        foreign key (blog_id) references blog (id),
    constraint comment_user_id_fk
        foreign key (commentator_id) references user (id)
)engine = innodb
 default charset = utf8mb4
    comment '评论表';

# 点赞/点踩表
drop table if exists `likes`;
create table `likes`
(
    id bigint not null comment 'id',
    commentator_id bigint not null comment '点赞人id',
    blog_id bigint not null comment '被赞贴id',
    is_like boolean null comment '是点赞（还是点踩）',
    comment_time timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '时间',
    constraint like_pk
        primary key (id),
    constraint like_blog_id_fk
        foreign key (blog_id) references blog (id),
    constraint like_user_id_fk
        foreign key (commentator_id) references user (id)
)
    comment '点赞/反对表';

# 消息表
drop table if exists `message`;
create table message
(
    id bigint not null,
    sender_id bigint null,
    receiver_id bigint null,
    content varchar(2000) null,
    time timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    constraint message_pk
        primary key (id)
)engine = innodb
 default charset = utf8mb4
    comment '消息表';

# 照片表
drop table if exists `photo`;
create table photo(
    id bigint not null comment 'id',
    album_id bigint not null comment '相册id',
    name varchar(100) null comment '图片名称',
    photo varchar(100) not null default 'default.png' comment '图片',
    up_time timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '上传时间',
    view_count int not null comment '浏览量',
    vote_count int not null comment '点赞量',
    constraint album_pk
        primary key (id),
    constraint photo_user_album_fk
        foreign key (album_id) references user_album (id)
)engine = innodb
 default charset = utf8mb4
    comment '相册表';

# 用户-相册表
drop table if exists `user_album`;
create table user_album(
    id bigint not null comment 'id',
    user_id bigint not null comment '用户id',
    name varchar(100) not null comment '相册名称',
    tag varchar(100) not null comment '标签',
    description varchar(100) not null comment '描述',
    up_time timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '创建时间',
    photo_count int not null comment '照片总数',
    view_count int not null comment '浏览总量',
    vote_count int not null comment '点赞总量',
    cover varchar(200) null comment '封面',
    constraint album_pk
        primary key (id),
    constraint user_album_user_fk
        foreign key (user_id) references user (id)
)engine = innodb
 default charset = utf8mb4
    comment '用户-相册表';

# 照片点赞表
drop table if exists `photo_likes`;
create table `photo_likes`
(
    id bigint not null comment 'id',
    photo_liker_id bigint not null comment '点赞人id',
    photo_id bigint not null comment '被赞照片id',
    photo_like_time timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '照片点赞时间',
    constraint photo_likes_pk
        primary key (id),
    constraint photo_likes_photo_fk
        foreign key (photo_id) references photo (id),
    constraint photo_likes_user_fk
        foreign key (photo_liker_id) references user (id)
)
    comment '照片点赞表';

# 第六天：登录后端，登录前后端交互