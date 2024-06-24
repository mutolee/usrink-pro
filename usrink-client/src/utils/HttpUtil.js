import axios from 'axios'
import loginUtil from '@/utils/LoginUtil'
import {ElMessage} from "element-plus";
import router from "@/router";
import {BASE_URL, RES_STATUS_CODE} from "@/utils/Constant";

/**
 * 创建一个Axios实例，应用中的请求基于该实例配置
 * <p>
 * 文档地址：https://axios-http.com/zh/docs/intro
 */
const instance = axios.create({
    baseURL: BASE_URL,
    // 请求超时时间
    timeout: 10 * 1000,
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded', // 设置请求头
    },
})

// 添加请求拦截器
instance.interceptors.request.use((request) => {
    // 在发送请求之前做些什么
    if (request.url.indexOf("/login") === -1) {
        let token = loginUtil.isAuthenticated();
        if (token) {
            // 做点什么，比如在请求中加点额外的参数
            request.headers['Authorization'] = `Bearer ${token}`;
        } else {
            loginUtil.logout()
        }
    }
    return request;
}, (error) => {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 添加响应拦截器
instance.interceptors.response.use((response) => {
    // 对响应数据做点什么
    let res = response.data;
    if (res.code === RES_STATUS_CODE.SUCCESS) {
        return res;
    } else if (res.code === RES_STATUS_CODE.UNAUTHORIZED) {
        ElMessage.error("身份凭证过期，请重新登录！");
        // 服务器返回未登陆，跳转登录页面
        loginUtil.logout()
        router.push('/login')
    } else if (res.statusCode === RES_STATUS_CODE.FORBIDDEN) {
        ElMessage.error("没有权限，请联系管理员！");
    } else if (res.code === RES_STATUS_CODE.NOT_FOUND) {
        ElMessage.error("请求地址不存在！");
    } else {
        ElMessage.error(res.msg || 'Unknown Error');
    }
    return Promise.reject(new Error(res.msg || 'Unknown Error'));
}, (error) => {
    // 对响应错误做点什么
    ElMessage({
        dangerouslyUseHTMLString: true,
        type: 'error',
        message: '操作失败，请刷新浏览器再次尝试，或联系管理员。&nbsp;&nbsp;微信：<strong><i>Super_Mutolee</i></strong>',
    })
    return Promise.reject(error);
});

export default instance