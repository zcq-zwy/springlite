package com.zcqzwy.springframework.context.support;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.zcqzwy.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.zcqzwy.springframework.beans.factory.config.BeanPostProcessor;
import com.zcqzwy.springframework.context.ApplicationEvent;
import com.zcqzwy.springframework.context.ApplicationListener;
import com.zcqzwy.springframework.context.ConfigurableApplicationContext;
import com.zcqzwy.springframework.context.event.ApplicationEventMulticaster;
import com.zcqzwy.springframework.context.event.ContextClosedEvent;
import com.zcqzwy.springframework.context.event.ContextRefreshedEvent;
import com.zcqzwy.springframework.context.event.SimpleApplicationEventMulticaster;
import com.zcqzwy.springframework.core.OrderComparator;
import com.zcqzwy.springframework.core.Ordered;
import com.zcqzwy.springframework.core.PriorityOrdered;
import com.zcqzwy.springframework.core.convert.ConversionService;
import com.zcqzwy.springframework.core.io.DefaultResourceLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: AbstractApplicationContext </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/25 </p>
 * 这段 Javadoc 描述了 `AbstractApplicationContext` 类的功能和设计模式，以下是对其内容的总结翻译：
 *1. 类概述
 * `AbstractApplicationContext` 是 `ApplicationContext` 接口的抽象实现类。
 *  它不强制规定配置的存储类型，仅实现了公共的上下文功能。使用了模板方法设计模式，要求具体的子类实现抽象方法。
 *2. 与 `BeanFactory` 的区别：
 * 与普通的 `BeanFactory` 不同，`ApplicationContext`
 * 会自动检测并注册其内部 Bean 工厂中定义的特殊 Bean。
 * 具体来说，它会自动注册以下类型的 Bean：
 * `BeanFactoryPostProcessor`
 *  `BeanPostProcessor`
 *  `ApplicationListener`
 * 3.消息源和事件广播器：
 * 可以在上下文中提供一个名为 `messageSource` 的 `MessageSource` Bean，
 * 用于消息解析；如果没有提供，则消息解析会委托给父上下文。
 * 可以在上下文中提供一个类型为
 * `ApplicationEventMulticaster` 的 `applicationEventMulticaster` Bean，用于应用事件的多播；
 * 如果没有提供，则使用默认的 `SimpleApplicationEventMulticaster`。
 * 4. 资源加载：
 *  通过继承 `DefaultResourceLoader` 实现资源加载。
 *   默认将非 URL 资源路径视为类路径资源
 *   （支持包含包路径的完整类路径资源名称，如 `mypackage/myresource.dat`），
 *   除非子类重写了 `getResourceByPath` 方法。
 * 总结：`AbstractApplicationContext` 是一个抽象类，提供了 `ApplicationContext` 接口的通用实现，
 * 支持自动注册特殊 Bean、消息源、事件广播器以及资源加载功能，并要求子类实现特定的抽象方法。
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements
    ConfigurableApplicationContext {


  public static final String CONVERSION_SERVICE_BEAN_NAME = "conversionService";


  public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

  /**
   * 应用事件广播器 委托者模式
   */
  private ApplicationEventMulticaster applicationEventMulticaster;


  @Override
  public void refresh() throws BeansException {
    // 1. 创建 BeanFactory，并加载 BeanDefinition
    refreshBeanFactory();

    // 2. 获取 BeanFactory
    ConfigurableListableBeanFactory beanFactory = getBeanFactory();

    // 3.准备 Bean Factory 以在此上下文中使用。
    prepareBeanFactory(beanFactory);

    // 4. 在 Bean 实例化之前，执行 BeanFactoryPostProcessor
    // 优先级：BeanFactoryPostProcessor 被视为特殊的"系统级" bean，
    // 它们在 BeanPostProcessor 实例化之前就已经完全初始化并执行了。
    invokeBeanFactoryPostProcessors(beanFactory);

    // 5. BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
    registerBeanPostProcessors(beanFactory);

    // 6. 初始化事件发布者
    initApplicationEventMulticaster();

    // 7. 注册事件监听器
    registerListeners();


    //8.注册类型转换器和提前实例化单例bean
    finishBeanFactoryInitialization(beanFactory);

    //  9. 发布容器刷新完成事件
    finishRefresh();


  }

  protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
    //设置类型转换器
    if (beanFactory.containsBean(CONVERSION_SERVICE_BEAN_NAME)) {
      Object conversionService = beanFactory.getBean(CONVERSION_SERVICE_BEAN_NAME);
      if (conversionService instanceof ConversionService) {
        beanFactory.setConversionService((ConversionService) conversionService);
      }
    }

    //提前实例化单例bean
    beanFactory.preInstantiateSingletons();
  }


  private void registerListeners() {
    Collection<ApplicationListener> applicationListeners = getBeansOfType(
        ApplicationListener.class).values();
    for (ApplicationListener listener : applicationListeners) {
      applicationEventMulticaster.addApplicationListener(listener);
    }
  }

  /**
   * 初始化 ApplicationEventMulticaster。
   * 如果在上下文中未定义，则使用 SimpleApplicationEventMulticaster。
   */
  private void initApplicationEventMulticaster() {
    ConfigurableListableBeanFactory beanFactory = getBeanFactory();
    applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
    beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME,
        applicationEventMulticaster);

  }

  private void finishRefresh() {
    publishEvent(new ContextRefreshedEvent(this));
  }

  @Override
  public void publishEvent(ApplicationEvent event) {
    applicationEventMulticaster.multicastEvent(event);
  }

  @Override
  public void close() {
    // 发布容器关闭事件
    publishEvent(new ContextClosedEvent(this));

    // 执行销毁单例bean的销毁方法
    getBeanFactory().destroySingletons();
  }

  protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
    beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
  }

  /**
   * 刷新BeanFactory，并加载BeanDefinition
   * 子类必须实现此方法以执行实际的配置加载。
   * 该方法会在 {@link #refresh()} 方法执行任何其他初始化工作之前被调用。
   * 子类要么创建一个新的 Bean 工厂并保存对其的引用，要么返回其持有的单个 BeanFactory 实例。
   * 在后一种情况下，如果支持多次刷新上下文，则通常会在多次刷新时抛出 IllegalStateException
   *
   * @throws BeansException 刷新BeanFactory异常
   */
  protected abstract void refreshBeanFactory() throws BeansException;

  protected abstract ConfigurableListableBeanFactory getBeanFactory();

  private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
    // 注册的BeanFactoryPostProcessor
    List<BeanFactoryPostProcessor> priorityOrderedPostProcessors = new ArrayList<>();
    List<BeanFactoryPostProcessor> orderedPostProcessors = new ArrayList<>();
    List<BeanFactoryPostProcessor> noorderedPostProcessors = new ArrayList<>();
    //将已经执行的BFPP存储在processBean中，防止重复执行
    Set<BeanFactoryPostProcessor> processedBeans = new HashSet<>();
    Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(
        BeanFactoryPostProcessor.class);
    for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
      if (processedBeans.contains(beanFactoryPostProcessor)) {
      } else if (beanFactoryPostProcessor instanceof PriorityOrdered) {
        priorityOrderedPostProcessors.add(beanFactoryPostProcessor);
        processedBeans.add(beanFactoryPostProcessor);
      } else if (beanFactoryPostProcessor instanceof Ordered) {
        orderedPostProcessors.add(beanFactoryPostProcessor);
      } else {
        noorderedPostProcessors.add(beanFactoryPostProcessor);
      }
    }
    // 执行优先级排序的BFPP
    OrderComparator.sort(priorityOrderedPostProcessors);
    invokeBeanFactoryPostProcessors(priorityOrderedPostProcessors, beanFactory);

    // 执行普通排序的BFPP
    OrderComparator.sort(orderedPostProcessors);
    invokeBeanFactoryPostProcessors(orderedPostProcessors, beanFactory);

    invokeBeanFactoryPostProcessors(noorderedPostProcessors, beanFactory);
  }

  private void invokeBeanFactoryPostProcessors(
      Collection<? extends BeanFactoryPostProcessor> postProcessors,
      ConfigurableListableBeanFactory beanFactory) {

    for (BeanFactoryPostProcessor postProcessor : postProcessors) {
      postProcessor.postProcessBeanFactory(beanFactory);
    }
  }

  private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
    Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(
        BeanPostProcessor.class);
    for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
      beanFactory.addBeanPostProcessor(beanPostProcessor);
    }
  }

  @Override
  public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
    return getBeanFactory().getBeansOfType(type);
  }

  @Override
  public String[] getBeanDefinitionNames() {
    return getBeanFactory().getBeanDefinitionNames();
  }

  @Override
  public Object getBean(String name) throws BeansException {
    return getBeanFactory().getBean(name);
  }

  @Override
  public Object getBean(String name, Object... args) throws BeansException {
    return getBeanFactory().getBean(name, args);
  }

  @Override
  public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
    return getBeanFactory().getBean(name, requiredType);
  }

  @Override
  public void registerShutdownHook() {
    Runtime.getRuntime().addShutdownHook(new Thread(this::close));
  }

  @Override
  public <T> T getBean(Class<T> requiredType) throws BeansException {
    return getBeanFactory().getBean(requiredType);
  }

  @Override
  public boolean containsBean(String name) {
    return getBeanFactory().containsBean(name);
  }

}
