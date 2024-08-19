package ink.usr.admin.aspectj;

import ink.usr.admin.service.ShiroService;
import ink.usr.common.core.annotation.Log;
import ink.usr.common.core.utils.*;
import ink.usr.common.interfaces.admin.ISysLogOperatorService;
import ink.usr.common.model.mysql.SysLogOperatorModel;
import ink.usr.framework.shiro.domain.ShiroRoleInfo;
import ink.usr.framework.shiro.domain.ShiroUserInfo;
import ink.usr.framework.shiro.utils.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class SysOperatorAspectj {

    /**
     * 操作计时
     */
    private static final ThreadLocal<Long> Operator_use_time = new ThreadLocal<>();

    @Autowired
    private ShiroService shiroService;

    /**
     * 操作切入点
     * !execution(public * ink.usr.admin.controller.SysLoginController.login(..))
     * 除了登录接口，其他接口都会被拦截
     */
    @Pointcut("execution(public * ink.usr.admin.controller.*.*(..)) && !execution(public * ink.usr.admin.controller.SysLoginController.login(..))")
    public void operatorPoint() {

    }

    /**
     * 操作前置通知
     */
    @Before("operatorPoint()")
    public void doBeforeOperator() {
        Operator_use_time.set(System.currentTimeMillis());
    }

    /**
     * 操作后置通知
     *
     * @param joinPoint 切点
     * @param result    返回结果
     */
    @AfterReturning(pointcut = "operatorPoint()", returning = "result")
    public void doAfterOperator(JoinPoint joinPoint, Object result) {
        long useTime = System.currentTimeMillis() - Operator_use_time.get();
        Operator_use_time.remove();

        String jsonResult = JsonUtil.toJson(result);
        jsonResult = StringUtil.subString(jsonResult, 0, 1000);

        recordOperatorInfo(joinPoint, useTime, 0, jsonResult);
    }

    /**
     * 操作异常通知
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(pointcut = "operatorPoint()", throwing = "e")
    public void doAfterThrowingOperator(JoinPoint joinPoint, Throwable e) {
        long useTime = System.currentTimeMillis() - Operator_use_time.get();
        Operator_use_time.remove();

        String result = e.getMessage();
        result = StringUtil.subString(result, 0, 1000);

        recordOperatorInfo(joinPoint, useTime, -1, result);
    }

    /**
     * 记录操作日志
     *
     * @param joinPoint 切点
     * @param costTime  操作用时
     * @param status    操作状态，0成功，-1失败
     * @param result    操作结果
     */
    protected void recordOperatorInfo(JoinPoint joinPoint, long costTime, int status, String result) {
        SysLogOperatorModel sysLogOperatorModel = new SysLogOperatorModel();
        // 接口耗时
        sysLogOperatorModel.setCostTime(costTime);
        // 接口状态，0成功，-1失败
        sysLogOperatorModel.setStatus(status);
        // 返回结果
        sysLogOperatorModel.setResult(result);

        HttpServletRequest request = ServletUtil.getHttpServletRequest();
        // 请求的IP地址
        sysLogOperatorModel.setIpAddr(IpUtil.getClientIpAddr(request));
        String userAgent = request.getHeader("User-Agent");
        // 获取浏览器类型
        sysLogOperatorModel.setBrowser(UserAgentUtil.getBrowser(userAgent));
        // 获取操作系统
        sysLogOperatorModel.setOs(UserAgentUtil.getOs(userAgent));
        // 请求方法
        sysLogOperatorModel.setReqType(request.getMethod());
        // 请求URI
        sysLogOperatorModel.setReqUrl(request.getRequestURI());
        // 请求参数
        Map<String, Object> parameterMap = ServletUtil.getAllParameters();
        String paramJson = JsonUtil.toJson(parameterMap);
        sysLogOperatorModel.setReqParam(StringUtil.subString(paramJson, 0, 500));
        Signature signature = joinPoint.getSignature();
        // 请求的类
        String declaringTypeName = signature.getDeclaringTypeName();
        // 请求的方法
        String methodName = signature.getName();
        sysLogOperatorModel.setReqMethod(declaringTypeName + "." + methodName + "()");

        // 请求方法上的注解
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Log annotation = methodSignature.getMethod().getAnnotation(Log.class);
            if (annotation != null) {
                sysLogOperatorModel.setOperatorDesc(annotation.value());
            }else{
                sysLogOperatorModel.setOperatorDesc("未知操作");
            }
        }

        ShiroUserInfo shiroUserInfo = ShiroUtil.getShiroUserInfo();
        // 用户名
        sysLogOperatorModel.setUserName(shiroUserInfo.getUserName());
        ShiroRoleInfo shiroRoleInfo = shiroService.getRoleByUserId(shiroUserInfo.getUserId());
        // 用户角色
        String roleName = shiroRoleInfo != null ? shiroRoleInfo.getRoleName() : "默认角色";
        sysLogOperatorModel.setUserRoleName(roleName);

        // 执行异步任务
        AsyncUtil.executeAsyncTask(() -> {
            try {
                // 获取登录地址
                String locationJsonString = HttpUtil.get("https://whois.pconline.com.cn/ipJson.jsp?json=true&ip=" + sysLogOperatorModel.getIpAddr());
                Object addr = JsonUtil.parse(locationJsonString, Map.class).get("addr");
                sysLogOperatorModel.setLocation(addr.toString());
            } catch (Exception e) {
                log.error("获取登录地址失败", e);
            }

            // 保存登录日志
            SpringBeanUtil.getBean(ISysLogOperatorService.class).insertSysLogOperator(sysLogOperatorModel);
        });
    }
}
