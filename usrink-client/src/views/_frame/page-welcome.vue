<script setup>
import httpUtil from "@/utils/HttpUtil";
import {useUserInfoStore} from "@/stores/_frame/userInfoStore";
import {onMounted, ref} from "vue";
import router from "@/router";
import loginUtil from "@/utils/LoginUtil";
import GenerateUtil from "../../utils/GenerateUtil";
import {BASE_URL} from "@/utils/Constant.js";
import {ElementPlus} from "@element-plus/icons-vue";

const userInfoStore = useUserInfoStore()

/**
 * 打开页面
 * @param e 路由地址
 */
const openPage = (e) => {
    router.push(e)
}

/**
 * 退出登录
 */
const logout = () => {
    loginUtil.logout()
    router.push("/login")
}

onMounted(() => {
    // 加载欢迎信息
    loadGreeting()
    // 加载CPU信息
    loadCpuInfo()
    // 加载Disk信息
    loadDiskInfo()
    // 加载Memory信息
    loadMemoryInfo()
    // 加载服务器信息
    loadServerInfo()
    // 加载Jvm信息
    loadJvmInfo()
})

// 欢迎
const greeting = ref('')
const welcomes = ref([])

/**
 * 加载欢迎信息
 */
const loadGreeting = () => {
    greeting.value = GenerateUtil.greeting()
    welcomes.value = GenerateUtil.getRandomTexts()
}


// CPU信息
const cpuData = ref([])
const cpuLoading = ref(true)
const cpuMode = ref('')

/**
 * 加载CPU信息
 */
const loadCpuInfo = () => {
    cpuData.value = []
    cpuLoading.value = true
    httpUtil.get("/sysServer/cpu").then(res => {
        // 解析数据
        cpuData.value.push({
            name: "核心数",
            value: res.data.coreCount
        }, {
            name: "系统使用率",
            value: res.data.sysUsageRate + '%'
        }, {
            name: "用户使用率",
            value: res.data.userUsageRate + '%'
        }, {
            name: "空闲率",
            value: res.data.idleRate + '%'
        })
        cpuMode.value = res.data.cpuMode
    }).catch(error => {
        console.error(error)
    }).finally(() => {
        cpuLoading.value = false
    })
}

// Disk信息
const diskData = ref([])
const diskLoading = ref(true)

/**
 * 加载Disk信息
 */
const loadDiskInfo = () => {
    diskData.value = []
    diskLoading.value = true
    httpUtil.get("/sysServer/disk").then(res => {
        // 解析数据
        diskData.value.push({
            name: "磁盘总大小",
            value: res.data.totalSize
        }, {
            name: "磁盘已用大小",
            value: res.data.usedSize
        }, {
            name: "磁盘剩余大小",
            value: res.data.freeSize
        }, {
            name: "磁盘使用率",
            value: res.data.usageRate + '%'
        })
    }).catch(error => {
        console.error(error)
    }).finally(() => {
        diskLoading.value = false
    })
}

// Memory信息
const memoryData = ref([])
const memoryLoading = ref(true)

/**
 * 加载Memory信息
 */
const loadMemoryInfo = () => {
    memoryData.value = []
    memoryLoading.value = true
    httpUtil.get("/sysServer/memory").then(res => {
        // 解析数据
        memoryData.value.push({
            name: "总大小",
            memValue: res.data.totalSize,
            jvmValue: res.data.jvmMaxMemory
        }, {
            name: "已用大小",
            memValue: res.data.used,
            jvmValue: res.data.jvmUsedMemory
        }, {
            name: "剩余大小",
            memValue: res.data.free,
            jvmValue: res.data.jvmUsableMemory
        }, {
            name: "内存使用率",
            memValue: res.data.usedRate + '%',
            jvmValue: res.data.jvmUsedRate + '%'
        })
    }).catch(error => {
        console.error(error)
    }).finally(() => {
        memoryLoading.value = false
    })
}

// 服务器信息
const serverData = ref([])
const serverLoading = ref(true)

/**
 * 加载服务器信息
 */
const loadServerInfo = () => {
    serverData.value = []
    serverLoading.value = true
    httpUtil.get("/sysServer/server").then(res => {
        // 解析数据
        serverData.value.push({
            col1: "服务器名称",
            col2: res.data.serverName,
            col3: "服务器IP",
            col4: res.data.serverIp
        }, {
            col1: "操作系统",
            col2: res.data.serverOs,
            col3: "系统架构",
            col4: res.data.serverOsArch
        })
    }).catch(error => {
        console.error(error)
    }).finally(() => {
        serverLoading.value = false
    })
}

// JVM信息
const jvmData = ref([])
const jvmLoading = ref(true)

/**
 * 加载JVM信息
 */
