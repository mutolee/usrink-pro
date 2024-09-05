package ink.usr.framework.shiro.realm;

import ink.usr.common.core.constants.Constants;
import ink.usr.common.core.utils.Md5Util;
import ink.usr.framework.shiro.domain.ShiroRoleInfo;
import ink.usr.framework.shiro.domain.ShiroUserInfo;
import ink.usr.framework.shiro.interfaces.IShiroService;
import ink.usr.framework.shiro.token.JwtToken;
import ink.usr.framework.shiro.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义Realm，用于Shiro的认证和授权
 */
@Slf4j
@Component
public class UserRealm extends AuthorizingRealm {

    /**
     * 业务接口，用于获取用户、角色、权限信息<br>
     */
    @Autowired
    private IShiroService shiroService;

    /**
     * 重写supports，让Realm支持自定义的Token类型<br>
     * 这里支持JwtToken和UsernamePasswordToken
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        log.debug("Shiro支持的Token类型：{}", token.getClass().getName());
        return token instanceof JwtToken || token instanceof UsernamePasswordToken;
    }

    /**
     * 认证，用于登录<br>
     * 登录时会调用此方法，用于验证用户名和密码是否正确，如果正确则返回AuthenticationInfo对象，否则抛出异常<br>
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (authenticationToken instanceof UsernamePasswordToken) { // 用户名密码登录
            log.debug("用户名密码登录认证");
            UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
            // 设置Token密码加盐，再进行MD5加密，随后与数据库中的密码进行比对
            token.setPassword(Md5Util.md5(new String(token.getPassword()) + Constants.SALT).toCharArray());
            // 获取用户名
            String userName = token.getUsername();

            // 查询用户信息
            ShiroUserInfo shiroUserInfo = shiroService.selectSysUserForLogin(userName);
            if (shiroUserInfo == null) {
                throw new UnknownAccountException("用户不存在");
            }

            // 返回SimpleAuthenticationInfo，Shiro会自动验证密码是否正确
            // 原理：SimpleAuthenticationInfo的密码参数，会和authenticationToken的密码进行比对
            return new SimpleAuthenticationInfo(shiroUserInfo, shiroUserInfo.getUserPassword(), getName());

        } else if (authenticationToken instanceof JwtToken) { // JWT登录
            log.debug("Jwt Token登录认证");
            JwtToken token = (JwtToken) authenticationToken;
            return new SimpleAuthenticationInfo(token, token.getCredentials(), getName());
        }

        // 不支持的Token类型
        throw new AuthenticationException("认证失败！");
    }


    /**
     * 授权，用于获取用户的角色和权限信息<br>
     * 在Controller中使用@RequiresRoles和@RequiresPermissions时会调用此方法<br>
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取用户凭证，即JWT Token
        JwtToken primaryPrincipal = (JwtToken) principalCollection.getPrimaryPrincipal();
        long userId = JwtUtil.getUserId(primaryPrincipal.getToken());
        log.debug("用户（{}）权限验证", userId);

        // 查询用户角色
        ShiroRoleInfo shiroRoleInfo = shiroService.getRoleByUserId(userId);
        if (shiroRoleInfo == null) {
            log.error("用户（{}）角色验证异常", userId);
            throw new UnauthorizedException("授权失败，用户角色验证异常");
        }

        // 分配角色
        Set<String> roles = new HashSet<>();
        roles.add(shiroRoleInfo.getRolePermKey());

        // 分配权限标识
        Set<String> permKeys = new HashSet<>(shiroRoleInfo.getPermKeys());

        // 创建授权信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permKeys);
        return authorizationInfo;
    }
}
