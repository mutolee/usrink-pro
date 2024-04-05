<script setup>
import {onMounted, ref, watch} from "vue";
import httpUtil from "@/utils/HttpUtil";
import {ElMessage} from "element-plus";
import UsrDescriptions from "@/components/_frame/common/usr-descriptions.vue";
import UsrDescriptionsItem from "@/components/_frame/common/usr-descriptions-item.vue";

const queryForm = ref({
    userName: '',
    reqMethod: '',
    location: '',
    status: '0',
    startTime: '',
    endTime: '',
    pageNum: 1,
    pageSize: 10
})
const queryDateRange = ref([])
const loading = ref(false)
const logOperatorList = ref([])
const total = ref(0)

/**
 * 监听查询条件日期范围的变化
 */
watch(queryDateRange, (newVal) => {
    // 日期范围为空时，清空查询条件的日期范围
    if (!newVal || newVal.length === 0) {
        queryForm.value.startTime = ''
        queryForm.value.endTime = ''
        return
    }
    queryForm.value.startTime = newVal[0]
    queryForm.value.endTime = newVal[1]
})

onMounted(() => {
    // 初始化操作日志列表
    selectLogOperatorListData()
})

/**
 * 查询操作日志列表数据
 */
const selectLogOperatorListData = () => {
    loading.value = true
    httpUtil.post("/sysLogOperator/selectSysLogOperatorList", {...queryForm.value}).then(res => {
        logOperatorList.value = res.data.list || [];
        total.value = res.data.total || 0
    }).catch(err => {
        console.error(err)
    }).finally(() => {
        loading.value = false
    })
}

/**
 * 分页查询
 * @param val 当前页码
 */
const handleCurrentChange = (val) => {
    queryForm.value.pageNum = val
    selectLogOperatorListData()
}

/**
 * 搜索日志
 */
const queryLogOperatorListData = () => {
    // 重置页码
    queryForm.value.pageNum = 1
    selectLogOperatorListData()
}

const deleteForm = ref({
    startTime: '',
    endTime: '',
})
const deleteDateRange = ref([])
const deleteDateRangeTips = ref('')
const deleteDateRangeVisible = ref(false)
const deleteDateRangeIng = ref(false)

/**
 * 监听删除条件日期范围的变化
 */
watch(deleteDateRange, (newVal) => {
    if (!newVal || newVal.length === 0) {
        deleteForm.value.startTime = ''
        deleteForm.value.endTime = ''
        return
    }
    deleteForm.value.startTime = newVal[0]
    deleteForm.value.endTime = newVal[1]
})

/**
 * 按日期删除日志Dialog
 */
const deleteLogOperatorByDateDialog = () => {
    if (deleteForm.value.startTime === '' || deleteForm.value.endTime === '') {
        ElMessage.warning('请选择要删除的日期范围！')
        return
    }

    deleteDateRangeTips.value = `确定删除【${deleteForm.value.startTime}】-【${deleteForm.value.endTime}】的操作日志吗？`
    deleteDateRangeVisible.value = true
}

/**
 * 按日期删除日志
 */
const deleteLogOperatorByDate = () => {
    deleteDateRangeIng.value = true
    httpUtil.post("/sysLogOperator/deleteSysLogOperatorByTime", {...deleteForm.value}).then(res => {
        selectLogOperatorListData()
    }).catch(err => {
        console.error(err)
    }).finally(() => {
        deleteDateRangeVisible.value = false
        deleteDateRangeIng.value = false
    })
}

const deleteAllTips = ref('')
const deleteAllVisible = ref(false)
const deleteAllIng = ref(false)

/**
 * 清空全部日志Dialog
 */
const deleteAllDialog = () => {
    deleteAllTips.value = "确定清空全部操作日志吗？"
    deleteAllVisible.value = true
}

/**
 * 清空全部日志
 */
const deleteAll = () => {
    deleteAllIng.value = true
    httpUtil.post("/sysLogOperator/cleanSysLogOperator", {}).then(res => {
        selectLogOperatorListData()
    }).catch(err => {
        console.error(err)
    }).finally(() => {
        deleteAllVisible.value = false
        deleteAllIng.value = false
    })
}

const deleteLogId = ref('')
const deleteByIdVisible = ref(false)
const deleteByIdTips = ref('')
const deleteByIdIng = ref(false)

/**
 * 删除日志Dialog
 * @param row
 */
const deleteLogOperatorDialog = (row) => {
    deleteLogId.value = row.logId
    deleteByIdTips.value = `确定删除【${row.userName}】的这条操作日志吗？`
    deleteByIdVisible.value = true
}

