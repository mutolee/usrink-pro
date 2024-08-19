package ink.usr.common.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * SpringBeanUtil
 */
@Component
public class SpringBeanUtil implements BeanFactoryPostProcessor {

    /**
     * Spring应用上下文环境
     */
    private static ConfigurableListableBeanFactory beanFactory;

    /**
     * 实现BeanFactoryPostProcessor接口，获取Spring应用上下文环境
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringBeanUtil.beanFactory = beanFactory;
    }

    /**
     * 获取指定类型的Bean
     *
     * @param clazz 类型
     */
    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        return beanFactory.getBean(clazz);
    }

    /**
     * 获取指定名称的Bean
     *
     * @param name 名称
     */
    public static Object getBean(String name) {
        checkApplicationContext();
        return beanFactory.getBean(name);
    }


    /**
     * 检查Spring上下文是否已初始化
     */
    private static void checkApplicationContext() {
        if (beanFactory == null) {
            throw new IllegalStateException("ConfigurableListableBeanFactory 为空. Make sure SpringBeanUtil is initialized.");
        }
    }
}
