package ink.usr.framework.shiro.utils;

import ink.usr.common.core.utils.JwtUtil;
import ink.usr.framework.shiro.domain.ShiroUserInfo;
import ink.usr.framework.shiro.token.AuthenticationJwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;

/**
 * Shiro工具类
 */
@Slf4j
public class ShiroUtil {

    /**
     * 获取Shiro用户信息, 从Shiro的Subject中获取，
     * 如果没有登录将抛出未认证异常
     */
    public static ShiroUserInfo getShiroUserInfo() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            log.error("获取用户信息，用户未认证");
            throw new UnauthenticatedException("用户未认证");
        }

        // 如果是JwtToken，从token中获取用户信息
        if (principal instanceof AuthenticationJwtToken) {
            AuthenticationJwtToken jwtToken = (AuthenticationJwtToken) principal;
            String token = jwtToken.getToken();
            return ShiroUserInfo.builder()
                    .userId(JwtUtil.getUserId(token))
                    .userName(JwtUtil.getUserName(token))
                    .userPassword(JwtUtil.getPassword(token)).build();
        } else if (principal instanceof ShiroUserInfo) {
            return (ShiroUserInfo) principal;
        }

        throw new UnauthenticatedException("未找到用户的认证信息");
    }
}
