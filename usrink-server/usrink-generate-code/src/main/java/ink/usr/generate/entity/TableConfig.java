package ink.usr.generate.entity;

import lombok.*;

/**
 * 表配置
 */
@Data
@NoArgsConstructor
@Builder
@ToString
@AllArgsConstructor
public class TableConfig {

    // 表名
    private String tableName;
    // jdbcUrl
    private String jdbcUrl;
    // 驱动
    private String jdbcDriver;
    // 数据库用户名
    private String jdbcUserName;
    // 数据库密码
    private String jdbcPassword;


    public static TableConfig buildTableConfig(String tableName, String jdbcUrl, String jdbcDriver, String jdbcUserName, String jdbcPassword) {
        return TableConfig.builder()
                .tableName(tableName)
                .jdbcUrl(jdbcUrl)
                .jdbcDriver(jdbcDriver)
                .jdbcUserName(jdbcUserName)
                .jdbcPassword(jdbcPassword)
                .build();
    }

}
