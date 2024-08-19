package ink.usr.common.core.exception.base;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {

    /**
     * 错误消息
     */
    private final String message;

    /**
     * 异常链
     */
    private Throwable cause;

    /**
     * 额外的错误信息
     */
    @Setter
    @Getter
    private Map<String, Object> additionalInfo;

    public BusinessException() {
        super();
        this.message = null;
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

}
