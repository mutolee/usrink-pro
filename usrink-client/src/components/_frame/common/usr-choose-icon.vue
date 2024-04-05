<script setup>
import {onMounted, ref} from "vue";
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const emit = defineEmits(['onConfirm'])

// 定义props，接收父组件传递的参数
const props = defineProps({
    index: {
        type: String,
        default: '自定义参数，同结果一起返回'
    },
    width: {
        type: Number,
        default: 300
    },
    height: {
        type: Number,
        default: 300
    }
})

onMounted(() => {
    // 加载所有图标
    loadIcons()
})

// 图标列表
const iconCollections = []
const icons = ref([])
// 容器大小
const containerSize = {
    width: props.width + 'px',
    height: props.height + 'px'
}

/**
 * 加载所有图标
 */
const loadIcons = () => {
    for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
        iconCollections.push(key)
    }
    icons.value = iconCollections
}

// 搜索关键字
const word = ref('')

/**
 * 选择图标事件
 *
 * @param icon
 */
const chooseItem = (icon) => {
    emit('onConfirm', icon)
}

/**
 * 搜索图标
 */
const searchIcon = () => {
    if (word.value === '') {
        icons.value = iconCollections
    } else {
        // 模糊搜索
        icons.value = iconCollections.filter(icon => icon.toLowerCase().includes(word.value.toLowerCase()))
    }
}

</script>

<template>
    <div class="choose_icon_panel" :style="{width: containerSize.width}">
        <el-input v-model="word" @input="searchIcon" placeholder="Please input" clearable>
            <template #append>
                <el-button icon="search" @click="searchIcon"></el-button>
            </template>
        </el-input>
        <div :style="{height: containerSize.height}">
            <el-scrollbar always>
                <div class="choose_icon_list">
                    <div class="icon_item" v-for="icon in icons" :key="icon" @click="chooseItem(icon)">
                        <el-icon :size="18" style="color: #7c7c7c">
                            <component :is="icon"/>
                        </el-icon>
                    </div>
                </div>
            </el-scrollbar>
        </div>
    </div>
</template>

<style scoped>

.choose_icon_panel {
    display: flex;
    flex-direction: column;
    row-gap: 10px;
}

.choose_icon_list {
    display: flex;
    flex-wrap: wrap;
    column-gap: 10px;
    row-gap: 10px;
}

:deep(.choose_icon_list) .icon_item {
    flex: 0 0 calc(20% - 10px); /* 每行 5 列，减去列间隙后每个格子占据的宽度 */
    box-sizing: border-box;
    border: 1px solid #ccc;
    padding: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
}

:deep(.choose_icon_list) .icon_item:hover {
    background-color: rgba(64, 158, 255, 0.1);
}
</style>