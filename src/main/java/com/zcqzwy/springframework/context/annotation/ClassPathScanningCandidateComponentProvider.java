package com.zcqzwy.springframework.context.annotation;

import com.zcqzwy.springframework.beans.factory.config.BeanDefinition;
import com.zcqzwy.springframework.stereotype.Component;
import com.zcqzwy.springframework.util.ClassUtils;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ClassPathScanningCandidateComponentProvider </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/27 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class ClassPathScanningCandidateComponentProvider {

  public Set<BeanDefinition> findCandidateComponents(String basePackage) {
    Set<BeanDefinition> candidates = new LinkedHashSet<>();
    // 扫描有org.springframework.stereotype.Component注解的类
    Set<Class<?>> classes = ClassUtils.scanPackageByAnnotation(basePackage, Component.class);
    for (Class<?> clazz : classes) {
      BeanDefinition beanDefinition = new BeanDefinition(clazz);
      candidates.add(beanDefinition);
    }
    return candidates;
  }
}
