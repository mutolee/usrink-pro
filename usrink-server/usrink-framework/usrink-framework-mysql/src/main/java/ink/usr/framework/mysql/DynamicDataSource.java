package ink.usr.framework.mysql;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * DynamicDataSource 是一个继承自 AbstractRoutingDataSource 的数据源类，
 * 用于动态路由到不同的数据源。它通过调用 DynamicDataSourceContextHolder 的 getDataSourceKey 方法，
 * 获取当前线程上下文中的数据源键，以决定当前需要使用的数据源。
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 通过调用 DynamicDataSourceContextHolder 获取当前线程上下文中的数据源键，
     * 以决定当前需要使用的数据源
     *
     * @return 当前数据源键
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceKey();
    }

}
