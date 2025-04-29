package com.zcqzwy.springframework.context.support;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.zcqzwy.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: AbstractRefreshableApplicationContext </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/25 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

  private DefaultListableBeanFactory beanFactory;

  @Override
  protected void refreshBeanFactory() throws BeansException {
    DefaultListableBeanFactory beanFactory = createBeanFactory();
    loadBeanDefinitions(beanFactory);
    this.beanFactory = beanFactory;
  }

  private DefaultListableBeanFactory createBeanFactory() {
    return new DefaultListableBeanFactory();
  }

  /**
   * 将 Bean 定义加载到给定的 Bean 工厂中，通常通过委托给一个或多个 Bean 定义读取器来实现。
   * @param beanFactory 要加载 Bean 定义的 Bean 工厂
   */
  protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

  @Override
  protected ConfigurableListableBeanFactory getBeanFactory() {
    return beanFactory;
  }

}
