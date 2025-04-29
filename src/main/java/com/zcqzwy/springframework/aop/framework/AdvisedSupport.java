package com.zcqzwy.springframework.aop.framework;

import com.zcqzwy.springframework.aop.Advisor;
import com.zcqzwy.springframework.aop.MethodMatcher;
import com.zcqzwy.springframework.aop.TargetSource;

import com.zcqzwy.springframework.aop.framework.ProxyConfig;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: AdvisedSupport </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class AdvisedSupport extends ProxyConfig {


  /**
   * 被代理的目标对象
   */
  private TargetSource targetSource;
  /**
   * 方法拦截器
   */
  private MethodInterceptor methodInterceptor;


  /**
   * 方法匹配器(检查目标方法是否符合通知条件)
   */
  private MethodMatcher methodMatcher;

  private transient Map<Integer, List<Object>> methodCache;

  AdvisorChainFactory advisorChainFactory = new DefaultAdvisorChainFactory();

  private List<Advisor> advisors = new ArrayList<>();

  public AdvisedSupport() {
    this.methodCache = new ConcurrentHashMap<>(32);
  }

  public void addAdvisor(Advisor advisor) {
    advisors.add(advisor);
  }

  public List<Advisor> getAdvisors() {
    return advisors;
  }

  /**
   * 用来返回方法的拦截器链
   */
  public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) {
    Integer cacheKey=method.hashCode();
    List<Object> cached = this.methodCache.get(cacheKey);
    if (cached == null) {
      cached = this.advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(
          this, method, targetClass);
      this.methodCache.put(cacheKey, cached);
    }
    return cached;
  }








  public TargetSource getTargetSource() {
    return targetSource;
  }

  public void setTargetSource(TargetSource targetSource) {
    this.targetSource = targetSource;
  }

  public MethodInterceptor getMethodInterceptor() {
    return methodInterceptor;
  }

  public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
    this.methodInterceptor = methodInterceptor;
  }

  public MethodMatcher getMethodMatcher() {
    return methodMatcher;
  }

  public void setMethodMatcher(MethodMatcher methodMatcher) {
    this.methodMatcher = methodMatcher;
  }

}