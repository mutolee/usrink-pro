import {defineStore} from "pinia/dist/pinia";
import {ref} from "vue";

export const useUserInfoStore = defineStore('userInfoStore', () => {

    // 用户信息
    const userInfo = ref({})

    // 角色信息
    const roleInfo = ref({})

    // 用户菜单
    // {pages: [], menus: []}
    const userMenus = ref({})

    return {userInfo, roleInfo, userMenus}

}, {
    persist: {
        enabled: true,
        strategies: [{
            // 保存到localStorage
            storage: localStorage
        }]
    }
})