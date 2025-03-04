package ink.usr.common.entity.model.mysql;

import lombok.*;
import java.io.Serializable;

/**
 * SysLogOperator(后台管理操作记录表) 的 Model 类，自动生成。
 *
 * @author http://usr.ink
 * @date 2025-03-04 00:50:00
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysLogOperatorModel implements Serializable {

    // -------------------------------------------------------------------
    // 该类为 SysLogOperator(后台管理操作记录表) 的 Model 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:50:00
    //
    //
    /**
     * 主键
     */
    private java.lang.Long logId;
    /**
     * 用户名
     */
    private java.lang.String userName;
    /**
     * 用户角色
     */
    private java.lang.String userRoleName;
    /**
     * 操作描述
     */
    private java.lang.String operatorDesc;
    /**
     * 请求地址
     */
    private java.lang.String reqUrl;
    /**
     * 请求方法，类名.方法名
     */
    private java.lang.String reqMethod;
    /**
     * 请求参数
     */
    private java.lang.String reqParam;
    /**
     * 请求类型
     */
    private java.lang.String reqType;
    /**
     * 请求IP地址
     */
    private java.lang.String ipAddr;
    /**
     * 请求地点
     */
    private java.lang.String location;
    /**
     * 请求的浏览器类型
     */
    private java.lang.String browser;
    /**
     * 请求的操作系统
     */
    private java.lang.String os;
    /**
     * 请求结果，如果成功，记录返回的json字符串；如果失败，记录异常信息
     */
    private java.lang.String result;
    /**
     * 请求耗时，单位毫秒
     */
    private java.lang.Long costTime;
    /**
     * 记录时间
     */
    private java.lang.String createTime;
    /**
     * 请求状态，0表示成功，-1表示失败
     */
    private java.lang.Integer status;
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:50:00
    //
    //
    // 该类为 SysLogOperator(后台管理操作记录表) 的 Model 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    // -------------------------------------------------------------------
}
