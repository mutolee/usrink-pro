<script setup>

// 定义props，接收父组件传递的参数
const props = defineProps({
    direction: {
        type: String,
        default: 'row'
    }
})


// 根据props的值，设置不同的class
const classes = {
    column: props.direction === 'column',
    row: props.direction === 'row'
}

</script>

<template>
    <div class="usr-descriptions" :class="classes">
        <slot></slot>
    </div>
</template>

<style scoped>
.usr-descriptions {
    display: flex;
    flex-wrap: wrap;
    row-gap: 20px;
}

/** 设置flex的方向，上下排列 */
.usr-descriptions.column {
    flex-direction: column;
    flex-wrap: nowrap;
}

/** 设置flex的方向，左右排列 */
.usr-descriptions.row {
    flex-direction: row;
    flex-wrap: wrap;
}
</style>