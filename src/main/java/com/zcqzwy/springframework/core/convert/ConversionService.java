package com.zcqzwy.springframework.core.convert;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ConversionService </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/28 </p>
 * 统一的类型转换服务。属于面向开发者使用的门面接口
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface ConversionService {

  boolean canConvert(Class<?> sourceType, Class<?> targetType);

  <T> T convert(Object source, Class<T> targetType);
}
