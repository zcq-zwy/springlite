package com.zcqzwy.springframework.beans.factory.config;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.factory.BeanFactory;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: AutowireCapableBeanFactory </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 * 自动装配容器 支持Bean的自动装配（如@Autowired）、生命周期管理（初始化/销毁）
 * 并允许在容器外手动创建和装配Bean
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface AutowireCapableBeanFactory extends BeanFactory {
  /**
   * 将 {@link BeanPostProcessor BeanPostProcessors}
   * 应用于给定的现有 Bean 实例，调用它们的 {@code postProcessBeforeInitialization} 方法。
   * 返回的 Bean 实例可能是原始实例的包装对象。
   *
   * @param existingBean 现有的 Bean 实例
   * @param beanName Bean 的名称，如果需要可以传递给它
   * @return  返回的 Bean 实例可能是原始实例的包装对象
   * @throws BeansException 如果任何后处理失败，则抛出 BeansException
   */
  Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

  /**
   * 执行 BeanPostProcessors 接口实现类的 postProcessorsAfterInitialization 方法
   *
   * @param existingBean 现有的 Bean 实例
   * @param beanName Bean 的名称，如果需要可以传递给它
   * @return 返回的 Bean 实例可能是原始实例的包装对象
   * @throws BeansException 如果任何后处理失败，则抛出 BeansException
   */
  Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;

}
