import {defineStore} from "pinia/dist/pinia";
import {ref} from "vue";

export const useNavStore = defineStore('navStore', () => {

    // Tabs 列表
    // 数据结构：{label: '欢迎页', path: '/welcome', closable: false}
    const tabs = ref([])

    // 当前导航的路由信息，
    // 从`系统所有路由`中获取的当前导航的路由信息
    const routeInfo = ref({})

    // 可以被缓存的页面的名称
    const includes = ref([])

    return {tabs, routeInfo, includes}

}, {
    persist: {
        enabled: true,
        strategies: [{
            // 保存到localStorage
            storage: localStorage
        }]
    }
})