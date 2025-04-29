package com.zcqzwy.springframework.beans.factory.config;

import com.zcqzwy.springframework.beans.factory.HierarchicalBeanFactory;
import com.zcqzwy.springframework.core.convert.ConversionService;
import com.zcqzwy.springframework.util.StringValueResolver;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ConfigurableBeanFactory </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 * 提供对BeanFactory的配置能力，如设置类加载器、作用域、后置处理器等。
 * 定制容器行为，如扩展Bean的生命周期管理
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

  String SCOPE_SINGLETON = "singleton";

  String SCOPE_PROTOTYPE = "prototype";

  /**
   * 添加后置处理器
   * @param beanPostProcessor 后置处理器
   */
  void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

  /**
   * 销毁单例对象
   */
  void destroySingletons();


  void addEmbeddedValueResolver(StringValueResolver valueResolver);

  String resolveEmbeddedValue(String value);


  void setConversionService(ConversionService conversionService);

  ConversionService getConversionService();


}