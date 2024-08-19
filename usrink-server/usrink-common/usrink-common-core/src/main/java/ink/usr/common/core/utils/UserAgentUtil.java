package ink.usr.common.core.utils;

/**
 * User-Agent 工具类
 */
public class UserAgentUtil {

    /**
     * 通过User-Agent检测操作系统
     */
    public static String getOs(String userAgent) {
        // 简化的操作系统检测逻辑
        if (userAgent.toLowerCase().contains("windows")) {
            return "Windows";
        } else if (userAgent.toLowerCase().contains("mac")) {
            return "Mac OS";
        } else if (userAgent.toLowerCase().contains("linux")) {
            return "Linux";
        } else {
            return "Unknown";
        }
    }

    /**
     * 通过User-Agent检测浏览器
     */
    public static String getBrowser(String userAgent) {
        // 简化的浏览器检测逻辑
        if (userAgent.toLowerCase().contains("msie")) {
            return "IE";
        } else if (userAgent.toLowerCase().contains("firefox")) {
            return "Firefox";
        } else if (userAgent.toLowerCase().contains("chrome")) {
            return "Chrome";
        } else if (userAgent.toLowerCase().contains("safari")) {
            return "Safari";
        } else {
            return "Unknown";
        }
    }

}