const loadJvmInfo = () => {
    jvmData.value = []
    jvmLoading.value = true
    httpUtil.get("/sysServer/jvm").then(res => {
        // 解析数据
        jvmData.value.push({
            name: "Jvm名称",
            value: res.data.javaName
        }, {
            name: "Jvm版本",
            value: res.data.javaVersion
        }, {
            name: "Jvm路径",
            value: res.data.javaHome
        }, {
            name: "Jvm启动时间",
            value: res.data.javaStartTime
        }, {
            name: "Jvm运行时长",
            value: res.data.javaRunTime
        }, {
            name: "项目路径",
            value: res.data.projectDir
        }, {
            name: "启动参数",
            value: res.data.javaRunParams
        })
    }).catch(error => {
        console.error(error)
    }).finally(() => {
        jvmLoading.value = false
    })
}

</script>

<template>
    <div class="welcome_panel">
        <el-row>
            <el-col :span="24">
                <el-card shadow="never" class="usr_el_card_override usr_el_card_introduce">
                    <div class="introduce_avatar">
                        <el-avatar :size="80" :src="BASE_URL + userInfoStore.userInfo.avatar"
                                   style="background-color: #409eff; border: 2px solid #409EFF">
                            <template #default>
                                <el-icon :size="50">
                                    <ElementPlus/>
                                </el-icon>
                            </template>
                        </el-avatar>
                    </div>
                    <div class="introduce_desc">
                        <p class="tit">
                            {{ greeting }}呀，{{ userInfoStore.userInfo.userNick }}，元气满满的一天！
                            <el-button link @click="loadGreeting">
                                <el-icon :size="20">
                                    <Refresh/>
                                </el-icon>
                            </el-button>
                        </p>
                        <p class="tips">
                            <p v-for="text in welcomes">
                                {{ text }}
                            </p>
                        </p>
                    </div>
                </el-card>
            </el-col>
        </el-row>
        <el-row>
            <el-col :xs="24" :sm="12" :md="6" :lg="3" :xl="3">
                <el-card body-class="card_hover" @click="openPage('/welcome')" shadow="hover" class="usr_el_card_override usr_el_card_button">
                    <el-icon :size="36" :color="'#409eff'">
                        <HomeFilled/>
                    </el-icon>
                    <p>首页</p>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="12" :md="6" :lg="3" :xl="3">
                <el-card body-class="card_hover" @click="openPage('/sys/user')" shadow="hover" class="usr_el_card_override usr_el_card_button">
                    <el-icon :size="36" :color="'#17a997'">
                        <User/>
                    </el-icon>
                    <p>用户管理</p>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="12" :md="6" :lg="3" :xl="3">
                <el-card body-class="card_hover" @click="openPage('/sys/role')" shadow="hover" class="usr_el_card_override usr_el_card_button">
                    <el-icon :size="36" :color="'#60941f'">
                        <UserFilled/>
                    </el-icon>
                    <p>角色管理</p>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="12" :md="6" :lg="3" :xl="3">
                <el-card body-class="card_hover" @click="openPage('/sys/menu')" shadow="hover" class="usr_el_card_override usr_el_card_button">
                    <el-icon :size="36" :color="'#088f75'">
                        <Menu/>
                    </el-icon>
                    <p>菜单管理</p>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="12" :md="6" :lg="3" :xl="3">
                <el-card body-class="card_hover" @click="openPage('/sys/log/login')" shadow="hover" class="usr_el_card_override usr_el_card_button">
                    <el-icon :size="36" :color="'#cb8c1d'">
                        <Tickets/>
                    </el-icon>
                    <p>登录日志</p>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="12" :md="6" :lg="3" :xl="3">
                <el-card body-class="card_hover" @click="openPage('/sys/log/operator')" shadow="hover" class="usr_el_card_override usr_el_card_button">
                    <el-icon :size="36" :color="'#731f94'">
                        <Files/>
                    </el-icon>
                    <p>操作日志</p>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="12" :md="6" :lg="3" :xl="3">
                <el-card body-class="card_hover" @click="openPage('/profile/info')" shadow="hover" class="usr_el_card_override usr_el_card_button">
                    <el-icon :size="36" :color="'#1d310e'">
                        <Avatar/>
                    </el-icon>
                    <p>个人中心</p>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="12" :md="6" :lg="3" :xl="3">
                <el-card body-class="card_hover" @click="logout" shadow="hover" class="usr_el_card_override usr_el_card_button">
                    <el-icon :size="36" :color="'#b32eb6'">
                        <SwitchButton/>
                    </el-icon>
                    <p>退出</p>
                </el-card>
            </el-col>
        </el-row>
        <el-row>
            <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
                <el-card v-loading="cpuLoading" shadow="hover" class="usr_el_card_override">
                    <template #header>
                        <div class="card-header">
                            <div class="usr_card_header_left">
                                <el-icon :size="20">
                                    <Cpu/>
                                </el-icon>
                                <span>CPU信息 <el-text type="info">{{ cpuMode }}</el-text></span>
                            </div>
                            <div class="usr_card_header_right">
                                <el-button link @click="loadCpuInfo">
                                    <el-icon :size="20">
                                        <Refresh/>
                                    </el-icon>
                                </el-button>
                            </div>
                        </div>
                    </template>
                    <el-table :data="cpuData" :height="200">
                        <el-table-column prop="name" label="属性"></el-table-column>
                        <el-table-column label="值">
                            <template #default="scope">
                                <el-text type="info">{{ scope.row.value }}</el-text>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
                <el-card shadow="hover" class="usr_el_card_override">
                    <template #header>
                        <div class="card-header">
                            <div class="usr_card_header_left">
                                <el-icon :size="20">
                                    <Box/>
                                </el-icon>
                                <span>磁盘信息</span>
                            </div>
                            <div class="usr_card_header_right">
                                <el-button link @click="loadDiskInfo">
                                    <el-icon :size="20">
                                        <Refresh/>
                                    </el-icon>
                                </el-button>
                            </div>
                        </div>
                    </template>
                    <el-table :data="diskData" :height="200">
                        <el-table-column prop="name" label="属性"></el-table-column>
                        <el-table-column label="值">
                            <template #default="scope">
                                <el-text type="info">{{ scope.row.value }}</el-text>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-card v-loading="memoryLoading" shadow="hover" class="usr_el_card_override">
                    <template #header>
                        <div class="card-header">
                            <div class="usr_card_header_left">
                                <el-icon :size="20">
                                    <Tickets/>
                                </el-icon>
                                <span>内存信息</span>
                            </div>
                            <div class="usr_card_header_right">
                                <el-button @click="loadMemoryInfo" link>
                                    <el-icon :size="20">
                                        <Refresh/>
                                    </el-icon>
                                </el-button>
                            </div>
                        </div>
                    </template>
                    <el-table :data="memoryData" :height="200">
                        <el-table-column prop="name" label="属性"></el-table-column>
                        <el-table-column label="内存">
                            <template #default="scope">
                                <el-text type="info">{{ scope.row.memValue }}</el-text>
                            </template>
                        </el-table-column>
                        <el-table-column label="JVM">
                            <template #default="scope">
                                <el-text type="info">{{ scope.row.jvmValue }}</el-text>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
        </el-row>
        <el-row>
            <el-col>
                <el-card v-loading="serverLoading" shadow="hover" class="usr_el_card_override">
                    <template #header>
                        <div class="card-header">
                            <div class="usr_card_header_left">
                                <el-icon :size="20">
                                    <Monitor/>
                                </el-icon>
                                <span>服务器信息</span>
                            </div>
                        </div>
                    </template>
                    <el-table :data="serverData" :show-header="false">
                        <el-table-column prop="col1" :width="150"></el-table-column>
                        <el-table-column label="值">
                            <template #default="scope">
                                <el-text type="info">{{ scope.row.col2 }}</el-text>
                            </template>
                        </el-table-column>
                        <el-table-column prop="col3" :width="150"></el-table-column>
                        <el-table-column label="值">
                            <template #default="scope">
                                <el-text type="info">{{ scope.row.col4 }}</el-text>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
        </el-row>
        <el-row>
            <el-col>
                <el-card v-loading="jvmLoading" shadow="hover" class="usr_el_card_override">
                    <template #header>
                        <div class="card-header">
                            <div class="usr_card_header_left">
                                <el-icon :size="20">
                                    <CoffeeCup/>
                                </el-icon>
                                <span>Java虚拟机信息</span>
                            </div>
                        </div>
                    </template>
                    <el-table :data="jvmData" :show-header="false">
                        <el-table-column :width="180" prop="name" label="名称"></el-table-column>
                        <el-table-column label="值">
                            <template #default="scope">
                                <el-text type="info">{{ scope.row.value }}</el-text>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<style scoped>
