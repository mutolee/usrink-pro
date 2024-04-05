<script setup>
import {onMounted, ref, watch} from "vue";
import httpUtil from "@/utils/HttpUtil";
import {Warning} from "@element-plus/icons-vue";
import loginUtil from "@/utils/LoginUtil";

const queryForm = ref({
    userId: '',
    userName: '',
    userNick: '',
    phone: '',
    userRoleId: '',
    status: '0',
    pageNum: 1,
    pageSize: 10
})
const loading = ref(false)
const total = ref(0)
const userList = ref([])

onMounted(() => {
    // 查询角色列表
    selectRoleListData()
    // 查询用户列表
    selectUserListData()
})

const roleListLoading = ref(false)
const roleNameFilterText = ref('')
const roleListTreeRef = ref(null)
const roleList = ref([])

/**
 * 查询角色列表
 */
const selectRoleListData = () => {
    roleListLoading.value = true
    // 获取角色列表
    httpUtil.get("/sysRole/selectSysRoleCommonList").then(res => {
        roleList.value = [{
            roleId: '',
            roleName: '全部角色',
            children: res.data.list || []
        }]
    }).catch(err => {
        console.error(err)
    }).finally(() => {
        roleListLoading.value = false
    })
}

/**
 * 查询用户列表
 */
const selectUserListData = () => {
    loading.value = true
    // 获取用户列表
    httpUtil.post("/sysUser/selectSysUserList", {...queryForm.value}).then(res => {
        userList.value = res.data.list || []
        total.value = res.data.total
    }).catch(err => {
        console.error(err)
    }).finally(() => {
        loading.value = false
    })
}

// 角色列表过滤，监听关键字变化
watch(roleNameFilterText, (val) => {
    roleListTreeRef.value.filter(val)
})

/**
 * 角色列表过滤
 * @param value 关键字
 * @param node TreeNode
 * @returns {boolean}
 */
const filterRoleListNode = (value, node) => {
    if (!value) return true
    return node.roleName.indexOf(value) !== -1
}

/**
 * 角色点击事件
 * @param treeData 角色树数据
 */
const roleTreeClick = (treeData) => {
    queryForm.value.userRoleId = treeData.roleId
    selectUserListData()
}

/**
 * 分页查询
 * @param val 当前页码
 */
const handleCurrentChange = (val) => {
    queryForm.value.pageNum = val
    selectUserListData()
}

const addUserForm = ref({})
const addUserDialogVisible = ref(false)
const addUserDialogIng = ref(false)
const addUserIng = ref(false)
const addUserFormRef = ref(null)

/**
 * 校验用户名是否存在
 * @param rule 规则
 * @param value 用户名
 * @param callback 回调
 */
const validatorUserName = (rule, value, callback) => {
    if (value === '' || value === undefined || value === null) {
        callback(new Error('请输入用户名'))
    } else {
        // 校验用户名是否存在
        httpUtil.get("/sysUser/checkUserNameUnique?userName=" + value).then(res => {
            if (!res.data.valid) {
                callback(new Error('用户名已存在'))
            } else {
                callback()
            }
        }).catch(err => {
            console.error(err)
        })
    }
}

const userAddFormRules = ref({
    userName: [
        {required: true, validator: validatorUserName, trigger: 'blur'}
    ],
    userNick: [
        {required: true, message: '请输入用户昵称', trigger: 'blur'}
    ],
    userPassword: [
        {required: true, message: '请输入密码', trigger: 'blur'}
    ]
})

const userEditFormRules = ref({
    userNick: [
        {required: true, message: '请输入用户昵称', trigger: 'blur'}
    ]
})

/**
 * 添加用户Dialog
 */
const addUserDialog = () => {
    // 清空表单
    addUserForm.value = {}
    // 设置默认密码
    addUserForm.value.userPassword = '123456'
    addUserDialogVisible.value = true
    if (addUserFormRef.value) {
        addUserFormRef.value.resetFields()
    }
}

/**
 * 添加用户
 */
const addUser = () => {
    addUserFormRef.value.validate((valid) => {
        if (valid) {
            addUserDialogIng.value = true
            addUserIng.value = true
            // 密码加密
            addUserForm.value.userPassword = loginUtil.encryptPassword(addUserForm.value.userPassword)
            httpUtil.post("/sysUser/addSysUser", {...addUserForm.value}).then(res => {
                // 刷新用户列表
                selectUserListData()
            }).catch(err => {
                console.error(err)
            }).finally(() => {
                addUserIng.value = false
                addUserDialogIng.value = false
                addUserDialogVisible.value = false
            })
        }
    })
}