/**
 * 删除日志
 */
const deleteLogOperatorById = () => {
    deleteByIdIng.value = true
    httpUtil.post("/sysLogOperator/deleteSysLogOperator", {logId: deleteLogId.value}).then(res => {
        selectLogOperatorListData()
    }).catch(err => {
        console.error(err)
    }).finally(() => {
        deleteByIdVisible.value = false
        deleteByIdIng.value = false
    })
}

const logOperatorInfo = ref({})
const logOperatorInfoVisible = ref(false)
const logOperatorInfoIng = ref(false)

/**
 * 日志详情Dialog
 * @param row
 */
const logOperatorInfoDialog = (row) => {
    logOperatorInfo.value = {}
    logOperatorInfoVisible.value = true
    logOperatorInfoIng.value = true
    httpUtil.post("/sysLogOperator/selectSysLogOperatorInfo", {logId: row.logId}).then(res => {
        logOperatorInfo.value = res.data || {}
    }).catch(err => {
        console.error(err)
        logOperatorInfo.value = {}
    }).finally(() => {
        logOperatorInfoIng.value = false
    })
}

</script>

<template>
    <div class="page">
        <el-card shadow="never" class="usr_card_override top">
            <el-form :inline="true" :model="queryForm">
                <el-form-item label="用户名">
                    <el-input v-model="queryForm.userName" maxlength="10" placeholder="用户名" clearable
                              style="width: 180px"/>
                </el-form-item>
                <el-form-item label="请求方法">
                    <el-input v-model="queryForm.reqMethod" maxlength="10" placeholder="请求方法" clearable
                              style="width: 180px"/>
                </el-form-item>
                <el-form-item label="用户地址">
                    <el-input v-model="queryForm.location" maxlength="10" placeholder="请求地址" clearable
                              style="width: 180px"/>
                </el-form-item>
                <el-form-item label="请求时间">
                    <el-date-picker
                        v-model="queryDateRange"
                        type="daterange"
                        :editable="false"
                        start-placeholder="开始时间"
                        end-placeholder="结束时间"
                        size="default"
                        value-format="YYYY-MM-DD"
                    />
                </el-form-item>
                <el-form-item label="状态">
                    <el-select v-model="queryForm.status" size="default" placeholder="选择状态" clearable
                               style="width: 180px">
                        <el-option label="正常" value="0"/>
                        <el-option label="失败" value="-1"/>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" plain @click="queryLogOperatorListData">搜索</el-button>
                </el-form-item>
            </el-form>
        </el-card>
        <div class="top_btn_panel">
            <el-form :inline="true" :model="deleteForm">
                <el-date-picker
                    v-model="deleteDateRange"
                    type="daterange"
                    :editable="false"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                    size="default"
                    value-format="YYYY-MM-DD"
                />
                <el-button type="warning" plain @click="deleteLogOperatorByDateDialog">按日期删除</el-button>
                <el-button type="danger" plain @click="deleteAllDialog">删除全部</el-button>
            </el-form>
        </div>
        <el-card v-loading="loading" shadow="never" class="usr_card_override content">
            <el-table
                class="log_operator_table"
                :data="logOperatorList"
                row-key="logId">
                <el-table-column label="记录ID" width="100" align="center">
                    <template #default="scope">
                        {{ scope.row.logId }}
                    </template>
                </el-table-column>
                <el-table-column label="用户名" width="120">
                    <template #default="scope">
                        {{ scope.row.userName }}
                    </template>
                </el-table-column>
                <el-table-column label="用户角色" width="120">
                    <template #default="scope">
                        {{ scope.row.userRoleName }}
                    </template>
                </el-table-column>
                <el-table-column label="操作描述" width="200">
                    <template #default="scope">
                        {{ scope.row.operatorDesc }}
                    </template>
                </el-table-column>
                <el-table-column label="请求方式" width="130" align="center">
                    <template #default="scope">
                        {{ scope.row.reqType }}
                    </template>
                </el-table-column>
                <el-table-column label="请求IP" width="150" align="center">
                    <template #default="scope">
                        {{ scope.row.ipAddr }}
                    </template>
                </el-table-column>
                <el-table-column label="用户地址" width="180">
                    <template #default="scope">
                        {{ scope.row.location }}
                    </template>
                </el-table-column>
                <el-table-column label="浏览器" width="120" align="center">
                    <template #default="scope">
                        {{ scope.row.browser }}
                    </template>
                </el-table-column>
                <el-table-column label="操作系统" width="120" align="center">
                    <template #default="scope">
                        {{ scope.row.os }}
                    </template>
                </el-table-column>
                <el-table-column label="操作耗时(MS)" width="120" align="center">
                    <template #default="scope">
                        {{ scope.row.costTime }}
                    </template>
                </el-table-column>
                <el-table-column label="操作时间" width="200">
                    <template #default="scope">
                        {{ scope.row.createTime }}
                    </template>
                </el-table-column>
                <el-table-column label="状态" align="center">
                    <template #default="scope">
                        <el-tag v-if="scope.row.status === 0">正常</el-tag>
                        <el-tag v-else-if="scope.row.status === -1" type="danger">失败</el-tag>
                    </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="160">
                    <template #default="scope">
                        <div class="action_btn">
                            <el-button type="primary" plain @click="logOperatorInfoDialog(scope.row)">详情
                            </el-button>
                            <el-button type="info" plain @click="deleteLogOperatorDialog(scope.row)">删除
                            </el-button>
                        </div>
                    </template>
                </el-table-column>
                <template #empty>
                    <el-empty description="暂无数据"/>
                </template>
            </el-table>
            <el-pagination background layout="prev, pager, next" :current-page="queryForm.pageNum"
                           @current-change="handleCurrentChange" :page-size="queryForm.pageSize" :total="total"/>
        </el-card>
        <!-- 按日期删除日志Dialog -->
        <el-dialog v-model="deleteDateRangeVisible" title="删除确认" width="500">
            <span>{{ deleteDateRangeTips }}</span>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="deleteDateRangeVisible = false">取消</el-button>
                    <el-button type="primary" @click="deleteLogOperatorByDate" :loading="deleteDateRangeIng">确定
                    </el-button>
                </div>
            </template>
        </el-dialog>
        <!-- 清空全部日志Dialog -->
        <el-dialog v-model="deleteAllVisible" title="删除确认" width="500">
            <span>{{ deleteAllTips }}</span>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="deleteAllVisible = false">取消</el-button>
                    <el-button type="primary" @click="deleteAll" :loading="deleteAllIng">确定</el-button>
                </div>
            </template>
        </el-dialog>
        <!-- 删除日志Dialog -->
        <el-dialog v-model="deleteByIdVisible" title="删除确认" width="500">
            <span>{{ deleteByIdTips }}</span>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="deleteByIdVisible = false">取消</el-button>
                    <el-button type="primary" @click="deleteLogOperatorById" :loading="deleteByIdIng">确定</el-button>
                </div>
            </template>
        </el-dialog>
        <!-- 日志详情Dialog -->
        <el-drawer class="usr_drawer_log_operator_info" v-model="logOperatorInfoVisible" direction="rtl" size="500">
            <template #header>
                <h4>日志详情</h4>
            </template>
            <template #default>
                <div class="usr_log_operator_panel" v-loading="logOperatorInfoIng">
                    <el-scrollbar>
                        <div v-if="Object.keys(logOperatorInfo).length !== 0" style="padding: 0 20px">
                            <usr-descriptions direction="column">
                                <usr-descriptions-item>
                                    <template #name>
                                        <el-text type="info">日志编号：</el-text>
                                    </template>
                                    <template #desc>{{ logOperatorInfo.logId }}</template>
                                </usr-descriptions-item>
                                <usr-descriptions-item>
                                    <template #name>
                                        <el-text type="info">用户名：</el-text>
                                    </template>
                                    <template #desc>{{ logOperatorInfo.userName }}</template>
                                </usr-descriptions-item>
                                <usr-descriptions-item>
                                    <template #name>
                                        <el-text type="info">用户角色：</el-text>
                                    </template>
                                    <template #desc>{{ logOperatorInfo.userRoleName }}</template>
                                </usr-descriptions-item>
                                <usr-descriptions-item>
                                    <template #name>
                                        <el-text type="info">操作描述：</el-text>
                                    </template>
                                    <template #desc>{{ logOperatorInfo.operatorDesc }}</template>
                                </usr-descriptions-item>
                                <usr-descriptions-item>
                                    <template #name>
                                        <el-text type="info">请求URL：</el-text>
                                    </template>
                                    <template #desc>{{ logOperatorInfo.reqUrl }}</template>
                                </usr-descriptions-item>
                                <usr-descriptions-item>
                                    <template #name>
                                        <el-text type="info">请求方式：</el-text>
                                    </template>
                                    <template #desc>
                                        <el-tag>{{ logOperatorInfo.reqType }}</el-tag>
                                    </template>
                                </usr-descriptions-item>
                                <usr-descriptions-item>
                                    <template #name>
                                        <el-text type="info">请求方法：</el-text>
                                    </template>
                                    <template #desc>
                                        <div class="code_info">
                                            {{ logOperatorInfo.reqMethod }}
                                        </div>
                                    </template>
                                </usr-descriptions-item>
                                <usr-descriptions-item>
                                    <template #name>
                                        <el-text type="info">请求参数：</el-text>
                                    </template>
                                    <template #desc>
                                        <div class="code_info">
                                            {{ logOperatorInfo.reqParam }}
                                        </div>
                                    </template>
                                </usr-descriptions-item>
                                <usr-descriptions-item>
                                    <template #name>
                                        <el-text type="info">请求IP：</el-text>
                                    </template>
                                    <template #desc>{{ logOperatorInfo.ipAddr }}</template>
                                </usr-descriptions-item>
                                <usr-descriptions-item>
                                    <template #name>
                                        <el-text type="info">操作地址：</el-text>
                                    </template>
                                    <template #desc>{{ logOperatorInfo.location }}</template>
                                </usr-descriptions-item>
                                <usr-descriptions-item>
                                    <template #name>
                                        <el-text type="info">浏览器：</el-text>
                                    </template>
                                    <template #desc>{{ logOperatorInfo.browser }}</template>
                                </usr-descriptions-item>
                                <usr-descriptions-item>
                                    <template #name>
                                        <el-text type="info">操作系统：</el-text>
                                    </template>
                                    <template #desc>{{ logOperatorInfo.os }}</template>
                                </usr-descriptions-item>
                                <usr-descriptions-item>
                                    <template #name>
                                        <el-text type="info">操作时间：</el-text>
                                    </template>
                                    <template #desc>{{ logOperatorInfo.createTime }}</template>
                                </usr-descriptions-item>
                                <usr-descriptions-item>
                                    <template #name>
                                        <el-text type="info">耗时(MS)：</el-text>
                                    </template>
                                    <template #desc>{{ logOperatorInfo.costTime }}</template>
                                </usr-descriptions-item>
                                <usr-descriptions-item>
                                    <template #name>
                                        <el-text type="info">状态：</el-text>
                                    </template>
                                    <template #desc>
                                        <el-tag v-if="logOperatorInfo.status === 0">正常</el-tag>
                                        <el-tag v-else-if="logOperatorInfo.status === -1" type="danger">失败</el-tag>
                                    </template>
                                </usr-descriptions-item>
                                <usr-descriptions-item>
                                    <template #name>
                                        <el-text type="info">结果：</el-text>
                                    </template>
                                    <template #desc>
                                        <div class="code_info">
                                            {{ logOperatorInfo.result }}
                                        </div>
                                    </template>
                                </usr-descriptions-item>
                            </usr-descriptions>
                        </div>
                        <el-empty v-else/>
                    </el-scrollbar>
                </div>
            </template>
            <template #footer>
                <div style="flex: auto">
                    <el-button @click="logOperatorInfoVisible = false">关闭</el-button>
                </div>
            </template>
        </el-drawer>
    </div>
