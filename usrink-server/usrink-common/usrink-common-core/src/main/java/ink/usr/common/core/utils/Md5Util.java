package ink.usr.common.core.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 */
public class Md5Util {

    /**
     * MD5加密
     *
     * @param src 待加密字符串
     * @return 加密后字符串
     */
    public static String md5(String src) {
        try {
            // 创建一个MD5哈希对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算哈希值
            byte[] messageDigest = md.digest(src.getBytes());

            // 将byte数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
