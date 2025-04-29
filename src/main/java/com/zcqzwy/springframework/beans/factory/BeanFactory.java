package com.zcqzwy.springframework.beans.factory;

import com.zcqzwy.springframework.beans.BeansException;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: BeanFactory </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 * 基本上，BeanFactory接口只定义如何访问容
 * 器内管理的Bean的方法，各个BeanFactory的具体实现类负责具体Bean的注册以及管理工作
 * 最基础的容器接口，定义Bean的获取、作用域判断、类型检查等核心功能
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface BeanFactory {

  static final String FACTORY_BEAN_PREFIX = "&";


  /**
   * 对 Bean 对象的获取以及在获取不到时需要拿到 Bean 的定义做相应 Bean 实例化操作
   *
   * @param name bean名称
   * @return bean对象
   * @throws BeansException 获取bean异常
   */
  Object getBean(String name) throws BeansException;

  /**
   * 根据 Bean 名称和参数创建 Bean 实例
   *
   * @param name Bean 名称
   * @param args 构造函数参数
   * @return Bean 实例
   * @throws BeansException 获取 Bean 异常
   */
  Object getBean(String name, Object... args) throws BeansException;

  /**
   * 根据 Bean 名称和类型创建 Bean 实例
   *
   * @param name         Bean 名称
   * @param requiredType Bean 类型
   * @return Bean 实例
   * @throws BeansException 获取 Bean 异常
   */
  <T> T getBean(String name, Class<T> requiredType);


  <T> T getBean(Class<T> requiredType) throws BeansException;


  boolean containsBean(String name);

}
