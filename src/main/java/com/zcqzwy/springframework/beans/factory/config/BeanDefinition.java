package com.zcqzwy.springframework.beans.factory.config;

import com.zcqzwy.springframework.beans.PropertyValues;
import java.util.Objects;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: BeanDefinition </p>
 * <p>描述: spring pojo bean的定义 </p>
 * <p>创建时间: 2025/4/24 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class BeanDefinition {

  private boolean lazyInit=false;

  private static final String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;

  private static final String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;
  private Class beanClass;

  private PropertyValues propertyValues;


  private String initMethodName;

  private String destroyMethodName;
  private String scope = SCOPE_SINGLETON;
  private boolean singleton = true;
  private boolean prototype = false;

  public BeanDefinition(Class beanClass) {
    this.beanClass = beanClass;
    this.propertyValues = new PropertyValues();
  }

  public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
    this.beanClass = beanClass;
    this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
  }

  public PropertyValues getPropertyValues() {
    return propertyValues;
  }

  public void setPropertyValues(PropertyValues propertyValues) {
    this.propertyValues = propertyValues;
  }

  public Class getBeanClass() {
    return beanClass;
  }

  public void setBeanClass(Class beanClass) {
    this.beanClass = beanClass;
  }

  public String getInitMethodName() {
    return initMethodName;
  }

  public void setInitMethodName(String initMethodName) {
    this.initMethodName = initMethodName;
  }

  public String getDestroyMethodName() {
    return destroyMethodName;
  }

  public void setDestroyMethodName(String destroyMethodName) {
    this.destroyMethodName = destroyMethodName;
  }

  public boolean isSingleton() {
    return singleton;
  }

  public boolean isPrototype() {
    return prototype;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
    this.singleton = SCOPE_SINGLETON.equals(scope);
    this.prototype = SCOPE_PROTOTYPE.equals(scope);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BeanDefinition that = (BeanDefinition) o;
    return beanClass.equals(that.beanClass);
  }

  @Override
  public int hashCode() {
    return Objects.hash(beanClass);
  }

  public boolean isLazyInit() {
    return lazyInit;
  }

  public void setLazyInit(boolean lazyInit) {
    this.lazyInit = lazyInit;
  }
}
