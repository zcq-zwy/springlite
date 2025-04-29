package com.zcqzwy.springframework.context.support;

import com.zcqzwy.springframework.beans.factory.FactoryBean;
import com.zcqzwy.springframework.beans.factory.InitializingBean;
import com.zcqzwy.springframework.core.convert.ConversionService;
import com.zcqzwy.springframework.core.convert.support.DefaultConversionService;
import com.zcqzwy.springframework.core.convert.support.GenericConversionService;
import com.zcqzwy.springframework.core.converter.Converter;
import com.zcqzwy.springframework.core.converter.ConverterFactory;
import com.zcqzwy.springframework.core.converter.ConverterRegistry;
import com.zcqzwy.springframework.core.converter.GenericConverter;
import java.util.Set;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ConversionServiceFactoryBean </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/28 </p>
 * 用于产生ConversionService类型转换服务的工厂Bean，为了方便和Spring容器整合而使用。
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>,
    InitializingBean {

  private Set<?> converters;

  private GenericConversionService conversionService;

  @Override
  public void afterPropertiesSet() throws Exception {
//    使用的是DefaultConversionService，因此那一大串的内建转换器们都会被添加进来的
    conversionService = new DefaultConversionService();
    registerConverters(converters, conversionService);
  }

  private void registerConverters(Set<?> converters, ConverterRegistry registry) {
    if (converters != null) {
      for (Object converter : converters) {
        if (converter instanceof GenericConverter) {
          registry.addConverter((GenericConverter) converter);
        } else if (converter instanceof Converter<?, ?>) {
          registry.addConverter((Converter<?, ?>) converter);
        } else if (converter instanceof ConverterFactory<?, ?>) {
          registry.addConverterFactory((ConverterFactory<?, ?>) converter);
        } else {
          throw new IllegalArgumentException("Each converter object must implement one of the " +
              "Converter, ConverterFactory, or GenericConverter interfaces");
        }
      }
    }
  }

  @Override
  public ConversionService getObject() throws Exception {
    return conversionService;
  }

  @Override
  public Class<?> getObjectType() {
    return ConversionService.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

  /**
   * 设置要注册的转换器集合
   * 自定义转换器可以通过setConverters()方法添加进来
   * 值得注意的是方法入参是Set<?>并没有明确泛型类型，因此那三种转换器(1:1/1:N/N:N)你是都可以添加.
   * @param converters 转换器集合
   */

  public void setConverters(Set<?> converters) {
    this.converters = converters;
  }
}
