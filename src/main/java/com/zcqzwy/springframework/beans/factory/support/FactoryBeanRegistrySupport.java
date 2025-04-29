package com.zcqzwy.springframework.beans.factory.support;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.factory.FactoryBean;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jspecify.annotations.Nullable;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: FactoryBeanRegistrySupport </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

  /**
   * 由 FactoryBeans 创建的单例对象的缓存：FactoryBean name --> 对象
   */
  private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<String, Object>();

  /**
   * 从给定的 FactoryBean 中获取要公开的对象（如果可用）以缓存形式。快速检查最小同步。
   *
   * @param beanName bean 的名称
   * @return 从FactoryBean 获取的对象，
   * 或 {@code null}（如果不可用）
   */
  protected @Nullable Object getCachedObjectForFactoryBean(String beanName) {
    return this.factoryBeanObjectCache.get(beanName);

  }


  /**
   * 从给定的 FactoryBean 中获取要公开的对象。
   *
   * @param factory           工厂Bean
   * @param beanName          bean 的名称
   * @param shouldPostProcess bean 是否进行后处理
   * @return从 FactoryBean 获取的对象
   */
  protected Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName,
      boolean shouldPostProcess) {
    if (factory.isSingleton() && containsSingleton(beanName)) {
      Object object = this.factoryBeanObjectCache.get(beanName);
      if (object == null) {
        object = doGetObjectFromFactoryBean(factory, beanName);
        //处理 循环依赖
        //可能在 doGetObjectFromFactoryBean() 执行时，其他依赖触发了当前 Bean 的提前创建并存入缓存
        Object alreadyThere = this.factoryBeanObjectCache.get(beanName);
        if (alreadyThere != null) {
          object = alreadyThere;
        } else {
          if (object != null && shouldPostProcess) {
            try {
              object = postProcessObjectFromFactoryBean(object, beanName);
            } catch (Throwable ex) {
              throw new BeansException("Post-processing of FactoryBean's singleton object failed", ex);
            }
          }
          this.factoryBeanObjectCache.put(beanName, (object != null ? object : NULL_OBJECT));
        }
      }
      return (object != NULL_OBJECT ? object : null);

    } else {
      // 非单例模式处理
      Object object = doGetObjectFromFactoryBean(factory, beanName);
      if (object != null && shouldPostProcess) {
        try {
          object = postProcessObjectFromFactoryBean(object, beanName);
        } catch (Throwable ex) {
          throw new BeansException("Post-processing of FactoryBean's singleton object failed", ex);
        }
      }
      return object;
    }
  }



  private Object doGetObjectFromFactoryBean(final FactoryBean<?> factory, final String beanName){
    Object object;
    try {
      object = factory.getObject();
    } catch (Exception e) {
      throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
    }

    return object;
  }

  protected Object postProcessObjectFromFactoryBean(Object object, String beanName) throws BeansException {
    return object;
  }


}
