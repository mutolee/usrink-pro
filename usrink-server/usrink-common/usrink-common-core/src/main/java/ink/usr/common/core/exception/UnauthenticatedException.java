package ink.usr.common.core.exception;

import ink.usr.common.core.exception.base.BusinessException;

/**
 * 认证异常
 */
public class UnauthenticatedException extends BusinessException {

    public UnauthenticatedException() {
        super();
    }

    public UnauthenticatedException(String message) {
        super(message);
    }
}
