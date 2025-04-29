package com.zcqzwy.springframework.context.support;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.factory.Aware;
import com.zcqzwy.springframework.beans.factory.config.BeanPostProcessor;
import com.zcqzwy.springframework.context.ApplicationContextAware;
import com.zcqzwy.springframework.context.ConfigurableApplicationContext;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ApplicationContextAwareProcessor </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
class ApplicationContextAwareProcessor implements BeanPostProcessor {

  private final ConfigurableApplicationContext applicationContext;


  /**
   * Create a new ApplicationContextAwareProcessor for the given context.
   */
  public ApplicationContextAwareProcessor(ConfigurableApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }


  @Override
  public Object postProcessBeforeInitialization(final Object bean, String beanName)
      throws BeansException {

    invokeAwareInterfaces(bean);
    return bean;
  }

  private void invokeAwareInterfaces(Object bean) {
    if (bean instanceof Aware) {
      if (bean instanceof ApplicationContextAware) {
        ((ApplicationContextAware) bean).setApplicationContext(this.applicationContext);
      }
    }
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) {
    return bean;
  }


}
