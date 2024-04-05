import {defineStore} from "pinia/dist/pinia";
import {ref} from "vue";

export const useCollapseStateStore = defineStore('collapseStateStore', () => {

    // 页面菜单部分折叠状态
    const collapseState = ref(false)

    return {collapseState}

}, {
    persist: {
        enabled: true,
        strategies: [{
            // 保存到localStorage
            storage: localStorage
        }]
    }
})