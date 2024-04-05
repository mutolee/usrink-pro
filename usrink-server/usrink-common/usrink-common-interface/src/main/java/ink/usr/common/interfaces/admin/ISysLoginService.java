package ink.usr.common.interfaces.admin;

import ink.usr.common.domain.admin.SysUserMenus;

public interface ISysLoginService {

    /**
     * 查询用户角色菜单列表
     */
    SysUserMenus selectUserMenuList(String roleMenuIds);

}
