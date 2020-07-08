//package com.cloudera.poverty.base.config;
//
//import com.cloudera.poverty.base.realm.CustomRealm;
//import com.cloudera.poverty.session.CustomSessionManager;
//import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
//import org.apache.shiro.mgt.DefaultSecurityManager;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
//import org.crazycake.shiro.RedisCacheManager;
//import org.crazycake.shiro.RedisManager;
//import org.crazycake.shiro.RedisSessionDAO;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
///**
// * 描述：
// *
// * @author caojing
// * @create 2019-01-27-13:38
// */
//@Configuration
//public class ShiroConfig {
//
//    //创建realm
//    @Bean
//    public CustomRealm getRealm() {
//        CustomRealm customRealm = new CustomRealm();
//        return customRealm;
//    }
//
//    //创建安全管理器
//    @Bean
//    public SecurityManager getSecurityManager() {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//       securityManager.setRealm(getRealm());
//        //5.将自定义会话管理器注册到安全管理器
//        securityManager.setSessionManager(sessionManager());
//        //6.将自定义缓存管理器注册到安全管理器
//        securityManager.setCacheManager(cacheManager());
//        return securityManager;
//    }
//    //配置Shiro过滤器工厂
//    @Bean(name = "shiroFilter")
//    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();//创建过滤器工厂
//        shiroFilterFactoryBean.setSecurityManager(securityManager);//设置安全管理器
//        shiroFilterFactoryBean.setLoginUrl("/login");//未授权跳转页面
//        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");//没有权限默认跳转的页面
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
//        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
//        filterChainDefinitionMap.put("/webjars/**", "anon");
//        filterChainDefinitionMap.put("/v2/**", "anon");
//        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
//        filterChainDefinitionMap.put("/webjars/springfox-swagger-ui/**", "anon");
//        filterChainDefinitionMap.put("/login", "anon");
//        filterChainDefinitionMap.put("/", "anon");
//        filterChainDefinitionMap.put("/front/**", "anon");
//        filterChainDefinitionMap.put("/api/**", "anon");
//
//        //filterChainDefinitionMap.put("/admin/**", "authc");
//        //filterChainDefinitionMap.put("/user/**", "authc");
//        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截 剩余的都需要认证
//        //filterChainDefinitionMap.put("/**", "authc");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        return shiroFilterFactoryBean;
//
//    }
//
//    @Value("${spring.redis.host}")
//    private String host;
//    @Value("${spring.redis.port}")
//    private int port;
//    @Value("${spring.redis.password}")
//    private String password;
//    /**
//     * 1,redis的控制器操作redis,指定会话管理器
//     */
//    public RedisManager redisManager(){
//        RedisManager redisManager=new RedisManager();
//        redisManager.setHost(host);
//        redisManager.setPort(port);
//        redisManager.setPassword(password);
//        return redisManager;
//    }
//    /**
//     * 2.sessionDao
//     */
//    public RedisSessionDAO redisSessionDAO(){
//        RedisSessionDAO sessionDAO=new RedisSessionDAO();
//        sessionDAO.setRedisManager(redisManager());
//        return sessionDAO;
//    }
//    /**
//     * 3.会话管理器
//     */
//    public DefaultWebSessionManager sessionManager(){
//        CustomSessionManager sessionManager=new CustomSessionManager();
//        sessionManager.setSessionDAO(redisSessionDAO());
//       // sessionManager.setSessionIdUrlRewritingEnabled(false);
//        return sessionManager;
//    }
//    /**
//     * 4.缓存管理器
//     */
//    public RedisCacheManager cacheManager(){
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        redisCacheManager.setRedisManager(redisManager());
//        return redisCacheManager;
//    }
//    /**
//     *  开启Shiro的注解(如@RequiresRoles,@RequiresPermissions)
//     * @return
//     */
//    @Bean
//    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
//        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        advisorAutoProxyCreator.setProxyTargetClass(true);
//        return advisorAutoProxyCreator;
//    }
//
//    //开启注解方式
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor
//                = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }
//}
