package com.zcqzwy.springframework.util;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: BasicTypes </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/28 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class BasicTypes {

  // 基本类型与包装类型的映射表
  private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_MAP = new HashMap<>();

  static {
    PRIMITIVE_WRAPPER_MAP.put(boolean.class, Boolean.class);
    PRIMITIVE_WRAPPER_MAP.put(byte.class, Byte.class);
    PRIMITIVE_WRAPPER_MAP.put(char.class, Character.class);
    PRIMITIVE_WRAPPER_MAP.put(double.class, Double.class);
    PRIMITIVE_WRAPPER_MAP.put(float.class, Float.class);
    PRIMITIVE_WRAPPER_MAP.put(int.class, Integer.class);
    PRIMITIVE_WRAPPER_MAP.put(long.class, Long.class);
    PRIMITIVE_WRAPPER_MAP.put(short.class, Short.class);
    PRIMITIVE_WRAPPER_MAP.put(void.class, Void.class);
  }

  /**
   * 将基本类型转换为对应的包装类型
   * @param type 原始类型（基本类型或对象类型）
   * @return 包装类型（如果是基本类型）或原类型
   */
  @SuppressWarnings("unchecked")
  public static <T> Class<T> wrap(Class<T> type) {
    if (type == null) {
      return null;
    }
    // 如果是基本类型，返回对应的包装类型
    if (type.isPrimitive()) {
      return (Class<T>) PRIMITIVE_WRAPPER_MAP.get(type);
    }
    // 否则直接返回原类型
    return type;
  }

}
