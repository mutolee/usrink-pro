import {ref} from 'vue'
import {defineStore} from 'pinia'

export const useLoadRoutesStateStore = defineStore('loadRoutesStateStore', () => {

    // 是否已经加载过路由信息
    // 记载过路由信息后，设置为true
    const hasLoadRoutes = ref(false)

    return {hasLoadRoutes}
})
