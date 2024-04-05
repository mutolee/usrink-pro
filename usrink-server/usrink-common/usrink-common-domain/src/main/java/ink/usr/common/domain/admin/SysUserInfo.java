package ink.usr.common.domain.admin;

import ink.usr.common.model.mysql.SysUserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户信息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SysUserInfo extends SysUserModel {

    /**
     * 角色名称
     */
    private String roleName;

}
