package ink.usr.admin.controller;

import com.github.pagehelper.Page;
import ink.usr.admin.service.ShiroService;
import ink.usr.common.core.annotation.Log;
import ink.usr.common.core.domain.Dict;
import ink.usr.common.core.domain.Res;
import ink.usr.common.core.utils.PageUtil;
import ink.usr.common.interfaces.admin.ISysRoleService;
import ink.usr.common.model.mysql.SysRoleModel;
import ink.usr.framework.shiro.domain.ShiroRoleInfo;
import ink.usr.framework.shiro.utils.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/sysRole")
public class SysRoleController {

    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ShiroService shiroService;

    /**
     * 查询角色列表
     *
     * @param sysRoleModel 角色模型
     */
    @Log("查询角色列表")
    @RequestMapping(value = "/selectSysRoleList")
    @RequiresPermissions("sys:role:list")
    public Res selectSysRoleList(SysRoleModel sysRoleModel) {
        // 开启分页
        Page<Object> pages = PageUtil.startPage();
        List<SysRoleModel> sysRoleModels = sysRoleService.selectSysRoleList(sysRoleModel);
        Dict result = Dict.create()
                .set("list", sysRoleModels)
                .set("total", pages.getTotal());
        return Res.success(result);
    }

    /**
     * 查询通用的角色列表，不需要权限，一般用于下拉框
     */
    @Log("查询通用的角色列表")
    @RequestMapping(value = "/selectSysRoleCommonList")
    public Res selectSysRoleCommonList() {
        SysRoleModel sysRoleModel = SysRoleModel.builder().status(0).build();
        List<SysRoleModel> sysRoleModels = sysRoleService.selectSysRoleList(sysRoleModel);
        Dict result = Dict.create()
                .set("list", sysRoleModels);
        return Res.success(result);
    }

    /**
     * 添加角色信息
     *
     * @param sysRoleModel 角色模型
     */
    @Log("添加角色信息")
    @RequestMapping(value = "/insertSysRole")
    @RequiresPermissions("sys:role:add")
    public Res insertSysRole(SysRoleModel sysRoleModel) {
        int resCount = sysRoleService.insertSysRole(sysRoleModel);
        if (resCount <= 0) {
            return Res.error("添加角色失败");
        }
        return Res.success();
    }

    /**
     * 更新角色信息
     *
     * @param sysRoleModel 角色模型
     */
    @Log("更新角色信息")
    @RequestMapping(value = "/updateSysRole")
    @RequiresPermissions("sys:role:update")
    public Res updateSysRole(SysRoleModel sysRoleModel) {
        int resCount = sysRoleService.updateSysRole(sysRoleModel);
        if (resCount <= 0) {
            return Res.error("更新角色失败");
        }

        return Res.success();
    }

    /**
     * 更新角色权限
     *
     * @param sysRoleModel 角色模型
     */
    @Log("更新角色权限")
    @RequestMapping(value = "/updateRolePerm")
    @RequiresPermissions("sys:role:perm")
    public Res updateRolePerm(SysRoleModel sysRoleModel) {
        // 如果更新的是超级管理员角色，需要判断当前用户是否是超级管理员
        if (sysRoleModel.getRoleId() == 1) {
            Long userId = ShiroUtil.getShiroUserInfo().getUserId();
            ShiroRoleInfo shiroRoleInfo = shiroService.getRoleByUserId(userId);
            if (shiroRoleInfo == null) {
                return Res.error("更新角色权限失败，用户角色信息不存在");
            }
            if (shiroRoleInfo.getRoleId() != 1) {
                return Res.error("更新角色权限失败，只有超级管理员(user:1)才能修改超级管理员角色权限");
            }
        }

        int resCount = sysRoleService.updateSysRolePerm(sysRoleModel);
        if (resCount <= 0) {
            return Res.error("更新角色权限失败");
        }
        // 修改了角色权限，删除角色权限缓存
        shiroService.deleteUserRoleCacheByRoleId(sysRoleModel.getRoleId());
        return Res.success();
    }

    /**
     * 逻辑删除角色
     *
     * @param sysRoleModel 角色模型
     */
    @Log("删除角色")
    @RequestMapping(value = "/deleteSysRoleLogic")
    @RequiresPermissions("sys:role:deleteLogic")
    public Res deleteSysRoleLogic(SysRoleModel sysRoleModel) {
        // 需要判断系统角色是否允许删除
        if (sysRoleModel.getRoleId() == 1) {
            return Res.error("超级管理员角色不允许删除");
        }
        if (sysRoleModel.getRoleId() == 2) {
            return Res.error("默认角色不允许删除");
        }
        if (sysRoleModel.getRoleId() == 3) {
            return Res.error("游客角色不允许删除");
        }
        int resCount = sysRoleService.deleteSysRoleLogic(sysRoleModel.getRoleId());
        if (resCount <= 0) {
            return Res.error("删除角色失败");
        }
        // 删除用户角色缓存
        shiroService.deleteUserRoleCacheByRoleId(sysRoleModel.getRoleId());
        return Res.success();
    }

    /**
     * 恢复删除的角色
     *
     * @param sysRoleModel 角色模型
     */
    @Log("恢复删除的角色")
    @RequestMapping(value = "/recoverSysRole")
    @RequiresPermissions("sys:role:deleteRecover")
    public Res recoverSysRole(SysRoleModel sysRoleModel) {
        int resCount = sysRoleService.recoverSysRole(sysRoleModel.getRoleId());
        if (resCount <= 0) {
            return Res.error("恢复角色失败");
        }
        return Res.success();
    }

    /**
     * 删除角色，物理删除
     *
     * @param sysRoleModel 角色模型
     */
    @Log("物理删除角色")
    @RequestMapping(value = "/deleteSysRolePhysics")
    @RequiresPermissions("sys:role:deletePhysics")
    public Res deleteSysRolePhysics(SysRoleModel sysRoleModel) {
        // 判断是否为系统角色，系统角色不允许删除
        if (sysRoleModel.getRoleId() == 1) {
            return Res.error("超级管理员角色不允许删除");
        }
        if (sysRoleModel.getRoleId() == 2) {
            return Res.error("默认角色不允许删除");
        }
        if (sysRoleModel.getRoleId() == 3) {
            return Res.error("游客角色不允许删除");
        }
        int resCount = sysRoleService.deleteSysRolePhysics(sysRoleModel.getRoleId());
        if (resCount <= 0) {
            return Res.error("删除角色失败");
        }
        return Res.success();
    }

}
