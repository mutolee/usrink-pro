// ElementPlus 组件
// 文档地址：https://element-plus.gitee.io/zh-CN/guide/installation.html
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

// ElementPlus Icon
// 文档地址：https://element-plus.gitee.io/zh-CN/component/icon.html
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

// 页面顶部进度条工具
// 文档地址：https://github.com/rstacruz/nprogress
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

// 状态管理插件
import store from "@/stores/index.js";
// 路由管理插件
import router from "@/router/index.js";



/**
 * 第三方管理插件，所有的第三方的配置都在这里管理，不限于第三方的注册、配置等
 * @param app 根实例对象
 */
const thirdPlugin = (app) => {

    // 状态管理插件配置
    app.use(store)
    // 路由管理插件配置
    app.use(router)

    // ElementPlus 插件配置
    app.use(ElementPlus, {size: 'medium', zIndex: 3000, locale: zhCn})

    // ElementPlus Icon 组件注册
    for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
        app.component(key, component)
    }

    // Nprogress工具配置
    // 配置不显示右上角的那个转圈进度
    NProgress.configure({showSpinner: false, speed: 600});

}

export default thirdPlugin