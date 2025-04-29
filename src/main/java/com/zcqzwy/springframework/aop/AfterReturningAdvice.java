package com.zcqzwy.springframework.aop;

import java.lang.reflect.Method;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: AfterReturningAdvice </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/29 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface AfterReturningAdvice extends AfterAdvice {
  void afterReturning( Object returnValue, Method method, Object[] args,  Object target) throws Throwable;
}
