package ink.usr.admin.controller;

import ink.usr.admin.service.ShiroService;
import ink.usr.common.core.annotation.Log;
import ink.usr.common.core.domain.Dict;
import ink.usr.common.core.domain.Res;
import ink.usr.common.interfaces.admin.ISysMenuService;
import ink.usr.common.model.mysql.SysMenuModel;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/sysMenu")
public class SysMenuController {

    @Autowired
    private ISysMenuService sysMenuService;
    @Autowired
    private ShiroService shiroService;


    /**
     * 查询菜单列表
     *
     * @param sysMenuModel 菜单模型
     */
    @Log("查询菜单列表")
    @RequestMapping(value = "/selectSysMenuList")
    @RequiresPermissions("sys:menu:list")
    public Res selectSysMenuList(SysMenuModel sysMenuModel) {
        List<SysMenuModel> sysMenuModels = sysMenuService.selectMenuList(sysMenuModel);
        Dict result = Dict.create()
                .set("list", sysMenuModels);
        return Res.success(result);
    }

    /**
     * 查询通用的菜单列表，不需要权限，一般用于下拉框
     */
    @Log("查询通用的菜单列表")
    @RequestMapping(value = "/selectSysMenuCommonList")
    public Res selectSysMenuCommonList() {
        SysMenuModel sysMenuModel = SysMenuModel.builder().status(0).build();
        List<SysMenuModel> sysMenuModels = sysMenuService.selectMenuList(sysMenuModel);
        Dict result = Dict.create()
                .set("list", sysMenuModels);
        return Res.success(result);
    }

    /**
     * 添加菜单信息
     *
     * @param sysMenuModel 菜单模型
     */
    @Log("添加菜单")
    @RequestMapping(value = "/insertSysMenu")
    @RequiresPermissions("sys:menu:add")
    public Res insertSysMenu(SysMenuModel sysMenuModel) {
        int resCount = sysMenuService.insertSysMenu(sysMenuModel);
        if (resCount <= 0) {
            return Res.error("添加菜单失败");
        }
        return Res.success();
    }

    /**
     * 更新菜单信息
     *
     * @param sysMenuModel 菜单模型
     */
    @Log("更新菜单信息")
    @RequestMapping(value = "/updateSysMenu")
    @RequiresPermissions("sys:menu:update")
    public Res updateSysMenu(SysMenuModel sysMenuModel) {
        int resCount = sysMenuService.updateSysMenu(sysMenuModel);
        if (resCount <= 0) {
            return Res.error("更新菜单失败");
        }
        // 删除所有用户角色缓存，便于用户重新获取角色及角色拥有的权限
        shiroService.deleteUserRoleCache();
        return Res.success();
    }

    /**
     * 逻辑删除菜单
     *
     * @param sysMenuModel 菜单模型
     */
    @Log("删除菜单")
    @RequestMapping(value = "/deleteSysMenuLogic")
    @RequiresPermissions("sys:menu:deleteLogic")
    public Res deleteSysMenuLogic(SysMenuModel sysMenuModel) {
        int resCount = sysMenuService.deleteSysMenuLogic(sysMenuModel.getMenuId());
        if (resCount <= 0) {
            return Res.error("删除菜单失败");
        }
        // 删除所有用户角色缓存，便于用户重新获取角色及角色拥有的权限
        shiroService.deleteUserRoleCache();
        return Res.success();
    }

    /**
     * 恢复删除的菜单
     *
     * @param sysMenuModel 菜单模型
     */
    @Log("恢复删除的菜单")
    @RequestMapping(value = "/recoverSysMenu")
    @RequiresPermissions("sys:menu:deleteRecover")
    public Res recoverSysMenu(SysMenuModel sysMenuModel) {
        int resCount = sysMenuService.recoverSysMenu(sysMenuModel.getMenuId());
        if (resCount <= 0) {
            return Res.error("恢复菜单失败");
        }
        return Res.success();
    }

    /**
     * 物理删除菜单
     *
     * @param sysMenuModel 菜单模型
     */
    @Log("物理删除菜单")
    @RequestMapping(value = "/deleteSysMenuPhysics")
    @RequiresPermissions("sys:menu:deletePhysics")
    public Res deleteSysMenuPhysics(SysMenuModel sysMenuModel) {
        int resCount = sysMenuService.deleteSysMenuPhysics(sysMenuModel.getMenuId());
        if (resCount <= 0) {
            return Res.error("删除菜单失败");
        }
        return Res.success();
    }

}
