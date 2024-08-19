package ink.usr.common.core.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP工具类
 */
@Slf4j
public class IpUtil {

    /**
     * 获取客户端IP地址
     */
    public static String getClientIpAddr(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("HTTP_CLIENT_IP");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }

        // 处理通过多级代理的情况，只取第一个IP
        if (clientIp != null && clientIp.contains(",")) {
            clientIp = clientIp.split(",")[0];
        }

        // 过滤本地和私有地址
        assert clientIp != null;
        return filterLocalAndPrivateAddresses(clientIp);
    }

    /**
     * 过滤本地和私有地址
     */
    private static String filterLocalAndPrivateAddresses(String ip) {
        if (ip.equals("0:0:0:0:0:0:0:1") || ip.equals("::1")) {
            // 本地主机
            return "127.0.0.1";
        }

        try {
            InetAddress inetAddress = InetAddress.getByName(ip);
            byte[] addressBytes = inetAddress.getAddress();

            // IPv4地址
            if (addressBytes.length == 4) {
                // 私有地址范围
                if ((addressBytes[0] == (byte) 10) ||
                        (addressBytes[0] == (byte) 172 && (addressBytes[1] >= 16 && addressBytes[1] <= 31)) ||
                        (addressBytes[0] == (byte) 192 && addressBytes[1] == (byte) 168)) {
                    return ip;
                } else {
                    // 非私有地址，将IPv6地址转换为IPv4地址
                    return inetAddress.getHostAddress();
                }
            }
        } catch (UnknownHostException e) {
            log.error("获取客户端IP地址失败", e);
        }

        return "unknown";
    }
}
