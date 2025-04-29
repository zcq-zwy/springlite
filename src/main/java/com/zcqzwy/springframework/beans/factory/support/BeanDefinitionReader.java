package com.zcqzwy.springframework.beans.factory.support;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.core.io.Resource;
import com.zcqzwy.springframework.core.io.ResourceLoader;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: BeanDefinitionReader </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *注意 getRegistry()、getResourceLoader()，都是用于提供给后面三个方法的工具，加载和注册，
 * 这两个方法的实现会包装到抽象类中，以免污染具体的接口实现方法
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface BeanDefinitionReader {

  /**
   * 获取注册器
   * @return BeanDefinitionRegistry
   */
  BeanDefinitionRegistry getRegistry();

  /**
   * 获取资源加载器
   * @return ResourceLoader
   */
  ResourceLoader getResourceLoader();

  /**
   * 加载资源
   * @param resource 资源
   * @throws BeansException BeansException
   */
  void loadBeanDefinitions(Resource resource) throws BeansException;

  /**
   * 加载多个资源
   * @param resources 多个资源
   * @throws BeansException BeansException
   */

  void loadBeanDefinitions(Resource... resources) throws BeansException;

  /**
   * 加载资源
   * @param location 资源位置
   * @throws BeansException BeansException
   */
  void loadBeanDefinitions(String location) throws BeansException;

  /**
   * 加载多个资源（String路径类型）
   * @param locations 多个资源位置
   * @throws BeansException 基础异常路径错误
   */
  void loadBeanDefinitions(String... locations) throws BeansException;

}
