package ink.usr.common.core.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet 工具类
 */
public class ServletUtil {

    /**
     * 获取HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        // 获取HttpServletRequest
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest();
    }

    /**
     * 获取String参数
     */
    public static String getParameter(String name) {
        return getHttpServletRequest().getParameter(name);
    }

    /**
     * 获取HttpServletRequest中的所有请求参数
     *
     * @return 包含所有请求参数的Map对象，值为Object类型
     */
    public static Map<String, Object> getAllParameters() {
        Map<String, String[]> parameterMap = getHttpServletRequest().getParameterMap();
        Map<String, Object> resultMap = new HashMap<>();

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String paramName = entry.getKey();
            String[] paramValues = entry.getValue();
            Object paramValue = (paramValues.length == 1) ? paramValues[0] : paramValues;
            resultMap.put(paramName, paramValue);
        }

        return resultMap;
    }

}
