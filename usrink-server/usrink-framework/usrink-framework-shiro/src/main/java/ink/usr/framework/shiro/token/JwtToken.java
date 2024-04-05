package ink.usr.framework.shiro.token;

import lombok.Getter;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * JwtToken，用于Shiro的认证
 */
@Getter
public class JwtToken implements AuthenticationToken {

    // JWT令牌
    private final String token;

    /**
     * 构造 JwtToken
     *
     * @param token JWT令牌
     */
    public JwtToken(String token) {
        this.token = token;
    }

    /**
     * 获取身份
     *
     * @return 身份，即JWT令牌
     */
    @Override
    public Object getPrincipal() {
        return token;
    }

    /**
     * 获取凭证
     *
     * @return 凭证，即JWT令牌
     */
    @Override
    public Object getCredentials() {
        return token;
    }
}
