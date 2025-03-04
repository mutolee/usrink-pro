package ink.usr.common.entity.model.mysql;

import lombok.*;
import java.io.Serializable;

/**
 * SysLogLogin(后台管理登录记录表) 的 Model 类，自动生成。
 *
 * @author http://usr.ink
 * @date 2025-03-04 00:49:52
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysLogLoginModel implements Serializable {

    // -------------------------------------------------------------------
    // 该类为 SysLogLogin(后台管理登录记录表) 的 Model 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:49:52
    //
    //
    /**
     * 主键
     */
    private java.lang.Long logId;
    /**
     * 登录用户名
     */
    private java.lang.String userName;
    /**
     * 请求地址
     */
    private java.lang.String reqUrl;
    /**
     * 请求参数
     */
    private java.lang.String reqParam;
    /**
     * 请求类型
     */
    private java.lang.String reqType;
    /**
     * 登录IP地址
     */
    private java.lang.String ipAddr;
    /**
     * 登录地点
     */
    private java.lang.String location;
    /**
     * 登录的浏览器类型
     */
    private java.lang.String browser;
    /**
     * 登录的操作系统
     */
    private java.lang.String os;
    /**
     * 登录结果，如果成功，记录返回的json字符串；如果失败，记录异常信息
     */
    private java.lang.String result;
    /**
     * 登录耗时，单位毫秒
     */
    private java.lang.Long costTime;
    /**
     * 记录时间
     */
    private java.lang.String createTime;
    /**
     * 登录状态，0表示成功，-1表示登录失败
     */
    private java.lang.Integer status;
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:49:52
    //
    //
    // 该类为 SysLogLogin(后台管理登录记录表) 的 Model 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    // -------------------------------------------------------------------
}
