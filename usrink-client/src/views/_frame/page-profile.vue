<script setup>
import {onMounted, ref} from "vue";
import {Edit, ElementPlus, Warning} from "@element-plus/icons-vue";
import {useUserInfoStore} from "@/stores/_frame/userInfoStore";
import httpUtil from "@/utils/HttpUtil.js";
import {ElMessage} from "element-plus";
import loginUtil from "@/utils/LoginUtil.js";
import {BASE_URL} from "@/utils/Constant.js";
import UsrImgCutter from "@/components/_frame/common/usr-img-cutter.vue";

// Store 中的数据
let userInfoStore = useUserInfoStore();
// 用户资料信息
const userInfoTableData = ref([]);
// 编辑用户表单数据
const editUserForm = ref({})

onMounted(() => {
    // 加载用户信息
    loadUserInfo();
    // 加载编辑信息
    loadEditUserInfo();
})

/**
 * 加载用户信息
 */
const loadUserInfo = () => {
    userInfoTableData.value = [
        {name: "头像", value: BASE_URL + userInfoStore.userInfo.avatar},
        {name: "用户名", value: userInfoStore.userInfo.userName},
        {name: "用户昵称", value: userInfoStore.userInfo.userNick},
        {
            name: "性别",
            value: userInfoStore.userInfo.sex === 0 ? "女" : userInfoStore.userInfo.sex === 1 ? "男" : "未知"
        },
        {name: "手机号", value: userInfoStore.userInfo.phone},
        {name: "邮箱", value: userInfoStore.userInfo.email},
        {name: "角色", value: userInfoStore.roleInfo.roleName},
        {name: "创建时间", value: userInfoStore.userInfo.createTime}
    ]
}

/**
 * 加载编辑信息
 */
const loadEditUserInfo = () => {
    editUserForm.value = {
        userId: userInfoStore.userInfo.userId,
        userName: userInfoStore.userInfo.userName,
        userNick: userInfoStore.userInfo.userNick,
        phone: userInfoStore.userInfo.phone,
        email: userInfoStore.userInfo.email,
        sex: userInfoStore.userInfo.sex
    }
}

/**
 * 合并单元格
 * @param row 当前行
 * @param column 当前列
 * @param rowIndex 当前行索引
 * @param columnIndex 当前列索引
 * @return {number[]} 该函数可以返回一个包含两个元素的数组，第一个元素代表 rowspan，第二个元素代表 colspan
 */
const colspanMethod = ({row, column, rowIndex, columnIndex}) => {
    if (rowIndex === 0) { // 第一行
        if (columnIndex === 0) {
            return [0, 0] // 不占行列
        } else if (columnIndex === 1) {
            return [1, 2] // 合并两列
        }
    }
}

// 编辑用户表单
const editUserFormRef = ref(null)
const editUserIng = ref(false)

// 用户编辑表单验证规则
const editUserFormRules = ref({
    userNick: [
        {required: true, message: '请输入用户昵称', trigger: 'blur'}
    ]
})

/**
 * 编辑用户
 */
const editUser = () => {
    editUserFormRef.value.validate((valid) => {
        if (valid) {
            editUserIng.value = true
            httpUtil.post("/sysUser/updateMyProfile", {...editUserForm.value}).then(res => {
                ElMessage.success("资料修改成功！")
                // 更新用户缓存
                updateUserInfoCacheForUpdateProfile()
                // 刷新用户信息
                loadUserInfo()
                // 刷新编辑信息
                loadEditUserInfo()
            }).catch(err => {
                console.error(err)
            }).finally(() => {
                editUserIng.value = false
            })
        }
    })
}

/**
 * 资料修改成功后，更新用户缓存
 */
const updateUserInfoCacheForUpdateProfile = () => {
    userInfoStore.userInfo.userNick = editUserForm.value.userNick
    userInfoStore.userInfo.phone = editUserForm.value.phone
    userInfoStore.userInfo.email = editUserForm.value.email
    userInfoStore.userInfo.sex = editUserForm.value.sex
}

// 用户重置密码表单数据
const editUserRestPwdForm = ref({
    oldPwd: "",
    newPwd: "",
    confirmPwd: ""
})
// 用户重置密码表单
const editUserRestPwdFormRef = ref(null)
const editUserRestPwdFormIng = ref(false)
// 用户重置密码表单验证规则
const editUserRestPwdFormRules = ref({
    oldPwd: [
        {required: true, message: '请输入老密码', trigger: 'blur'}
    ],
    newPwd: [
        {required: true, message: '请输入新密码', trigger: 'blur'}
    ],
    confirmPwd: [
        {required: true, message: '请再次输入新密码', trigger: 'blur'}
    ]
})

