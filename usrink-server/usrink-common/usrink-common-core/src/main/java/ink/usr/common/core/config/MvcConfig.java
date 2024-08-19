package ink.usr.common.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC 配置
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 文件上传路径
     */
    @Value("${file.upload.path}")
    private String uploadPth;


    /**
     * 配置静态资源处理器<br>
     * 将 /res/** 请求映射到类路径下的 public 目录
     *
     * @param registry 静态资源处理器注册表
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/res/**")
                .addResourceLocations("classpath:/public/", "file:" + uploadPth);
    }

    /**
     * 配置拦截器
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加其它拦截器
        // ...
    }
}
