package com.zcqzwy.springframework.beans.factory.config;

import com.zcqzwy.springframework.beans.BeansException;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: BeanFactoryPostProcessor </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/25 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface BeanFactoryPostProcessor {


  /**
   * 在标准初始化之后修改应用程序上下文的内部bean工厂。
   * 所有的bean定义都将被加载，但还没有任何bean被实例化。
   * 这允许覆盖或添加属性，甚至对于急切初始化的bean也是如此。
   * @param beanFactory 应用程序上下文使用的bean工厂
   * @throws BeansException 在发生错误的情况下
   */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

  }


