## 项目介绍

UsrInk-Pro 是一个基于SpringBoot + Mybatis + Shiro + Jwt + Vue3 + ElementPlus 的无状态前后端分离的后台管理系统，轻量、没有重度依赖，模块设计优雅，管理界面美观，可快速进行二次开发。

### 初衷

UsrInk-Pro 是我为了不用每次有了新项目，再搞重复事情，如系统模块的权限相关功能，及后台管理的UI搭建等。我希望用到技术栈能够有较好的生态支持，遇到问题可直接在网上找到相关答案，所以后端选择SpringBoot，前端选择Vue + ElementUI。

### 后端项目结构

    usrink-server
    ├── usrink-admin               后台管理Web服务接口模块，为后台管理系统提供无状态的API接口
    ├── userink-api                前端Web服务接口模块，为网站，APP，小程序等提供无状态的API接口
    ├── usrink-common        
    ├──── usrink-common-core       通用核心模块，提供基础工具类、通用配置、异常处理等
    ├──── usrink-common-domain     通用实体模块，业务实体类放在此模块，如POJO，VO，BO等，被其他模块引用
    ├──── usrink-common-interface  业务层接口模块，独立运行的模块的业务接口在此模块定义
    ├──── usrink-common-model      提供数据库表与对象映射，对象属性与数据库字段一一对应
    ├── usrink-framework
    ├──── usrink-framework-mong    Mongo模块，提供Mongo的配置，操作类、多数据源等，根据需要导入
    ├──── usrink-framework-redis   Redis模块，提供Redis的配置，操作类、多数据源等，根据需要导入
    ├──── usrink-framework-mysql   Mysql模块，提供Mysql的配置，多数据源配置，根据需要导入
    ├──── usrink-framework-shiro   Shiro模块，提供Shiro的配置，权限控制，JWT身份验证，根据需要导入

### 前端项目结构
    
    usrink-client
    ├── public                     静态资源文件夹
    ├── src                        
    ├──── components               公共组件文件夹
    ├────── _frame                 usrink-pro-admin框架组件
    ├────── other                  其他组件，二次开发的组件，可自行添加，或重命名
    ├──── plugins                  插件管理文件夹
    ├──── router                   路由配置文件夹
    ├──── stores                   状态管理文件夹
    ├────── _frame                 usrink-pro-admin框架状态管理文件夹
    ├────── other                  其他状态管理，二次开发的状态管理，可自行添加，或重命名
    ├──── utils                    工具类文件夹，所有工具类都在这里添加
    ├──── views                    页面组件文件夹
    ├────── _frame                 usrink-pro-admin框架页面
    ├────── other                  其他页面，二次开发的页面，可自行添加，或重命名
    ├──── App.vue                  根组件
    ├──── main.js                  入口文件

## 快速开始二次开发

### 开发环境

- JDK 1.8
- Maven 3.8.6
- Mysql 8.0
- Node v20.9.0
- Npm 10.1.0

### 后端开发

1. 克隆项目到本地
2. 创建数据库，导入数据库脚本，数据库脚本在 `usrink-server/usrink-admin/src/resources/` 目录下 db.sql
3. 安装项目依赖，进入 usrink-server 目录，执行 `mvn clean install`
4. 启动项目，进入 usrink-server 目录，运行 AdminApplication.java 启动后台服务
5. 开些编写接口，业务逻辑，完成后台开发。

### 前端开发

1. 克隆项目到本地（如果已经克隆过了，这步跳过）
2. 安装项目依赖，进入 usrink-client 目录，执行 `npm install`
3. 启动项目，进入 usrink-client 目录，执行 `npm run dev`，启动前端服务
4. 开始编写页面，组件，完成前端开发。

### 开发示例

开发一个带权限控制的 HelloWorld 页面，后端返回一个字符串，前端在 HelloWorld 页面上展示这个字符串。

1. 编写后端接口，在 usrink-admin 模块中的 controller 包下创建一个 HelloWorldController.java 文件，代码编写完成之后，记得重启服务，内容如下：

