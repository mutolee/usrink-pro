package ink.usr.common.entity.model.mysql;

import lombok.*;
import java.io.Serializable;

/**
 * SysRole(角色信息表) 的 Model 类，自动生成。
 *
 * @author http://usr.ink
 * @date 2025-03-04 00:54:11
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleModel implements Serializable {

    // -------------------------------------------------------------------
    // 该类为 SysRole(角色信息表) 的 Model 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:54:11
    //
    //
    /**
     * 角色ID，主键
     */
    private java.lang.Long roleId;
    /**
     * 角色名称
     */
    private java.lang.String roleName;
    /**
     * 角色权限标识符
     */
    private java.lang.String rolePermKey;
    /**
     * 角色拥有的菜单集合，也就是角色对应的权限集合，由菜单ID以“,”分割。
     */
    private java.lang.String roleMenuIds;
    /**
     * 角色描述
     */
    private java.lang.String roleDesc;
    /**
     * 创建时间
     */
    private java.lang.String createTime;
    /**
     * 更新时间
     */
    private java.lang.String updateTime;
    /**
     * 角色状态，0表示正常，-1表示禁用
     */
    private java.lang.Integer status;
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:54:11
    //
    //
    // 该类为 SysRole(角色信息表) 的 Model 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    // -------------------------------------------------------------------
}
