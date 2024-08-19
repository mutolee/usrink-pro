package ink.usr.admin.aspectj;

import ink.usr.common.core.utils.*;
import ink.usr.common.interfaces.admin.ISysLogLoginService;
import ink.usr.common.model.mysql.SysLogLoginModel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class SysLoginAspectj {

    /**
     * 登录计时
     */
    private static final ThreadLocal<Long> Login_use_time = new ThreadLocal<>();


    /**
     * 登录切入点
     */
    @Pointcut("execution(public * ink.usr.admin.controller.SysLoginController.login(..))")
    public void loginPoint() {

    }

    /**
     * 登录前置通知
     */
    @Before("loginPoint()")
    public void doBeforeLogin() {
        Login_use_time.set(System.currentTimeMillis());
    }

    /**
     * 登录后置通知
     *
     * @param joinPoint 切点
     * @param result    返回结果
     */
    @AfterReturning(pointcut = "loginPoint()", returning = "result")
    public void doAfterLogin(JoinPoint joinPoint, Object result) {
        long useTime = System.currentTimeMillis() - Login_use_time.get();
        Login_use_time.remove();

        String jsonResult = JsonUtil.toJson(result);
        jsonResult = StringUtil.subString(jsonResult, 0, 1000);

        recordLoginInfo(useTime, 0, jsonResult);
    }

    /**
     * 登录异常通知
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(pointcut = "loginPoint()", throwing = "e")
    public void doAfterThrowingLogin(JoinPoint joinPoint, Throwable e) {
        long useTime = System.currentTimeMillis() - Login_use_time.get();
        Login_use_time.remove();

        String result = e.getMessage();
        result = StringUtil.subString(result, 0, 1000);

        recordLoginInfo(useTime, -1, result);
    }


    /**
     * 记录登录日志
     *
     * @param costTime 接口耗时
     * @param status   接口状态，0成功，-1失败
     * @param result   返回结果
     */
    private void recordLoginInfo(Long costTime, int status, String result) {
        SysLogLoginModel sysLogLoginModel = new SysLogLoginModel();
        // 接口耗时
        sysLogLoginModel.setCostTime(costTime);
        // 接口状态，0成功，-1失败
        sysLogLoginModel.setStatus(status);
        // 返回结果
        sysLogLoginModel.setResult(result);

        HttpServletRequest request = ServletUtil.getHttpServletRequest();
        // 请求的IP地址
        sysLogLoginModel.setIpAddr(IpUtil.getClientIpAddr(request));

        String userAgent = request.getHeader("User-Agent");
        // 获取浏览器类型
        sysLogLoginModel.setBrowser(UserAgentUtil.getBrowser(userAgent));
        // 获取操作系统
        sysLogLoginModel.setOs(UserAgentUtil.getOs(userAgent));
        // 请求方法
        sysLogLoginModel.setReqType(request.getMethod());
        // 请求URI
        sysLogLoginModel.setReqUrl(request.getRequestURI());
        // 请求参数
        Map<String, Object> parameterMap = ServletUtil.getAllParameters();
        sysLogLoginModel.setReqParam(JsonUtil.toJson(parameterMap));
        // 登录账号
        sysLogLoginModel.setUserName(ServletUtil.getParameter("userName"));

        // 执行异步任务
        AsyncUtil.executeAsyncTask(() -> {
            // 获取登录地址
            String locationJsonString = HttpUtil.get("https://whois.pconline.com.cn/ipJson.jsp?json=true&ip=" + sysLogLoginModel.getIpAddr());
            Object addr = JsonUtil.parse(locationJsonString, Map.class).get("addr");
            sysLogLoginModel.setLocation(addr.toString());

            // 保存登录日志
            SpringBeanUtil.getBean(ISysLogLoginService.class).insertSysLogLogin(sysLogLoginModel);
        });
    }

}
