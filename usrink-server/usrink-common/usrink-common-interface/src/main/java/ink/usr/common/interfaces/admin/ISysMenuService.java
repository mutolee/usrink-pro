package ink.usr.common.interfaces.admin;

import ink.usr.common.model.mysql.SysMenuModel;

import java.util.List;

public interface ISysMenuService {

    /**
     * 查询菜单列表
     */
    List<SysMenuModel> selectMenuList(SysMenuModel sysMenuModel);

    /**
     * 添加菜单信息
     *
     * @param sysMenuModel 菜单信息
     */
    int insertSysMenu(SysMenuModel sysMenuModel);

    /**
     * 更新菜单信息
     *
     * @param sysMenuModel 菜单信息
     */
    int updateSysMenu(SysMenuModel sysMenuModel);

    /**
     * 逻辑删除菜单信息
     *
     * @param menuId 菜单ID
     */
    int deleteSysMenuLogic(Long menuId);

    /**
     * 恢复删除的菜单信息
     *
     * @param menuId 菜单ID
     */
    int recoverSysMenu(Long menuId);

    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     */
    int deleteSysMenuPhysics(Long menuId);

}
