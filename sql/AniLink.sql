DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `mc_id` varchar(20) NOT NULL COMMENT '主分区ID',
  `sc_id` varchar(20) NOT NULL COMMENT '子分区ID',
  `mc_name` varchar(20) NOT NULL COMMENT '主分区名称',
  `sc_name` varchar(20) NOT NULL COMMENT '子分区名称',
  `descr` varchar(200) DEFAULT NULL COMMENT '描述',
  `rcm_tag` varchar(500) DEFAULT NULL COMMENT '推荐标签',
  PRIMARY KEY (`mc_id`,`sc_id`),
  KEY `mc_id` (`mc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分区表';

DROP TABLE IF EXISTS `chat`;
-- auto-generated definition
create table chat
(
    id          int auto_increment comment '唯一主键'
        primary key,
    target_id   int      not null comment '对象UID',
    sender_id   int      not null comment '用户UID',
    latest_time datetime not null comment '最近接收消息的时间或最近打开聊天窗口的时间',
    constraint from_to
        unique (target_id, sender_id),
    constraint id
        unique (id)
)
    comment '聊天表';



DROP TABLE IF EXISTS `chat_message`;
-- auto-generated definition
create table chat_message
(
    id           int auto_increment comment '唯一主键'
        primary key,
    sender_id    int               not null comment '消息发送者',
    target_id    int               not null comment '消息接收者',
    content      varchar(500)      not null comment '消息内容',
    sender_del   tinyint default 0 not null comment '发送者是否删除',
    target_del   tinyint default 0 not null comment '接受者是否删除',
    withdraw     tinyint default 0 not null comment '是否撤回',
    time         datetime          not null comment '消息发送时间',
    message_type int     default 0 not null comment '消息类型，0文本，1图片，2文件',
    read_status  int     default 0 not null comment '是否已读，0未读1已读',
    constraint id
        unique (id)
)
    comment '聊天记录表';

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论主id',
  `vid` int(11) NOT NULL COMMENT '评论的视频id',
  `uid` int(11) DEFAULT NULL COMMENT '发送者id',
  `root_id` int(11) NOT NULL DEFAULT '0' COMMENT '根节点评论的id,如果为0表示为根节点',
  `parent_id` int(11) NOT NULL COMMENT '被回复的评论id，只有root_id为0时才允许为0，表示根评论',
  `to_user_id` int(11) NOT NULL COMMENT '回复目标用户id',
  `content` varchar(2000) NOT NULL COMMENT '评论内容',
  `love` int(11) NOT NULL DEFAULT '0' COMMENT '该条评论的点赞数',
  `bad` int(11) DEFAULT '0' COMMENT '不喜欢的数量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `is_top` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否置顶 0普通 1置顶',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '软删除 0未删除 1已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='评论表';

create table danmu
(
    id          int auto_increment comment '弹幕ID'
        primary key,
    vid         int                          not null comment '视频ID',
    uid         int                          not null comment '用户ID',
    content     varchar(100)                 not null comment '弹幕内容',
    fontsize    tinyint    default 25        not null comment '字体大小',
    mode        tinyint    default 1         not null comment '弹幕模式 1滚动 2顶部 3底部',
    color       varchar(7) default '#FFFFFF' not null comment '弹幕颜色 6位十六进制标准格式',
    time_point  double                       not null comment '弹幕所在视频的时间点',
    status      tinyint    default 1         not null comment '弹幕状态 1默认过审 2被举报审核中 3删除',
    create_date datetime                     not null comment '发送弹幕的日期时间',
    constraint id
        unique (id)
)
    comment '弹幕表';

DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite` (
  `fid` int(11) NOT NULL AUTO_INCREMENT COMMENT '收藏夹ID',
  `uid` int(11) NOT NULL COMMENT '所属用户ID',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '收藏夹类型 1默认收藏夹 2用户创建',
  `visible` tinyint(4) NOT NULL DEFAULT '1' COMMENT '对外开放 0隐藏 1公开',
  `cover` varchar(255) DEFAULT NULL COMMENT '收藏夹封面',
  `title` varchar(20) NOT NULL COMMENT '标题',
  `description` varchar(200) DEFAULT '' COMMENT '简介',
  `count` int(11) NOT NULL DEFAULT '0' COMMENT '收藏夹中视频数量',
  `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除 0否 1已删除',
  PRIMARY KEY (`fid`),
  UNIQUE KEY `fid` (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='收藏夹';

DROP TABLE IF EXISTS `favorite_video`;
CREATE TABLE `favorite_video` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `vid` int(11) NOT NULL COMMENT '视频ID',
  `fid` int(11) NOT NULL COMMENT '收藏夹ID',
  `time` datetime NOT NULL COMMENT '收藏时间',
  `is_remove` tinyint(4) DEFAULT NULL COMMENT '是否移除 null否 1已移除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `vid_fid__index` (`vid`,`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='视频收藏夹关系表';

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户账号',
  `password` varchar(255) NOT NULL COMMENT '用户密码',
  `nickname` varchar(32) NOT NULL COMMENT '用户昵称',
  `avatar` varchar(500) DEFAULT NULL COMMENT '用户头像url',
  `background` varchar(500) DEFAULT NULL COMMENT '主页背景图url',
  `gender` tinyint(4) NOT NULL DEFAULT '2' COMMENT '性别 0女 1男 2未知',
  `description` varchar(100) DEFAULT NULL COMMENT '个性签名',
  `exp` int(11) NOT NULL DEFAULT '0' COMMENT '经验值',
  `coin` double NOT NULL DEFAULT '0' COMMENT '硬币数',
  `vip` tinyint(4) NOT NULL DEFAULT '0' COMMENT '会员类型 0普通用户 1月度大会员 2季度大会员 3年度大会员',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 0正常 1封禁 2注销',
  `role` tinyint(4) NOT NULL DEFAULT '0' COMMENT '角色类型 0普通用户 1管理员 2超级管理员',
  `auth` tinyint(4) NOT NULL DEFAULT '0' COMMENT '官方认证 0普通用户 1个人认证 2机构认证',
  `auth_msg` varchar(30) DEFAULT NULL COMMENT '认证说明',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `delete_date` datetime DEFAULT NULL COMMENT '注销时间',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uid` (`uid`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `nickname` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='用户表';

create table user_stats
(
    fans_count       bigint default 0 null comment '粉丝数',
    follow_count     int    default 0 null comment '关注数',
    like_count       bigint default 0 null comment '获赞数',
    play_count       bigint default 0 null comment '播放数',
    video_count      int    default 0 null comment '投稿数',
    collection_count int    default 0 null comment '合集数',
    collect_count    int    default 0 null comment '收藏数',
    birth_date       date             null comment '出生日期',
    school           varchar(64)      null comment '学校信息',
    notice           varchar(500)     null comment '公告',
    uid              bigint auto_increment comment '用户ID'
        primary key
)
    comment '用户信息表';

DROP TABLE IF EXISTS `user_video`;
-- auto-generated definition
create table user_video
(
    id           int auto_increment comment '唯一标识'
        primary key,
    uid          int               not null comment '观看视频的用户UID',
    vid          int               not null comment '视频ID',
    play         int     default 0 not null comment '播放次数',
    love         tinyint default 0 not null comment '点赞 0没赞 1已点赞',
    unlove       tinyint default 0 not null comment '不喜欢 0没点 1已不喜欢',
    coin         tinyint default 0 not null comment '投币数 0-2 默认0',
    collect      tinyint default 0 not null comment '收藏 0没收藏 1已收藏',
    play_time    datetime          not null comment '最近播放时间',
    love_time    datetime          null comment '点赞时间',
    coin_time    datetime          null comment '投币时间',
    collect_time datetime          null comment '收藏操作的时间',
    constraint id
        unique (id),
    constraint uid_vid__index
        unique (uid, vid)
)
    comment '用户视频关联表';

DROP TABLE IF EXISTS `video`;
create table video
(
    vid         int auto_increment comment '视频ID'
        primary key,
    uid         int               not null comment '投稿用户ID',
    title       varchar(80)       not null comment '标题',
    type        tinyint default 1 not null comment '类型 1自制 2转载',
    auth        tinyint default 0 not null comment '作者声明 0不声明 1未经允许禁止转载',
    duration    double  default 0 not null comment '播放总时长 单位秒',
    mc_id       varchar(20)       not null comment '主分区ID',
    sc_id       varchar(20)       not null comment '子分区ID',
    tags        varchar(500)      null comment '标签 回车分隔',
    descr       varchar(2000)     null comment '简介',
    cover_url   varchar(500)      not null comment '封面url',
    video_url   varchar(500)      not null comment '视频url',
    status      tinyint default 0 not null comment '状态 0审核中 1已过审 2未通过 3已删除',
    upload_date datetime          not null comment '上传时间',
    delete_date datetime          null comment '删除时间',
    visible     tinyint default 0 null comment '可见范围：0公开，1仅自己可见',
    constraint vid
        unique (vid)
)
    comment '视频表' charset = utf8mb3;

--视频统计数据
DROP TABLE IF EXISTS `video_stats`;
-- auto-generated definition
create table user_stats
(
    fans_count       bigint default 0 null comment '粉丝数',
    follow_count     int    default 0 null comment '关注数',
    like_count       bigint default 0 null comment '获赞数',
    play_count       bigint default 0 null comment '播放数',
    video_count      int    default 0 null comment '投稿数',
    collection_count int    default 0 null comment '合集数',
    collect_count    int    default 0 null comment '收藏数',
    birth_date       date             null comment '出生日期',
    school           varchar(64)      null comment '学校信息',
    notice           varchar(500)     null comment '公告',
    uid              bigint auto_increment comment '用户ID'
        primary key,
    masterpiece      bigint           null comment '置顶视频'
)
    comment '用户信息表';

DROP TABLE IF EXISTS `follow`;
CREATE TABLE follow (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '关注与被关注关系ID',
    `follower_id` int NOT NULL COMMENT '关注者ID',
    `followee_id` int NOT NULL COMMENT '被关注者ID',
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT = '关注与被关注关系表';