package com.zcqzwy.springframework.aop.framework;

import java.lang.reflect.Method;
import java.util.List;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: CglibAopProxy </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class CglibAopProxy implements AopProxy {

  private final AdvisedSupport advised;

  public CglibAopProxy(AdvisedSupport advised) {
    this.advised = advised;
  }


  @Override
  public Object getProxy() throws Exception {
    //  创建cglib动态代理增强类
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(advised.getTargetSource().getTarget().getClass());
    enhancer.setInterfaces(advised.getTargetSource().getTargetClass());
    enhancer.setCallback(new DynamicAdvisedInterceptor(advised));
    return enhancer.create();
  }

  @Override
  public Object getProxy(ClassLoader classLoader) {
    return null;
  }

  /**
   * 注意此处的MethodInterceptor是cglib中的接口，advised中的MethodInterceptor的AOP联盟中定义的接口，因此定义此类做适配
   */
  private static class DynamicAdvisedInterceptor implements MethodInterceptor {

    private final AdvisedSupport advised;

    private DynamicAdvisedInterceptor(AdvisedSupport advised) {
      this.advised = advised;
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
      // 获取目标对象
      Object target=advised.getTargetSource().getTarget();
      Class<?> targetClass = target.getClass();
      Object retVal = null;
      List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
      CglibMethodInvocation methodInvocation = new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy);
      if(chain==null||chain.isEmpty()){
        retVal =methodProxy.invoke(target, args);
      }else retVal=methodInvocation.proceed();
      return retVal;
    }
  }

  private static class CglibMethodInvocation extends ReflectiveMethodInvocation {

    private final MethodProxy methodProxy;

    public CglibMethodInvocation(Object proxy, Object target, Method method,
        Object[] arguments, Class<?> targetClass,
        List<Object> interceptorsAndDynamicMethodMatchers, MethodProxy methodProxy) {
      super(proxy, target, method, arguments, targetClass, interceptorsAndDynamicMethodMatchers);
      this.methodProxy = methodProxy;
    }

    @Override
    public Object proceed() throws Throwable {
      return super.proceed();
    }
  }
}
