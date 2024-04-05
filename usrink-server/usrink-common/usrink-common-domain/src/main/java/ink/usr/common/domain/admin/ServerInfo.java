package ink.usr.common.domain.admin;

import lombok.*;

/**
 * 操作系统信息
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerInfo {

    /**
     * 服务器名称
     */
    private String serverName;

    /**
     * 服务器IP
     */
    private String serverIp;

    /**
     * 服务器操作系统
     */
    private String serverOs;

    /**
     * 服务器系统架构
     */
    private String serverOsArch;
}
