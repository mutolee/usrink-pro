package ink.usr.framework.shiro.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * JWT工具类，提供JWT的生成、解析、验证等功能
 */
public class JwtUtil {

    // 秘钥，默认为 ink-usr-jwt-secret-123456，可通过 init 方法修改
    private static String SECRET = "ink-usr-jwt-secret-123456";

    // 过期时间，单位（MS），默认为7天
    private static long EXPIRE = 1000 * 60 * 60 * 24 * 7;


    /**
     * 初始化 JWTUtil 配置
     */
    public static void init(String secret, long expire) {
        SECRET = secret;
        EXPIRE = expire;
    }


    /**
     * 生成JWT Token
     *
     * @param userName     用户名
     * @param userId       用户ID
     * @param userPassword 用户密码
     */
    public static String createToken(String userName, long userId, String userPassword) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRE);

        return JWT.create()
                .withClaim("userName", userName)
                .withClaim("userId", userId)
                .withClaim("userPassword", userPassword)
                .withExpiresAt(expiryDate)
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 解析JWT Token
     *
     * @param token JWT Token
     * @return 解析后的JWT对象
     */
    public static DecodedJWT parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token);
    }

    /**
     * 验证JWT Token
     *
     * @param token JWT Token
     * @return 是否验证通过
     */
    public static boolean verifyToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SECRET))
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证JWT Token是否过期
     *
     * @param token JWT Token
     * @return 是否过期
     */
    public static boolean isExpired(String token) {
        return parseToken(token).getExpiresAt().before(new Date());
    }

    /**
     * 获取JWT Token中的用户名
     *
     * @param token JWT Token
     * @return 用户名
     */
    public static String getUserName(String token) {
        return parseToken(token).getClaim("userName").asString();
    }

    /**
     * 获取JWT Token中的用户ID
     *
     * @param token JWT Token
     * @return 用户ID
     */
    public static long getUserId(String token) {
        return parseToken(token).getClaim("userId").asLong();
    }

    /**
     * 获取JWT Token中的密码
     *
     * @param token JWT Token
     * @return 密码
     */
    public static String getPassword(String token) {
        return parseToken(token).getClaim("password").asString();
    }

}
