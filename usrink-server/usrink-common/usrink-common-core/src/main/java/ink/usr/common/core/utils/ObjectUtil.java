package ink.usr.common.core.utils;

/**
 * 对象工具类
 */
public class ObjectUtil {

    /**
     * 对象属性拷贝<br>
     * 使用Jackson的ObjectMapper做深度拷贝
     *
     * @param source      源对象
     * @param targetClass 目标对象类型
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        if (source == null) {
            throw new IllegalArgumentException("Source object must not be null");
        }

        String sourceJsonString = JsonUtil.toJson(source);
        return JsonUtil.parse(sourceJsonString, targetClass);
    }

}
