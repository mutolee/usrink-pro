<script setup>
import {ref, onUnmounted, onMounted, computed} from 'vue'
import {Delete, Plus} from "@element-plus/icons-vue";
// JSON 视图
// 文档地址：https://github.com/qiuquanwu/vue3-json-viewer
import {JsonViewer} from "vue3-json-viewer"
import "vue3-json-viewer/dist/index.css";
import {ElMessage} from "element-plus";
import axios from "axios";

onMounted(() => {

    // 获取历史记录
    getHistoryList();
})

/**********************************************************************
 * 布局拖拽相关
 */
const container = ref(null)
const leftWidth = ref(300)
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
    leftWidth.value = Math.max(MIN_WIDTH, Math.min(rawWidth, MAX_WIDTH))
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

/**********************************************************************
 * 历史记录相关
 */
// 创建新接口
const createNewRequest = () => {
    dataModel.value = {
        name: '',
        method: 'POST',
        url: '',
        paramType: 'x-www-form-urlencoded',
        params: [{key: '', value: ''}],
        headers: [{key: '', value: ''}],
        requestJsonBody: '',
        response: '',
        timeCost: 0
    }
}

// 获取历史记录
const getHistoryList = () => {
    const historyListStr = localStorage.getItem('historyList')
    if (historyListStr) {
        historyList.value = JSON.parse(historyListStr)
    }
}

// 历史数据列表
const historyList = ref([]);

// 悬浮索引
const hoverIndex = ref(null);

// 删除操作
const handleDelete = (index) => {
    historyList.value.splice(index, 1);
    // 更新本地存储
    localStorage.setItem('historyList', JSON.stringify(historyList.value));
};

/**********************************************************************
 * 请求相关
 */
const activeName = ref('first')
const loading = ref(false)

// 数据模型
const dataModel = ref({
    name: '',
    method: 'POST',
    url: '',
    paramType: 'x-www-form-urlencoded',
    params: [{key: '', value: ''}],
    headers: [{key: '', value: ''}],
    requestJsonBody: '',
    response: "",
    timeCost: 0
})

// 添加参数
const addParam = () => dataModel.value.params.push({key: '', value: ''})
const removeParam = (index) => dataModel.value.params.splice(index, 1)

// 添加Header
const addHeader = () => dataModel.value.headers.push({key: '', value: ''})
const removeHeader = (index) => dataModel.value.headers.splice(index, 1)

// 保存请求
const saveRequest = () => {
    if (dataModel.value.name === '' && dataModel.value.url === '') {
        ElMessage.warning('保存请求，需要【接口名称】或者【请求URL】！')
        return;
    }

    ElMessage.success('保存成功！')

    // 添加到历史记录
    historyList.value.unshift(dataModel.value)

    // 重置数据
    createNewRequest()

    // 保存到本地存储
    localStorage.setItem('historyList', JSON.stringify(historyList.value))
}

// 处理请求参数
const requestParams = computed(() => {
    if (dataModel.value.paramType !== 'x-www-form-urlencoded') return null
    return dataModel.value.params.reduce((acc, { key, value }) => {
        if (key) acc[key] = value
        return acc
    }, {})
})

// 处理请求头
const requestHeaders = computed(() => {
    return dataModel.value.headers.reduce((acc, { key, value }) => {
        if (key) acc[key] = value
        return acc
    }, {})
})

// 发送请求
const sendRequest = async () => {
    // 请求Url不能为空
    if (dataModel.value.url === '') {
        ElMessage.warning('请求URL不能为空！')
        return;
    }

    const startTime = Date.now()
    try {
        loading.value = true
        const config = {
            method: dataModel.value.method,
            url: dataModel.value.url,
            headers: requestHeaders.value,
        }

        // 处理不同参数类型
        if (dataModel.value.paramType === 'x-www-form-urlencoded') {
            config.headers['Content-Type'] = 'application/x-www-form-urlencoded'
            config.data = new URLSearchParams(requestParams.value).toString()
        } else if (dataModel.value.paramType === 'application/json') {
            config.headers['Content-Type'] = 'application/json'
            config.data = JSON.parse(dataModel.value.requestJsonBody)
        }

        // GET请求处理参数
        if (dataModel.value.method === 'GET' && requestParams.value) {
            config.params = requestParams.value
        }

        const res = await axios(config)
        dataModel.value.response = res.data
    } catch (error) {
        dataModel.value.response = error.response ? error.response.data : {error: error.message}
    } finally {
        dataModel.value.timeCost = (Date.now() - startTime) / 1000
        loading.value = false
    }
}
</script>

