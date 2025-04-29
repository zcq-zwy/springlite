package com.zcqzwy.springframework.context.event;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.factory.BeanClassLoaderAware;
import com.zcqzwy.springframework.beans.factory.BeanFactory;
import com.zcqzwy.springframework.beans.factory.BeanFactoryAware;
import com.zcqzwy.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.zcqzwy.springframework.beans.factory.support.AbstractBeanFactory;
import com.zcqzwy.springframework.context.ApplicationEvent;
import com.zcqzwy.springframework.context.ApplicationListener;
import com.zcqzwy.springframework.util.ClassUtils;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: AbstractApplicationEventMulticaster </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster,
    BeanFactoryAware, BeanClassLoaderAware {

  public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

  private BeanFactory beanFactory;
  private ClassLoader beanClassLoader;


  @Override
  public void setBeanClassLoader(ClassLoader classLoader) {
    this.beanClassLoader = classLoader;
  }
  private BeanFactory getBeanFactory() {
    if (this.beanFactory == null) {
      throw new IllegalStateException("ApplicationEventMulticaster cannot retrieve listener beans " +
          "because it is not associated with a BeanFactory");
    }
    return this.beanFactory;
  }


  @Override
  public void addApplicationListener(ApplicationListener<?> listener) {
    applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
  }

  @Override
  public void removeApplicationListener(ApplicationListener<?> listener) {
    applicationListeners.remove(listener);
  }

  @Override
  public final void setBeanFactory(BeanFactory beanFactory) {
    this.beanFactory = beanFactory;
  }

  protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event) {
    LinkedList<ApplicationListener> allListeners = new LinkedList<ApplicationListener>();
    for (ApplicationListener<ApplicationEvent> listener : applicationListeners) {
      if (supportsEvent(listener, event)) {
        allListeners.add(listener);
      }
    }
    return allListeners;
  }

  /**
   * 监听器是否对该事件感兴趣
   */
  protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener,
      ApplicationEvent event) {
    Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();

    // 按照 CglibSubclassingInstantiationStrategy、SimpleInstantiationStrategy 不同的实例化类型，需要判断后获取目标 class
    Class<?> targetClass =
        ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;
    Type genericInterface = targetClass.getGenericInterfaces()[0];

    Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
    String className = actualTypeArgument.getTypeName();
    Class<?> eventClassName;
    try {
      eventClassName = Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new BeansException("wrong event class name: " + className);
    }
    // 判定此 eventClassName 对象所表示的类或接口与指定的 event.getClass() 参数所表示的类或接口是否相同，或是否是其超类或超接口。
    // isAssignableFrom是用来判断子类和父类的关系的，或者接口的实现类和接口的关系的，默认所有的类的终极父类都是Object。如果A.isAssignableFrom(B)结果是true，证明B可以转换成为A,也就是A可以由B转换而来。
    return eventClassName.isAssignableFrom(event.getClass());
  }

}
