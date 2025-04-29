package com.zcqzwy.springframework.context.support;

import com.zcqzwy.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.zcqzwy.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.zcqzwy.springframework.context.support.AbstractRefreshableApplicationContext;
import com.zcqzwy.springframework.core.io.Resource;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: AbstractXmlApplicationContext </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/25 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

  @Override
  protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
    XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
    String[] configLocations = getConfigLocations();
    if (null != configLocations){
      beanDefinitionReader.loadBeanDefinitions(configLocations);
    }
  }
/**
   *返回一系列资源对象的位置，参考XML BEAN定义应使用此上下文的文件。
   * <p>默认实现返回{@code null}。子类可以覆盖
   * @return 一系列资源对象，或{@code null}如果无
   */
protected abstract String[] getConfigLocations();



}
