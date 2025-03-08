<script setup>
</script>

<template>
    <div class="forbidden_panel">
        <el-card shadow="never" class="usr_card_override">
            <div class="notfound_panel_left">
                <el-empty description=" "/>
            </div>
            <div class="notfound_panel_right">
                <h2>302 Forbidden</h2>
                <p>1. 请确定浏览器地址栏URL是否正确</p>
                <p>2. 联系管理员，确定是否拥有该URL的访问权限</p>
            </div>
        </el-card>
    </div>
</template>

<style scoped>

.forbidden_panel {
    padding: 20px;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    height: 100%;
}

.usr_card_override {
    flex: 1; /* 填充满剩余空间 */
    display: flex;
    align-items: center;
    justify-content: center;
}

:deep(.usr_card_override) .el-card__body {
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