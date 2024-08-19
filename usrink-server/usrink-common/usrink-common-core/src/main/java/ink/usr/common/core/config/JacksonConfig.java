package ink.usr.common.core.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ink.usr.common.core.utils.JsonUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Jackson 配置
 */
@Configuration
public class JacksonConfig {

    /**
     * 配置 ObjectMapper 的序列化及反序列化规则
     */
    @Bean(value = "objectMapper")
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 在反序列化时忽略在 JSON 中存在但在对象中不存在的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许空的 JSON 字符串
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        // 初始化 JsonUtil
        JsonUtil.initObjectMapper(objectMapper);

        // 其他配置
        // ...
        return objectMapper;
    }

}
