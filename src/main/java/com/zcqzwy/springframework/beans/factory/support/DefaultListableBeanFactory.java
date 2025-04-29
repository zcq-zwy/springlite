package com.zcqzwy.springframework.beans.factory.support;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.zcqzwy.springframework.beans.factory.config.BeanDefinition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: DefaultListableBeanFactory </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *  BeanDefinitionRegistry接口定义了注册bean定义，
 *  抽象类定义了获取bean定义，创建bean
 *  定义了一些存放Bean定义相关信息的Map ==> beanDefinitionMap
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry,
    ConfigurableListableBeanFactory {





  /**
   * beanDefinitionMap用来存储BeanDefinition
   */
  private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

  @Override
  public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
    beanDefinitionMap.put(beanName, beanDefinition);
  }

  @Override
  public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
    BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
    if (beanDefinition == null) {
      throw new BeansException("No bean named '" + beanName + "' is defined");
    }
    return beanDefinition;
  }

  @Override
  public void preInstantiateSingletons() throws BeansException {
    if (this.logger.isInfoEnabled()) {
      this.logger.info("Pre-instantiating singletons in " + this);
    }
    beanDefinitionMap.forEach((beanName, beanDefinition) -> {
      // 是单列且不是懒加载
      if(beanDefinition.isSingleton() &&!beanDefinition.isLazyInit()){
        getBean(beanName);
      }
    });

  }

  @Override
  public boolean containsBeanDefinition(String beanName) {
   return this.beanDefinitionMap.containsKey(beanName);
  }


  @Override
  public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
    Map<String, T> result = new HashMap<>();
    beanDefinitionMap.forEach((beanName, beanDefinition) -> {
      Class beanClass = beanDefinition.getBeanClass();
      if (type.isAssignableFrom(beanClass)) {
        result.put(beanName, (T) getBean(beanName));
      }
    });
    return result;
  }

  @Override
  public String[] getBeanDefinitionNames() {
    return beanDefinitionMap.keySet().toArray(new String[0]);
  }


  @Override
  public <T> T getBean(Class<T> requiredType) throws BeansException {
    List<String> beanNames = new ArrayList<>();
    for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
      Class beanClass = entry.getValue().getBeanClass();
      if (requiredType.isAssignableFrom(beanClass)) {
        beanNames.add(entry.getKey());
      }
    }
    if (beanNames.size() == 1) {
      return getBean(beanNames.get(0), requiredType);
    }

    throw new BeansException(requiredType + "expected single bean but found " +
        beanNames.size() + ": " + beanNames);
  }



}
