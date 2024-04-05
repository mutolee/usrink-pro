package ink.usr.common.model.mysql;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysLogOperatorModel implements Serializable {

    /**
     * 日志ID
     */
    private Long logId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户角色
     */
    private String userRoleName;

    /**
     * 操作描述
     */
    private String operatorDesc;

    /**
     * 请求地址
     */
    private String reqUrl;

    /**
     * 请求方法
     */
    private String reqMethod;

    /**
     * 请求参数
     */
    private String reqParam;

    /**
     * 请求方式
     */
    private String reqType;

    /**
     * 请求IP地址
     */
    private String ipAddr;

    /**
     * 请求地点
     */
    private String location;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 客户端操作系统
     */
    private String os;

    /**
     * 记录时间
     */
    private String createTime;

    /**
     * 请求耗时，单位毫秒
     */
    private Long costTime;

    /**
     * 请求结果，如果成功，记录返回的json字符串；如果失败，记录异常信息
     */
    private String result;

    /**
     * 请求状态，0成功，-1失败
     */
    private Integer status;

}
