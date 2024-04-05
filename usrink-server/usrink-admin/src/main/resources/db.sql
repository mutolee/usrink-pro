create table sys_user
(
    userId       bigint auto_increment comment '系统用户ID'
        primary key,
    userName     varchar(15)    not null comment '系统用户名，登录名',
    userPassword varchar(32)    not null comment '用户密码，登录密码',
    userRoleId   int default 3  not null comment '用户角色ID，默认角色为默认角色',
    userNick     varchar(16)    null comment '用户昵称，显示的昵称',
    avatar       varchar(150)   null comment '用户头像地址',
    email        varchar(30)    null comment '用户邮箱',
    phone        varchar(20)    null comment '用户电话号码',
    createTime   varchar(30)    null comment '创建时间',
    updateTime   varchar(30)    null comment '更新时间',
    sex          int default -1 null comment '性别，0为女，1为男，-1为未知，默认-1',
    status       int default 0  null comment '用户状态，0表示正常，-1表示禁用不可登录，默认0。',
    constraint sys_user_pk
        unique (userName)
)
    comment '系统用户表';

create table sys_role
(
    roleId      int auto_increment comment '角色ID，主键'
        primary key,
    roleName    varchar(20)   not null comment '角色名称',
    rolePermKey varchar(20)   not null comment '角色权限标识符',
    roleMenuIds varchar(200)  null comment '角色拥有的菜单集合，也就是角色对应的权限集合，由菜单ID以“,”分割。',
    roleDesc    varchar(50)   null comment '角色描述',
    createTime  varchar(30)   null comment '创建时间',
    updateTime  varchar(30)   null comment '更新时间',
    status      int default 0 not null comment '角色状态，0表示正常，-1表示禁用'
)
    comment '角色信息表';

create table sys_menu
(
    menuId       bigint auto_increment comment '菜单ID'
        primary key,
    menuName     varchar(20)      not null comment '菜单名称',
    orderIndex   int    default 1 not null comment '排序，正序排列',
    icon         varchar(20)      null comment '菜单图标',
    permKey      varchar(30)      null comment '权限标识符',
    parentMenuId bigint default 0 not null comment '父类菜单ID',
    sceneType    int    default 0 not null comment '菜单场景类型，0表示主菜单，1表示独立页面(非主菜单)',
    menuType     int    default 0 not null comment '菜单类型，0表示目录，1表示菜单，2表示按钮',
    route        varchar(150)     null comment '页面路由',
    component    varchar(150)     null comment '菜单请求页面组件',
    createTime   varchar(30)      null comment '创建时间',
    updateTime   varchar(30)      null comment '更新时间',
    status       int    default 0 not null comment '菜单状态，0表示正常，-1表示禁用'
)
    comment '系统菜单';

create table sys_log_login
(
    logId      bigint auto_increment comment '主键'
        primary key,
    userName   varchar(20)   null comment '登录用户名',
    reqUrl     varchar(100)  null comment '请求地址',
    reqParam   varchar(255)  null comment '请求参数',
    reqType    varchar(10)   null comment '请求类型',
    ipAddr     varchar(50)   null comment '登录IP地址',
    location   varchar(100)  null comment '登录地点',
    browser    varchar(50)   null comment '登录的浏览器类型',
    os         varchar(50)   null comment '登录的操作系统',
    result     varchar(1000) null comment '登录结果，如果成功，记录返回的json字符串；如果失败，记录异常信息',
    costTime   bigint        null comment '登录耗时，单位毫秒',
    createTime varchar(30)   not null comment '记录时间',
    status     int           not null comment '登录状态，0表示成功，-1表示登录失败'
)
    comment '后台管理登录记录表';

create table sys_log_operator
(
    logId        bigint auto_increment comment '主键'
        primary key,
    userName     varchar(20)   null comment '用户名',
    userRoleName varchar(20)   null comment '用户角色',
    operatorDesc varchar(50)   null comment '操作描述',
    reqUrl       varchar(100)  null comment '请求地址',
    reqMethod    varchar(100)  null comment '请求方法，类名.方法名',
    reqParam     varchar(500)  null comment '请求参数',
    reqType      varchar(10)   null comment '请求类型',
    ipAddr       varchar(50)   null comment '请求IP地址',
    location     varchar(100)  null comment '请求地点',
    browser      varchar(50)   null comment '请求的浏览器类型',
    os           varchar(50)   null comment '请求的操作系统',
    result       varchar(1000) null comment '请求结果，如果成功，记录返回的json字符串；如果失败，记录异常信息',
    costTime     bigint        null comment '请求耗时，单位毫秒',
    createTime   varchar(30)   not null comment '记录时间',
    status       int           not null comment '请求状态，0表示成功，-1表示失败'
)
    comment '后台管理操作记录表';

