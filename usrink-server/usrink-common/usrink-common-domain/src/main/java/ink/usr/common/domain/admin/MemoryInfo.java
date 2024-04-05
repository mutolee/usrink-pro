package ink.usr.common.domain.admin;

import lombok.*;

/**
 * 内存信息
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemoryInfo {

    /**
     * 系统总内存
     */
    private String totalSize;

    /**
     * 已使用内存
     */
    private String used;

    /**
     * 剩余内存
     */
    private String free;

    /**
     * 内存使用率
     */
    private double usedRate;

    /**
     * Java 虚拟机允许的最大堆内存总量
     */
    private String jvmMaxMemory;

    /**
     * Java 虚拟机（JVM）当前已分配的堆内存的总量,
     * 初始时，Java 虚拟机会分配一个初始的堆内存大小, 以后 Java 虚拟机会根据应用程序的内存需求动态地调整堆内存的大小
     */
    private String jvmTotalMemory;

    /**
     * JVM空闲内存
     */
    private String jvmFreeMemory;

    /**
     * JVM已使用内存, totalMemory - freeMemory
     */
    private String jvmUsedMemory;

    /**
     * JVM可用内存, maxMemory - usedMemory
     */
    private String jvmUsableMemory;

    /**
     * Java 内存使用率
     */
    private double jvmUsedRate;
}
