package ink.usr.admin.service;

import ink.usr.admin.dao.SysMenuDao;
import ink.usr.admin.dao.SysRoleDao;
import ink.usr.admin.dao.SysUserDao;
import ink.usr.common.core.utils.CollUtil;
import ink.usr.common.core.utils.ObjectUtil;
import ink.usr.common.core.utils.StringUtil;
import ink.usr.common.model.mysql.SysMenuModel;
import ink.usr.common.model.mysql.SysRoleModel;
import ink.usr.common.model.mysql.SysUserModel;
import ink.usr.framework.mysql.enums.Ds;
import ink.usr.framework.shiro.domain.ShiroRoleInfo;
import ink.usr.framework.shiro.domain.ShiroUserInfo;
import ink.usr.framework.shiro.interfaces.IShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Shiro服务实现，实现获取用户、角色、权限信息
 */
@Service("shiroService")
public class ShiroService implements IShiroService {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysMenuDao sysMenuDao;


    /**
     * 用户角色缓存,<br>
     * 一个用户对应一个角色,<br>
     * key: 用户ID, value: 角色信息
     */
    private final Map<Long, ShiroRoleInfo> userRoleCache = new HashMap<>();

    /**
     * 更新用户角色缓存
     *
     * @param userId       用户ID
     * @param sysRoleModel 角色
     */
    public void updateUserRoleCache(Long userId, SysRoleModel sysRoleModel) {
        // 获取角色权限信息
        String roleMenuIds = sysRoleModel.getRoleMenuIds();
        if (StringUtil.isEmpty(roleMenuIds)) {
            return;
        }
        // 缓存不存在，从数据库中获取权限信息
        SysMenuModel paramModel = SysMenuModel.builder().status(0).build();
        List<SysMenuModel> sysMenuModels = sysMenuDao.selectSysMenuList(Ds.W, paramModel);
        if (CollUtil.isEmpty(sysMenuModels)) {
            return;
        }
        // 构建角色信息
        ShiroRoleInfo shiroRoleInfo = ObjectUtil.copyProperties(sysRoleModel, ShiroRoleInfo.class);
        List<String> permKeys = getPermKeys(sysMenuModels, roleMenuIds);
        shiroRoleInfo.setPermKeys(permKeys);

        // 缓存用户的角色信息
        userRoleCache.put(userId, shiroRoleInfo);
    }

    /**
     * 根据用户ID，删除用户角色缓存<br>
     *
     * @param userId 用户ID
     */
    public void deleteUserRoleCacheByUserId(Long userId) {
        userRoleCache.remove(userId);
    }

    /**
     * 根据角色ID，删除所有跟该角色相关的用户角色缓存<br>
     *
     * @param roleId 角色ID
     */
    public void deleteUserRoleCacheByRoleId(Long roleId) {
        List<Long> userIds = new ArrayList<>();
        userRoleCache.forEach((userId, roleInfo) -> {
            if (roleInfo.getRoleId().equals(roleId)) {
                userIds.add(userId);
            }
        });
        userIds.forEach(this::deleteUserRoleCacheByUserId);
    }

    /**
     * 删除所有用户角色缓存，便于用户重新获取角色及角色拥有的权限<br>
     */
    public void deleteUserRoleCache() {
        userRoleCache.clear();
    }


    /**
     * 登录，根据用户名获取用户信息
     *
     * @param userName 用户名
     */
    @Override
    public ShiroUserInfo selectSysUserForLogin(String userName) {
        SysUserModel sysUserModel = sysUserDao.selectSysUserForLogin(Ds.W, userName);
        if (sysUserModel == null) {
            return null;
        }

        return ObjectUtil.copyProperties(sysUserModel, ShiroUserInfo.class);
    }

    /**
     * Shiro授权，根据用户ID获取角色信息
     *
     * @param userId 用户ID
     * @return 角色信息
     */
    @Override
    public ShiroRoleInfo getRoleByUserId(Long userId) {
        // 从缓存中获取角色信息
        ShiroRoleInfo shiroRoleInfo = userRoleCache.get(userId);
        if (shiroRoleInfo == null) {
            // 查询用户信息
            SysUserModel sysUserModel = selectUserById(userId);
            if (sysUserModel == null) {
                return null;
            }
            // 查询用户角色
            SysRoleModel sysRoleModel = selectRoleById(sysUserModel.getUserRoleId());
            if (sysRoleModel == null) {
                return null;
            }
            // 获取角色权限信息
            String roleMenuIds = sysRoleModel.getRoleMenuIds();
            if (StringUtil.isEmpty(roleMenuIds)) {
                return null;
            }
            // 缓存不存在，从数据库中获取权限信息
            SysMenuModel paramModel = SysMenuModel.builder().status(0).build();
            List<SysMenuModel> sysMenuModels = sysMenuDao.selectSysMenuList(Ds.W, paramModel);
            if (CollUtil.isEmpty(sysMenuModels)) {
                return null;
            }
            // 构建角色信息
            shiroRoleInfo = ObjectUtil.copyProperties(sysRoleModel, ShiroRoleInfo.class);
            List<String> permKeys = getPermKeys(sysMenuModels, roleMenuIds);
            shiroRoleInfo.setPermKeys(permKeys);

            // 缓存用户的角色信息
            userRoleCache.put(userId, shiroRoleInfo);
        }
        return shiroRoleInfo;
    }


    /**
     * 获取权限信息
     *
     * @param sysMenuModels 菜单信息
     * @param roleMenuIds   角色拥有的菜单ID，以逗号分隔
     */
    private List<String> getPermKeys(List<SysMenuModel> sysMenuModels, String roleMenuIds) {
        List<String> permKeys = new ArrayList<>();
        for (SysMenuModel sysMenuModel : sysMenuModels) {
            // 菜单类型为按钮
            if (sysMenuModel.getMenuType() == 2) {
                String[] menuIds = roleMenuIds.split(",");
                for (String menuId : menuIds) {
                    if (menuId.equals(sysMenuModel.getMenuId().toString())) {
                        permKeys.add(sysMenuModel.getPermKey());
                    }
                }
            }
        }
        return permKeys;
    }

    /**
     * 根据角色ID获取权限信息
     *
     * @param roleId 角色ID
     */
    private SysRoleModel selectRoleById(Long roleId) {
        SysRoleModel sysRoleModel = sysRoleDao.selectRoleById(Ds.W, roleId);
        if (sysRoleModel == null) {
            return null;
        }
        // 角色状态为禁用
        if (sysRoleModel.getStatus() != 0) {
            return null;
        }
        return sysRoleModel;
    }

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     */
    private SysUserModel selectUserById(Long userId) {
        SysUserModel sysUserModel = sysUserDao.selectSysUserById(Ds.W, userId);
        if (sysUserModel == null) {
            return null;
        }
        // 用户状态为禁用
        if (sysUserModel.getStatus() != 0) {
            return null;
        }
        return sysUserModel;
    }

}
