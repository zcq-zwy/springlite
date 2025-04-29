package com.zcqzwy.springframework.aop.framework;

import com.zcqzwy.springframework.aop.BeforeAdvice;
import java.lang.reflect.Method;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: MethodBeforeAdvice </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/27 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface MethodBeforeAdvice extends BeforeAdvice {

  void before(Method method, Object[] args, Object target) throws Throwable;
}
