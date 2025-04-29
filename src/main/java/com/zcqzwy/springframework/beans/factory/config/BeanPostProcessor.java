package com.zcqzwy.springframework.beans.factory.config;

import com.zcqzwy.springframework.beans.BeansException;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: BeanPostProcessor </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/25 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface BeanPostProcessor {
  /**
   * 在任何bean初始化回调（如InitializingBean的`afterPropertiesSet`方法或自定义的init方法）之前，
   * 将此BeanPostProcessor应用于给定的新bean实例。此时，bean已经填充了属性值。
   * 返回的bean实例可能是原始bean的包装器。
   * @param bean 新的bean实例
   * @param beanName bean的名称
   * @return 要使用的bean实例，可以是原始的或包装后的实例；如果返回`null`，则不会调用后续的BeanPostProcessor
   * @throws BeansException 如果发生错误
   */
  Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

  /**
   * 在任何bean初始化回调（如InitializingBean的`afterPropertiesSet`方法或自定义的init方法）之后，
   * 将此BeanPostProcessor应用于给定的新bean实例。此时，bean已经填充了属性值。
   * 返回的bean实例可能是原始bean的包装器。
   * <p>对于FactoryBean，此回调将同时应用于FactoryBean实例和由FactoryBean创建的对象
   * 后处理器可以通过相应的`bean instanceof FactoryBean`检查来决定是应用于FactoryBean还是创建的对象，或者两者都应用。
   * <p>此回调还会在由`InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation`方法触发的短路后调用，
   * 与其他所有BeanPostProcessor回调不同。
   * @param bean 新的bean实例
   * @param beanName bean的名称
   * @return 要使用的bean实例，可以是原始的或包装后的实例；如果返回`null`，则不会调用后续的BeanPostProcessor
   * @throws BeansException 如果发生错误
   */
  Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
