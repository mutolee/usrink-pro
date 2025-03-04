package ink.usr.admin.handler;

import ink.usr.framework.shiro.token.AuthenticationJwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

    /**
     * 拦截器的核心方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取JwtToken，前序拦截器已经将JwtToken设置到HttpServletRequest中了
        Object jwtTokenObj = request.getAttribute("jwtToken");
        if (jwtTokenObj != null) {
            String jwtToken = (String) jwtTokenObj;
            // 将JWT Token设置到Shiro的Subject中进行认证
            SecurityUtils.getSubject().login(new AuthenticationJwtToken(jwtToken));
        }
        return true;
    }
}
