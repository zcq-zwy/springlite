package com.zcqzwy.springframework.aop.framework.adapter;

import com.zcqzwy.springframework.aop.AfterAdvice;
import com.zcqzwy.springframework.aop.AfterReturningAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: AfterReturningAdviceInterceptor </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/29 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class AfterReturningAdviceInterceptor implements MethodInterceptor, AfterAdvice {

  private  AfterReturningAdvice advice;

  public AfterReturningAdviceInterceptor() {
  }

  public AfterReturningAdviceInterceptor(AfterReturningAdvice advice) {
    this.advice = advice;
  }


  @Override
  public Object invoke(MethodInvocation mi) throws Throwable {
    Object retVal = mi.proceed();
    this.advice.afterReturning(retVal, mi.getMethod(), mi.getArguments(), mi.getThis());
    return retVal;
  }
}
