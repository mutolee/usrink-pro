package ink.usr.framework.shiro.domain;

import lombok.*;

import java.io.Serializable;


/**
 * Shiro用户模型
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShiroUserInfo implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPassword;

}