```java
@RestController
public class HelloWorldController {

    @Log("HelloWorld 消息")
    @RequestMapping(value = "/helloWorld")
    @RequiresPermissions("test:helloWorld")
    public Res helloWorld() {
        Dict result = Dict.create();
        result.set("msg", "恭喜你，你成功访问了HelloWorld接口！");
        return Res.success(result);
    }
}

```

2. 编写前端页面，在 usrink-client/src/views/other 目录下创建一个 page-helloworld.vue 文件，内容如下：

```vue
<script setup>
    import {onMounted, ref} from "vue";
    import httpUtil from "@/utils/HttpUtil.js";

    const msg = ref("")

    onMounted(() => {
        selectHelloWorld()
    })

    const selectHelloWorld = () => {
        httpUtil.get("/helloWorld").then(res => {
            msg.value = res.data.msg
        })
    }
</script>

<template>
    <div class="hello-world">
        <h1>{{msg}}</h1>
    </div>
</template>

<style scoped>

</style>
```

3. 配置页面菜单，进入后台管理系统-系统管理-菜单管理，添加一个菜单，表单内容：


    - 场景类型：主菜单
    - 菜单名称：HelloWorld
    - 排序：2
    - 菜单类型：菜单
    - 页面路由：/helloworld
    - 页面组件：/other/page-helloworld

4. 配置权限，进入后台管理系统-系统管理-菜单管理，搜索 `HelloWorld` 菜单，找到刚刚添加的菜单，在操作列点击 `添加`，为HelloWorld页面添加一个消息查询的权限，表单内容：


    - 场景类型：主菜单
    - 菜单名称：消息查询
    - 排序：1
    - 菜单类型：按钮
    - 权限标识：test:helloWorld

5. 分配权限，进入后台管理系统-系统管理-角色管理，在超级管理员的操作列点击 `分配权限`，给超级管理员分配 `HelloWorld` 以及HelloWorld下的 `消息查询` 权限。
6. 重新登录，右上角用户名下拉框中点击 `退出`，重新登录，进入系统管理，在主菜单将看到 `HelloWorld` 菜单，点击 `HelloWorld` 菜单，将看到 “恭喜你，你成功访问了HelloWorld接口！”。
7. 至此，一个带权限控制的 HelloWorld 页面就开发完成了。

## 系统功能

1. 用户管理：系统的操作者，可以登录系统，操作系统。
2. 角色管理：用户的角色，可以分配角色的操作权限。
3. 菜单管理：系统的菜单，可以配置菜单的路由，组件，权限等。
4. 日志管理：用户登录日志，操作日志。
5. 服务器监控：系统的监控，包括内存，磁盘，CPU等。
6. 其他...

### 服务端

1. 使用JWT作为身份验证，Shiro作为权限控制，实现无状态请求，该功能为独立模块，可按需导入。
2. 使用Mybatis作为持久层框架，提供多数据源配置，可在业务层，通过`Ds.R`或者`Ds.W`动态切换数据源，该功能为独立模块，可按需导入。
3. 使用Redis作为缓存，提供多数据源配置，该功能为独立模块，可按需导入。（未完成）
4. 使用MongoDB作为数据库，提供多数据源配置，该功能为独立模块，可按需导入。（未完成）
5. 使用Logback作为日志框架，提供日志记录，日志按等级放在不同文件夹，方便查找。
6. 使用Swagger作为接口文档，方便查看接口信息，测试接口。（未完成）
7. 使用Lombok简化代码，提高开发效率。
8. 使用Jackson作为Json序列化，提供Json序列化，反序列化功能，可配置。
9. 使用PageHelper作为分页插件，提供分页查询功能。
10. 其他...

### 前端

1. 使用Vue3作为前端框架，提供响应式，组件化开发。
2. 使用ElementPlus作为UI框架，提供美观的UI组件。
3. 使用VueRouter作为路由管理，提供路由配置，动态路由，路由跳转等功能。
4. 使用Pinia作为状态管理，提供状态管理，状态共享等功能，如导航管理。
5. 使用Axios作为Http请求，提供Http请求，拦截器等功能。
6. 其他...

