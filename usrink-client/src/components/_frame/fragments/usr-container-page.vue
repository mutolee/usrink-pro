<script setup>
import {computed} from 'vue'
import {useNavStore} from "@/stores/_frame/navStore";

const navStore = useNavStore()

// 获取可被缓存的路由映射的组件的名称<br>
// 当页面组件需要缓存，那么加入到该集合中，比如新打开页面的时候<br>
// 当页面不需要缓存的时候，从该集合中删除，比如关闭Tab的时候
const includes = computed(() => {
    let includes_cache = navStore.includes
    let result = []
    includes_cache.forEach((item) => {
        result.push(item.cptName)
    })
    return result
})
</script>

<template>
    <section class="usr_container_page">
        <el-scrollbar>
            <router-view v-slot="{ Component }">
                <keep-alive :include="includes">
                    <component :is="Component"/>
                </keep-alive>
            </router-view>
        </el-scrollbar>
    </section>
</template>

<style scoped>
.usr_container_page {
    flex: 1;
    height: 100%;
    min-height: 0; /* 防止内容撑出父容器 */
}

/* 让el-scrollbar__view 100%，防止子节点 height 100% 失效 */
:deep(.el-scrollbar) .el-scrollbar__view{
    height: 100%;
}
</style>
