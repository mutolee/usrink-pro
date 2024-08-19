package ink.usr.framework.mysql.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import ink.usr.common.core.utils.SpringBeanUtil;
import ink.usr.framework.mysql.DynamicDataSource;
import ink.usr.framework.mysql.config.properties.base.DruidBaseProperties;
import ink.usr.framework.mysql.enums.Ds;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置<br>
 * <br>
 * 1. 主库数据源配置<br>
 * 2. 从库数据源配置<br>
 * 3. 动态数据源配置<br>
 * 4. Mybatis SqlSessionFactory配置<br>
 * 5. 事务管理器配置<br>
 */
@Slf4j
@Configuration
@MapperScan(basePackages = "ink.usr.*.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {

    /**
     * 主库数据源配置
     *
     * @param baseProperties 指定的数据源配置
     */
    @Bean("masterDataSource")
    public DataSource masterDataSource(@Qualifier("druidMasterProperties") DruidBaseProperties baseProperties) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        log.debug("配置 mysql master datasource 完成.");
        return baseProperties.dataSource(dataSource);
    }

    /**
     * 从库数据源配置
     *
     * @param baseProperties 指定的数据源配置
     */
    @Bean("slaveDataSource")
    @ConditionalOnProperty(name = "slave.druid.datasource.enable", havingValue = "true")
    public DataSource slaveDataSource(@Qualifier("druidSlaveProperties") DruidBaseProperties baseProperties) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        log.debug("配置 mysql slave datasource 完成.");
        return baseProperties.dataSource(dataSource);
    }

    /**
     * 动态数据源配置
     */
    @Bean(name = "dynamicDataSource")
    public DynamicDataSource DataSource() {
        DynamicDataSource dataSource = new DynamicDataSource();
        // 设置默认数据源，当没有指定数据源时使用默认数据源
        DataSource masterDataSource = (DataSource) SpringBeanUtil.getBean("masterDataSource");
        dataSource.setDefaultTargetDataSource(masterDataSource);

        // 设置多数据源
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(Ds.W, masterDataSource);

        try {
            // 如果从库数据源存在，则设置从库数据源
            DataSource slaveDataSource = (DataSource) SpringBeanUtil.getBean("slaveDataSource");
            targetDataSource.put(Ds.R, slaveDataSource);
        } catch (Exception e) {
            log.warn("slave datasource not found. 如果没有开启从库，请忽略该警告！");
        }

        dataSource.setTargetDataSources(targetDataSource);
        log.debug("配置 mysql dynamic datasource 完成. targetDataSource 数量: {}", targetDataSource.size());
        return dataSource;
    }

    /**
     * Mybatis SqlSessionFactory配置
     *
     * @param dataSource 动态数据源
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            // 业务模块Mapping配置文件
            Resource[] resourcesServiceModel = resolver.getResources("classpath:sqlconf/*Mapping.xml");
            sqlSessionFactoryBean.setMapperLocations(resourcesServiceModel);
            log.debug("配置 sqlSessionFactory 完成.");
            return sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            log.error("Mybatis SqlSessionFactory配置异常", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 事务管理器配置
     *
     * @param dataSource 动态数据源
     */
    @Bean(name = "dataSourceTx")
    public DataSourceTransactionManager transactionManager(@Qualifier("dynamicDataSource") DataSource dataSource) {
        log.debug("配置 transactionManager 完成.");
        return new DataSourceTransactionManager(dataSource);
    }
}
