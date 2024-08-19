package ink.usr.common.core.enums;

import lombok.Getter;

/**
 * 接口状态码
 */
@Getter
public enum StatusCode {

    /**
     * 成功
     */
    SUCCESS(200),

    /**
     * 请求地址不存在
     */
    NOT_FOUND(404),

    /**
     * 异常，失败，如需要更详情的错误码，可在此枚举中自行扩展
     */
    ERROR(300),

    /**
     * 未登录，未认证
     */
    UNAUTHORIZED(301),

    /**
     * 未授权，无权限
     */
    FORBIDDEN(302);

    private final int code;

    StatusCode(int code) {
        this.code = code;
    }

}
