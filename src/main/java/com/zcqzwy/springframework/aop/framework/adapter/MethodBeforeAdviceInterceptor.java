package com.zcqzwy.springframework.aop.framework.adapter;

import com.zcqzwy.springframework.aop.BeforeAdvice;
import com.zcqzwy.springframework.aop.framework.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: MethodBeforeAdviceInterceptor </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/27 </p>
 *  将MethodBeforeAdvice适配为MethodInterceptor
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class MethodBeforeAdviceInterceptor implements MethodInterceptor, BeforeAdvice {

  private MethodBeforeAdvice advice;


  public MethodBeforeAdviceInterceptor() {
  }

  public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
    this.advice = advice;
  }

  public MethodBeforeAdvice getAdvice() {
    return advice;
  }

  public void setAdvice(MethodBeforeAdvice advice) {
    this.advice = advice;
  }

  @Override
  public Object invoke(MethodInvocation mi) throws Throwable {
    this.advice.before(mi.getMethod(), mi.getArguments(), mi.getThis());
    return mi.proceed();
  }
}
