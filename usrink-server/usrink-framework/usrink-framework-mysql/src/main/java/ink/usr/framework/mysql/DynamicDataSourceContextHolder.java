package ink.usr.framework.mysql;

import ink.usr.framework.mysql.enums.Ds;

/**
 * DynamicDataSourceContextHolder 是一个管理动态选择数据源键的实用工具类，
 * 基于当前线程的上下文。该类通常用于多数据库环境，其中应用程序的不同部分需要与不同的数据源交互。
 */
public class DynamicDataSourceContextHolder {

    /**
     * ThreadLocal 用于存储当前线程的数据源键
     */
    private static final ThreadLocal<Ds> dataSourceKey = new ThreadLocal<>();

    /**
     * 获取当前线程设置的数据源键
     *
     * @return 当前数据源键
     */
    public static Ds getDataSourceKey() {
        return dataSourceKey.get();
    }

    /**
     * 设置当前线程的数据源键
     *
     * @param key 要为当前线程设置的数据源键
     */
    public static void setDataSourceKey(Ds key) {
        dataSourceKey.set(key);
    }

    /**
     * 清除当前线程的数据源键
     */
    public static void clearDataSourceKey() {
        dataSourceKey.remove();
    }

}
