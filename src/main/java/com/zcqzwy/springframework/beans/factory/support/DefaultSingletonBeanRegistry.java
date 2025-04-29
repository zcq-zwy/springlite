package com.zcqzwy.springframework.beans.factory.support;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.factory.DisposableBean;
import com.zcqzwy.springframework.beans.factory.ObjectFactory;
import com.zcqzwy.springframework.beans.factory.config.SingletonBeanRegistry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: DefaultSingletonBeanRegistry </p>
 * <p>描述: [单例注册接口] </p>
 * <p>创建时间: 2025/4/24 </p>
 * 单例bean注册器，其实它定义了三级缓存，其实就是三个Map属性
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

  /**
   * earlySingletonObjects用来存放提前创建的单例对象，也就是二级缓存，存放的是提前暴露的对象bean
   * 可能是经过aop的，可能是没有的
   */

  /** 子类的logger记录 */
  protected   final Logger logger = LoggerFactory.getLogger(getClass());


/**
   * 空单例对象的内部标记：
   * 用作并发 Map（不支持 null 值）的标记值。
   */
  protected static final Object NULL_OBJECT = new Object();

  /**
   * 单列对象缓存 一级缓存
   */
  private Map<String, Object> singletonObjects = new HashMap<>();


  /**
   * earlySingletonObjects用来存放提前创建的单例对象，也就是二级缓存，存放的是提前暴露的对象bean
   * 可能是经过aop的，可能是没有的
   */
  private Map<String, Object> earlySingletonObjects = new HashMap<>();

  /**
   * 三级缓存
   */
  private Map<String, ObjectFactory<?>> singletonFactories = new HashMap<String, ObjectFactory<?>>();

  /** Disposable bean instances: bean name to disposable instance. */
  private final Map<String, DisposableBean> disposableBeans = new ConcurrentHashMap<>();

  @Override
  public Object getSingleton(String beanName) {
    Object singletonObject = singletonObjects.get(beanName);
    if (singletonObject == null) {
      singletonObject = earlySingletonObjects.get(beanName);
      if (singletonObject == null) {
        ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
        if (singletonFactory != null) {
          /*
           * 通过getObject()方法获取bean，通过此方法获取到的实例不单单是提前曝光出来的实例，
           * 它还经过了SmartInstantiationAwareBeanPostProcessor的getEarlyBeanReference方法处理过。
           * 这也正是三级缓存存在的意义，可以通过重写该后置处理器对提前曝光的实例，在被提前引用时进行一些操作
           */
          singletonObject = singletonFactory.getObject();
          //从三级缓存放进二级缓存
          earlySingletonObjects.put(beanName, singletonObject);
          singletonFactories.remove(beanName);
        }
      }
    }
    return singletonObject;
  }

  /**
   * 提前曝光的对象被放入Map<String, ObjectFactory<?>> singletonFactories缓存中，
   * 这里并不是直接将Bean放入缓存，而是包装成ObjectFactory对象再放入。
   * @param beanName
   * @param singletonFactory
   */
  protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
       // 一级缓存
    if (!this.singletonObjects.containsKey(beanName)) {
      // 三级缓存
      this.singletonFactories.put(beanName, singletonFactory);
      // 二级缓存
      this.earlySingletonObjects.remove(beanName);
    }

  }




  protected void addSingleton(String beanName, Object singletonObject) {
    // 添加到第一级缓存
    singletonObjects.put(beanName, singletonObject);
    // 删除对应的二三级缓存
    this.singletonFactories.remove(beanName);
    this.earlySingletonObjects.remove(beanName);
  }

  public void registerDisposableBean(String beanName, DisposableBean bean) {
    disposableBeans.put(beanName, bean);
  }

  @Override
  public void registerSingleton(String beanName, Object singletonObject) {
    singletonObjects.put(beanName, singletonObject);
  }


  public void destroySingletons() {
    ArrayList<String> beanNames = new ArrayList<>(disposableBeans.keySet());
    for (String beanName : beanNames) {
      DisposableBean disposableBean = disposableBeans.remove(beanName);
      try {
        disposableBean.destroy();
      } catch (Exception e) {
        throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
      }
    }
  }

  @Override
  public boolean containsSingleton(String beanName) {
    return this.singletonObjects.containsKey(beanName);
  }

}
