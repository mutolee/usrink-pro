package ink.usr.common.domain.admin;

import lombok.*;

/**
 * CPU 信息
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CpuInfo {

    /**
     * 核心数
     */
    private int coreCount;

    /**
     * CPU系统使用率
     */
    private double sysUsageRate;

    /**
     * CPU用户使用率
     */
    private double userUsageRate;

    /**
     * CPU当前空闲率
     */
    private double idleRate;

    /**
     * CPU型号
     */
    private String cpuMode;

}