## 在线体验

体验地址：http://usr.ink

体验账号：`usrink` 密码：`123456`

## 项目部署

### 服务端

1. 配置运行模式，进入 usrink-admin 模块，找到 application.properties 文件，修改 `spring.profiles.active=prod`。
2. 配置文件上传目录，进入 usrink-admin 模块，找到 application-prod.properties 文件，修改 `file.upload.path=/data_disk/usrink/upload/`。
3. 配置数据库连接，进入 usrink-admin 模块，找到 db/mysql/druid-mysql-prod.properties 文件，修改 `master.druid.datasource.url`，`master.druid.datasource.username`，`master.druid.datasource.password`。
4. 打包项目，进入 usrink-server 目录，执行 `mvn clean package`，打包完成后，会在 usrink-admin/target 目录下生成一个可执行 `usrink-admin-x.x.x.jar` 包。
5. 运行项目，执行 `java -jar usrink-admin-x.x.x.jar`，启动服务。

### 前端

1. 配置服务器地址，进入 usrink-client/src/utils/Constant.js 文件，修改 `HTTP_BASE_URL`。
```js
// xxx.com 为你的服务器地址
const HTTP_BASE_URL = "http://xxx.com/admin-api"
```
2. 打包项目，进入 usrink-client 目录，执行 `npm run build`，打包完成后，会在 usrink-client/dist 下生成相关html/js/css。

### 服务器容器

以Nginx为例，部署前端项目。

安装Nginx，配置Nginx，找到Nginx配置文件，添加如下配置：

```nginx
server {
    listen 80;
    
    # 你的域名
    server_name xxx.com www.xxx.com;

    # 前端项目打包后的文件夹，可自行修改
    root /data_disk/usrink/client/dist;
    index index.html index.htm;

    location / {
        # 访问的文件不存在时，重定向到/,也就是index.html
        try_files $uri $uri/ /;
    }

    # 后端资源访问路径
    location /res/ {
        proxy_pass http://localhost:9999/res/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    # 后端服务的API接口
    location /admin-api/ {
        proxy_pass http://localhost:9999/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```
配置文件描述：`xxx.com` 为你的访问地址，`/data_disk/usrink/client/dist` 为前端项目打包后的文件夹，可自行修改，`/res/` 为后端资源访问路径，/admin-api/` 为后端服务的API接口。

<font color=red>注意：部署后，登录系统后台，一定要修改系统默认的3个用户的密码，切记！！！。</font>

## 系统美图

<img src="https://github.com/mutolee/usrink-pro/blob/main/usrink-client/public/img/WechatIMG61.jpeg?raw=true" />
<img src="https://github.com/mutolee/usrink-pro/blob/main/usrink-client/public/img/WechatIMG62.jpeg?raw=true" />
<img src="https://github.com/mutolee/usrink-pro/blob/main/usrink-client/public/img/WechatIMG63.jpeg?raw=true" />
<img src="https://github.com/mutolee/usrink-pro/blob/main/usrink-client/public/img/WechatIMG64.jpeg?raw=true" />
<img src="https://github.com/mutolee/usrink-pro/blob/main/usrink-client/public/img/WechatIMG65.jpeg?raw=true" />
<img src="https://github.com/mutolee/usrink-pro/blob/main/usrink-client/public/img/WechatIMG66.jpeg?raw=true" />
<img src="https://github.com/mutolee/usrink-pro/blob/main/usrink-client/public/img/WechatIMG67.jpeg?raw=true" />
<img src="https://github.com/mutolee/usrink-pro/blob/main/usrink-client/public/img/WechatIMG68.jpeg?raw=true" />
<img src="https://github.com/mutolee/usrink-pro/blob/main/usrink-client/public/img/WechatIMG69.jpeg?raw=true" />



