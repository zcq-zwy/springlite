package com.zcqzwy.springframework.core.convert.support;

import com.zcqzwy.springframework.core.convert.ConversionService;
import com.zcqzwy.springframework.core.converter.Converter;
import com.zcqzwy.springframework.core.converter.ConverterFactory;
import com.zcqzwy.springframework.core.converter.ConverterRegistry;
import com.zcqzwy.springframework.core.converter.GenericConverter;
import com.zcqzwy.springframework.core.converter.GenericConverter.ConvertiblePair;
import com.zcqzwy.springframework.util.BasicTypes;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: GenericConversionService </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/28 </p>
 * 实现了注册管理、转换服务的几乎所有功能，是个实现类而非抽象类
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class GenericConversionService implements ConversionService, ConverterRegistry {

  private Map<ConvertiblePair, GenericConverter> converters = new HashMap<>();

  @Override
  public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
    GenericConverter converter = getConverter(sourceType, targetType);
    return converter != null;
  }

  @Override
  public <T> T convert(Object source, Class<T> targetType) {
    Class<?> sourceType = source.getClass();
    targetType = (Class<T>) BasicTypes.wrap(targetType);
    GenericConverter converter = getConverter(sourceType, targetType);
    return (T) converter.convert(source, sourceType, targetType);
  }

  @Override
  public void addConverter(Converter<?, ?> converter) {
    ConvertiblePair typeInfo = getRequiredTypeInfo(converter);
    ConverterAdapter converterAdapter = new ConverterAdapter(typeInfo, converter);
    for (ConvertiblePair convertibleType : converterAdapter.getConvertibleTypes()) {
      converters.put(convertibleType, converterAdapter);
    }
  }

  @Override
  public void addConverterFactory(ConverterFactory<?, ?> converterFactory) {
    ConvertiblePair typeInfo = getRequiredTypeInfo(converterFactory);
    ConverterFactoryAdapter converterFactoryAdapter = new ConverterFactoryAdapter(typeInfo, converterFactory);
    for (ConvertiblePair convertibleType : converterFactoryAdapter.getConvertibleTypes()) {
      converters.put(convertibleType, converterFactoryAdapter);
    }
  }

  @Override
  public void addConverter(GenericConverter converter) {
    for (ConvertiblePair convertibleType : converter.getConvertibleTypes()) {
      converters.put(convertibleType, converter);
    }
  }

  private ConvertiblePair getRequiredTypeInfo(Object object) {
    Type[] types = object.getClass().getGenericInterfaces();
    ParameterizedType parameterized = (ParameterizedType) types[0];
    Type[] actualTypeArguments = parameterized.getActualTypeArguments();
    Class sourceType = (Class) actualTypeArguments[0];
    Class targetType = (Class) actualTypeArguments[1];
    return new ConvertiblePair(sourceType, targetType);
  }

  protected GenericConverter getConverter(Class<?> sourceType, Class<?> targetType) {
    List<Class<?>> sourceCandidates = getClassHierarchy(sourceType);
    List<Class<?>> targetCandidates = getClassHierarchy(targetType);
    for (Class<?> sourceCandidate : sourceCandidates) {
      for (Class<?> targetCandidate : targetCandidates) {
        ConvertiblePair convertiblePair = new ConvertiblePair(sourceCandidate, targetCandidate);
        GenericConverter converter = converters.get(convertiblePair);
        if (converter != null) {
          return converter;
        }
      }
    }
    return null;
  }

  private List<Class<?>> getClassHierarchy(Class<?> clazz) {
    List<Class<?>> hierarchy = new ArrayList<>();
    //原始类转为包装类
    clazz = BasicTypes.wrap(clazz);
    while (clazz != null) {
      hierarchy.add(clazz);
      clazz = clazz.getSuperclass();
    }
    return hierarchy;
  }

  private final class ConverterAdapter implements GenericConverter {

    private final ConvertiblePair typeInfo;

    private final Converter<Object, Object> converter;

    public ConverterAdapter(ConvertiblePair typeInfo, Converter<?, ?> converter) {
      this.typeInfo = typeInfo;
      this.converter = (Converter<Object, Object>) converter;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
      return Collections.singleton(typeInfo);
    }

    @Override
    public Object convert(Object source, Class sourceType, Class targetType) {
      return converter.convert(source);
    }
  }

  private final class ConverterFactoryAdapter implements GenericConverter {

    private final ConvertiblePair typeInfo;

    private final ConverterFactory<Object, Object> converterFactory;

    public ConverterFactoryAdapter(ConvertiblePair typeInfo, ConverterFactory<?, ?> converterFactory) {
      this.typeInfo = typeInfo;
      this.converterFactory = (ConverterFactory<Object, Object>) converterFactory;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
      return Collections.singleton(typeInfo);
    }

    @Override
    public Object convert(Object source, Class sourceType, Class targetType) {
      return converterFactory.getConverter(targetType).convert(source);
    }
  }
}
