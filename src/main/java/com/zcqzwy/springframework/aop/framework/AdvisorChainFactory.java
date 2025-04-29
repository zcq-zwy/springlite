package com.zcqzwy.springframework.aop.framework;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: AdvisorChainFactory </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/29 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/

import java.lang.reflect.Method;
import java.util.List;

public interface AdvisorChainFactory {


  List<Object> getInterceptorsAndDynamicInterceptionAdvice(AdvisedSupport config, Method method, Class<?> targetClass);

}
