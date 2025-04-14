-- 导出数据库结构
create database if not exists SpringAI;
use SpringAI;

-- 创建表 course 表
create table if not exists course
(
    id       int unsigned             not null auto_increment comment '主键' primary key,
    name     varchar(50)  default ''  not null comment '学科名称',
    edu      int          default '0' not null comment '学历背景要求：0-无，1-初中，2-高中，3-大专，4-本科以上',
    type     varchar(256) default '0' not null comment '课程类型：编程、设计、自媒体、其他',
    price    bigint       default '0' not null comment '课程价格',
    duration int unsigned default '0' not null comment '学习时长,单位：天'
    ) comment '学科表' collate = utf8mb4_general_ci;

-- 插入数据
insert into course (id, name, edu, type, price, duration)
values (1, 'JavaEE', 4, '编程', 21999, 108),
       (2, '鸿蒙应用开发', 3, '编程', 20999, 98),
       (3, 'AI人工智能', 4, '编程', 24999, 100),
       (4, 'Python大数据开发', 4, '编程', 23999, 102),
       (5, '跨境电商', 0, '自媒体', 12999, 68),
       (6, '新媒体运营', 0, '自媒体', 10999, 61),
       (7, 'UI设计', 2, '设计', 11999, 66);


create table if not exists course_reservation
(
    id           int                     not null auto_increment comment '主键' primary key,
    course       varchar(50)  default '' not null comment '预约课程',
    student_name varchar(255) default '' not null comment '学生姓名',
    contact_info varchar(255) default '' not null comment '联系方式',
    school       varchar(50)  default NULL comment '预约校区',
    remark       text                    comment '备注'
    ) comment '学科表' collate = utf8mb4_general_ci;

insert into course_reservation(id, course, student_name, contact_info, school, remark)
values (1, '新媒体运营', '张三丰', '13899762348', '广东校区', '安排好一点的老师');


create table if not exists school
(
    id   int not null auto_increment comment '主键' primary key,
    name varchar(50) default NULL comment '校区名称',
    city varchar(50) default NULL comment '校区所在城市'
    ) comment '校区表' collate = utf8mb4_general_ci;

-- 插入数据
insert into school (id, name, city)
values (1, '昌平校区', '北京'),
       (2, '顺义校区', '北京'),
       (3, '杭州校区', '杭州'),
       (4, '上海校区', '上海'),
       (5, '南京校区', '南京'),
       (6, '西安校区', '西安'),
       (7, '郑州校区', '郑州'),
       (8, '广东校区', '广东'),
       (9, '深圳校区', '深圳');
