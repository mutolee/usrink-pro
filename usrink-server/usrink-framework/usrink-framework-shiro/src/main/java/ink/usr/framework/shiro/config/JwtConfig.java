package ink.usr.framework.shiro.config;

import ink.usr.framework.shiro.interceptors.JwtInterceptor;
import ink.usr.framework.shiro.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

/**
 * JWT配置
 */
@Slf4j
@Configuration
public class JwtConfig implements WebMvcConfigurer {

    /**
     * JWT密钥
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * JWT有效期
     */
    @Value("${jwt.expire}")
    private long expire;

    /**
     * 排除不需要JWT认证的资源
     */
    @Value("${jwt.interceptor.exclude.resources}")
    private String excludeResourcesUrl;

    /**
     * JWT拦截器
     */
    @Autowired
    private JwtInterceptor jwtInterceptor;

    /**
     * 初始化JWT工具类
     */
    @PostConstruct
    public void initJwtUtil() {
        // 初始化JWT工具类
        JwtUtil.init(secret, expire);
        log.debug("初始化JWT工具类完成，密钥：{}，有效期：{}ms", secret, expire);
    }


    /**
     * 配置拦截器，添加JWT拦截器
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludeResourcesUrl.split(",")) // 排除不需要JWT认证的资源
                .order(-99); // 优先级，值越小，优先级越高，让Jwt的拦截器最早拦截请求
        // 添加其它拦截器
        // ...
    }
}