/**
 * 重置密码
 */
const editUserRestPwd = () => {
    editUserRestPwdFormRef.value.validate((valid) => {
        if (valid) {
            editUserRestPwdFormIng.value = true
            if (editUserRestPwdForm.value.newPwd !== editUserRestPwdForm.value.confirmPwd) {
                ElMessage.error("两次输入的新密码不一致！")
                editUserRestPwdFormIng.value = false
                return
            }

            // 密码加密
            editUserRestPwdForm.value.oldPwd = loginUtil.encryptPassword(editUserRestPwdForm.value.oldPwd)
            editUserRestPwdForm.value.newPwd = loginUtil.encryptPassword(editUserRestPwdForm.value.newPwd)
            editUserRestPwdForm.value.confirmPwd = loginUtil.encryptPassword(editUserRestPwdForm.value.confirmPwd)

            // 重置密码请求
            httpUtil.post("/sysUser/updateMyPwd", {...editUserRestPwdForm.value}).then(res => {
                ElMessage.success("密码修改成功！")
            }).catch(err => {
                console.error(err)
            }).finally(() => {
                editUserRestPwdFormIng.value = false

                // 清空密码
                editUserRestPwdForm.value.oldPwd = ""
                editUserRestPwdForm.value.newPwd = ""
                editUserRestPwdForm.value.confirmPwd = ""
            })
        }
    })
}

const editAvatarDialogVisible = ref(false)
const editAvatarIng = ref(false)

/**
 * 头像编辑确认
 * @param data
 */
const editAvatarOnConfirm = (data) => {
    // 计算Base64字符串的长度
    const fileSizeInBytes = data.dataURL.length;
    // 头像大小不能超过100KB
    if (fileSizeInBytes > 102400) {
        ElMessage.error("头像大小不能超过100KB！")
        return
    }

    editAvatarDialogVisible.value = false
    editAvatarIng.value = true
    httpUtil.post("/sysUser/updateMyAvatar", {base64Image: data.dataURL}).then(res => {
        ElMessage.success("头像修改成功！")
        // 更新用户缓存
        userInfoStore.userInfo.avatar = res.data.avatar
        // 刷新用户信息
        loadUserInfo()
    }).catch(err => {
        console.error(err)
    }).finally(() => {
        editAvatarIng.value = false
    })
}

</script>

