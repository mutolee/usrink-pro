package ink.usr.admin.controller;

import ink.usr.admin.service.ShiroService;
import ink.usr.common.core.annotation.Log;
import ink.usr.common.core.domain.Dict;
import ink.usr.common.core.domain.Res;
import ink.usr.common.domain.admin.SysUserMenus;
import ink.usr.common.interfaces.admin.ISysLoginService;
import ink.usr.common.interfaces.admin.ISysRoleService;
import ink.usr.common.interfaces.admin.ISysUserService;
import ink.usr.common.model.mysql.SysRoleModel;
import ink.usr.common.model.mysql.SysUserModel;
import ink.usr.framework.shiro.domain.ShiroRoleInfo;
import ink.usr.framework.shiro.domain.ShiroUserInfo;
import ink.usr.framework.shiro.utils.JwtUtil;
import ink.usr.framework.shiro.utils.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SysLoginController {

    @Autowired
    private ISysLoginService sysLoginService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ShiroService shiroService;


    /**
     * 登录接口<br>
     *
     * @param userName 用户名
     * @param password 密码
     */
    @RequestMapping(value = "/login")
    public Res login(String userName, String password) {
        // 创建一个用户名密码令牌
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

        // Shiro进行身份认证
        // 用户名不存在将抛出Shiro的UnknownAccountException异常
        // 密码错误将抛出Shiro的IncorrectCredentialsException异常
        SecurityUtils.getSubject().login(token);

        // 用户认证凭证信息
        ShiroUserInfo shiroUserInfo = ShiroUtil.getShiroUserInfo();

        // 查询用户资料信息
        SysUserModel sysUserModel = sysUserService.selectSysUserInfo(shiroUserInfo.getUserId());
        // 不返回密码
        sysUserModel.setUserPassword(null);

        // 查询用户角色
        SysRoleModel sysRoleModel = sysRoleService.selectRoleInfo(sysUserModel.getUserRoleId());

        // 查询用户拥有的菜单
        SysUserMenus userMenus = sysLoginService.selectUserMenuList(sysRoleModel.getRoleMenuIds());

        // 生成Token令牌
        String tokenString = JwtUtil.createToken(
                shiroUserInfo.getUserName(),
                shiroUserInfo.getUserId(),
                shiroUserInfo.getUserPassword());

        // 返回登录结果
        Dict result = Dict.create()
                .set("token", tokenString)
                .set("userInfo", sysUserModel)
                .set("roleInfo", sysRoleModel)
                .set("userMenus", userMenus);

        // 保存用户角色缓存
        shiroService.updateUserRoleCache(shiroUserInfo.getUserId(), sysRoleModel);

        return Res.success(result);
    }


    /**
     * 查询用户菜单列表
     */
    @Log("查询用户的菜单列表")
    @RequestMapping(value = "/userMenu")
    public Res selectUserMenuList() {
        ShiroUserInfo shiroUserInfo = ShiroUtil.getShiroUserInfo();
        ShiroRoleInfo shiroRoleInfo = shiroService.getRoleByUserId(shiroUserInfo.getUserId());
        if (shiroRoleInfo == null) {
            return Res.error("用户角色信息不存在");
        }

        // 查询用户拥有的菜单
        SysUserMenus userMenus = sysLoginService.selectUserMenuList(shiroRoleInfo.getRoleMenuIds());
        return Res.success(userMenus);
    }


}
