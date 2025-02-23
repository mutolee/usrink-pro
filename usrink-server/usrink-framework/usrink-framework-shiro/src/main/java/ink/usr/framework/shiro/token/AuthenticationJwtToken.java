package ink.usr.framework.shiro.token;

import lombok.Getter;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * 用于Shiro的认证的Token；<br />
 * 这里保存的令牌字符串为JWT Token字符串，也可以是其它类型的Token，如自定义的Token。
 */
@Getter
public class AuthenticationJwtToken implements AuthenticationToken {

    // 令牌字符串
    private final String token;

    /**
     * 构造认证令牌
     *
     * @param token 令牌字符串
     */
    public AuthenticationJwtToken(String token) {
        this.token = token;
    }

    /**
     * 获取身份
     *
     * @return 身份，即令牌字符串
     */
    @Override
    public Object getPrincipal() {
        return token;
    }

    /**
     * 获取凭证
     *
     * @return 凭证，即令牌字符串
     */
    @Override
    public Object getCredentials() {
        return token;
    }
}
