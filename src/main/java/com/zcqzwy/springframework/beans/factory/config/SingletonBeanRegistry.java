package com.zcqzwy.springframework.beans.factory.config;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: SingletonBeanRegistry </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 * 单例 Bean 注册表 获得注册的单例对象
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface SingletonBeanRegistry {

  /**
   * 获取单例对象
   * @param beanName bean名字
   * @return 单例对象
   */
  Object getSingleton(String beanName);

  boolean containsSingleton(String beanName);

  void registerSingleton(String beanName, Object singletonObject);
}
