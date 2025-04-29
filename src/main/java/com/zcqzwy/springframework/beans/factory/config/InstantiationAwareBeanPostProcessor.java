package com.zcqzwy.springframework.beans.factory.config;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.PropertyValues;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: InstantiationAwareBeanPostProcessor </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/27 </p>
 * 可以短路后面的createbean
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

  /**
   * 在bean实例化之前执行
   *
   * @param beanClass   bean的类型
   * @param beanName bean名字
   * @return 返回一个可能经过修改的bean实例
   * @throws BeansException 如果抛出异常，则bean实例化将被 considered as failed
   */
  Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;


  /**
   * bean实例化之后，设置属性之前执行
   *
   * @param pvs 属性值
   * @param bean bean
   * @param beanName bean名字
   * @return 属性值
   * @throws BeansException 如果抛出异常，则bean实例化将被 considered as failed
   */
  PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName)
      throws BeansException;

  /**
   * 提前暴露bean
   *
   * @param bean
   * @param beanName
   * @return
   * @throws BeansException
   */
   default Object getEarlyBeanReference(Object bean, String beanName) throws BeansException{
     return bean;
   }

  boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;


  }

