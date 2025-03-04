package ink.usr.framework.jwt.handler;

import ink.usr.common.core.entity.Res;
import ink.usr.common.core.entity.enums.StatusCode;
import ink.usr.common.core.exception.UnauthenticatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(-99) // Jwt模块异常处理需要优先执行，这样才能保证Jwt模块的异常能被正确处理
public class JwtExceptionHandler {

    /**
     * JWT认证异常
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public Res handleUnauthenticatedException(UnauthenticatedException e) {
        return Res.me().setCode(StatusCode.UNAUTHORIZED).setMsg("认证失败，请重新登录！");
    }

}
