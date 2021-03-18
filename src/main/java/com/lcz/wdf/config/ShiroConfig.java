package com.trs.ai.ty.config;

import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置
 *
 * @author lianglu
 * @version 1.0
 * @date 2020/9/27 17:32
 */
@Configuration
public class ShiroConfig {

  @Value("${shiro.session.id}")
  private String sessionName;

  @Bean(name = "sessionDAO")
  public MemorySessionDAO getMemorySessionDAO() {
    return new MemorySessionDAO();
  }

  @Bean(name = "sessionIdCookie")
  public SimpleCookie getSimpleCookie() {
    SimpleCookie simpleCookie = new SimpleCookie();
    simpleCookie.setName(sessionName);
    return simpleCookie;
  }

  // 配置shiro session 的一个管理器
  @Bean(name = "shiroSessionManager")
  public DefaultWebSessionManager getDefaultWebSessionManager() {
    DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
    sessionManager.setSessionDAO(getMemorySessionDAO());
    sessionManager.setSessionIdCookie(getSimpleCookie());
    return sessionManager;
  }

  @Bean(name = "shiroCacheManager")
  public MemoryConstrainedCacheManager getMemoryConstrainedCacheManager() {
    return new MemoryConstrainedCacheManager();
  }

  @Bean
  @ConditionalOnMissingBean
  public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
    advisorAutoProxyCreator.setProxyTargetClass(true);
    return advisorAutoProxyCreator;
  }

  /** 将自己的验证方式加入容器 */
  @Bean
  public CustomRealm myShiroRealm() {
    return new CustomRealm();
  }

  /** 权限管理，配置主要是Realm的管理认证 */
  @Bean
  public SecurityManager securityManager() {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setRealm(myShiroRealm());
    securityManager.setCacheManager(getMemoryConstrainedCacheManager());
    securityManager.setSessionManager(getDefaultWebSessionManager());
    return securityManager;
  }

  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    shiroFilterFactoryBean.setSecurityManager(securityManager);
    Map<String, String> map = new LinkedHashMap<>(16);
    // 登出
    map.put("/login", "anon");
    map.put("/auth/code", "anon");
    map.put("/mq/**", "anon");
    map.put("/error", "anon");
    map.put("/logout", "anon");
    map.put("/event/**","anon");
    map.put("/wordCloud/ciyun","anon");
    map.put("/entity/**","anon");
    map.put("/entity/list","anon");
    map.put("/ruleApply/**","anon");
    map.put("/entity/data/list", "anon");
    map.put("/ruleApply/expression/name", "anon");
    map.put("/entity/meta/thead", "anon");
    map.put("/ss/search/image", "anon");
    map.put("/ss/sample/image", "anon");
    map.put("/tag/agent", "anon");
    map.put("/task/status", "anon");
    map.put("/task/transfer", "anon");
    map.put("/data/set/agent", "anon");
    map.put("/operation/detail/insert", "anon");
    map.put("/data/detail", "anon");
    map.put("/data/detail/delete", "anon");
    map.put("/evaluate/status", "anon");
    map.put("/evaluate/transfer", "anon");
    // 对所有用户认证
    map.put("/**", "authc");
    // 登录
    shiroFilterFactoryBean.setLoginUrl("/unauthorized");
    shiroFilterFactoryBean.setSuccessUrl("/index");
    shiroFilterFactoryBean.setUnauthorizedUrl("/error");
    shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

    return shiroFilterFactoryBean;
  }

  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
      SecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
        new AuthorizationAttributeSourceAdvisor();
    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
    return authorizationAttributeSourceAdvisor;
  }
}
