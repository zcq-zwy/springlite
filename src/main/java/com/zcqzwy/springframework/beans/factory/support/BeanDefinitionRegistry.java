package com.zcqzwy.springframework.beans.factory.support;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.factory.config.BeanDefinition;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: BeanDefinitionRegistry </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *  bean定义的注册中心
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface BeanDefinitionRegistry {
  /**
   * 注册beanDefinition
   * @param beanName bean名字
   * @param beanDefinition beanDefinition定义
   */
  void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

  /**
   * 使用Bean名称查询BeanDefinition
   *
   * @param beanName bean名称
   * @return bean定义
   * @throws BeansException 获取bean定义异常
   */
  BeanDefinition getBeanDefinition(String beanName) throws BeansException;

  /**
   * 判断是否包含指定名称的BeanDefinition
   * @param beanName bean名字
   * @return 是否包含指定名称的BeanDefinition
   */
  boolean containsBeanDefinition(String beanName);

  /**
   * 返回注册表中所有的Bean名称数组
   * @return  Bean名称数组
   */
  String[] getBeanDefinitionNames();
}
