package ink.usr.common.model.mysql;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleModel implements Serializable {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String rolePermKey;

    /**
     * 角色拥有的菜单集合，也就是角色对应的权限集合，由菜单ID以“,”分割。
     */
    private String roleMenuIds;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 用户状态, 0:正常, -1:禁用
     */
    private Integer status;

}
