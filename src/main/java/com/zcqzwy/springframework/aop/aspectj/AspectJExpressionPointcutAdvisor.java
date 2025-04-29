package com.zcqzwy.springframework.aop.aspectj;

import com.zcqzwy.springframework.aop.Pointcut;
import com.zcqzwy.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: AspectJExpressionPointcutAdvisor </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/27 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

  private AspectJExpressionPointcut pointcut;

  private Advice advice;

  private String expression;

  public void setExpression(String expression) {
    this.expression = expression;
  }

  @Override
  public Pointcut getPointcut() {
    if (pointcut == null) {
      pointcut = new AspectJExpressionPointcut(expression);
    }
    return pointcut;
  }

  @Override
  public Advice getAdvice() {
    return advice;
  }

  public void setAdvice(Advice advice) {
    this.advice = advice;
  }
}
