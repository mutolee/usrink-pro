package ink.usr.common.entity.admin.interfaces;

import ink.usr.common.entity.admin.vo.SysUserMenus;

public interface ISysLoginService {

    /**
     * 查询用户角色菜单列表
     */
    SysUserMenus selectUserMenuList(String roleMenuIds);

}
