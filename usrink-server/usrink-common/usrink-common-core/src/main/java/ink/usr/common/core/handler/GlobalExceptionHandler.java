package ink.usr.common.core.handler;

import ink.usr.common.core.domain.Res;
import ink.usr.common.core.enums.StatusCode;
import ink.usr.common.core.exception.WarningException;
import ink.usr.common.core.exception.base.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Order(Integer.MAX_VALUE - 1)
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 请求地址不存在
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Res handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        return Res.me().setCode(StatusCode.NOT_FOUND).setMsg(e.getMessage());
    }

    /**
     * 请求类型不被支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Res handleRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return Res.error().setMsg(e.getMessage());
    }

    /**
     * 服务器发生警告异常
     */
    @ExceptionHandler(WarningException.class)
    public Res handleWarningException(WarningException e) {
        log.error(e.getMessage(), e);
        return Res.error().setMsg(e.getMessage());
    }

    /**
     * 服务器发生业务处理异常
     */
    @ExceptionHandler(BusinessException.class)
    public Res handleBusinessException(BusinessException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址{}，服务器发生业务处理异常！", requestURI, e);
        return Res.error().setMsg("服务器发生业务处理异常！");
    }

    /**
     * 运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Res handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址{}，发生未知异常！", requestURI, e);
        return Res.error().setMsg("服务器发生未知异常！");
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public Res handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址{}，发生系统异常！", requestURI, e);
        return Res.error().setMsg("服务器发生系统异常！");
    }
}
