package ink.usr.common.core.controller;

import ink.usr.common.core.domain.Res;
import ink.usr.common.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * 在Spring Boot中，/error 是一个特殊的端点（endpoint），用于处理应用程序中发生的错误或异常。<br>
 * 当应用程序遇到错误时，Spring Boot会自动调用 /error 端点<br>
 * <p>
 * 比如：访问一个不存在的地址，Spring Boot会自动调用 /error 端点
 */
@Slf4j
@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    /**
     * 定制 Spring Boot中 /error 的行为，
     * 根据异常类型，抛出自定义异常，交给 @RestControllerAdvice 注解类来处理异常
     *
     * @param request 请求
     */
    @RequestMapping("/error")
    public Res error(HttpServletRequest request) throws Throwable {
        // 获取错误状态码
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            // 转发到 `/error` 之前的请求
            Object forwardRequestUri = request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
            // 如果Http状态码为404，抛出NoHandlerFoundException异常，交给 @RestControllerAdvice 注解类来处理异常
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                throw new NoHandlerFoundException(request.getMethod(), forwardRequestUri.toString(), new HttpHeaders());
            }
            // 请求类型不被支持
            else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
                log.error("请求类型不被支持！请求类型：{}，请求地址：{}", request.getMethod(), forwardRequestUri.toString());
                throw new HttpRequestMethodNotSupportedException(request.getMethod());
            } else {
                // 获取异常类型
                Object attribute = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
                String errMessage = "";
                if (attribute != null) {
                    Throwable throwable = (Throwable) attribute;
                    // 异常信息
                    errMessage = throwable.getMessage();
                }
                // 未知异常
                String err = StringUtil.format("服务器发生未知异常！请求类型：{}，HttpStatus：{}，转发前的请求地址：{}，errMessage：{}",
                        request.getMethod(),
                        statusCode,
                        forwardRequestUri,
                        errMessage);
                // 其他类型的异常
                throw new RuntimeException(err);
            }
        }
        return Res.error().setMsg("无效访问！");
    }

}
