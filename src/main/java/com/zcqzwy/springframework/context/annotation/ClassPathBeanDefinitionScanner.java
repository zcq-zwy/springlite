package com.zcqzwy.springframework.context.annotation;

import com.zcqzwy.springframework.beans.factory.config.BeanDefinition;
import com.zcqzwy.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.zcqzwy.springframework.stereotype.Component;
import com.zcqzwy.springframework.util.StringUtils;
import java.util.Set;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ClassPathBeanDefinitionScanner </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/27 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

  private BeanDefinitionRegistry registry;

  public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
    this.registry = registry;
  }

  public void doScan(String... basePackages) {
    for (String basePackage : basePackages) {
      Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
      for (BeanDefinition candidate : candidates) {
        // 解析bean的作用域
        String beanScope = resolveBeanScope(candidate);
        if (StringUtils.isNotEmpty(beanScope)) {
          candidate.setScope(beanScope);
        }
        //生成bean的名称
        String beanName = determineBeanName(candidate);
        //注册BeanDefinition
        registry.registerBeanDefinition(beanName, candidate);
      }
    }
  }

  /**
   * 获取bean的作用域
   *
   * @param beanDefinition
   * @return
   */
  private String resolveBeanScope(BeanDefinition beanDefinition) {
    Class<?> beanClass = beanDefinition.getBeanClass();
    Scope scope = beanClass.getAnnotation(Scope.class);
    if (scope != null) {
      return scope.value();
    }

    return StringUtils.EMPTY;
  }


  /**
   * 生成bean的名称
   *
   * @param beanDefinition
   * @return
   */
  private String determineBeanName(BeanDefinition beanDefinition) {
    Class<?> beanClass = beanDefinition.getBeanClass();
    Component component = beanClass.getAnnotation(Component.class);
    String value = component.value();
    if (StringUtils.isEmpty(value)) {
      value = StringUtils.lowerFirst(beanClass.getSimpleName());
    }
    return value;
  }
}