<template>
    <div class="page">
        <el-row :gutter="20" class="usr_row">
            <el-col :xs="24" :sm="10" :md="9" :lg="7" :xl="6">
                <el-card shadow="never">
                    <template #header>
                        <div class="card-header">
                            <span>个人信息</span>
                        </div>
                    </template>
                    <el-table class="no-hover-background" :data="userInfoTableData" :span-method="colspanMethod"
                              :show-header="false">
                        <el-table-column width="80">
                            <template #default="scope">
                                {{ scope.row.name }}
                            </template>
                        </el-table-column>
                        <el-table-column>
                            <template #default="scope">
                                <div v-if="scope.$index === 0" class="avatar_row" v-loading="editAvatarIng">
                                    <div class="avatar_panel">
                                        <el-avatar :size="100" :src="scope.row.value"
                                                   style="background-color: #409eff; border: 2px solid #409EFFFF">
                                            <template #default>
                                                <el-icon :size="50">
                                                    <ElementPlus/>
                                                </el-icon>
                                            </template>
                                        </el-avatar>
                                        <span class="mask" @click="editAvatarDialogVisible = true">
                                            <el-icon :size="24" color="#FFFFFF">
                                            <Edit/>
                                            </el-icon>
                                        </span>
                                    </div>
                                </div>
                                <div v-else style="text-align: right; color: #909399">{{ scope.row.value }}</div>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="14" :md="15" :lg="17" :xl="18">
                <el-card shadow="never">
                    <template #header>
                        <div class="card-header">
                            <span>基本资料</span>
                        </div>
                    </template>
                    <el-tabs class="usr_tabs">
                        <el-tab-pane label="基本资料" v-loading="editUserIng">
                            <el-form :model="editUserForm" :rules="editUserFormRules" ref="editUserFormRef"
                                     label-width="100px">
                                <el-form-item label="用户名" prop="userName">
                                    <el-input disabled v-model="editUserForm.userName" style="width: 250px"/>
                                    <el-tooltip>
                                        <template #content>用户名为登录账号，<br/>系统唯一，不能重复；<br/>不能被修改，请谨慎设置。
                                        </template>
                                        <el-icon :size="18" color="#b1b3b8">
                                            <Warning/>
                                        </el-icon>
                                    </el-tooltip>
                                </el-form-item>
                                <el-form-item label="用户昵称" prop="userNick">
                                    <el-input v-model="editUserForm.userNick" maxlength="16"
                                              placeholder="请输入用户昵称" style="width: 250px"
                                              clearable/>
                                </el-form-item>
                                <el-form-item label="手机号" prop="phone">
                                    <el-input v-model="editUserForm.phone" maxlength="20" placeholder="请输入手机号"
                                              style="width: 250px"
                                              clearable/>
                                </el-form-item>
                                <el-form-item label="邮箱" prop="email">
                                    <el-input v-model="editUserForm.email" maxlength="30" placeholder="请输入邮箱地址"
                                              style="width: 250px"
                                              clearable/>
                                </el-form-item>
                                <el-form-item label="性别" prop="sex">
                                    <el-select v-model="editUserForm.sex" size="default" placeholder="选择性别"
                                               clearable
                                               style="width: 180px">
                                        <el-option label="女" :value="0"/>
                                        <el-option label="男" :value="1"/>
                                        <el-option label="未知" :value="-1"/>
                                    </el-select>
                                </el-form-item>
                                <el-form-item>
                                    <el-button type="primary" @click="editUser">保存</el-button>
                                </el-form-item>
                            </el-form>
                        </el-tab-pane>
                        <el-tab-pane label="密码修改" v-loading="editUserRestPwdFormIng">
                            <el-form :model="editUserRestPwdForm" :rules="editUserRestPwdFormRules"
                                     ref="editUserRestPwdFormRef" label-width="100px">
                                <el-form-item label="老密码" prop="oldPwd">
                                    <el-input v-model="editUserRestPwdForm.oldPwd" maxlength="32"
                                              type="password"
                                              placeholder="请输入老密码" style="width: 250px"
                                              show-password
                                              clearable/>
                                </el-form-item>
                                <el-form-item label="新密码" prop="newPwd">
                                    <el-input v-model="editUserRestPwdForm.newPwd" maxlength="32"
                                              type="password"
                                              placeholder="请输入新密码" style="width: 250px"
                                              show-password
                                              clearable/>
                                </el-form-item>
                                <el-form-item label="确认密码" prop="confirmPwd">
                                    <el-input v-model="editUserRestPwdForm.confirmPwd" maxlength="32"
                                              type="password"
                                              placeholder="请再次输入新密码" style="width: 250px"
                                              show-password
                                              clearable/>
                                </el-form-item>
                                <el-form-item>
                                    <el-button type="primary" @click="editUserRestPwd">保存</el-button>
                                </el-form-item>
                            </el-form>
                        </el-tab-pane>
                    </el-tabs>
                </el-card>
            </el-col>
        </el-row>
        <!-- 头像修改裁剪 -->
        <el-dialog class="edit_avatar_dialog" title="头像编辑" :close-on-click-modal="false"
                   v-model="editAvatarDialogVisible"
                   width="600">
            <UsrImgCutter
                :box-width="300"
                :box-height="300"
                :cut-width="150"
                :cut-height="150"
                @onCancel="editAvatarDialogVisible = false"
                @onConfirm="editAvatarOnConfirm"/>
        </el-dialog>
    </div>
</template>

<style scoped>
.page {
    padding: 20px;
}

.avatar_row {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 20px;
}

.avatar_panel {
    position: relative;
}

.avatar_panel .mask {
    cursor: pointer;
    position: absolute;
    top: 2px;
    left: 2px;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 96px;
    height: 96px;
    border-radius: 50%;
    background-color: var(--el-overlay-color-lighter);
    opacity: 0;
    transition: opacity var(--el-transition-duration)
}

.avatar_panel:hover .mask {
    opacity: 1;
}

:deep(.no-hover-background) .el-table__body-wrapper tbody tr:hover td {
    background-color: transparent !important;
}

.usr_row .el-col {
    margin-bottom: 20px;
}

.usr_row .el-col:last-child {
    margin-bottom: 0;
}

:deep(.usr_tabs) .el-tabs__content {
    padding-top: 20px;
    padding-bottom: 10px;
}

:deep(.usr_tabs) .el-form .el-form-item__content {
    column-gap: 10px;
}

.el-form-item:last-child {
    margin-bottom: 0;
}

</style>