</template>

<style scoped>
.page {
    padding: 20px;
}

.usr_card_override.top .el-form {
    display: flex;
    flex-wrap: wrap;
    column-gap: 20px;
    row-gap: 15px;
}

.usr_card_override.top .el-form .el-form-item {
    margin-bottom: 0;
    margin-right: 0;
}

.top_btn_panel {
    display: flex;
    margin: 15px 0 15px 0;
    justify-content: flex-end;
}

.top_btn_panel .el-form {
    display: flex;
    flex-wrap: wrap;
    column-gap: 10px;
    row-gap: 10px;
}

.top_btn_panel .el-form .el-button + .el-button {
    margin-left: 0;
}

.usr_card_override.content .el-table {
    margin-bottom: 20px;
}

:deep(.log_operator_table) .action_btn {
    display: flex;
    flex-wrap: wrap;
    column-gap: 10px;
}

:deep(.log_operator_table) .action_btn .el-button + .el-button {
    margin-left: 0;
}

:deep(.usr_drawer_log_operator_info) .el-drawer__header {
    padding: 20px;
    margin-bottom: 0;
}

:deep(.usr_drawer_log_operator_info) .el-drawer__body {
    padding: 0;
    overflow: hidden;
}

:deep(.usr_drawer_log_operator_info) .el-drawer__footer {
    padding-top: 20px;
}

.usr_log_operator_panel {
    height: calc(100vh - 60px - 72px);
}

:deep(.usr_log_operator_panel) .usr-descriptions-item .desc {
    width: 340px;
}

.code_info {
    border: 1px solid #e0e0e0;
    padding: 10px;
    background-color: #f9f9f9;
    border-radius: 3px;
}
</style>