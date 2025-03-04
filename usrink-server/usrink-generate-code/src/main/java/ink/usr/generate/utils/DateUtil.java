package ink.usr.generate.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     * 获取当前系统时间，格式：yyyy-MM-dd HH:mm:ss
     *
     * @return 格式化后的时间字符串
     */
    public static String now() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

}
