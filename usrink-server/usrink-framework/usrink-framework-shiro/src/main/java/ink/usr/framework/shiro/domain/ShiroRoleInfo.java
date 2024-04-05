package ink.usr.framework.shiro.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Shiro角色模型
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShiroRoleInfo implements Serializable {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色拥有的菜单ID集合
     */
    private String roleMenuIds;

    /**
     * 角色权限字符串，用于Shiro做角色判断
     * 如：Controller 中的 @RequiresRoles("admin")
     */
    private String rolePermKey;

    /**
     * 角色拥有的权限字符串集合，用于Shiro做权限判断<br />
     * 一个角色对应多个权限，一个权限对应一个权限字符串，<br />
     * 如：Controller 中的 @RequiresPermissions("sys:user:list")
     */
    private List<String> permKeys;

}
