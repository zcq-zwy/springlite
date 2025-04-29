package com.zcqzwy.springframework.core.converter;

import java.util.Set;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: GenericConverter </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/28 </p>
 * 通用的类型转换器，n：n
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface GenericConverter {

  Set<ConvertiblePair> getConvertibleTypes();

  Object convert(Object source, Class sourceType, Class targetType);

  public static final class ConvertiblePair {

    private final Class<?> sourceType;

    private final Class<?> targetType;

    public ConvertiblePair(Class<?> sourceType, Class<?> targetType) {
      this.sourceType = sourceType;
      this.targetType = targetType;
    }

    public Class<?> getSourceType() {
      return this.sourceType;
    }

    public Class<?> getTargetType() {
      return this.targetType;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || obj.getClass() != ConvertiblePair.class) {
        return false;
      }
      ConvertiblePair other = (ConvertiblePair) obj;
      return this.sourceType.equals(other.sourceType) && this.targetType.equals(other.targetType);

    }

    @Override
    public int hashCode() {
      return this.sourceType.hashCode() * 31 + this.targetType.hashCode();
    }
  }

}
