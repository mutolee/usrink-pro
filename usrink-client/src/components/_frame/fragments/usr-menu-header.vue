<script setup>
import {computed, onMounted, ref, watch, onUnmounted} from "vue";
import {useCollapseStateStore} from "@/stores/_frame/collapseStateStore";

// 定义自定义事件
const emit = defineEmits(['message-sent']);

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

/********************************************************
 * 拖拽菜单宽度
 */
const container = ref(null)
const MIN_WIDTH = 200
const MAX_WIDTH = 400
let isDragging = false

const startDrag = (e) => {
    isDragging = true
    document.addEventListener('mousemove', handleDrag)
    document.addEventListener('mouseup', stopDrag)
    e.preventDefault()
}

const handleDrag = (e) => {
    if (!isDragging) return

    const containerRect = container.value.getBoundingClientRect()
    // 计算鼠标相对于容器左侧的位置
    const rawWidth = e.clientX - containerRect.left
    // 应用最小和最大宽度限制
    let width = Math.max(MIN_WIDTH, Math.min(rawWidth, MAX_WIDTH))

    // 触发自定义事件并传递数据
    emit('message-sent', width);
}

const stopDrag = () => {
    isDragging = false
    document.removeEventListener('mousemove', handleDrag)
    document.removeEventListener('mouseup', stopDrag)
}

onUnmounted(() => {
    document.removeEventListener('mousemove', handleDrag)
    document.removeEventListener('mouseup', stopDrag)
})

</script>

<template>
    <section ref="container" class="usr_menu_header" :style="{height:height}">
        <div class="usr_menu_header_panel">
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
        </div>
        <div class="divider" v-show="!collapseState" @mousedown="startDrag"></div>
    </section>
</template>

<style scoped>
.usr_menu_header {
    display: flex;
    background: linear-gradient(90deg, #409eff 90%, #00537E 120%);
    transition: height .3s;
    flex-shrink: 0; /* 该项目不会缩小 */
}

.usr_menu_header .usr_menu_header_panel {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    flex: 1;
    min-width: 0;
}

.usr_menu_header .el-icon {
    transition: font-size .5s, margin-bottom .5s;
}

.usr_menu_header_txt {
    color: #ffffff;
    font-size: 20px;
    transition: height .5s;
}

.divider {
    width: 5px;
    cursor: col-resize;
    position: relative;
    z-index: 1;
    flex-shrink: 0;
}
</style>