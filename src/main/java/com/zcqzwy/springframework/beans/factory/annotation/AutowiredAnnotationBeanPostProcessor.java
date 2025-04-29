package com.zcqzwy.springframework.beans.factory.annotation;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.beans.PropertyValues;
import com.zcqzwy.springframework.beans.factory.BeanFactory;
import com.zcqzwy.springframework.beans.factory.BeanFactoryAware;
import com.zcqzwy.springframework.beans.factory.Value;
import com.zcqzwy.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.zcqzwy.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.zcqzwy.springframework.core.convert.ConversionService;
import com.zcqzwy.springframework.util.BeanUtils;

import com.zcqzwy.springframework.util.ClassUtils;
import com.zcqzwy.springframework.util.TypeUtils;
import java.lang.reflect.Field;


/**
 * <p>作者： zcq</p>
 * <p>文件名称: AutowiredAnnotationBeanPostProcessor </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/27 </p>
 * 处理@Autowired和@Value注解的BeanPostProcessor
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor,
    BeanFactoryAware {

  private ConfigurableListableBeanFactory beanFactory;




  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
  }

  @Override
  public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
    //处理@Value注解
    Class<?> clazz = bean.getClass();
    // 要得到类的原始类，不然就设置不了值
    clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      Value valueAnnotation = field.getAnnotation(Value.class);
      if (valueAnnotation != null) {
        Object value = valueAnnotation.value();
        value = beanFactory.resolveEmbeddedValue((String) value);

        //类型转换
        Class<?> sourceType = value.getClass();
        Class<?> targetType = (Class<?>) TypeUtils.getType(field);
        ConversionService conversionService = beanFactory.getConversionService();
        if (conversionService != null) {
          // 如果容器内有转换器，那就可以进行类型转换
          if (conversionService.canConvert(sourceType, targetType)) {
            value = conversionService.convert(value, targetType);
          }
        }
        BeanUtils.setFieldValue(bean, field.getName(), value);
      }
    }

    //处理@Autowired注解
    for (Field field : fields) {
      Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
      if (autowiredAnnotation != null) {
        Class<?> fieldType = field.getType();
        String dependentBeanName;
        Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
        Object dependentBean;
        if (qualifierAnnotation != null) {
          dependentBeanName = qualifierAnnotation.value();
          dependentBean = beanFactory.getBean(dependentBeanName, fieldType);
        } else {
          dependentBean = beanFactory.getBean(fieldType);
        }
        BeanUtils.setFieldValue(bean, field.getName(), dependentBean);
      }
    }

    return pvs;
  }

  @Override
  public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
    return true;
  }

  @Override
  public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
    return null;
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    return null;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    return null;
  }
}