const editUserForm = ref({})
const editUserDialogVisible = ref(false)
const editUserDialogIng = ref(false)
const editUserIng = ref(false)
const editUserFormRef = ref(null)

/**
 * 编辑用户Dialog
 * @param row
 */
const editUserDialog = (row) => {
    // 先清空表单
    editUserForm.value = {}
    editUserForm.value = {...row}
    editUserDialogVisible.value = true
    if (editUserFormRef.value) {
        editUserFormRef.value.resetFields()
    }
}

/**
 * 编辑用户
 */
const editUser = () => {
    editUserFormRef.value.validate((valid) => {
        if (valid) {
            editUserDialogIng.value = true
            editUserIng.value = true
            httpUtil.post("/sysUser/updateSysUser", {...editUserForm.value}).then(res => {
                // 刷新用户列表
                selectUserListData()
            }).catch(err => {
                console.error(err)
            }).finally(() => {
                editUserIng.value = false
                editUserDialogIng.value = false
                editUserDialogVisible.value = false
            })
        }
    })
}

const restorePasswordTips = ref('')
const restorePasswordDialogVisible = ref(false)
const restorePasswordIng = ref(false)

/**
 * 重置密码Dialog
 * @param row
 */
const restorePasswordDialog = (row) => {
    editUserForm.value = {} // 清空更新表单
    editUserForm.value.userId = row.userId
    restorePasswordTips.value = `确定重置用户【${row.roleName} - ${row.userNick}】的密码吗？<br />重置后密码为：123456`
    restorePasswordDialogVisible.value = true
}

/**
 * 重置密码
 */
const restorePassword = () => {
    restorePasswordIng.value = true
    httpUtil.post("/sysUser/resetUserPwd", {...editUserForm.value}).then(res => {
        // 刷新用户列表
        selectUserListData()
    }).catch(err => {
        console.error(err)
    }).finally(() => {
        restorePasswordIng.value = false
        restorePasswordDialogVisible.value = false
    })
}

const distributeRoleDialogVisible = ref(false)
const distributeRoleDialogIng = ref(false)
const distributeRoleIng = ref(false)
const distributeRoleList = ref([])

/**
 * 分配角色Dialog
 * @param row
 */
const distributeRoleDialog = (row) => {
    editUserForm.value = {} // 清空更新表单
    editUserForm.value.userId = row.userId
    editUserForm.value.userRoleId = row.userRoleId
    distributeRoleDialogVisible.value = true
    distributeRoleDialogIng.value = true
    // 获取角色列表
    httpUtil.get("/sysRole/selectSysRoleCommonList").then(res => {
        distributeRoleList.value = res.data.list || []
    }).catch(err => {
        console.error(err)
    }).finally(() => {
        distributeRoleDialogIng.value = false
    })
}

/**
 * 分配角色
 */
const distributeRole = () => {
    distributeRoleIng.value = true
    distributeRoleDialogIng.value = true
    httpUtil.post("/sysUser/updateSysUserRole", {...editUserForm.value}).then(res => {
        // 刷新用户列表
        selectUserListData()
    }).catch(err => {
        console.error(err)
    }).finally(() => {
        distributeRoleIng.value = false
        distributeRoleDialogIng.value = false
        distributeRoleDialogVisible.value = false
    })
}

const deleteLogicTips = ref('')
const deleteLogicDialogVisible = ref(false)
const deleteLogicIng = ref(false)

/**
 * 逻辑删除用户Dialog
 * @param row
 */
const deleteLogicRoleDialog = (row) => {
    editUserForm.value = {} // 清空更新表单
    editUserForm.value.userId = row.userId
    deleteLogicTips.value = `确定删除用户【${row.roleName} - ${row.userNick}】吗？`
    deleteLogicDialogVisible.value = true
}

/**
 * 逻辑删除用户
 */
const deleteLogicRole = () => {
    deleteLogicIng.value = true
    httpUtil.post("/sysUser/deleteSysUserLogic", {...editUserForm.value}).then(res => {
        // 刷新用户列表
        selectUserListData()
    }).catch(err => {
        console.error(err)
    }).finally(() => {
        deleteLogicIng.value = false
        deleteLogicDialogVisible.value = false
    })
}

const restoreTips = ref('')
const restoreDialogVisible = ref(false)
const restoreIng = ref(false)

/**
 * 恢复用户Dialog
 * @param row
 */
const restoreUserDialog = (row) => {
    editUserForm.value = {} // 清空更新表单
    editUserForm.value.userId = row.userId
    restoreTips.value = `确定恢复用户【${row.roleName} - ${row.userNick}】吗？`
    restoreDialogVisible.value = true
}

