package ink.usr.common.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import ink.usr.common.core.enums.StatusCode;
import ink.usr.common.core.exception.UnsupportedDataTypeException;
import lombok.Getter;

import java.io.Serializable;

/**
 * 接口数据返回对象
 */
@Getter
public class Res implements Serializable {

    /**
     * 接口返回状态码
     */
    private int code;

    /**
     * 接口返回信息
     */
    private String msg;

    /**
     * 接口返回数据
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Object data;

    private Res() {
    }

    public static Res me() {
        return new Res();
    }

    public Res setCode(StatusCode statusCode) {
        this.code = statusCode.getCode();
        return this;
    }

    public Res setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Res setData(Object data) {
        if (data instanceof Iterable) {
            // 如果 data 是集合类型，抛出异常
            throw new UnsupportedDataTypeException("Data cannot be a collection type.");
        }

        this.data = data;
        return this;
    }

    public static Res success() {
        return success(null);
    }

    public static Res success(Object data) {
        return buildRes(data, StatusCode.SUCCESS, "操作成功！");
    }

    public static Res error() {
        return error("操作失败！");
    }

    public static Res error(String msg) {
        return buildRes(null, StatusCode.ERROR, msg);
    }

    /**
     * 构建接口数据返回对象
     *
     * @param data       接口返回数据
     * @param statusCode 接口返回状态码
     * @param msg        接口返回信息
     * @return Res
     */
    private static Res buildRes(Object data, StatusCode statusCode, String msg) {
        return Res.me()
                .setCode(statusCode)
                .setMsg(msg)
                .setData(data);
    }
}
