package com.zcqzwy.springframework.core.convert.support;

import com.zcqzwy.springframework.core.converter.ConverterRegistry;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: DefaultConversionService </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/28 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class DefaultConversionService extends GenericConversionService {

  public DefaultConversionService() {
    addDefaultConverters(this);
  }

  public static void addDefaultConverters(ConverterRegistry converterRegistry) {
    // 可以再次添加默认的转换器
    converterRegistry.addConverterFactory(new StringToNumberConverterFactory());

  }
}
