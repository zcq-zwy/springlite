package com.zcqzwy.springframework.beans.factory.support;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.factory.config.BeanDefinition;
import java.lang.reflect.Constructor;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: InstantiationStrategy </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 * 实例化策略接口，这样就可以扩展，比如使用CGLIB
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface InstantiationStrategy {
  /**
   * 实例化Bean
   * @param beanDefinition BeanDefinition
   * @param beanName beanName名字
   * @param ctor 构造器
   * @param args 构造参数
   * @return
   * @throws BeansException
   */
  Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException;

}


