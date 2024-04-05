// Cookie 工具
// 文档地址：https://github.com/js-cookie/js-cookie
import jsCookie from "js-cookie"
import {useRoutesStore} from "@/stores/_frame/routesStore";
import {useNavStore} from "@/stores/_frame/navStore";
import {md5} from "js-md5";
import {useUserInfoStore} from "@/stores/_frame/userInfoStore";
import {useLoadRoutesStateStore} from "@/stores/_frame/loadRoutesStateStore";

/**
 * 验证用户是否已经授权成功,
 * <p>
 * 是否授权的标准是本地是否存有用户的Token信息
 */
const isAuthenticated = () => {
    return jsCookie.get('token')
}

/**
 * 用户退出登录，处理一些清理工作，路由跳转至登录页面
 */
const logout = () => {

    // 清除用户Token
    jsCookie.remove("token")

    // 清空 Store 中用户信息
    let userInfoStore = useUserInfoStore()
    userInfoStore.userInfo = {}
    userInfoStore.roleInfo = {}
    userInfoStore.userMenus = {}

    // 清空 Store 中菜单信息
    let routesStore = useRoutesStore()
    routesStore.menus = []
    routesStore.routes = []

    // 清空 Store 中当前nav的信息
    let navStore = useNavStore()
    navStore.routeInfo = {}
    navStore.tabs = []
    navStore.includes = []

    // 清空 Store 中加载路由的状态
    let loadRoutesStateStore = useLoadRoutesStateStore()
    loadRoutesStateStore.hasLoadRoutes = false
}

/**
 * 缓存用户信息
 * @param data 用户登录，服务器返回的用户信息
 */
const cacheUserInfo = (data) => {
    // 缓存Token
    jsCookie.set("token", data.token, {expires: 1})

    // 缓存用户信息到Store中
    let userInfoStore = useUserInfoStore()
    // 缓存用户信息
    userInfoStore.userInfo = data.userInfo
    // 缓存角色信息
    userInfoStore.roleInfo = data.roleInfo
    // 缓存菜单信息
    userInfoStore.userMenus = data.userMenus
}

/**
 * 加密密码
 * @param password
 * @return {*}
 */
const encryptPassword = (password) => {
    return md5(password)
}

export default {
    isAuthenticated,
    logout,
    cacheUserInfo,
    encryptPassword
}