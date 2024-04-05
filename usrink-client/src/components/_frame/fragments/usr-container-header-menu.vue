<script setup>
import {useRouter} from "vue-router";
import {useCollapseStateStore} from "@/stores/_frame/collapseStateStore";
import {useNavStore} from "@/stores/_frame/navStore";
import {useUserInfoStore} from "@/stores/_frame/userInfoStore";
import {computed} from "vue";
import loginUtil from "@/utils/LoginUtil";
import screenUtil from "@/utils/ScreenUtil";
import {BASE_URL} from "@/utils/Constant.js";
import {ElementPlus} from "@element-plus/icons-vue";

const router = useRouter()
const navStore = useNavStore()
const userInfoStore = useUserInfoStore()
const collapseStateStore = useCollapseStateStore()

// 菜单折叠状态
const collapseState = computed(() => collapseStateStore.collapseState)

// 面包屑
const breadcrumb = computed(() => {
    let routeInfo = navStore.routeInfo
    return routeInfo.breadcrumb
})

/**
 * 菜单折叠按钮点击事件
 */
const collapseClick = () => {
    collapseStateStore.collapseState = !collapseState.value
}

/**
 * 个人中心
 */
const userInfoClick = () => {
    router.push("/profile/info")
}

/**
 * 全屏
 */
const screenFull = () => {
    screenUtil.screenFull(val => {
    })
}

/**
 * 退出登录
 */
const logout = () => {
    loginUtil.logout()
    router.push("/login")
}

</script>

<template>
    <section class="usr_container_header_menu">
        <div class="usr_container_header_menu_left">
            <div
                @click="collapseClick"
                class="usr_header_item usr_container_header_menu_left_collapse">
                <el-icon :size="18">
                    <component :is="collapseState ? 'Expand' : 'Fold'"/>
                </el-icon>
            </div>
            <div class="usr_header_item usr_container_header_menu_left_breadcrumb">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item
                        v-for="(crumb, index) of breadcrumb"
                        :key="index">
                        {{ crumb }}
                    </el-breadcrumb-item>
                </el-breadcrumb>
            </div>
        </div>
        <div class="usr_container_header_menu_right">
            <div class="usr_header_item usr_container_header_menu_right_nick">
                <el-dropdown class="el_dropdown_override">
                    <span>
                        <el-badge
                            class="el_badge_override">
                            <el-avatar
                                class="el_avatar_override"
                                :size="30"
                                :src="BASE_URL + userInfoStore.userInfo.avatar"
                                style="border: 1px solid #409EFFFF">
                                <template #default>
                                    <el-icon :size="24">
                                        <ElementPlus/>
                                    </el-icon>
                                </template>
                            </el-avatar>
                        </el-badge>
                        <span>{{ userInfoStore.userInfo.userNick }}<el-text
                            type="info">({{ userInfoStore.roleInfo.roleName }})</el-text></span>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu class="el_dropdown_menu_override">
                            <el-dropdown-item @click="userInfoClick">
                                <el-icon :size="18">
                                    <User/>
                                </el-icon>
                                个人中心
                            </el-dropdown-item>
                            <el-dropdown-item
                                @click="logout"
                                divided>
                                <el-icon :size="18">
                                    <SwitchButton/>
                                </el-icon>
                                退出
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </div>
            <div
                @click="screenFull"
                class="usr_header_item usr_container_header_menu_right_fullscreen">
                <el-icon :size="18">
                    <FullScreen/>
                </el-icon>
            </div>
        </div>
    </section>
</template>

<style scoped>
.usr_container_header_menu {
    height: 50px;
    border-bottom: 1px solid #f4f4f4;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.usr_container_header_menu_left, .usr_container_header_menu_right {
    display: flex;
    align-items: center;
}

.usr_header_item {
    height: 50px;
    padding: 0 10px;
    display: flex;
    align-items: center;
    transition: background-color .2s;
}

.usr_header_item:not(.usr_container_header_menu_left_breadcrumb,.usr_container_header_menu_right_nick):hover {
    background-color: #F2F6FC;
    cursor: pointer;
}

:deep(.el_badge_override) sup.is-dot {
    right: 8px;
    top: 4px
}

.el_badge_override {
    margin-right: 5px;
}

.el_avatar_override {
    background-color: #409eff;
}

:deep(li).el-dropdown-menu__item {
    padding: 10px 15px;
    min-width: 120px;
    color: #7c7c7c;
}

:deep(li).el-dropdown-menu__item:focus {
    color: #303133;
    background-color: #F2F6FC;
}

:deep(li).el-dropdown-menu__item .el-badge {
    display: flex;
    align-items: center;
    margin-left: 5px;
}
</style>