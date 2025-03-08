<script setup>
import {computed} from "vue";
import {useCollapseStateStore} from "@/stores/_frame/collapseStateStore";
import usrContainerHeader from "@/components/_frame/fragments/usr-container-header.vue";
import usrContainerPage from "@/components/_frame/fragments/usr-container-page.vue";

const collapseStateStore = useCollapseStateStore()

// 根据菜单的折叠状态，计算容器padding-left的值
const containerPaddingLeft = computed(() => collapseStateStore.collapseState ? '64px' : '220px')

</script>

<template>
    <section class="usr_container">
        <usr-container-header/>
        <usr-container-page/>
    </section>
</template>

<style scoped>
.usr_container {
    height: 100%;
    width: 100%;
    flex: 1; /* 该项目会扩展以填充多余空间 */
    min-width: 0; /* 防止内容撑出父容器 */
    min-height: 0;
    background-color: #f6f8f9;
    display: flex;
    flex-direction: column;
}
</style>