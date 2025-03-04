package ink.usr.admin.controller;

import ink.usr.common.core.annotation.Log;
import ink.usr.common.core.entity.Res;
import ink.usr.common.core.utils.ByteUtil;
import ink.usr.common.core.utils.DateUtil;
import ink.usr.common.core.utils.StringUtil;
import ink.usr.common.entity.admin.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/sysServer")
public class SysServerController {

    // 创建 SystemInfo 对象
    private final SystemInfo systemInfo = new SystemInfo();

    /**
     * 查询CPU信息
     */
    @Log(value = "查询CPU信息")
    @RequestMapping(value = "/cpu")
    public Res selectSysServerCupInfo() {
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        CentralProcessor processor = hardware.getProcessor();
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        // 这里必须要设置延迟
        Util.sleep(1000);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softIrq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long ioWait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = Math.max(user + nice + cSys + idle + ioWait + irq + softIrq + steal, 0);
        DecimalFormat format = new DecimalFormat("#.00");

        CpuInfo cpuInfo = CpuInfo.builder()
                .coreCount(processor.getLogicalProcessorCount())
                .sysUsageRate(Double.parseDouble(format.format(cSys <= 0 ? 0 : (100d * cSys / totalCpu))))
                .userUsageRate(Double.parseDouble(format.format(user <= 0 ? 0 : (100d * user / totalCpu))))
                .idleRate(Double.parseDouble(format.format(idle <= 0 ? 0 : (100d * idle / totalCpu))))
                .build();

        CentralProcessor.ProcessorIdentifier processorIdentifier = processor.getProcessorIdentifier();
        String cpuMode = StringUtil.format("{}({})", processorIdentifier.getVendor(), processorIdentifier.getModel());
        cpuInfo.setCpuMode(cpuMode);

        return Res.success(cpuInfo);
    }

    /**
     * 查询磁盘信息
     */
    @Log(value = "查询Disk信息")
    @RequestMapping("/disk")
    public Res selectSysServerDiskInfo() {
        // 获取操作系统信息
        OperatingSystem os = systemInfo.getOperatingSystem();
        // 获取文件系统信息
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fileStores = fileSystem.getFileStores();

        DiskInfo diskInfo = DiskInfo.builder().build();
        DecimalFormat format = new DecimalFormat("#.00");
        // 遍历文件系统信息
        for (OSFileStore fileStore : fileStores) {
            // 判断文件系统是否是根目录 "/"
            if ("/".equals(fileStore.getMount())) {
                diskInfo.setTotalSize(ByteUtil.convertBytes(fileStore.getTotalSpace()));
                diskInfo.setFreeSize(ByteUtil.convertBytes(fileStore.getUsableSpace()));
                long used = fileStore.getTotalSpace() - fileStore.getUsableSpace();
                diskInfo.setUsedSize(ByteUtil.convertBytes(used));
                diskInfo.setUsageRate(Double.parseDouble(format.format(100d * used / fileStore.getTotalSpace())));
                break;
            }
        }

        return Res.success(diskInfo);
    }

    /**
     * 查询内存信息
     */
    @Log("查询Memory信息")
    @RequestMapping("/memory")
    public Res selectSysServerMemoryInfo() {
        // Java总内存
        long maxMemory = Runtime.getRuntime().maxMemory();
        // Java已分配的内存
        long totalMemory = Runtime.getRuntime().totalMemory();
        // 已经分配的内存中的空闲内存
        long freeMemory = Runtime.getRuntime().freeMemory();
        // 已经使用的内存
        long usedMemory = totalMemory - freeMemory;
        // 剩余可用内存
        long usableMemory = maxMemory - usedMemory;

        // 获取系统内存信息
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        long total = memory.getTotal();
        long free = memory.getAvailable();
        long used = total - free;

        DecimalFormat format = new DecimalFormat("#.00");
        MemoryInfo memoryInfo = MemoryInfo.builder()
                .totalSize(ByteUtil.convertBytes(total))
                .free(ByteUtil.convertBytes(free))
                .used(ByteUtil.convertBytes(used))
                .usedRate(Double.parseDouble(format.format(100d * used / total)))
                .jvmMaxMemory(ByteUtil.convertBytes(maxMemory))
                .jvmTotalMemory(ByteUtil.convertBytes(totalMemory))
                .jvmFreeMemory(ByteUtil.convertBytes(freeMemory))
                .jvmUsedMemory(ByteUtil.convertBytes(usedMemory))
                .jvmUsableMemory(ByteUtil.convertBytes(usableMemory))
                .jvmUsedRate(Double.parseDouble(format.format(100d * usedMemory / maxMemory)))
                .build();
        return Res.success(memoryInfo);
    }

    /**
     * 查询服务器信息
     */
    @Log("查询服务器信息")
    @RequestMapping("/server")
    public Res selectSysServerInfo() {
        ServerInfo serverInfo = ServerInfo.builder().build();
        try {
            // 获取本地主机名
            String hostname = InetAddress.getLocalHost().getHostName();
            serverInfo.setServerName(hostname);

            // 获取本地主机的 IP 地址
            String ip = InetAddress.getLocalHost().getHostAddress();
            serverInfo.setServerIp(ip);

        } catch (UnknownHostException e) {
            log.error("获取服务器内网信息异常");
        }

        // 获取操作系统类型
        String os = System.getProperty("os.name");
        serverInfo.setServerOs(os);

        // 获取系统架构类型
        String arch = System.getProperty("os.arch");
        serverInfo.setServerOsArch(arch);

        return Res.success(serverInfo);
    }

    /**
     * 查询Jvm信息
     */
    @Log("查询Jvm信息")
    @RequestMapping("/jvm")
    public Res selectSysJvmInfo() {
        JvmInfo jvmInfo = JvmInfo.builder()
                .javaName(System.getProperty("java.vendor"))
                .javaVersion(System.getProperty("java.version"))
                .javaHome(System.getProperty("java.home"))
                .projectDir(System.getProperty("user.dir"))
                .build();

        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        long startTime = runtimeMXBean.getStartTime();
        jvmInfo.setJavaStartTime(DateUtil.formatDate(startTime, DateUtil.YYYY_MM_DD_HH_MM_SS));
        jvmInfo.setJavaRunTime(DateUtil.timeDistance(DateUtil.timestampToDate(System.currentTimeMillis()), DateUtil.timestampToDate(startTime)));
        jvmInfo.setJavaRunParams(StringUtil.arrayToString(runtimeMXBean.getInputArguments().toArray()));

        return Res.success(jvmInfo);
    }

}
