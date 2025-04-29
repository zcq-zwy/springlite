package com.zcqzwy.springframework.aop;

import java.lang.reflect.Method;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: MethodMatcher </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface MethodMatcher {

 /**
   * 执行静态检查给定的方法是否匹配。如果此
   * @return 此方法是否静态匹配
   */
  boolean matches(Method method, Class<?> targetClass);

}
