package com.zcqzwy.springframework.aop;

import com.zcqzwy.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.zcqzwy.springframework.bean.AccountService;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: PointcutExpressionTest </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class PointcutExpressionTest {
  @Test
  public void testPointcutExpression() throws Exception {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* com.zcqzwy.springframework.bean.AccountService.*(..))");
    Class<AccountService> clazz = AccountService.class;
    Method method = clazz.getDeclaredMethod("test");
    System.out.println(pointcut.matches(clazz));
    System.out.println(pointcut.matches(method, clazz));

  }

}
