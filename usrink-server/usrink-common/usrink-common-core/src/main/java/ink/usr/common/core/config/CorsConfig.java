package ink.usr.common.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 */
@Configuration
public class CorsConfig {

    /**
     * 跨域过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // 允许跨域访问的路径
        config.addAllowedOriginPattern("*"); // 或者可以指定特定的域名，如：http://example.com
        // 允许跨域访问的方法
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        // 允许跨域访问的头
        config.addAllowedHeader("*");
        // 是否发送Cookie
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
