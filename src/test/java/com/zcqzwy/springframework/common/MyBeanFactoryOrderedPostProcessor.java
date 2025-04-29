package com.zcqzwy.springframework.common;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.PropertyValue;
import com.zcqzwy.springframework.beans.PropertyValues;
import com.zcqzwy.springframework.beans.factory.config.BeanDefinition;
import com.zcqzwy.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.zcqzwy.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.zcqzwy.springframework.core.Ordered;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: MyBeanFactoryOrderedPostProcessor </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/25 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class MyBeanFactoryOrderedPostProcessor implements BeanFactoryPostProcessor, Ordered {

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
    BeanDefinition beanDefinition = beanFactory.getBeanDefinition("accountDao");
    PropertyValues propertyValues = beanDefinition.getPropertyValues();

    propertyValues.addPropertyValue(new PropertyValue("password", "zwy123"));

  }

  @Override
  public int getOrder() {
    return 123;
  }
}
