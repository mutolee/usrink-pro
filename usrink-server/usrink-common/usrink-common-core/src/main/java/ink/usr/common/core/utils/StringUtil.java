package ink.usr.common.core.utils;

/**
 * 字符串工具类
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return 是否为空
     */
    public static boolean isEmpty(final CharSequence str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return 是否为空
     */
    public static boolean isNotEmpty(final CharSequence str) {
        return !isEmpty(str);
    }

    /**
     * 字符串去空格
     *
     * @param str 字符串
     * @return 字符串
     */
    public static String trim(final String str) {
        return str == null ? "" : str.trim();
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始位置(包含)
     * @param end   结束位置(不包含)
     * @return 字符串
     */
    public static String subString(String str, int start, int end) {
        if (str == null) {
            return "";
        }
        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }
        if (end > str.length()) {
            end = str.length();
        }
        if (start > end) {
            return "";
        }
        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }
        return str.substring(start, end);
    }

    /**
     * 字符串格式化
     *
     * @param template 模板字符串，变量使用 {} 表示
     * @param params   参数值
     */
    public static String format(String template, Object... params) {
        String result = template;
        for (Object arg : params) {
            result = result.replaceFirst("\\{}", String.valueOf(arg));
        }
        return result;
    }


    /**
     * 将数组转换为逗号分隔的字符串
     *
     * @param array 数组
     * @return 返回逗号分割的字符串
     */
    public static String arrayToString(Object[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }


}
