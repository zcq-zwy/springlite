package com.zcqzwy.springframework.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: TypeUtils </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/28 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class TypeUtils {

  private TypeUtils() {
  }


  /**
   * 获取字段的类型（优先返回泛型原始类型，若没有则返回声明类型）
   * @param field Java字段
   * @return 字段的类型对应的 Class 对象
   */
  public static Class<?> getType(Field field) {
    Type genericType = field.getGenericType();

    // 1. 处理普通类型（非泛型）
    if (genericType instanceof Class) {
      return (Class<?>) genericType;
    }

    // 2. 处理泛型类型（如 List<String>）
    if (genericType instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) genericType;
      Type rawType = parameterizedType.getRawType();
      if (rawType instanceof Class) {
        return (Class<?>) rawType;
      }
    }

    // 3. 默认返回字段的声明类型（兜底逻辑）
    return field.getType();
  }


  /**
   * 获取字段的类型（支持递归查找父类）
   * @param clazz     目标类
   * @param fieldName 字段名
   * @return 字段的类型 Class 对象
   * @throws IllegalArgumentException 如果字段不存在
   */
  public static Class<?> getFieldType(Class<?> clazz, String fieldName) {
    Field field = findField(clazz, fieldName);
    return field.getType();
  }

  /**
   * 递归查找字段（包括父类）
   */
  private static Field findField(Class<?> clazz, String fieldName) {
    try {
      // 尝试获取当前类的字段
      return clazz.getDeclaredField(fieldName);
    } catch (NoSuchFieldException e) {
      // 递归查找父类
      Class<?> superClass = clazz.getSuperclass();
      if (superClass != null && superClass != Object.class) {
        return findField(superClass, fieldName);
      }
      throw new IllegalArgumentException(
          "Field '" + fieldName + "' not found in class hierarchy of " + clazz.getName()
      );
    }
  }

}
