package ink.usr.framework.shiro.interfaces;

import ink.usr.framework.shiro.domain.ShiroRoleInfo;
import ink.usr.framework.shiro.domain.ShiroUserInfo;

/**
 * Shiro服务接口，用于获取用户、角色、权限信息，依赖Shiro的模块需要实现该接口
 */
public interface IShiroService {

    /**
     * Shiro认证，根据用户名获取用户信息
     *
     * @param userName 用户名
     * @return 用户信息
     */
    ShiroUserInfo selectSysUserForLogin(String userName);

    /**
     * Shiro授权，根据用户ID获取角色信息
     *
     * @param userId 用户ID
     * @return 角色信息
     */
    ShiroRoleInfo getRoleByUserId(Long userId);

}
