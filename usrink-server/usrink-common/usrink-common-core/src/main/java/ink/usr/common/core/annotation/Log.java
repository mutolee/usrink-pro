package ink.usr.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 定义注解
@Retention(RetentionPolicy.RUNTIME) // 指定注解在运行时可用
@Target(ElementType.METHOD) // 指定注解可以应用在方法上
public @interface Log {

    /**
     * 日志描述
     */
    String value() default "";

}
