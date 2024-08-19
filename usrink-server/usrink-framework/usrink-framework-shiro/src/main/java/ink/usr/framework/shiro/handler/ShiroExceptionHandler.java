package ink.usr.framework.shiro.handler;

import ink.usr.common.core.domain.Res;
import ink.usr.common.core.enums.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(-99) // Shiro模块异常处理需要优先执行，这样才能保证Shiro模块的异常能被正确处理
public class ShiroExceptionHandler {

    /**
     * Shiro未授权异常，请求没有携带token，或token过期，或token无效抛出的异常
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public Res handleUnauthenticatedException(UnauthenticatedException e) {
        return Res.me().setCode(StatusCode.UNAUTHORIZED).setMsg("凭证为空或凭证过期，请重新登录！");
    }

    /**
     * Shiro认证，账号不存在异常，用户名不存在，抛出的异常
     */
    @ExceptionHandler(UnknownAccountException.class)
    public Res handleUnknownAccountException(UnknownAccountException e) {
        return Res.error().setMsg("认证失败，用户名不存在！");
    }

    /**
     * Shiro认证，密码错误，抛出的异常
     */
    @ExceptionHandler(IncorrectCredentialsException.class)
    public Res handleIncorrectCredentialsException(IncorrectCredentialsException e) {
        return Res.error().setMsg("认证失败，密码错误！");
    }

    /**
     * Shiro认证，认证异常
     */
    @ExceptionHandler(AuthenticationException.class)
    public Res handleAuthenticationException(AuthenticationException e) {
        log.error("认证未知异常！", e);
        return Res.error().setMsg("认证时未知异常！");
    }

    /**
     * Shiro权限不足异常，请求的资源需要的权限不足，抛出的异常
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Res HandleUnauthorizedException(UnauthorizedException e) {
        return Res.me().setCode(StatusCode.FORBIDDEN).setMsg("操作失败，权限不足！");
    }

    /**
     * Shiro权限验证，发生异常
     */
    @ExceptionHandler(AuthorizationException.class)
    public Res handleAuthorizationException(AuthorizationException e) {
        log.error("权限验证异常！", e);
        return Res.error().setMsg("权限验证异常！");
    }

    /**
     * Shiro异常，Shiro未知异常
     */
    @ExceptionHandler(ShiroException.class)
    public Res handleShiroException(ShiroException e) {
        log.error("Shiro未知异常！", e);
        return Res.error().setMsg("权限验证未知异常！");
    }

}
