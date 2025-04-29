package com.zcqzwy.springframework.core.convert.support;

import com.zcqzwy.springframework.core.converter.Converter;
import com.zcqzwy.springframework.core.converter.ConverterFactory;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: StringToNumberConverterFactory </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/28 </p>
 * 继承自GenericConversionService，在其基础上注册了一批默认转换器（Spring内建），
 * 从而具备基础转换能力，能解决日常绝大部分场景
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class StringToNumberConverterFactory implements ConverterFactory<String, Number> {

  @Override
  public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
    return new StringToNumber<T>(targetType);
  }

  private static final class StringToNumber<T extends Number> implements Converter<String, T> {

    private final Class<T> targetType;

    public StringToNumber(Class<T> targetType) {
      this.targetType = targetType;
    }

    @Override
    public T convert(String source) {
      if (source.length() == 0) {
        return null;
      }

      if (targetType.equals(Integer.class)) {
        return (T) Integer.valueOf(source);
      } else if (targetType.equals(Long.class)) {
        return (T) Long.valueOf(source);
      }
      //TODO 其他数字类型

      else {
        throw new IllegalArgumentException(
            "Cannot convert String [" + source + "] to target class [" + targetType.getName() + "]");
      }
    }
  }

}