/**
 * 恢复用户
 */
const restoreUser = () => {
    restoreIng.value = true
    httpUtil.post("/sysUser/recoverSysUser", {...editUserForm.value}).then(res => {
        // 刷新用户列表
        selectUserListData()
    }).catch(err => {
        console.error(err)
    }).finally(() => {
        restoreIng.value = false
        restoreDialogVisible.value = false
    })
}

const deleteTips = ref('')
const deleteDialogVisible = ref(false)
const deleteIng = ref(false)

/**
 * 物理删除用户Dialog
 * @param row
 */
const deleteRoleDialog = (row) => {
    editUserForm.value = {} // 清空更新表单
    editUserForm.value.userId = row.userId
    deleteTips.value = `确定彻底删除用户【${row.roleName} - ${row.userNick}】吗？删除后不可恢复！`
    deleteDialogVisible.value = true
}

/**
 * 物理删除删除用户
 */
const deleteRole = () => {
    deleteIng.value = true
    httpUtil.post("/sysUser/deleteSysUserPhysics", {...editUserForm.value}).then(res => {
        // 刷新用户列表
        selectUserListData()
    }).catch(err => {
        console.error(err)
    }).finally(() => {
        deleteIng.value = false
        deleteDialogVisible.value = false
    })
}


</script>

