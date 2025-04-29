package com.zcqzwy.springframework.bean;

import com.zcqzwy.springframework.aop.framework.MethodBeforeAdvice;
import java.lang.reflect.Method;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: UserServiceBeforeAdvice </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/27 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class UserServiceBeforeAdvice implements MethodBeforeAdvice {

  @Override
  public void before(Method method, Object[] args, Object target) throws Throwable {
    System.out.println("拦截方法：" + method.getName());
  }

}
