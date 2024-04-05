<script setup>
import {onMounted, ref} from "vue";
import stringUtil from "@/utils/StringUtil";
import httpUtil from "@/utils/HttpUtil";
import TreeUtil from "@/utils/TreeUtil";

const queryForm = ref({
    roleName: '',
    rolePermKey: '',
    status: '0',
    pageNum: 1,
    pageSize: 10
})
const loading = ref(false)
const roleList = ref([])
const total = ref(0)

onMounted(() => {
    // 初始化角色列表
    selectRoleListData()
})

/**
 * 查询菜单列表
 */
const selectRoleListData = () => {
    loading.value = true
    httpUtil.post("/sysRole/selectSysRoleList", {...queryForm.value}).then(res => {
        roleList.value = res.data.list || [];
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
    selectRoleListData()
}

/**
 * 搜索角色
 */
const queryRoleListData = () => {
    // 重置页码
    queryForm.value.pageNum = 1
    selectRoleListData()
}

const addRoleForm = ref({})
const addRoleDialogVisible = ref(false)
const addRoleDialogIng = ref(false)
const addRoleIng = ref(false)
const addFormRef = ref(null)
const roleFormRules = ref({
    roleName: [
        {required: true, message: '角色名称不能为空！', trigger: 'blur'}
    ],
    rolePermKey: [
        {required: true, message: '角色权限符不能为空！', trigger: 'blur'}
    ]
});

/**
 * 添加角色Dialog
 */
const addRoleDialog = () => {
    // 清空添加表单
    addRoleForm.value = {}
    addRoleDialogVisible.value = true
    if (addFormRef.value) {
        addFormRef.value.resetFields()
    }
}

/**
 * 添加角色
 */
const addRole = () => {
    addFormRef.value.validate((valid) => {
        if (valid) {
            addRoleDialogIng.value = true
            addRoleIng.value = true
            httpUtil.post("/sysRole/insertSysRole", {...addRoleForm.value}).then(res => {
                // 刷新角色列表
                selectRoleListData()
            }).catch(err => {
                console.error(err)
            }).finally(() => {
                addRoleDialogIng.value = false
                addRoleIng.value = false
                addRoleDialogVisible.value = false
            })
        }
    })
}

const editRoleForm = ref({})
const editRoleDialogVisible = ref(false)
const editRoleDialogIng = ref(false)
const editRoleIng = ref(false)
const editFormRef = ref(null)

/**
 * 编辑角色Dialog
 * @param row
 */
const editRoleDialog = (row) => {
    // 先清空表单
    editRoleForm.value = {}
    editRoleForm.value = {...row}
    editRoleDialogVisible.value = true
    if (editFormRef.value) {
        editFormRef.value.resetFields()
    }
}

/**
 * 编辑菜单
 */
const editRole = () => {
    editFormRef.value.validate((valid) => {
        if (valid) {
            editRoleDialogIng.value = true
            editRoleIng.value = true
            httpUtil.post("/sysRole/updateSysRole", {...editRoleForm.value}).then(res => {
                // 刷新角色列表
                selectRoleListData()
            }).catch(err => {
                console.error(err)
            }).finally(() => {
                editRoleDialogIng.value = false
                editRoleIng.value = false
                editRoleDialogVisible.value = false
            })
        }
    })
}

const permRoleTree = ref({})
const availableMenuList = ref([])
const permRoleDialogTitle = ref('')
const permRoleDialogVisible = ref(false)
const permRoleDialogIng = ref(false)
const permRoleIng = ref(false)

/**
 * 分配权限Dialog
 * @param row
 */
const permRoleDialog = (row) => {
    // 先清空表单
    editRoleForm.value = {}
    editRoleForm.value = {
        roleId: row.roleId
    }
    permRoleDialogTitle.value = `角色【${row.roleName}】权限分配`
    permRoleDialogVisible.value = true
    permRoleDialogIng.value = true
    // 查询可用菜单列表
    httpUtil.get("/sysMenu/selectSysMenuCommonList").then(res => {
        let menuListData = res.data.list || []
        // 构建菜单树
        availableMenuList.value = TreeUtil.buildMenuTree(menuListData)
        // 把树形菜单扁平化
        let menuFlatList = TreeUtil.treeToFlat(availableMenuList.value)
        // 获取角色拥有的权限的最底层节点(过滤掉父节点)，
        // 为什么要获取最底层节点，因为数据库中角色的权限是包括父节点的，如果把父节点设置为选中状态，那么子节点会全部选中
        // 但是如果只有部分子节点的权限，那么也会因为父节点的选中状态而全部选中
        let permMenuIds = stringUtil.stringToArray(row.roleMenuIds);

        // 默认选中的节点
        let defaultCheckedKeys = []
        menuFlatList.forEach(item => {
            // if ((!item.children || item.children.length === 0) && permMenuIds.includes(item.menuId.toString())) {
            //     defaultCheckedKeys.push(item.menuId)
            // }
            if (permMenuIds.includes(item.menuId.toString())) {
                defaultCheckedKeys.push(item.menuId)
            }
        })
        // 设置选中状态
        permRoleTree.value.setCheckedKeys(defaultCheckedKeys)
    }).catch(err => {
        console.error(err)
    }).finally(() => {
        permRoleDialogIng.value = false
    })
}

/**
 * 分配权限
 */
const permRole = () => {
    permRoleDialogIng.value = true
    permRoleIng.value = true
    // 全部选中的节点
    let checkedKeys = permRoleTree.value.getCheckedKeys()
    // 半选中的节点
    let halfCheckedKeys = permRoleTree.value.getHalfCheckedKeys()
    // 合并选中的节点
    let allCheckedKeys = checkedKeys.concat(halfCheckedKeys)
    // allCheckedKeys 正序排序
    allCheckedKeys.sort((a, b) => a - b)
    editRoleForm.value.roleMenuIds = stringUtil.arrayToString(allCheckedKeys)
    httpUtil.post("/sysRole/updateRolePerm", {...editRoleForm.value}).then(res => {
        // 刷新角色列表
        selectRoleListData()
    }).catch(err => {
        console.error(err)
    }).finally(() => {
        permRoleDialogIng.value = false
        permRoleIng.value = false
        permRoleDialogVisible.value = false
    })
}

const deleteLogicTips = ref('')
const deleteLogicDialogVisible = ref(false)
const deleteLogicIng = ref(false)

/**
 * 逻辑删除角色Dialog
 * @param row
 */
const deleteLogicRoleDialog = (row) => {
    editRoleForm.value = {} // 清空更新表单
    editRoleForm.value.roleId = row.roleId
    deleteLogicTips.value = `确定删除角色【${row.roleName}】吗？`
    deleteLogicDialogVisible.value = true
}

/**
 * 逻辑删除角色
 */
const deleteLogicRole = () => {
    deleteLogicIng.value = true
    httpUtil.post("/sysRole/deleteSysRoleLogic", {...editRoleForm.value}).then(res => {
        // 刷新角色列表
        selectRoleListData()
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
 * 恢复角色Dialog
 * @param row
 */
const restoreRoleDialog = (row) => {
    editRoleForm.value = {} // 清空更新表单
    editRoleForm.value.roleId = row.roleId
    restoreTips.value = `确定恢复角色【${row.roleName}】吗？`
    restoreDialogVisible.value = true
}

/**
 * 恢复角色
 */
const restoreRole = () => {
    restoreIng.value = true
    httpUtil.post("/sysRole/recoverSysRole", {...editRoleForm.value}).then(res => {
        // 刷新角色列表
        selectRoleListData()
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
 * 物理删除角色Dialog
 * @param row
 */
const deleteRoleDialog = (row) => {
    editRoleForm.value = {} // 清空更新表单
    editRoleForm.value.roleId = row.roleId
    deleteTips.value = `确定彻底删除角色【${row.roleName}】吗？删除之后将无法恢复！`
    deleteDialogVisible.value = true
}

/**
 * 物理删除角色
 */
const deleteRole = () => {
    deleteIng.value = true
    httpUtil.post("/sysRole/deleteSysRolePhysics", {...editRoleForm.value}).then(res => {
        // 刷新角色列表
        selectRoleListData()
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
                <el-form-item label="角色名称">
                    <el-input v-model="queryForm.roleName" maxlength="10" placeholder="角色名称" clearable
                              style="width: 180px"/>
                </el-form-item>
                <el-form-item label="角色标识符">
                    <el-input v-model="queryForm.rolePermKey" maxlength="10" placeholder="角色标识符" clearable
                              style="width: 180px"/>
                </el-form-item>
                <el-form-item label="状态">
                    <el-select v-model="queryForm.status" size="default" placeholder="选择状态" clearable
                               style="width: 180px">
                        <el-option label="正常" value="0"/>
                        <el-option label="已删除" value="-1"/>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" plain @click="queryRoleListData">搜索</el-button>
                </el-form-item>
            </el-form>
        </el-card>
        <div class="top_btn_panel">
            <el-button type="primary" @click="addRoleDialog">添加角色</el-button>
        </div>
        <el-card v-loading="loading" shadow="never" class="usr_card_override content">
            <el-table
                class="role_table"
                :data="roleList"
                row-key="roleId">
                <el-table-column label="角色ID" width="100" align="center">
                    <template #default="scope">
                        {{ scope.row.roleId }}
                    </template>
                </el-table-column>
                <el-table-column label="角色名称" width="200">
                    <template #default="scope">
                        {{ scope.row.roleName }}
                    </template>
                </el-table-column>
                <el-table-column label="角色标识符" width="200">
                    <template #default="scope">
                        {{ scope.row.rolePermKey }}
                    </template>
                </el-table-column>
                <el-table-column label="角色描述" width="300">
                    <template #default="scope">
                        <div class="role_desc">
                            <el-tooltip placement="top">
                                <template #content>
                                    <div v-html="stringUtil.addLineBreaks(scope.row.roleDesc,25)"></div>
                                </template>
                                {{ scope.row.roleDesc }}
                            </el-tooltip>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="状态" align="center">
                    <template #default="scope">
                        <el-tag v-if="scope.row.status === 0">正常</el-tag>
                        <el-tag v-else-if="scope.row.status === -1" type="warning">已删除</el-tag>
                    </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="280">
                    <template #default="scope">
                        <div class="action_btn">
                            <template v-if="scope.row.status === 0">
                                <el-button type="primary" plain @click="permRoleDialog(scope.row)">分配权限</el-button>
                                <el-button type="success" plain @click="editRoleDialog(scope.row)">编辑</el-button>
                                <el-button type="danger" v-if="scope.row.roleId > 3" plain
                                           @click="deleteLogicRoleDialog(scope.row)">删除
                                </el-button>
                            </template>
                            <template v-if="scope.row.status === -1">
                                <el-button type="warning" plain @click="restoreRoleDialog(scope.row)">恢复</el-button>
                                <el-button type="info" plain @click="deleteRoleDialog(scope.row)">删除</el-button>
                            </template>
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
        <!-- 添加角色Dialog -->
        <el-dialog class="add_role_dialog" :close-on-click-modal="false" v-model="addRoleDialogVisible" title="添加角色"
                   width="600">
            <div class="add_role_form" v-loading="addRoleDialogIng">
                <el-form :model="addRoleForm" ref="addFormRef" :rules="roleFormRules" label-width="100px">
                    <el-form-item label="角色名称" prop="roleName">
                        <el-input v-model="addRoleForm.roleName" maxlength="10" placeholder="角色名称"
                                  style="width: 250px" clearable/>
                    </el-form-item>
                    <el-form-item label="权限标识符" prop="rolePermKey">
                        <el-input v-model="addRoleForm.rolePermKey" maxlength="30" placeholder="权限标识符"
                                  style="width: 250px" clearable/>
                    </el-form-item>
                    <el-form-item label="角色描述" prop="roleDesc">
                        <el-input v-model="addRoleForm.roleDesc" type="textarea" maxlength="50" rows="4"
                                  placeholder="角色描述" style="width: 320px"/>
                    </el-form-item>
                </el-form>
            </div>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="addRoleDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="addRole" :loading="addRoleIng">确定</el-button>
                </div>
            </template>
        </el-dialog>
        <!-- 编辑角色Dialog -->
        <el-dialog class="edit_role_dialog" :close-on-click-modal="false" v-model="editRoleDialogVisible"
                   title="编辑角色" width="600">
            <div class="edit_role_form" v-loading="editRoleDialogIng">
                <el-form :model="editRoleForm" ref="editFormRef" :rules="roleFormRules" label-width="100px">
                    <el-form-item label="角色名称" prop="roleName">
                        <el-input v-model="editRoleForm.roleName" maxlength="10" placeholder="角色名称"
                                  style="width: 250px" clearable/>
                    </el-form-item>
                    <el-form-item label="权限标识符" prop="rolePermKey">
                        <el-input v-model="editRoleForm.rolePermKey" maxlength="30" placeholder="权限标识符"
                                  style="width: 250px" clearable/>
                    </el-form-item>
                    <el-form-item label="角色描述" prop="roleDesc">
                        <el-input v-model="editRoleForm.roleDesc" type="textarea" maxlength="50" rows="4"
                                  placeholder="角色描述" style="width: 320px"/>
                    </el-form-item>
                </el-form>
            </div>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="editRoleDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="editRole" :loading="editRoleIng">确定</el-button>
                </div>
            </template>
        </el-dialog>
        <!-- 逻辑删除菜单Dialog -->
        <el-dialog v-model="deleteLogicDialogVisible" title="删除确认" width="500">
            <span>{{ deleteLogicTips }}</span>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="deleteLogicDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="deleteLogicRole" :loading="deleteLogicIng">确定</el-button>
                </div>
            </template>
        </el-dialog>
        <!-- 恢复菜单Dialog -->
        <el-dialog v-model="restoreDialogVisible" title="恢复确认" width="500">
            <span>{{ restoreTips }}</span>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="restoreDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="restoreRole" :loading="restoreIng">确定</el-button>
                </div>
            </template>
        </el-dialog>
        <!-- 物理删除菜单Dialog -->
        <el-dialog v-model="deleteDialogVisible" title="彻底删除" width="500">
            <span>{{ deleteTips }}</span>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="deleteDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="deleteRole" :loading="deleteIng">确定</el-button>
                </div>
            </template>
        </el-dialog>
        <!-- 分配权限Dialog -->
        <el-drawer class="usr_drawer_role_perm" v-model="permRoleDialogVisible" direction="rtl" size="350">
            <template #header>
                <h4>{{ permRoleDialogTitle }}</h4>
            </template>
            <template #default>
                <div class="usr_role_perm_panel" v-loading="permRoleDialogIng">
                    <el-scrollbar>
                        <el-tree
                            ref="permRoleTree"
                            :data="availableMenuList"
                            :props="{label: 'menuName', children: 'children'}"
                            show-checkbox
                            :check-strictly="true"
                            default-expand-all
                            node-key="menuId"
                        />
                    </el-scrollbar>
                </div>
            </template>
            <template #footer>
                <div style="flex: auto">
                    <el-button @click="permRoleDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="permRole" :loading="permRoleIng">确定</el-button>
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
    flex-wrap: wrap;
    column-gap: 10px;
    row-gap: 10px;
    margin: 15px 0 15px 0;
}

.top_btn_panel .el-button + .el-button {
    margin-left: 0;
}

.usr_card_override.content .el-table {
    margin-bottom: 20px;
}

:deep(.role_table) .role_desc {
    overflow: hidden; /* 超出部分隐藏 */
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 1; /* 设置行数 */
}

:deep(.role_table) .action_btn {
    display: flex;
    flex-wrap: wrap;
    column-gap: 10px;
}

:deep(.role_table) .action_btn .el-button + .el-button {
    margin-left: 0;
}

:deep(.add_role_dialog) .el-form .el-form-item__content,
:deep(.edit_role_dialog) .el-form .el-form-item__content {
    column-gap: 10px;
}

:deep(.usr_drawer_role_perm) .el-drawer__header {
    padding: 20px;
    margin-bottom: 0;
}

:deep(.usr_drawer_role_perm) .el-drawer__body {
    padding: 0;
    overflow: hidden;
}

:deep(.usr_drawer_role_perm) .el-drawer__footer {
    padding-top: 20px;
}

.usr_role_perm_panel {
    height: calc(100vh - 60px - 72px);
}

:deep(.usr_drawer_role_perm) .el-tree {
    margin: 0 15px 0 15px;
}

</style>