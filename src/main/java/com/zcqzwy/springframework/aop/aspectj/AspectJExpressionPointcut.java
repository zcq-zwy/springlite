package com.zcqzwy.springframework.aop.aspectj;

import com.zcqzwy.springframework.aop.ClassFilter;
import com.zcqzwy.springframework.aop.MethodMatcher;
import com.zcqzwy.springframework.aop.Pointcut;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: AspectJExpressionPointcut </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {

  private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<PointcutPrimitive>();

  static {
    SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
  }

  private final PointcutExpression pointcutExpression;

  public AspectJExpressionPointcut(String expression) {
    PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORTED_PRIMITIVES, this.getClass().getClassLoader());
    pointcutExpression = pointcutParser.parsePointcutExpression(expression);
  }

  @Override
  public boolean matches(Class<?> clazz) {
    return pointcutExpression.couldMatchJoinPointsInType(clazz);
  }

  @Override
  public boolean matches(Method method, Class<?> targetClass) {
    return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
  }

  @Override
  public ClassFilter getClassFilter() {
    return this;
  }

  @Override
  public MethodMatcher getMethodMatcher() {
    return this;
  }

}
