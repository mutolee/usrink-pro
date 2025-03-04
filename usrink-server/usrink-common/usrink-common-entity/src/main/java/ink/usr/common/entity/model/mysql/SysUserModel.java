package ink.usr.common.entity.model.mysql;

import lombok.*;
import java.io.Serializable;

/**
 * SysUser(系统用户表) 的 Model 类，自动生成。
 *
 * @author http://usr.ink
 * @date 2025-03-04 00:56:31
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserModel implements Serializable {

    // -------------------------------------------------------------------
    // 该类为 SysUser(系统用户表) 的 Model 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:56:31
    //
    //
    /**
     * 系统用户ID
     */
    private java.lang.Long userId;
    /**
     * 系统用户名，登录名
     */
    private java.lang.String userName;
    /**
     * 用户密码，登录密码
     */
    private java.lang.String userPassword;
    /**
     * 用户角色ID，默认角色为默认角色
     */
    private java.lang.Long userRoleId;
    /**
     * 用户昵称，显示的昵称
     */
    private java.lang.String userNick;
    /**
     * 用户头像地址
     */
    private java.lang.String avatar;
    /**
     * 用户邮箱
     */
    private java.lang.String email;
    /**
     * 用户电话号码
     */
    private java.lang.String phone;
    /**
     * 创建时间
     */
    private java.lang.String createTime;
    /**
     * 更新时间
     */
    private java.lang.String updateTime;
    /**
     * 性别，0为女，1为男，-1为未知，默认-1
     */
    private java.lang.Integer sex;
    /**
     * 加密盐
     */
    private java.lang.String salt;
    /**
     * 用户状态，0表示正常，-1表示禁用不可登录，默认0。
     */
    private java.lang.Integer status;
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:56:31
    //
    //
    // 该类为 SysUser(系统用户表) 的 Model 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    // -------------------------------------------------------------------
}
