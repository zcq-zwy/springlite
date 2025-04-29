package com.zcqzwy.springframework.aop.framework;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ReflectiveMethodInvocation </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class ReflectiveMethodInvocation implements MethodInvocation {

  protected final Object proxy;

  protected final Class<?> targetClass;

  protected final List<Object> interceptorsAndDynamicMethodMatchers;

  private int currentInterceptorIndex = -1;

  /**
   * 目标对象
   */
  protected final Object target;
  /**
   * 方法
   */
  protected final Method method;
  /**
   * 入参
   */
  protected final Object[] arguments;

  public ReflectiveMethodInvocation(Object proxy,Object target, Method method, Object[] arguments,Class<?> targetClass,List<Object> chain) {
    this.proxy=proxy;
    this.target = target;
    this.method = method;
    this.arguments = arguments;
    this.targetClass=targetClass;
    this.interceptorsAndDynamicMethodMatchers=chain;
  }

  @Override
  public Method getMethod() {
    return method;
  }

  @Override
  public Object[] getArguments() {
    return arguments;
  }

  @Override
  public Object proceed() throws Throwable {
    // 初始currentInterceptorIndex为-1，每调用一次proceed就把currentInterceptorIndex+1
    if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
      // 当调用次数 = 拦截器个数时
      // 触发当前method方法
      return method.invoke(this.target, this.arguments);
    }

    Object interceptorOrInterceptionAdvice =
        this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
    // 普通拦截器，直接触发拦截器invoke方法
    return ((MethodInterceptor) interceptorOrInterceptionAdvice).invoke(this);
  }

  @Override
  public Object getThis() {
    return target;
  }

  @Override
  public AccessibleObject getStaticPart() {
    return method;
  }

}
