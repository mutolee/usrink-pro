package ink.usr.framework.mysql.config.properties.env.dev;

import ink.usr.framework.mysql.config.properties.base.DruidBaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * Druid 开发环境，主库配置
 */
@Configuration(value = "druidMasterProperties")
@Profile("dev")
@PropertySource("classpath:db/mysql/druid-mysql-dev.properties")
@ConfigurationProperties(prefix = "master.druid.datasource")
public class DruidMasterProperties extends DruidBaseProperties {
}
