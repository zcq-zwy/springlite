package com.zcqzwy.springframework.core.converter;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ConverterRegistry </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/28 </p>
 * 转换器注册中心。负责转换器的注册、删除
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface ConverterRegistry {

  void addConverter(Converter<?, ?> converter);

  void addConverterFactory(ConverterFactory<?, ?> converterFactory);

  void addConverter(GenericConverter converter);
}
