package com.zcqzwy.springframework.util;

import com.zcqzwy.springframework.beans.PropertyValue;
import com.zcqzwy.springframework.beans.PropertyValues;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: BeanUtils </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
import java.lang.reflect.Field;

public class BeanUtils {

  public static void setFieldValue(Object bean, String name, Object value) {
    if (bean == null) {
      throw new IllegalArgumentException("目标对象不能为 null");
    }
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("字段名不能为空");
    }

    try {
      // 获取目标字段
      Field field = findField(bean.getClass(), name);
      // 设置字段可访问（针对私有字段）
      field.setAccessible(true);
      // 设置字段值
      field.set(bean, value);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
      throw new RuntimeException("字段 '" + name + "' 不存在于类 " + bean.getClass().getName(), e);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      throw new RuntimeException("无法访问字段 '" + name + "'", e);
    }
  }

  private static Field findField(Class<?> clazz, String name) throws NoSuchFieldException {
    // 遍历类及其父类查找字段
    while (clazz != null && clazz != Object.class) {
      try {
        return clazz.getDeclaredField(name);
      } catch (NoSuchFieldException e) {
        // 继续查找父类
        clazz = clazz.getSuperclass();
      }
    }
    throw new NoSuchFieldException("字段 '" + name + "' 在类层次结构中未找到");
  }
}