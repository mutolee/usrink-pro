package ink.usr.admin.config;

import ink.usr.admin.handler.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdminConfig implements WebMvcConfigurer {

    /**
     * 请求拦截器
     */
    @Autowired
    private RequestInterceptor requestInterceptor;

    /**
     * 配置拦截器，添加JWT拦截器
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor)
                .addPathPatterns("/**")
                .order(Integer.MAX_VALUE - 1); // 优先级，值越小，优先级越高
        // 添加其它拦截器
        // ...
    }
}
