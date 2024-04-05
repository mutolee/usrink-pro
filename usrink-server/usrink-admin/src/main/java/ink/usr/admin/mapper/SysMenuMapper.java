package ink.usr.admin.mapper;

import ink.usr.common.model.mysql.SysMenuModel;

import java.util.List;

public interface SysMenuMapper {

    /**
     * 查询菜单列表
     *
     * @return 菜单列表
     */
    List<SysMenuModel> selectSysMenuList(SysMenuModel sysMenuModel);

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
    int deleteSysMenuLogicById(Long menuId);

    /**
     * 恢复删除的菜单信息
     *
     * @param menuId 菜单ID
     */
    int recoverSysMenuById(Long menuId);

    /**
     * 物理删除菜单
     *
     * @param menuId 菜单ID
     */
    int deleteSysMenuById(Long menuId);

}
