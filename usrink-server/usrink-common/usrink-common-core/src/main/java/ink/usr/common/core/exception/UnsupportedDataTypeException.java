package ink.usr.common.core.exception;

import ink.usr.common.core.exception.base.BusinessException;

/**
 * 不支持的数据类型异常
 * <p>
 * 用于在进行数据转换时，如果不支持的数据类型，抛出此异常
 * </p>
 */
public class UnsupportedDataTypeException extends BusinessException {

    public UnsupportedDataTypeException(String message) {
        super(message);
    }

}
