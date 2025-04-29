package com.zcqzwy.springframework.common;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.PropertyValue;
import com.zcqzwy.springframework.beans.PropertyValues;
import com.zcqzwy.springframework.beans.factory.config.BeanDefinition;
import com.zcqzwy.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.zcqzwy.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: MyBeanFactoryPostProcessor </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/25 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    BeanDefinition beanDefinition = beanFactory.getBeanDefinition("accountService");
    PropertyValues propertyValues = beanDefinition.getPropertyValues();

    propertyValues.addPropertyValue(new PropertyValue("company", "字节跳动"));
  }

}
