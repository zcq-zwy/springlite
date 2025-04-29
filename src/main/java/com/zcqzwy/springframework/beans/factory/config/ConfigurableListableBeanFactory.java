package com.zcqzwy.springframework.beans.factory.config;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.factory.ListableBeanFactory;
import com.zcqzwy.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.zcqzwy.springframework.beans.factory.config.BeanDefinition;
import com.zcqzwy.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ConfigurableListableBeanFactory </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 * 可配置+可枚举容器 最完整的BeanFactory接口，整合了配置、枚举、自动装配功能，
 * 用于容器启动时的Bean预实例化和配置检查。也就是提供分析和修改Bean以及预先实例化的操作接口
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface ConfigurableListableBeanFactory extends ListableBeanFactory,
    AutowireCapableBeanFactory, ConfigurableBeanFactory {
  /**
   * 获取BeanDefinition
   * @param beanName Bean名称
   * @return beanDefinition定义
   * @throws BeansException 获取BeanDefinition异常
   */

  BeanDefinition getBeanDefinition(String beanName) throws BeansException;

 /**
   *确保所有非懒加载的单例Bean（包括FactoryBean）被实例化。，也考虑
   * 如果需要的话，通常在工厂设置结束时调用。
   *  @throws BeansException ==>  如果无法创建其中一个单例。
   *注意：这可能使工厂已经初始化了工厂！
   *在这种情况下，请调用destroysingletons()以进行完整清理。
   */
  void preInstantiateSingletons() throws BeansException;

}
