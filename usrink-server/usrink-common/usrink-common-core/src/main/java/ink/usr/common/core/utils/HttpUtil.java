package ink.usr.common.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * HTTP工具类
 */
@Slf4j
public class HttpUtil {

    // 默认连接超时时间（毫秒）
    public static final int DEFAULT_CONNECT_TIMEOUT = 30000;
    // 默认读取超时时间（毫秒）
    public static final int DEFAULT_READ_TIMEOUT = 30000;

    /**
     * 发送GET请求
     *
     * @param url 请求URL
     * @return 响应字符串
     */
    public static String get(String url) {
        return get(url, null);
    }

    /**
     * 发送GET请求
     *
     * @param url     请求URL
     * @param headers 请求头
     * @return 响应字符串
     */
    public static String get(String url, Map<String, String> headers) {
        return get(url, headers, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送GET请求
     *
     * @param url            请求URL
     * @param headers        请求头
     * @param connectTimeout 连接超时时间（毫秒）
     * @param readTimeout    读取超时时间（毫秒）
     * @return 响应字符串
     */
    public static String get(String url, Map<String, String> headers, int connectTimeout, int readTimeout) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();

        try {
            URL urlObj = new URL(url);
            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GBK"));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            log.error("HttpUtil GET IOException, url=" + url, e);
        } catch (Exception e) {
            log.error("HttpUtil GET Exception, url=" + url, e);

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("HttpUtil GET Exception, url=" + url, e);
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response.toString();
    }

    /**
     * 发送POST请求
     *
     * @param url      请求URL
     * @param postData POST请求数据
     * @return 响应字符串
     */
    public static String post(String url, String postData) {
        return post(url, postData, null);
    }

    /**
     * 发送POST请求
     *
     * @param url      请求URL
     * @param postData POST请求数据
     * @param headers  请求头
     * @return 响应字符串
     */
    public static String post(String url, String postData, Map<String, String> headers) {
        return post(url, postData, headers, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送POST请求
     *
     * @param url            请求URL
     * @param postData       POST请求数据
     * @param headers        请求头
     * @param connectTimeout 连接超时时间（毫秒）
     * @param readTimeout    读取超时时间（毫秒）
     * @return 响应字符串
     */
    public static String post(String url, String postData, Map<String, String> headers, int connectTimeout, int readTimeout) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();

        try {
            URL urlObj = new URL(url);
            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(postData.getBytes(StandardCharsets.UTF_8));
            }

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GBK"));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            log.error("HttpUtil POST IOException, url=" + url, e);
        } catch (Exception e) {
            log.error("HttpUtil POST Exception, url=" + url, e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("HttpUtil POST Exception, url=" + url, e);
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response.toString();
    }
}