<template>
    <div class="page" ref="container" v-loading="loading">
        <div class="left-panel" :style="{ width: leftWidth + 'px',boxSizing: 'border-box' }">
            <div class="left-panel-head">
                <el-button type="primary" size="large" style="width: 100%" @click="createNewRequest">新建接口
                </el-button>
            </div>
            <div class="left-panel-title">
                <el-text>
                    <el-icon size="16" style="margin-right: 5px">
                        <Clock/>
                    </el-icon>
                    历史记录
                </el-text>
            </div>
            <div class="left-panel-history">
                <el-scrollbar>
                    <div class="history-list">
                        <el-empty v-if="!historyList || historyList.length === 0" description="暂无历史记录！"/>
                        <div v-else v-for="(item, index) in historyList" :key="index" class="list-item"
                             @mouseenter="hoverIndex = index" @mouseleave="hoverIndex = null">
                            <div class="item-span" @click="dataModel = {...item}">
                                <span style="color: #606266">{{ item.name }}</span>
                                <span style="color: #909399">
                                    <el-tag :type="item.method === 'GET' ? 'primary' : 'warning'" size="small" style="margin-right: 5px">{{item.method}}</el-tag>
                                    {{item.url}}
                                </span>
                            </div>
                            <div class="item-del" v-if="hoverIndex === index" @click="handleDelete(index)">
                                <el-icon class="delete-icon" size="16">
                                    <Delete/>
                                </el-icon>
                            </div>
                        </div>
                    </div>
                </el-scrollbar>
            </div>
        </div>
        <div class="divider" @mousedown="startDrag"></div>
        <div class="right-panel">
            <el-scrollbar>
                <div class="right-panel-content-head">
                    <el-form :model="dataModel">
                        <el-form-item>
                            <el-input v-model="dataModel.name" placeholder="接口名称" style="width: 100%" clearable/>
                        </el-form-item>
                        <el-form-item>
                            <el-input
                                v-model="dataModel.url"
                                style="width: 100%"
                                placeholder="请输入请求URL"
                                clearable
                            >
                                <template #prepend>
                                    <el-select v-model="dataModel.method" style="width: 100px">
                                        <el-option
                                            v-for="m in ['GET', 'POST', 'PUT', 'DELETE']"
                                            :key="m"
                                            :label="m"
                                            :value="m"
                                        />
                                    </el-select>
                                </template>
                            </el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-tabs v-model="activeName" style="width: 100%">
                                <el-tab-pane label="请求参数" name="first">
                                    <div v-if="dataModel.method === 'GET'" class="request-param">
                                        <div class="request-param-title">
                                            <el-text class="name">Key</el-text>
                                            <el-text class="value">Value</el-text>
                                        </div>
                                        <div v-for="(param, index) in dataModel.params" :key="index"
                                             class="request-param-item">
                                            <el-input class="name" v-model="param.key"></el-input>
                                            <el-input class="value" v-model="param.value"></el-input>
                                            <el-button v-if="index === dataModel.params.length - 1" class="add"
                                                       type="primary" :icon="Plus" circle plain
                                                       @click="addParam"></el-button>
                                            <el-button v-if="index > 0 || (index == 0 && dataModel.params.length > 1)"
                                                       class="delete" type="danger" :icon="Delete" circle plain
                                                       @click="removeParam(index)"></el-button>
                                        </div>
                                    </div>
                                    <div v-else class="request-param-post">
                                        <el-radio-group v-model="dataModel.paramType">
                                            <el-radio value="x-www-form-urlencoded">x-www-form-urlencoded</el-radio>
                                            <el-radio value="application/json">application/json</el-radio>
                                        </el-radio-group>
                                        <div v-if="dataModel.paramType === 'x-www-form-urlencoded'"
                                             class="request-param">
                                            <div class="request-param-title">
                                                <el-text class="name">Key</el-text>
                                                <el-text class="value">Value</el-text>
                                            </div>
                                            <div v-for="(param, index) in dataModel.params" :key="index"
                                                 class="request-param-item">
                                                <el-input class="name" v-model="param.key"></el-input>
                                                <el-input class="value" v-model="param.value"></el-input>
                                                <el-button v-if="index === dataModel.params.length - 1" class="add"
                                                           type="primary" :icon="Plus" circle plain
                                                           @click="addParam"></el-button>
                                                <el-button
                                                    v-if="index > 0 || (index == 0 && dataModel.params.length > 1)"
                                                    class="delete" type="danger" :icon="Delete" circle plain
                                                    @click="removeParam(index)"></el-button>
                                            </div>
                                        </div>
                                        <div v-else class="request-param">
                                            <el-input
                                                v-model="dataModel.requestJsonBody"
                                                :rows="5"
                                                type="textarea"
                                                placeholder='例如：{"name" : "mutolee"}'
                                            />
                                        </div>
                                    </div>
                                </el-tab-pane>
                                <el-tab-pane label="H 请求头" name="second">
                                    <div class="request-param">
                                        <div class="request-param-title">
                                            <el-text class="name">Key</el-text>
                                            <el-text class="value">Value</el-text>
                                        </div>
                                        <div v-for="(header, index) in dataModel.headers" :key="index"
                                             class="request-param-item">
                                            <el-input class="name" v-model="header.key"></el-input>
                                            <el-input class="value" v-model="header.value"></el-input>
                                            <el-button v-if="index === dataModel.headers.length - 1" class="add"
                                                       type="primary" :icon="Plus" circle plain
                                                       @click="addHeader"></el-button>
                                            <el-button v-if="index > 0 || (index == 0 && dataModel.headers.length > 1)"
                                                       class="delete" type="danger" :icon="Delete" circle plain
                                                       @click="removeHeader(index)"></el-button>
                                        </div>
                                    </div>
                                </el-tab-pane>
                            </el-tabs>
                        </el-form-item>
                        <el-form-item class="right-panel-content-head-request-action">
                            <el-button type="primary" @click="sendRequest">发送请求</el-button>
                            <el-button type="success" plain @click="saveRequest">保存</el-button>
                        </el-form-item>
                    </el-form>
                    <div class="right-panel-content-title">
                        <el-text style="font-size: 20px">响应结果</el-text>
                        <el-text>耗时 {{ dataModel.timeCost }} s</el-text>
                    </div>
                </div>
                <div class="right-panel-content-body">
                    <el-empty v-if="dataModel.response === ''" description="暂无结果！"/>
                    <JsonViewer v-else :value="dataModel.response" copyable sort theme="light"/>
                </div>
            </el-scrollbar>
        </div>
    </div>
