package ink.usr.common.core.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ink.usr.common.core.config.JacksonConfig;
import ink.usr.common.core.exception.JsonProcessException;


/**
 * Jackson工具类
 */
public class JsonUtil {

    private static ObjectMapper objectMapper;

    /**
     * 初始化 ObjectMapper<br>
     * <p>
     * Warning: 请在 Spring 中统一配置 ObjectMapper, 并在 Spring 容器初始化完成后调用此方法;<br>
     * 如果非Spring环境，请使用 `JsonUtil.initObjectMapper(objectMapper)` 方法初始化 ObjectMapper
     *
     * @param singleObjectMapper 全局唯一的 ObjectMapper
     * @see JacksonConfig
     */
    public static void initObjectMapper(ObjectMapper singleObjectMapper) {
        if (objectMapper == null) {
            objectMapper = singleObjectMapper;
        }
    }

    /**
     * 将对象序列化为 JSON 字符串
     *
     * @param object 需要序列化的对象
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new JsonProcessException("Error converting object to JSON", e);
        }
    }

    /**
     * 将 JSON 字符串反序列化为指定类型的对象
     *
     * @param json      JSON 字符串
     * @param valueType 指定类型
     */
    public static <T> T parse(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            throw new JsonProcessException("Error converting JSON to object", e);
        }
    }

    /**
     * 创建一个空的 JSON 对象
     */
    public static ObjectNode createObjectNode() {
        return JsonNodeFactory.instance.objectNode();
    }

    /**
     * 创建一个空的 JSON 数组
     */
    public static ArrayNode createArrayNode() {
        return JsonNodeFactory.instance.arrayNode();
    }

    /**
     * 将对象转为 JsonNode
     *
     * @param object 需要转换的对象
     */
    public static JsonNode convertToJsonNode(Object object) {
        try {
            return objectMapper.convertValue(object, JsonNode.class);
        } catch (Exception e) {
            throw new JsonProcessException("Error converting object to JsonNode", e);
        }
    }

    /**
     * 将对象转为 ArrayNode
     *
     * @param object 需要转换的对象
     */
    public static ArrayNode convertToArrayNode(Object object) {
        try {
            return objectMapper.convertValue(object, ArrayNode.class);
        } catch (Exception e) {
            throw new JsonProcessException("Error converting object to ArrayNode", e);
        }
    }

    /**
     * 将 JsonNode 转为指定类型的对象
     *
     * @param jsonNode  需要转换的 JsonNode
     * @param valueType 指定类型
     */
    public static <T> T convertToObject(JsonNode jsonNode, Class<T> valueType) {
        try {
            return objectMapper.convertValue(jsonNode, valueType);
        } catch (Exception e) {
            throw new JsonProcessException("Error converting JsonNode to object", e);
        }
    }

    /**
     * 将 ArrayNode 字符串转为指定类型的对象
     *
     * @param arrayNode 需要转换的 ArrayNode
     * @param valueType 指定类型
     */
    public static <T> T convertToObject(ArrayNode arrayNode, Class<T> valueType) {
        try {
            return objectMapper.convertValue(arrayNode, valueType);
        } catch (Exception e) {
            throw new JsonProcessException("Error converting ArrayNode to object", e);
        }
    }

    /**
     * 将 JsonNode 转为 JSON 字符串
     *
     * @param jsonNode 需要转换的 JsonNode
     */
    public static String convertToJsonString(JsonNode jsonNode) {
        try {
            return objectMapper.writeValueAsString(jsonNode);
        } catch (Exception e) {
            throw new JsonProcessException("Error converting JsonNode to JSON string", e);
        }
    }

    /**
     * 将 ArrayNode 转为 JSON 字符串
     *
     * @param arrayNode 需要转换的 ArrayNode
     */
    public static String convertToJsonString(ArrayNode arrayNode) {
        try {
            return objectMapper.writeValueAsString(arrayNode);
        } catch (Exception e) {
            throw new JsonProcessException("Error converting ArrayNode to JSON string", e);
        }
    }


}
