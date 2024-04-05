package ink.usr.framework.shiro.config;

import ink.usr.framework.shiro.realm.UserRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Shiro配置
 */
@Slf4j
@Configuration
public class ShiroConfig {

    /**
     * 配置SecurityManager
     */
    @Bean
    public DefaultWebSecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm，用于认证和授权
        securityManager.setRealm(userRealm);

        // 禁用session，因为我们是基于JWT实现无状态的认证，不需要session。
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        // 设置 SecurityManager 到 SecurityUtils 中
        SecurityUtils.setSecurityManager(securityManager);

        log.debug("配置 Shiro SecurityManager");
        return securityManager;
    }

    /**
     * 开启Shiro注解，如@RequiresRoles，@RequiresPermissions
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        log.debug("配置AuthorizationAttributeSourceAdvisor，开启Shiro注解");
        return authorizationAttributeSourceAdvisor;
    }
}
