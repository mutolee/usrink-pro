package ink.usr.common.core.exception;

import ink.usr.common.core.exception.base.BusinessException;

/**
 * 警告异常
 * <p>
 * 用于在进行业务处理时，如果出现警告，抛出此异常
 * </p>
 */
public class WarningException extends BusinessException {

    public WarningException(String message) {
        super(message);
    }

    public WarningException(String message, Throwable cause) {
        super(message, cause);
    }

}