.welcome_panel {
    padding: 20px 0 0 20px;
}

:deep(.usr_el_card_override) .el-card__header {
    padding-top: 15px;
    padding-bottom: 15px;
}

:deep(.usr_el_card_introduce) .el-card__body {
    display: flex;
    flex-wrap: wrap;
}

.introduce_avatar {
    width: 120px;
    display: flex;
    justify-content: center;
    align-items: center;
}

.introduce_desc {
    flex: 1;
}

.introduce_desc .tit {
    margin: 20px 0 15px 0;
    color: #141b1e;
    font-weight: 400;
    font-size: 20px;
}

.introduce_desc .tips {
    color: #808695;
}

.el-row .el-col {
    padding-bottom: 20px;
    padding-right: 20px;
}

.usr_el_card_override .card-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.usr_el_card_override .usr_card_header_left {
    display: flex;
    align-items: center;
}

.usr_el_card_override .usr_card_header_left .el-icon {
    margin-right: 5px;
}

:deep(.usr_el_card_button) .card_hover:hover{
  cursor: pointer;
}

:deep(.usr_el_card_button) .el-card__body {
    display: flex;
    flex-direction: column;
    align-items: center;
}


:deep(.usr_el_card_button) .el-card__body p {
    padding-top: 15px;
}

</style>
