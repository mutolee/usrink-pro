package ink.usr.common.model.mysql;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysLogLoginModel implements Serializable {

    /**
     * 日志ID
     */
    private Long logId;

    /**
     * 登录用户名
     */
    private String userName;

    /**
     * 请求地址
     */
    private String reqUrl;

    /**
     * 请求参数
     */
    private String reqParam;

    /**
     * 请求方式
     */
    private String reqType;

    /**
     * 登录IP地址
     */
    private String ipAddr;

    /**
     * 登录地点
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
     * 登录耗时，单位毫秒
     */
    private Long costTime;

    /**
     * 登录结果，如果成功，记录返回的json字符串；如果失败，记录异常信息
     */
    private String result;

    /**
     * 登录状态，0成功，-1失败
     */
    private Integer status;

}
