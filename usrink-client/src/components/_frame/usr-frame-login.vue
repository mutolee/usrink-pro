<script setup>
import {ref} from "vue";
import loginUtil from "@/utils/LoginUtil";
import httpUtil from "@/utils/HttpUtil";
import {useRouter} from 'vue-router'
import {ElMessage} from "element-plus";

const router = useRouter()

// 登录按钮Loading状态
const loading = ref(false)

// 登录表单
const loginForm = ref({
    userName: '',
    password: ''
})

// 登录事件
const submitLogin = () => {
    // 验证表单
    if (!loginForm.value.userName) {
        ElMessage.error('用户名不能为空，请输入用户名！')
        return
    }
    if (!loginForm.value.password) {
        ElMessage.error('密码不能为空，请输入密码！')
        return
    }

    loading.value = true
    // 密码加密
    loginForm.value.password = loginUtil.encryptPassword(loginForm.value.password)
    // 登录请求
    httpUtil.post('/login', {...loginForm.value}).then(async res => {
        // 缓存用户信息
        loginUtil.cacheUserInfo(res.data)
        // 模拟登录耗时操作
        // await new Promise(resolve => setTimeout(resolve, 500))
        router.push('/')
    }).catch(e => {
        // 清空密码
        loginForm.value.password = ''
    }).finally(() => {
        // 关闭Loading
        loading.value = false
    })
}
</script>

<template>
    <section class="usr_login">
        <el-form
            :model="loginForm"
            class="el_form_override"
            @submit.native.prevent @keyup.enter="submitLogin">
            <h3>
                <el-icon
                    :size="36"
                    color="#409EFF">
                    <ElementPlus/>
                </el-icon>
                usrink-pro-admin
            </h3>
            <el-form-item>
                <el-input
                    v-model="loginForm.userName"
                    placeholder="用户名"
                    size="large"
                    prefix-icon="UserFilled"
                    clearable/>
            </el-form-item>
            <el-form-item>
                <el-input
                    v-model="loginForm.password"
                    placeholder="密码"
                    type="password"
                    size="large"
                    prefix-icon="Lock"
                    show-password/>
            </el-form-item>
            <el-form-item>
                <el-button
                    type="primary"
                    :loading="loading"
                    @click="submitLogin"
                    size="large"
                    style="width: 100%">
                    登录
                </el-button>
            </el-form-item>
            <el-form-item>
                <el-text type="success">游客账号：usrink/123456</el-text>
            </el-form-item>
        </el-form>
    </section>
</template>

<style scoped>
.usr_login {
    background-color: #2d3a4b;
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
}

.el_form_override {
    min-width: 300px;
    padding: 50px 50px 20px 50px;
    background-color: rgb(41, 51, 67);
    border: 1px solid rgb(50, 60, 74);
    border-radius: 5px;
}

.el_form_override h3 {
    font-size: 26px;
    color: #eee;
    margin-bottom: 40px;
    font-weight: 700;
    display: flex;
    align-items: center;
    justify-content: center;
}

.el_form_override h3 .el-icon {
    margin-right: 10px;
}

.el-form-item:last-child {
    margin-bottom: 0;
}
</style>
