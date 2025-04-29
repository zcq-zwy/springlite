package com.zcqzwy.springframework.beans.factory.support;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.PropertyValue;
import com.zcqzwy.springframework.beans.PropertyValues;
import com.zcqzwy.springframework.beans.factory.Aware;
import com.zcqzwy.springframework.beans.factory.BeanClassLoaderAware;
import com.zcqzwy.springframework.beans.factory.BeanFactoryAware;
import com.zcqzwy.springframework.beans.factory.BeanNameAware;
import com.zcqzwy.springframework.beans.factory.DisposableBean;
import com.zcqzwy.springframework.beans.factory.InitializingBean;
import com.zcqzwy.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.zcqzwy.springframework.beans.factory.config.BeanDefinition;
import com.zcqzwy.springframework.beans.factory.config.BeanPostProcessor;
import com.zcqzwy.springframework.beans.factory.config.BeanReference;
import com.zcqzwy.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.zcqzwy.springframework.core.convert.ConversionService;
import com.zcqzwy.springframework.util.BeanUtils;
import com.zcqzwy.springframework.util.StringUtils;
import com.zcqzwy.springframework.util.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: AbstractAutowireCapableBeanFactory </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 * 实现了创建bean的功能，所有与创建bean的相关的功能都在这里
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements
    AutowireCapableBeanFactory {

  /**
   * 创建bean实例的策略，默认是直接构造器生成，不然如果后面有aop的代理，就会代理2次，进而出错
   */
  private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();


  @Override
  protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args)
      throws BeansException {
//    如果bean需要代理，则直接返回代理对象
    Object bean = resolveBeforeInstantiation(beanName, beanDefinition);
    if (bean != null) {
      return bean;
    }

    return doCreateBean(beanName, beanDefinition, args);
  }

  /**
   * 执行InstantiationAwareBeanPostProcessor的方法，如果bean需要代理，直接返回代理对象
   *
   * @param beanName       beanName
   * @param beanDefinition bean定义
   * @return 可能提前短路的aop对象
   */
  protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
    Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(),
        beanName);
    if (bean != null) {
      bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
    }
    return bean;
  }

  protected Object applyBeanPostProcessorsBeforeInstantiation(Class beanClass, String beanName) {
    for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
      if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
        Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(
            beanClass, beanName);
        if (result != null) {
          return result;
        }
      }
    }

    return null;
  }

  /**
   * 在设置bean属性之前，允许BeanPostProcessor修改属性值
   *
   * @param beanName       bean名字
   * @param bean           实列化bean
   * @param beanDefinition bean定义
   */
  protected void applyBeanPostprocessorsBeforeApplyingPropertyValues(String beanName, Object bean,
      BeanDefinition beanDefinition) {
    for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
      if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
        PropertyValues pvs = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessPropertyValues(
            beanDefinition.getPropertyValues(), bean, beanName);
        if (pvs != null) {
          for (PropertyValue propertyValue : pvs.getPropertyValues()) {
            beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
          }
        }
      }
    }
  }

  /**
   * bean实例化后执行，如果返回false，不执行后续设置属性的逻辑
   *
   * @param beanName bean的名字
   * @param bean     实列bean
   * @return 是否在实列化后进行设置属性值
   */
  private boolean applyBeanPostProcessorsAfterInstantiation(String beanName, Object bean) {
    boolean continueWithPropertyPopulation = true;
    for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
      if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
        if (!((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessAfterInstantiation(
            bean, beanName)) {
          continueWithPropertyPopulation = false;
          break;
        }
      }
    }
    return continueWithPropertyPopulation;
  }


  protected Object doCreateBean(String beanName, BeanDefinition beanDefinition, Object[] args) {

    Object bean;
    Object exposedObject;

    try {
      // 创造bean实列
      bean = createBeanInstance(beanDefinition, beanName, args);

      //为解决循环依赖问题，将实例化后的bean放进缓存中提前暴露
      if (beanDefinition.isSingleton()) {
        Object finalBean = bean;
        addSingletonFactory(beanName,
            () -> getEarlyBeanReference(beanName, beanDefinition, finalBean));
      }



      //实例化bean之后执行
      boolean continueWithPropertyPopulation = applyBeanPostProcessorsAfterInstantiation(beanName,
          bean);
      if (!continueWithPropertyPopulation) {
        return bean;
      }
      exposedObject = bean;

      //在设置bean属性之前，允许BeanPostProcessor修改属性值
      applyBeanPostprocessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition);

      // 给 Bean 填充属性
      applyPropertyValues(beanName, bean, beanDefinition);

      // 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
      exposedObject = initializeBean(beanName, bean, beanDefinition);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BeansException("Instantiation of bean failed", e);
    }

    // 注册实现了 DisposableBean 接口 或者 有destroy-method的 Bean 对象
    registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);



    // 缓存单例对象
    if (beanDefinition.isSingleton()) {
      Object earlySingletonReference = getSingleton(beanName);

      if (earlySingletonReference != null) {
        if (exposedObject == bean) {
          // 如果从一级和二级缓存中获取Bean实例不为空，并且exposedObject == bean的话，
          // 将earlySingletonReference赋值给exposedObject返回
          exposedObject = earlySingletonReference;
        }

      }

      addSingleton(beanName, bean);
    }
    return exposedObject;
  }


  protected Object getEarlyBeanReference(String beanName, BeanDefinition beanDefinition,
      Object bean) {
    Object exposedObject = bean;
    for (BeanPostProcessor bp : getBeanPostProcessors()) {
      if (bp instanceof InstantiationAwareBeanPostProcessor) {
        // 这么一大段就这句话是核心，也就是当bean要进行提前曝光时，
        // 给一个机会，通过重写后置处理器的getEarlyBeanReference方法，来自定义操作bean
        // 值得注意的是，如果提前曝光了，但是没有被提前引用，则该后置处理器并不生效!!!
        // 这也正式三级缓存存在的意义，否则二级缓存就可以解决循环依赖的问题
        exposedObject = ((InstantiationAwareBeanPostProcessor) bp).getEarlyBeanReference(
            exposedObject, beanName);
        if (exposedObject == null) {
          return exposedObject;
        }
      }
    }

    return exposedObject;
  }

  protected void registerDisposableBeanIfNecessary(String beanName, Object bean,
      BeanDefinition beanDefinition) {

    // 非 Singleton 类型的 Bean 不执行销毁方法
    if (!beanDefinition.isSingleton()) {
      return;
    }

    if (bean instanceof DisposableBean || StringUtils.isNotEmpty(
        beanDefinition.getDestroyMethodName())) {
      registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
    }
  }


  /**
   * 为 Bean 填充属性
   *
   * @param beanName       beanName
   * @param bean           bean实例
   * @param beanDefinition beanDefinition
   */
  protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {

    try {
      PropertyValues propertyValues = beanDefinition.getPropertyValues();
      for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {

        String name = propertyValue.getName();
        Object value = propertyValue.getValue();

        // 如果是引用类型的话，让它先实列化
        if (value instanceof BeanReference) {
          // A 依赖 B，获取 B 的实例化
          BeanReference beanReference = (BeanReference) value;
          // getBean如果bean不存在，则先实列化
          value = getBean(beanReference.getBeanName());
        } else {
          //类型转换
          Class<?> sourceType = value.getClass();
          Class<?> targetType = TypeUtils.getFieldType(bean.getClass(), name);
          ConversionService conversionService = getConversionService();
          if (conversionService != null) {
            if (conversionService.canConvert(sourceType, targetType)) {
              value = conversionService.convert(value, targetType);
            }
          }
        }
        // 属性填充
        BeanUtils.setFieldValue(bean, name, value);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new BeansException("Error setting property values：" + beanName);
    }
  }

  protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName,
      Object[] args) {

    // 如果入参为空，则使用默认构造函数进行实例化。
    if (null == args || args.length == 0) {
      return getInstantiationStrategy().instantiate(beanDefinition, beanName, null, null);
    }
    Constructor constructorToUse = null;
    Class<?> beanClass = beanDefinition.getBeanClass();
    Constructor<?>[] ctors = beanClass.getDeclaredConstructors();

    // 首先查找精确匹配的构造函数
    for (Constructor<?> ctor : ctors) {
      Class<?>[] paramTypes = ctor.getParameterTypes();
      if (paramTypes.length == args.length) {
        boolean match = true;
        for (int i = 0; i < paramTypes.length; i++) {
          // 检查参数类型兼容性
          if (args[i] != null && !paramTypes[i].isAssignableFrom(args[i].getClass())) {
            match = false;
            break;
          }
        }
        if (match) {
          constructorToUse = ctor;
          break;
        }
      }
    }

    // 如果找不到匹配的构造函数，则抛出异常
    if (constructorToUse == null) {
      throw new IllegalStateException("No suitable constructor found for bean [" + beanName + "]");
    }

    return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
  }

  public InstantiationStrategy getInstantiationStrategy() {
    return instantiationStrategy;
  }

  public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
    this.instantiationStrategy = instantiationStrategy;
  }

  private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {

    //调用 aware接口的设置
    invokeAwareMethods(beanName, bean);

    // 1. 执行 BeanPostProcessor Before 处理
    Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

    // 待完成内容：invokeInitMethods(beanName, wrappedBean, beanDefinition);
    try {
      invokeInitMethods(beanName, wrappedBean, beanDefinition);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    // 2. 执行 BeanPostProcessor After 处理
    wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
    return wrappedBean;
  }


  private void invokeAwareMethods(final String beanName, final Object bean) {
    if (bean instanceof Aware) {
      if (bean instanceof BeanNameAware) {
        ((BeanNameAware) bean).setBeanName(beanName);
      }
      if (bean instanceof BeanClassLoaderAware) {
        ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
      }
      if (bean instanceof BeanFactoryAware) {
        ((BeanFactoryAware) bean).setBeanFactory(AbstractAutowireCapableBeanFactory.this);
      }
    }
  }


  private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition)
      throws Exception {

    boolean isInitializingBean = wrappedBean instanceof InitializingBean;
    // 1. 实现接口 InitializingBean
    if (isInitializingBean) {
      ((InitializingBean) wrappedBean).afterPropertiesSet();
    }

    // 2. 配置信息 init-method {判断是为了避免二次执行销毁}
    String initMethodName = beanDefinition.getInitMethodName();
    if (StringUtils.isNotEmpty(initMethodName) && !(isInitializingBean
        && "afterPropertiesSet".equals(initMethodName))) {
      Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
      if (null == initMethod) {
        if (logger.isDebugEnabled()) {
          logger.debug("No default init method named '" + initMethodName +
              "' found on bean with name '" + beanName + "'");
        }
        throw new BeansException(
            "Could not find an init method named '" + initMethodName + "' on bean with name '"
                + beanName + "'");
      }
      if (logger.isDebugEnabled()) {
        logger.debug(
            "Invoking init method  '" + initMethodName + "' on bean with name '" + beanName + "'");
      }
      //设置方法可访问
      initMethod.setAccessible(true);
      initMethod.invoke(wrappedBean);
    }
  }


  @Override
  public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
      throws BeansException {
    Object result = existingBean;
    for (BeanPostProcessor processor : getBeanPostProcessors()) {
      Object current = processor.postProcessBeforeInitialization(result, beanName);
      if (null == current) {
        continue;
      }
      result = current;
    }
    return result;
  }

  @Override
  public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
      throws BeansException {
    Object result = existingBean;
    for (BeanPostProcessor processor : getBeanPostProcessors()) {
      Object current = processor.postProcessAfterInitialization(result, beanName);
      if (null == current) {
        continue;
      }
      result = current;
    }
    return result;
  }


}
