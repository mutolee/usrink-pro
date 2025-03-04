package ink.usr.generate.entity;

import ink.usr.generate.utils.DateUtil;
import lombok.*;

/**
 * 项目配置
 */
@Data
@NoArgsConstructor
@Builder
@ToString
@AllArgsConstructor
public class ProjectConfig {

    /**
     * 项目地址
     */
    private String projectPath;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 时间
     */
    private String dateTime;


    public static ProjectConfig buildProjectConfig(String projectPath, String projectName, String moduleName, String packageName) {
        return ProjectConfig.builder()
                .projectPath(projectPath)
                .projectName(projectName)
                .moduleName(moduleName)
                .packageName(packageName)
                .dateTime(DateUtil.now())
                .build();
    }
}
