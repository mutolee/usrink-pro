<script setup>
</script>

<template>
    <div class="notfound_panel">
        <el-card shadow="never" class="usr_card_override">
            <div class="notfound_panel_left">
                <el-empty description=" "/>
            </div>
            <div class="notfound_panel_right">
                <h2>404 NotFound</h2>
                <p>1. 请确定浏览器地址栏URL是否正确</p>
                <p>2. 联系管理员，确定该页面组件是否存在！</p>
            </div>
        </el-card>
    </div>
</template>

<style scoped>

.notfound_panel {
    padding: 20px;
}

.usr_card_override {
    height: calc(100vh - 20px - 20px - 2px);
}

:deep(.usr_card_override) .el-card__body {
    height: calc(100% - 40px);
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
}

.notfound_panel_right h2 {
    padding-bottom: 20px;
}

.notfound_panel_right p {
    padding-bottom: 5px;
}


</style>