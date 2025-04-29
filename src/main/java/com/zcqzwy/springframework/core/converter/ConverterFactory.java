package com.zcqzwy.springframework.core.converter;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ConverterFactory </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/28 </p>
 * 1:N的转换器
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface ConverterFactory<S, R> {

  <T extends R> Converter<S, T> getConverter(Class<T> targetType);
}
