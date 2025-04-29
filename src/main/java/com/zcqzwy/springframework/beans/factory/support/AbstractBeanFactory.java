package com.zcqzwy.springframework.beans.factory.support;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.factory.FactoryBean;
import com.zcqzwy.springframework.beans.factory.config.BeanDefinition;
import com.zcqzwy.springframework.beans.factory.BeanFactory;
import com.zcqzwy.springframework.beans.factory.config.BeanPostProcessor;
import com.zcqzwy.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.zcqzwy.springframework.core.convert.ConversionService;
import com.zcqzwy.springframework.util.Assert;
import com.zcqzwy.springframework.util.BeanFactoryUtils;
import com.zcqzwy.springframework.util.ClassUtils;
import com.zcqzwy.springframework.util.StringValueResolver;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: AbstractBeanFactory </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 * 实现了一系列操作IOC容器的功能，
 * 但最终的createBean依旧交由子类AbstractAutowireCapableBeanFactory完成
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements
    ConfigurableBeanFactory{

  private ConversionService conversionService;


  private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<StringValueResolver>();

  /** beanPostProcessors将在CreateBean中调用*/
  private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();


  private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

  @Override
  public Object getBean(String name) throws BeansException {
    return doGetBean(name, null);
  }

  @Override
  public Object getBean(String name, Object... args) throws BeansException {
    return doGetBean(name, args);
  }

  @Override
  public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
    return (T) getBean(name);
  }

  protected <T> T doGetBean(final String name, final Object[] args) {

    // beanName需要转换成标准名称，也就是去掉前面的&字符
    //因为单列池存的是FactoryBean，也就是 beanName ==> FactoryBean
    // FactoryBean#getObject()对象如果是单列的，其实是缓存在FactoryBeanRegistrySupport#factoryBeanObjectCache中
    // 名字也是 FactoryBean beanName ==> FactoryBean
    // 所以要加一个判断，当name以&开头，则表明是要返回FactoryBean，而不是要返回FactoryBean#getObject()对象
    String transformedBeanName =  BeanFactoryUtils.transformedBeanName(name);

    //是否在单例池中
    Object bean = getSingleton(transformedBeanName);



    if (bean != null) {
      // 如果是 FactoryBean，则需要调用 FactoryBean#getObject
      return (T) getObjectForBeanInstance(bean, name);
    }

    BeanDefinition beanDefinition = getBeanDefinition(transformedBeanName);
    Object objectBean = createBean(transformedBeanName, beanDefinition, args);
    // 因为前面可能只存入了FactoryBean，进入单列池。所以，此时要调用FactoryBean#getObject让它放进缓存
    return  (T) getObjectForBeanInstance(objectBean, name);
  }


  private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
    if (!(beanInstance instanceof FactoryBean)) {
      // 如果是普通Bean，直接返回
      return beanInstance;
    }

    if(beanName.startsWith(BeanFactoryUtils.FACTORY_BEAN_PREFIX)) {
      // 如果需要获取工厂FactoryBean，也就是&开头，直接返回
      return beanInstance;
    }
    String name = BeanFactoryUtils.transformedBeanName(beanName);

    Object object = getCachedObjectForFactoryBean(name);

    if (object == null) {
      FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
      object = getObjectFromFactoryBean(factoryBean, name,true);
    }

    return object;
  }

  /**
   * 获取用户注册的bean定义
   * @param beanName bean名称
   * @return bean定义
   * @throws BeansException 获取bean定义异常
   */
  protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

  /**
   * 根据bean定义和bean名字创建bean对象
   * @param beanName bean名称
   * @param beanDefinition bean定义
   * @param args 构造函数参数
   * @return bean对象
   * @throws BeansException 创建bean异常
   */
  protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;


  @Override
  public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
    Assert.notNull(beanPostProcessor, "BeanPostProcessor must not be null");
    this.beanPostProcessors.remove(beanPostProcessor);
    this.beanPostProcessors.add(beanPostProcessor);
  }

  /**
   *返回将应用的BeanPostProcessors列表
   */
  public List<BeanPostProcessor> getBeanPostProcessors() {
    return this.beanPostProcessors;
  }


  public int getBeanPostProcessorCount() {
    return this.beanPostProcessors.size();
  }

  public ClassLoader getBeanClassLoader() {
    return this.beanClassLoader;
  }


  @Override
  public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
    this.embeddedValueResolvers.add(valueResolver);
  }

  @Override
  public String resolveEmbeddedValue(String value) {
    String result = value;
    for (StringValueResolver resolver : this.embeddedValueResolvers) {
      result = resolver.resolveStringValue(result);
    }
    return result;
  }

  @Override
  public boolean containsBean(String name) {
    return containsBeanDefinition(name);
  }

  protected abstract boolean containsBeanDefinition(String beanName);


  @Override
  public ConversionService getConversionService() {
    return conversionService;
  }

  @Override
  public void setConversionService(ConversionService conversionService) {
    this.conversionService = conversionService;
  }

}
