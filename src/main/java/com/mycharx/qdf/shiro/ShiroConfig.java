package com.mycharx.qdf.shiro;

import com.mycharx.qdf.shiro.cache.QdfRedisCacheManager;
import com.mycharx.qdf.shiro.jwt.JwtFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * RefreshToken过期时间-30分钟-30*60(秒为单位)
     */
//    @Value("${myshiro.shiro.refreshTokenExpireTime}")
//    private Integer refreshTokenExpireTime;

//    @Value("${myshiro.shiro.jwt-cache-key}")
//    private String jwtCacheKeyPrefix;

    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(MyRealm realm, QdfRedisCacheManager qdfRedisCacheManager) {
//        realm.setCachingEnabled(true);
//        //启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
//        realm.setAuthenticationCachingEnabled(true);
//        //缓存AuthenticationInfo信息的缓存名称
//        realm.setAuthenticationCacheName("authenticationCache");
        //启用授权缓存，即缓存AuthorizationInfo信息，默认false
//        realm.setAuthorizationCachingEnabled(true);
//        //缓存AuthorizationInfo信息的缓存名称
//        realm.setAuthorizationCacheName("authorizationCache");
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用自己的realm
        manager.setRealm(realm);
        // 设置自定义Cache缓存
//        manager.setCacheManager(qdfRedisCacheManager);
        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager,
                                          @Value("${myshiro.shiro.refreshTokenExpireTime}")
                                                  Integer refreshTokenExpireTime,
                                          @Value("${myshiro.shiro.jwt-cache-key}")
                                                      String jwtCacheKeyPrefix) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>();
        JwtFilter jwtFilter = new JwtFilter();
        jwtFilter.setRefreshTokenExpireTime(refreshTokenExpireTime);
        jwtFilter.setJwtCacheKeyPrefix(jwtCacheKeyPrefix);
        filterMap.put("jwt", jwtFilter);
        factoryBean.setFilters(filterMap);

        factoryBean.setSecurityManager(securityManager);
        factoryBean.setUnauthorizedUrl("/401");

        /*
         * 自定义url规则
         * http://shiro.apache.org/web.html#urls-
         */
        Map<String, String> filterRuleMap = new HashMap<>();
        // 所有请求通过我们自己的JWT Filter
        filterRuleMap.put("/**", "jwt");
        // 访问401和404页面不通过我们的Filter
        filterRuleMap.put("/401", "anon");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
