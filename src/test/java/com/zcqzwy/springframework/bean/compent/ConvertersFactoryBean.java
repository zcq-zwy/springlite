package com.zcqzwy.springframework.bean.compent;

import com.zcqzwy.springframework.beans.factory.FactoryBean;
import com.zcqzwy.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ConvertersFactoryBean </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/28 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/


public class ConvertersFactoryBean implements FactoryBean<Set<?>> {

  @Override
  public Set<?> getObject() throws Exception {
    HashSet<Object> converters = new HashSet<>();
    StringToLocalDateConverter stringToLocalDateConverter = new StringToLocalDateConverter("yyyy-MM-dd");
    converters.add(stringToLocalDateConverter);
    return converters;
  }

  @Override
  public Class<?> getObjectType() {
    return Set.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }
}