INSERT INTO `usrink-pro`.sys_user (userId, userName, userPassword, userRoleId, userNick, avatar, email, phone, createTime, updateTime, sex, status) VALUES (1, 'admin', '6fac10dd958a27d9ceb81a3011c55369', 1, '杨林恩', '/res/img/avatar/avatar_1.jpg?t=1712074111479', 'mutolee@qq.com', '18721272099', '2024-04-01 00:00:00', '2024-04-01 00:00:00', 1, 0);
INSERT INTO `usrink-pro`.sys_user (userId, userName, userPassword, userRoleId, userNick, avatar, email, phone, createTime, updateTime, sex, status) VALUES (2, 'mutolee', '6fac10dd958a27d9ceb81a3011c55369', 4, '木头人', null, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', -1, 0);
INSERT INTO `usrink-pro`.sys_user (userId, userName, userPassword, userRoleId, userNick, avatar, email, phone, createTime, updateTime, sex, status) VALUES (3, 'usrink', '6fac10dd958a27d9ceb81a3011c55369', 3, 'UsrInk', '/res/img/avatar/avatar_4.jpg?t=1712295541471', null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 1, 0);

INSERT INTO `usrink-pro`.sys_role (roleId, roleName, rolePermKey, roleMenuIds, roleDesc, createTime, updateTime, status) VALUES (1, '超级管理员', 'admin', '1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,25,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45', '超级管理员，系统角色，拥有所有权限，不可删除。', '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_role (roleId, roleName, rolePermKey, roleMenuIds, roleDesc, createTime, updateTime, status) VALUES (2, '默认角色', 'default', '23,43', '默认角色，不可被删除。当删除了某些角色后，本属于这个角色的用户将变为默认角色。', '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_role (roleId, roleName, rolePermKey, roleMenuIds, roleDesc, createTime, updateTime, status) VALUES (3, '游客', 'guest', '1,2,3,4,5,6,7,8,12,17,21,22,23,43', '游客，系统角色，只有访问权限，没有修改权限，该角色不可被删除。', '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_role (roleId, roleName, rolePermKey, roleMenuIds, roleDesc, createTime, updateTime, status) VALUES (4, '开发工程师', 'develop', '1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,25,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45', '开发工程师，用于开发调试程序', '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);

INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (1, '系统管理', 1, 'Menu', null, 0, 0, 0, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (2, '用户管理', 1, 'Document', null, 1, 0, 1, '/sys/user', '/_frame/page-user', '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (3, '角色管理', 2, 'Document', null, 1, 0, 1, '/sys/role', '/_frame/page-role', '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (4, '菜单管理', 3, 'Document', null, 1, 0, 1, '/sys/menu', '/_frame/page-menu', '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (5, '日志管理', 4, 'Files', null, 1, 0, 0, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (6, '操作日志', 2, 'Document', null, 5, 0, 1, '/sys/log/operator', '/_frame/log/page-operator', '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (7, '登录日志', 1, 'Document', null, 5, 0, 1, '/sys/log/login', '/_frame/log/page-login', '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (8, '用户查询', 1, null, 'sys:user:list', 2, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (9, '用户新增', 2, null, 'sys:user:add', 2, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (10, '用户修改', 3, null, 'sys:user:update', 2, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (11, '用户删除', 6, null, 'sys:user:deleteLogic', 2, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (12, '角色查询', 1, null, 'sys:role:list', 3, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (13, '角色新增', 2, null, 'sys:role:add', 3, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (14, '角色更新', 3, null, 'sys:role:update', 3, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (15, '角色删除', 5, null, 'sys:role:deleteLogic', 3, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (16, '角色分配权限', 4, null, 'sys:role:perm', 3, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (17, '菜单查询', 1, null, 'sys:menu:list', 4, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (18, '菜单新增', 2, null, 'sys:menu:add', 4, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (19, '菜单更新', 3, null, 'sys:menu:update', 4, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (20, '菜单删除', 4, null, 'sys:menu:deleteLogic', 4, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (21, '操作日志查询', 1, null, 'sys:log:operator:list', 6, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (22, '登录日志查询', 1, null, 'sys:log:login:list', 7, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (23, '个人中心', 1, null, null, 43, 1, 1, '/profile/info', '/_frame/page-profile', '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (25, '资料修改', 3, null, 'user:profile:update', 23, 1, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (27, '按日期删除日志', 4, null, 'sys:log:login:clear', 7, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (28, '清空日志', 5, null, 'sys:log:operator:clearAll', 6, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (29, '分配角色', 4, null, 'sys:user:distribute:role', 2, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (30, '重置密码', 5, null, 'sys:user:rest:pwd', 2, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (31, '日志详情', 2, null, 'sys:log:login:info', 7, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (32, '删除日志', 3, null, 'sys:log:login:delete', 7, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (33, '清空全部', 5, null, 'sys:log:login:clearAll', 7, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (34, '日志详情', 2, null, 'sys:log:operator:info', 6, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (35, '删除日志', 3, null, 'sys:log:operator:delete', 6, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (36, '按日志删除日志', 4, null, 'sys:log:operator:clear', 6, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (37, '物理删除', 8, null, 'sys:user:deletePhysics', 2, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (38, '删除恢复', 7, null, 'sys:user:deleteRecover', 2, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (39, '删除恢复', 6, null, 'sys:role:deleteRecover', 3, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (40, '物理删除', 7, null, 'sys:role:deletePhysics', 3, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (41, '删除恢复', 5, null, 'sys:menu:deleteRecover', 4, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (42, '物理删除', 6, null, 'sys:menu:deletePhysics', 4, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (43, '普通页面', 2, null, null, 0, 1, 0, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (44, '密码修改', 2, null, 'user:profile:restPwd', 23, 1, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);
INSERT INTO `usrink-pro`.sys_menu (menuId, menuName, orderIndex, icon, permKey, parentMenuId, sceneType, menuType, route, component, createTime, updateTime, status) VALUES (45, '修改头像', 1, null, 'user:profile:updateAvatar', 23, 0, 2, null, null, '2024-04-01 00:00:00', '2024-04-01 00:00:00', 0);