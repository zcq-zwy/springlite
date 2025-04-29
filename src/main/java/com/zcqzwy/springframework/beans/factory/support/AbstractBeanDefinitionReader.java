package com.zcqzwy.springframework.beans.factory.support;

import com.zcqzwy.springframework.core.io.DefaultResourceLoader;
import com.zcqzwy.springframework.core.io.ResourceLoader;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: AbstractBeanDefinitionReader </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/


public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

  private final BeanDefinitionRegistry registry;

  private ResourceLoader resourceLoader;

  protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
    this(registry, new DefaultResourceLoader());
  }

  public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
    this.registry = registry;
    this.resourceLoader = resourceLoader;
  }

  @Override
  public BeanDefinitionRegistry getRegistry() {
    return registry;
  }

  @Override
  public ResourceLoader getResourceLoader() {
    return resourceLoader;
  }

}