</template>

<style scoped>
/* 基础布局 ************************************/
.page {
    display: flex;
    height: 100%;
}

.left-panel {
    height: 100%;
    min-height: 0;
    flex-shrink: 0; /* 该项目不会缩小 */
    min-width: 250px;
    max-width: 400px;
    display: flex;
    flex-direction: column;
}

.right-panel {
    background-color: #ffffff;
    height: 100%;
    width: 100%;
    flex: 1;
    min-height: 0;
    min-width: 0;
}

.divider {
    width: 1px;
    border-left: 1px solid #ececec;
    cursor: col-resize;
    position: relative;
    z-index: 1;
    flex-shrink: 0;
    background-color: #ffffff;
}

.left-panel-head {
    height: 90px;
    padding: 20px;
    box-sizing: border-box;
    flex-shrink: 0;
    display: flex;
    align-items: center;
}

.left-panel-title {
    flex-shrink: 0;
    padding: 0 20px 10px 20px;
    box-sizing: border-box;
    border-bottom: 1px solid #ececec;
}

.left-panel-history {
    flex: 1;
    height: 100%;
    min-height: 0;
    background-color: #ffffff;
}

.history-list {
    padding: 20px 0 20px 15px;
}

.right-panel-content-head {
    padding: 20px 20px 10px 20px;
    border-bottom: 1px solid #eee;
    background-color: #fafafa;
}

:deep(.right-panel-content-head-request-action) .el-form-item__content {
    display: flex;
    flex-wrap: wrap;
    column-gap: 10px;
    row-gap: 10px;
}

.right-panel-content-head-request-action .el-button + .el-button {
    margin-left: 0;
}

.right-panel-content-title {
    border-left: 5px solid #409eff;
    padding-left: 10px;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 40px;
}

.right-panel-content-body {
    padding: 0px;
}

/* history 列表项 ************************************/
.list-item {
    display: flex;
    align-items: center;
    height: 60px;
    padding-left: 10px;
    box-sizing: border-box;
    border-bottom: 1px solid #eee;
    transition: background-color 0.3s;
}

.list-item:hover {
    background-color: #f5f7fa;
}

.item-span {
    flex: 1;
    width: 100%;
    min-width: 0;
    display: flex;
    flex-direction: column;
    gap: 3px;
    cursor: pointer;
    padding-right: 10px;
}

.item-span span {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.item-del {
    flex-shrink: 0;
    width: 45px;
    height: 60px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #ff0000;
    cursor: pointer;
}

.delete-icon {
    color: #ffffff;
    transition: color 0.3s;
}

/* request param 列表项 ************************************/
.request-param {
    display: flex;
    flex-direction: column;
    gap: 5px;
}

.request-param-title {
    display: flex;
    gap: 20px;
    margin-bottom: -5px;
}

.request-param-title .el-text {
    font-size: 12px;
    color: #9f9f9f;
    font-weight: 500;
}

.request-param-title .el-text.name {
    width: 185px;
    flex-shrink: 0;
}

.request-param-title .el-text.value {
    flex: 1;
    min-width: 0;
}

.request-param-item {
    display: flex;
    gap: 20px;
}

.request-param-item .el-button + .el-button {
    margin-left: 0;
}

.request-param-item .name {
    width: 185px;
    flex-shrink: 0;
}

</style>