package ink.usr.framework.mysql.aspectj;

import ink.usr.framework.mysql.DynamicDataSourceContextHolder;
import ink.usr.framework.mysql.enums.Ds;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 数据源切面
 */
@Slf4j
@Aspect
@Component
public class DataSourceAspectj {

    /**
     * 定义有一个切入点，范围为Dao层下的类的方法
     */
    @Pointcut("execution(public * ink.usr.*.dao.*Dao.*(..))")
    public void daoPoint() {
    }


    /**
     * 根据参数值动态切换数据源
     */
    @Around("daoPoint()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        // 判断是否是第一次进来,用于处理嵌套调用（一个Dao中的方法调用另外一个Dao的方法叫做嵌套）
        // 怎么判断是否是第一次进来呢，判断DynamicDataSourceContextHolder.getDataSourceKey()是否为null，如果不为null,就表示不是第一次进来
        boolean isFirst = false;
        try {
            if (DynamicDataSourceContextHolder.getDataSourceKey() == null) {
                isFirst = true;
            }
            if (isFirst) {
                MethodSignature signature = (MethodSignature) point.getSignature();
                // 得到拦截的方法
                Method method = signature.getMethod();
                // 获取方法参数名
                // String[] paramNames = signature.getParameterNames();
                // 获取参数值
                Object[] paramValues = point.getArgs();
                // 获取参数类型
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (parameterTypes[i].getName().equals(Ds.class.getName())) {
                        if (paramValues[i].equals(Ds.R)) {
                            log.debug("切换到读库(Ds.R)");
                            DynamicDataSourceContextHolder.setDataSourceKey(Ds.R);
                        } else if (paramValues[i].equals(Ds.W)) {
                            log.debug("切换到写库(Ds.W)");
                            DynamicDataSourceContextHolder.setDataSourceKey(Ds.W);
                        }
                        break;
                    }
                }
            }
            return point.proceed();
        } finally {
            if (isFirst) {
                // 每次Dao层执行完毕，非嵌套(一个Dao中的方法调用另外一个Dao的方法叫做嵌套)情况下，清除该线程连接的主从标识
                // 如果为嵌套调用，这里isFirst就为false了，不会执行下面清除方法
                DynamicDataSourceContextHolder.clearDataSourceKey();
                log.debug("清除当前线程数据源标识");
            }
        }
    }


}
