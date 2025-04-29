package com.zcqzwy.springframework.bean;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: UserServiceInterceptor </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/27 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class UserServiceInterceptor implements MethodInterceptor {

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    long start = System.currentTimeMillis();
    try {
      return invocation.proceed();
    } finally {
      System.out.println("监控 - Begin By AOP");
      System.out.println("方法名称：" + invocation.getMethod());
      System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
      System.out.println("监控 - End\r\n");
    }
  }

}
