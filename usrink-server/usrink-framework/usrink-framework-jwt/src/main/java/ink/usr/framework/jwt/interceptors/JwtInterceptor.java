package ink.usr.framework.jwt.interceptors;

import ink.usr.common.core.exception.UnauthenticatedException;
import ink.usr.common.core.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT拦截器，用于验证JWT Token
 */
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    /**
     * 拦截器的核心方法，用于验证JWT Token
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestURI = request.getRequestURI();
        log.debug("Jwt拦截请求: {}", requestURI);

        // 从请求中获取JWT Token
        String jwtToken = extractJwtToken(request);
        if (jwtToken == null) {
            throw new UnauthenticatedException("JWT Token不存在");
        }

        // 验证JWT Token
        if (!JwtUtil.verifyToken(jwtToken)) {
            throw new UnauthenticatedException("JWT Token无效");
        }

        // 验证JWT Token是否过期
        if (JwtUtil.isExpired(jwtToken)) {
            throw new UnauthenticatedException("JWT Token已过期");
        }

        // 保存JWT Token到HttpServletRequest中，交给后续拦截器使用
        request.setAttribute("jwtToken", jwtToken);

        return true;
    }

    /**
     * 从请求中提取JWT Token
     */
    private String extractJwtToken(HttpServletRequest request) {
        // 从请求头中获取JWT Token
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // 去除 "Bearer " 前缀
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
