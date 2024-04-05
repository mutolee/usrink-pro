package ink.usr.framework.mysql.config.properties.env.prod;

import ink.usr.framework.mysql.config.properties.base.DruidBaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * Druid 生产环境，主库配置
 */
@Configuration(value = "druidMasterProperties")
@Profile("prod")
@PropertySource("classpath:db/mysql/druid-mysql-prod.properties")
@ConfigurationProperties(prefix = "master.druid.datasource")
public class DruidMasterProperties extends DruidBaseProperties {
}
