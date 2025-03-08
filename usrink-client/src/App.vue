<script setup>
</script>

<template>
    <router-view/>
</template>

<style>
/*! ------------------------ 全局基础样式 ------------------------ */

/* 重置默认样式和盒模型 */
* {
    margin: 0;
    padding: 0;
    scrollbar-width: none; /* Firefox 隐藏滚动条 */
    -ms-overflow-style: none; /* IE/Edge 隐藏滚动条 */
}

/* 滚动条宽度 */
::-webkit-scrollbar {
    width: 0px;
    display: none;
}

/*
   - 根元素和主体布局
   - 定义全局字体和颜色方案
   - 确保全屏高度布局
*/
html, body, #app {
    color: #303133;
    height: 100%;
    width: 100%;
    font-size: 14px;
    background-color: #fff;
    font-family: 'Helvetica Neue', Helvetica, 'PingFang SC',
    'Hiragino Sans GB', 'Microsoft YaHei',
    '微软雅黑', Arial, sans-serif;
}

/* 通用链接样式 */
html body a {
    color: #409EFF;
    text-decoration: none;
}

/* 列表项重置 */
li {
    list-style: none;
}

/*! ------------------------ 第三方库样式覆盖 ------------------------ */

/* NProgress进度条主色调整 */
#nprogress .bar {
    background: #409EFF !important;
}

/* ElementUI 日期组件样式 */
.el-date-table {
    td.in-range .el-date-table-cell {
        background-color: #c6e2ff !important;
    }

    td.disabled .el-date-table-cell {
        background-color: #fff !important;
        color: #dedfe0 !important;
    }
}

/* 弹出层统一阴影配置 */
.el-popper.is-light {
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>