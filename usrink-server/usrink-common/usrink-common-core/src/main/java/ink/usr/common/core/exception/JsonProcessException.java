package ink.usr.common.core.exception;

import ink.usr.common.core.exception.base.BusinessException;

/**
 * JSON处理异常
 * <p>
 * 用于在进行JSON处理时，如果出现异常，抛出此异常
 * </p>
 */
public class JsonProcessException extends BusinessException {

    public JsonProcessException(String message, Throwable cause) {
        super(message, cause);
    }

}
