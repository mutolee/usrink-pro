package ink.usr.common.core.utils;

public class ByteUtil {

    /**
     * 根据字节长度返回人类可读的字符串
     *
     * @param bytes 字节长度
     * @return string
     */
    public static String convertBytes(long bytes) {
        if (bytes < 0) {
            throw new IllegalArgumentException("Bytes should not be negative");
        }

        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.2f KB", (double) bytes / 1024);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", (double) bytes / (1024 * 1024));
        } else {
            return String.format("%.2f GB", (double) bytes / (1024 * 1024 * 1024));
        }
    }

}
