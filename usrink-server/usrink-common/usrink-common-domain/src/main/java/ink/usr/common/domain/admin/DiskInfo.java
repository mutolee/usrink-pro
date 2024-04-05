package ink.usr.common.domain.admin;

import lombok.*;

/**
 * 磁盘信息
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiskInfo {

    /**
     * 总大小
     */
    private String totalSize;

    /**
     * 剩余大小
     */
    private String freeSize;

    /**
     * 已经使用量
     */
    private String usedSize;

    /**
     * 磁盘的使用率
     */
    private double usageRate;

}
