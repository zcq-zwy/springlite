package com.zcqzwy.springframework.aop;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: Pointcut </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface Pointcut {

  /**
   * 返回此切入点的 ClassFilter。
   *@return ClassFilter （never <code>null</code>）
   */
  ClassFilter getClassFilter();

/**
   * 返回此切入点的 MethodMatcher。
   *@return MethodMatcher （永不<code>为空</code>）
   */
  MethodMatcher getMethodMatcher();

}
