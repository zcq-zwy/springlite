package com.zcqzwy.springframework.aop.framework.autoproxy;

import com.zcqzwy.springframework.aop.Advisor;
import com.zcqzwy.springframework.aop.ClassFilter;
import com.zcqzwy.springframework.aop.Pointcut;
import com.zcqzwy.springframework.aop.TargetSource;
import com.zcqzwy.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.zcqzwy.springframework.aop.framework.AdvisedSupport;
import com.zcqzwy.springframework.aop.framework.ProxyFactory;
import com.zcqzwy.springframework.aop.framework.SimpleTargetSource;
import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.PropertyValues;
import com.zcqzwy.springframework.beans.factory.BeanFactory;
import com.zcqzwy.springframework.beans.factory.BeanFactoryAware;
import com.zcqzwy.springframework.beans.factory.config.BeanDefinition;
import com.zcqzwy.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.zcqzwy.springframework.beans.factory.support.DefaultListableBeanFactory;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: DefaultAdvisorAutoProxyCreator </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/27 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor,
    BeanFactoryAware {


  private final Map<Object, Boolean> earlyProxyReferences = new ConcurrentHashMap<Object, Boolean>(16);
  private DefaultListableBeanFactory beanFactory;

  @Override
  public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {

    return null;
  }

  @Override
  public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName)
      throws BeansException {
    return pvs;
  }

  private boolean isInfrastructureClass(Class<?> beanClass) {
    return Advice.class.isAssignableFrom(beanClass)
        || Pointcut.class.isAssignableFrom(beanClass)
        || Advisor.class.isAssignableFrom(beanClass);
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = (DefaultListableBeanFactory) beanFactory;
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    return bean;
  }


  @Override
  public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
    if (!this.earlyProxyReferences.containsKey(beanName)) {
      this.earlyProxyReferences.put(beanName, Boolean.TRUE);
    }
    return wrapIfNecessary(bean, beanName);
  }


  protected Object wrapIfNecessary(Object bean, String beanName) {
    Class<?> beanClass = bean.getClass();
    //避免死循环
    if (isInfrastructureClass(beanClass)) {
      return null;
    }

    Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
    try {
      ProxyFactory proxyFactory = new ProxyFactory();
      for (AspectJExpressionPointcutAdvisor advisor : advisors) {
        ClassFilter classFilter = advisor.getPointcut().getClassFilter();
        if (classFilter.matches(beanClass)) {


          // 这时候不能选择原始的实列化策略了，不然就会有cast错误
          TargetSource targetSource = new SimpleTargetSource(bean);

          //返回代理对象
          proxyFactory.setTargetSource(targetSource);
          proxyFactory.addAdvisor(advisor);
          proxyFactory.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
        }
      }
      if(!proxyFactory.getAdvisors().isEmpty()) {
        return proxyFactory.getProxy();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new BeansException("Error create proxy bean for: " + beanName, ex);
    }
    // 如果没有匹配的切面，则返回原对象
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
      if(bean != null) {
        if (!earlyProxyReferences.containsKey(beanName)) {
          return wrapIfNecessary(bean, beanName);
        }
      }

    return bean;
  }


    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
    return true;
  }
}
