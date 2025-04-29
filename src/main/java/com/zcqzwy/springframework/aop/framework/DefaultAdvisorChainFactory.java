package com.zcqzwy.springframework.aop.framework;

import com.zcqzwy.springframework.aop.Advisor;
import com.zcqzwy.springframework.aop.MethodMatcher;
import com.zcqzwy.springframework.aop.PointcutAdvisor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: DefaultAdvisorChainFactory </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/29 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class DefaultAdvisorChainFactory implements AdvisorChainFactory {
  @Override
  public List<Object> getInterceptorsAndDynamicInterceptionAdvice(AdvisedSupport config, Method method, Class<?> targetClass) {
    Advisor[] advisors = config.getAdvisors().toArray(new Advisor[0]);
    List<Object> interceptorList = new ArrayList<>(advisors.length);
    Class<?> actualClass = (targetClass != null ? targetClass : method.getDeclaringClass());
    for (Advisor advisor : advisors) {
      if (advisor instanceof PointcutAdvisor) {
        // Add it conditionally.
        PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
        // 校验当前Advisor是否适用于当前对象
        if (pointcutAdvisor.getPointcut().getClassFilter().matches(actualClass)) {
          MethodMatcher mm = pointcutAdvisor.getPointcut().getMethodMatcher();
          boolean match;
          // 校验Advisor是否应用到当前方法上
          match = mm.matches(method,actualClass);
          if (match) {
            MethodInterceptor interceptor = (MethodInterceptor) advisor.getAdvice();
            interceptorList.add(interceptor);
          }
        }
      }
    }
    return interceptorList;
  }
}