<template>
    <div class="page">
        <el-card shadow="never" class="usr_card_override top">
            <el-form :inline="true" :model="queryForm">
                <el-form-item label="用户ID">
                    <el-input v-model="queryForm.userId" placeholder="用户ID" clearable style="width: 180px"/>
                </el-form-item>
                <el-form-item label="用户名">
                    <el-input v-model="queryForm.userName" placeholder="用户名" clearable style="width: 180px"/>
                </el-form-item>
                <el-form-item label="用户昵称">
                    <el-input v-model="queryForm.userNick" placeholder="用户昵称" clearable style="width: 180px"/>
                </el-form-item>
                <el-form-item label="手机号">
                    <el-input v-model="queryForm.phone" placeholder="手机号" clearable style="width: 180px"/>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="queryForm.email" placeholder="邮箱地址" clearable style="width: 180px"/>
                </el-form-item>
                <el-form-item label="状态">
                    <el-select v-model="queryForm.status" size="default" placeholder="选择状态" clearable
                               style="width: 180px">
                        <el-option label="正常" value="0"/>
                        <el-option label="禁用" value="-1"/>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" plain @click="selectUserListData">搜索</el-button>
                </el-form-item>
            </el-form>
        </el-card>
        <div class="top_btn_panel">
            <el-button type="primary" @click="addUserDialog">添加用户</el-button>
        </div>
        <div class="usr_user_panel">
            <!-- 角色列表 -->
            <div class="usr_user_panel_left">
                <el-card v-loading="roleListLoading" shadow="never">
                    <el-input v-model="roleNameFilterText" placeholder="关键字" clearable style="width: 180px"/>
                    <div class="user_role_list_panel">
                        <el-scrollbar>
                            <el-tree
                                ref="roleListTreeRef"
                                class="usr_role_tree"
                                :data="roleList"
                                :expand-on-click-node="false"
                                highlight-current
                                :props="{label: 'roleName', children: 'children'}"
                                default-expand-all
                                :filter-node-method="filterRoleListNode"
                                @node-click="roleTreeClick"
                            />
                        </el-scrollbar>
                    </div>
                </el-card>
            </div>
            <!-- 用户列表 -->
            <div class="usr_user_panel_right">
                <el-card v-loading="loading" shadow="never" class="usr_card_override content">
                    <el-table
                        class="user_table"
                        :data="userList"
                        row-key="roleId">
                        <el-table-column prop="userId" label="用户ID" width="120" align="center">
                            <template #default="scope">
                                {{ scope.row.userId }}
                            </template>
                        </el-table-column>
                        <el-table-column prop="userName" label="用户名" width="150">
                            <template #default="scope">
                                {{ scope.row.userName }}
                            </template>
                        </el-table-column>
                        <el-table-column prop="userNick" label="用户昵称" width="150">
                            <template #default="scope">
                                {{ scope.row.userNick }}
                            </template>
                        </el-table-column>
                        <el-table-column prop="roleName" label="角色" width="150">
                            <template #default="scope">
                                {{ scope.row.roleName }}
                            </template>
                        </el-table-column>
                        <el-table-column prop="phone" label="手机号" width="150">
                            <template #default="scope">
                                {{ scope.row.phone }}
                            </template>
                        </el-table-column>
                        <el-table-column prop="email" label="邮箱" width="150">
                            <template #default="scope">
                                {{ scope.row.email }}
                            </template>
                        </el-table-column>
                        <el-table-column label="性别" align="center">
                            <template #default="scope">
                                <el-text v-if="scope.row.sex === 0">女</el-text>
                                <el-text v-else-if="scope.row.sex === 1">男</el-text>
                                <el-text v-else>未知</el-text>
                            </template>
                        </el-table-column>
                        <el-table-column label="状态" align="center">
                            <template #default="scope">
                                <el-tag v-if="scope.row.status === 0">正常</el-tag>
                                <el-tag v-else-if="scope.row.status === -1" type="warning">已删除</el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column fixed="right" label="操作" width="360">
                            <template #default="scope">
                                <div class="action_btn">
                                    <template v-if="scope.row.status === 0">
                                        <el-button type="success" plain @click="editUserDialog(scope.row)">编辑
                                        </el-button>
                                        <el-button type="warning" plain @click="restorePasswordDialog(scope.row)">
                                            重置密码
                                        </el-button>
                                        <el-button type="primary" v-if="scope.row.userId > 1" plain
                                                   @click="distributeRoleDialog(scope.row)">分配角色
                                        </el-button>
                                        <el-button type="danger" v-if="scope.row.userId > 1" plain
                                                   @click="deleteLogicRoleDialog(scope.row)">删除
                                        </el-button>
                                    </template>
                                    <template v-if="scope.row.status === -1">
                                        <el-button type="warning" plain @click="restoreUserDialog(scope.row)">恢复
                                        </el-button>
                                        <el-button type="info" plain @click="deleteRoleDialog(scope.row)">删除
                                        </el-button>
                                    </template>
                                </div>
                            </template>
                        </el-table-column>
                        <template #empty>
                            <el-empty description="暂无数据"/>
                        </template>
                    </el-table>
                    <el-pagination background layout="prev, pager, next" :current-page="queryForm.pageNum"
                                   @current-change="handleCurrentChange" :page-size="queryForm.pageSize"
                                   :total="total"/>
                </el-card>
            </div>
        </div>
        <!-- 添加用户 -->
        <el-dialog class="add_user_dialog" title="添加用户" :close-on-click-modal="false" v-model="addUserDialogVisible"
                   width="600">
            <div class="add_user_form" v-loading="addUserDialogIng">
                <el-form :model="addUserForm" :rules="userAddFormRules" ref="addUserFormRef" label-width="100px">
                    <el-form-item label="用户名" prop="userName">
                        <el-input v-model="addUserForm.userName" maxlength="15" placeholder="请输入用户名"
                                  style="width: 250px"
                                  clearable/>
                        <el-tooltip>
                            <template #content>用户名为登录账号，<br/>系统唯一，不能重复；<br/>不能被修改，请谨慎设置。
                            </template>
                            <el-icon :size="18" color="#b1b3b8">
                                <Warning/>
                            </el-icon>
                        </el-tooltip>
                    </el-form-item>
                    <el-form-item label="用户昵称" prop="userNick">
                        <el-input v-model="addUserForm.userNick" maxlength="16" placeholder="请输入用户昵称"
                                  style="width: 250px"
                                  clearable/>
                    </el-form-item>
                    <el-form-item label="手机号" prop="phone">
                        <el-input v-model="addUserForm.phone" maxlength="20" placeholder="请输入手机号"
                                  style="width: 250px"
                                  clearable/>
                    </el-form-item>
                    <el-form-item label="邮箱" prop="email">
                        <el-input v-model="addUserForm.email" maxlength="30" placeholder="请输入邮箱地址"
                                  style="width: 250px"
                                  clearable/>
                    </el-form-item>
                    <el-form-item label="性别" prop="sex">
                        <el-select v-model="queryForm.sex" size="default" placeholder="选择性别" clearable
                                   style="width: 180px">
                            <el-option label="女" value="0"/>
                            <el-option label="男" value="1"/>
                            <el-option label="未知" value="-1"/>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input type="password" v-model="addUserForm.userPassword" placeholder="请输入密码"
                                  style="width: 250px" show-password clearable/>
                    </el-form-item>
                </el-form>
            </div>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="addUserDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="addUser" :loading="addUserIng">确定</el-button>
                </div>
            </template>
        </el-dialog>
        <!-- 编辑用户 -->
        <el-dialog class="edit_user_dialog" title="编辑用户" :close-on-click-modal="false"
                   v-model="editUserDialogVisible"
                   width="600">
            <div class="edit_user_form" v-loading="editUserDialogIng">
                <el-form :model="editUserForm" :rules="userEditFormRules" ref="editUserFormRef" label-width="100px">
                    <el-form-item label="用户名" prop="userName">
                        <el-input v-model="editUserForm.userName" disabled maxlength="15" placeholder="请输入用户名"
                                  style="width: 250px"
                                  clearable/>
                        <el-tooltip>
                            <template #content>用户名为登录账号，<br/>系统唯一，不能重复；<br/>不能被修改，请谨慎设置。
                            </template>
                            <el-icon :size="18" color="#b1b3b8">
                                <Warning/>
                            </el-icon>
                        </el-tooltip>
                    </el-form-item>
                    <el-form-item label="用户昵称" prop="userNick">
                        <el-input v-model="editUserForm.userNick" maxlength="16" placeholder="请输入用户昵称"
                                  style="width: 250px"
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
                        <el-select v-model="editUserForm.sex" size="default" placeholder="选择性别" clearable
                                   style="width: 180px">
                            <el-option label="女" :value="0"/>
                            <el-option label="男" :value="1"/>
                            <el-option label="未知" :value="-1"/>
                        </el-select>
                    </el-form-item>
                </el-form>
            </div>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="editUserDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="editUser" :loading="editUserIng">确定</el-button>
                </div>
            </template>
        </el-dialog>
        <!-- 逻辑删除用户 -->
        <el-dialog title="删除用户" :close-on-click-modal="false" v-model="deleteLogicDialogVisible" width="400">
            <div class="delete_logic_dialog">
                <p>{{ deleteLogicTips }}</p>
            </div>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="deleteLogicDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="deleteLogicRole" :loading="deleteLogicIng">确定</el-button>
                </div>
            </template>
        </el-dialog>
        <!-- 恢复用户 -->
        <el-dialog title="恢复用户" :close-on-click-modal="false" v-model="restoreDialogVisible" width="400">
            <div class="restore_dialog">
                <p>{{ restoreTips }}</p>
            </div>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="restoreDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="restoreUser" :loading="restoreIng">确定</el-button>
                </div>
            </template>
        </el-dialog>
        <!-- 物理删除用户 -->
        <el-dialog title="删除用户" :close-on-click-modal="false" v-model="deleteDialogVisible" width="400">
            <div class="delete_dialog">
                <p>{{ deleteTips }}</p>
            </div>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="deleteDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="deleteRole" :loading="deleteIng">确定</el-button>
                </div>
            </template>
        </el-dialog>
        <!-- 重置密码 -->
        <el-dialog title="重置密码" :close-on-click-modal="false" v-model="restorePasswordDialogVisible" width="400">
            <div class="restore_password_dialog">
                <p v-html="restorePasswordTips"></p>
            </div>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="restorePasswordDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="restorePassword" :loading="restorePasswordIng">确定</el-button>
                </div>
            </template>
        </el-dialog>
        <!-- 分配角色 -->
        <el-dialog title="分配角色" :close-on-click-modal="false" v-model="distributeRoleDialogVisible" width="400">
            <div class="distribute_role_dialog" v-loading="distributeRoleDialogIng">
                <el-select v-model="editUserForm.userRoleId" placeholder="请选择角色" style="width: 250px">
                    <el-option v-for="item in distributeRoleList" :key="item.roleId" :label="item.roleName"
                               :value="item.roleId"/>
                </el-select>
            </div>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="distributeRoleDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="distributeRole" :loading="distributeRoleIng">确定</el-button>
                </div>
            </template>
        </el-dialog>
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
    flex-wrap: wrap;
    column-gap: 10px;
    row-gap: 10px;
    margin: 15px 0 15px 0;
}

.top_btn_panel .el-button + .el-button {
    margin-left: 0;
}

.usr_user_panel {
    display: flex;
    column-gap: 20px;
}

.usr_user_panel_left {
    min-width: 220px;
}

.user_role_list_panel {
    margin-top: 20px;
    height: 250px;
}

:deep(.usr_role_tree) .el-tree-node__content {
    height: 35px;
}

:deep(.usr_role_tree).el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
    background-color: #cce9ff;
}

.usr_user_panel_right {
    width: 0;
    flex: 1;
}

.usr_card_override.content .el-table {
    margin-bottom: 20px;
}

:deep(.user_table) .action_btn {
    display: flex;
    flex-wrap: wrap;
    column-gap: 10px;
}

:deep(.user_table) .action_btn .el-button + .el-button {
    margin-left: 0;
}

:deep(.add_user_dialog) .el-form .el-form-item__content,
:deep(.edit_user_dialog) .el-form .el-form-item__content {
    column-gap: 10px;
}


</style>