<script setup>
import {computed, onMounted, ref, watch} from "vue";
import {useCollapseStateStore} from "@/stores/_frame/collapseStateStore";

const collapseStateStore = useCollapseStateStore();

// 菜单折叠状态
const collapseState = computed(() => collapseStateStore.collapseState)
// Logo 高度
const height = computed(() => collapseStateStore.collapseState ? '60px' : '135px')
// Logo size
const logoSize = computed(() => collapseStateStore.collapseState ? 30 : 44)
// Logo MarginBottom
const marginBottom = computed(() => collapseStateStore.collapseState ? '0px' : '10px')
// Logo font 高度
const fontHeight = computed(() => collapseStateStore.collapseState ? '0px' : '24px')

// 默认显示Logo名称
const showLogoName = ref(true)

onMounted(() => {
    // 初始化时，根据菜单折叠状态，设置Logo名称显示状态
    showLogoName.value = !collapseState.value
})

// 监听菜单折叠状态
watch(collapseState, (newVal) => {
    if (!newVal) {
        // 菜单折叠状态为`开启`，延迟显示Logo名称
        setTimeout(() => {
            showLogoName.value = true
        }, 200);
    } else {
        // 菜单折叠状态为`关闭`，立即隐藏Logo名称
        showLogoName.value = false
    }
})

</script>

<template>
    <section
        class="usr_menu_header"
        :style="{height:height}">
        <el-icon
            :size="logoSize"
            color="#FFFFFF"
            :style="{marginBottom:marginBottom}">
            <ElementPlus/>
        </el-icon>
        <div
            class="usr_menu_header_txt"
            :style="{height:fontHeight}">
            <span v-show="showLogoName">usrink-pro-admin</span>
        </div>
    </section>
</template>

<style scoped>
.usr_menu_header {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    transition: height .5s;
    background: linear-gradient(90deg, #409eff 90%, #00537E 120%);
}

.usr_menu_header .el-icon {
    transition: font-size .5s, margin-bottom .5s;
}

.usr_menu_header_txt {
    color: #ffffff;
    font-size: 20px;
    transition: height .5s;
}
</style>