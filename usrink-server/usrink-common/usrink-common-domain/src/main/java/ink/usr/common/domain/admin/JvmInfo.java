package ink.usr.common.domain.admin;

import lombok.*;

/**
 * JVM信息
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JvmInfo {

    /**
     * JDK名称
     */
    private String javaName;

    /**
     * JDK版本
     */
    private String javaVersion;

    /**
     * JDK路径
     */
    private String javaHome;

    /**
     * Java启动时间
     */
    private String javaStartTime;

    /**
     * Java运行时间
     */
    private String javaRunTime;

    /**
     * Java运行参数
     */
    private String javaRunParams;

    /**
     * 项目路径
     */
    private String projectDir;

}
