package com.zcqzwy.springframework.aop.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: JdkDynamicAopProxy </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

  private final AdvisedSupport advised;

  public JdkDynamicAopProxy(AdvisedSupport advised) {
    this.advised = advised;
  }

  @Override
  public Object getProxy() {

    return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), advised.getTargetSource().getTargetClass(), this);
  }

  @Override
  public Object getProxy(ClassLoader classLoader) {
    return null;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    // 获取目标对象
    Object target=advised.getTargetSource().getTarget();
    Class<?> targetClass = target.getClass();
    Object retVal = null;
    // 获取拦截器链
    List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
    if(chain==null||chain.isEmpty()){
      return method.invoke(target, args);
    }else{
      // 将拦截器统一封装成ReflectiveMethodInvocation
      MethodInvocation invocation =
          new ReflectiveMethodInvocation(proxy, target, method, args, targetClass, chain);
      // Proceed to the joinpoint through the interceptor chain.
      // 执行拦截器链
      retVal = invocation.proceed();
    }
    return retVal;
  }

}
