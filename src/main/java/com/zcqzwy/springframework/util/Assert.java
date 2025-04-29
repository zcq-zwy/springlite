package com.zcqzwy.springframework.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: Assert </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class Assert {

  private Assert() {
    // 禁止实例化
  }

  // ---------------------- 基础断言 ----------------------

  /**
   * 断言对象为 null
   * @param object 待检查对象
   * @param message 异常消息
   * @throws IllegalArgumentException 如果对象不为 null
   */
  public static void isNull(Object object, String message) {
    if (object != null) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void isNull(Object object) {
    isNull(object, "[断言失败] - 对象必须为 null");
  }

  /**
   * 断言对象不为 null
   * @param object 待检查对象
   * @param message 异常消息
   * @throws IllegalArgumentException 如果对象为 null
   */
  public static void notNull(Object object, String message) {
    if (object == null) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void notNull(Object object) {
    notNull(object, "[断言失败] - 对象不能为 null");
  }

  // ---------------------- 布尔条件断言 ----------------------

  /**
   * 断言条件为 true
   * @param condition 条件
   * @param message 异常消息
   * @throws IllegalStateException 如果条件为 false
   */
  public static void isTrue(boolean condition, String message) {
    if (!condition) {
      throw new IllegalStateException(message);
    }
  }

  public static void isTrue(boolean condition) {
    isTrue(condition, "[断言失败] - 条件必须为 true");
  }

  // ---------------------- 字符串断言 ----------------------

  /**
   * 断言字符串不为空（非 null 且长度大于0）
   * @param text 待检查字符串
   * @param message 异常消息
   * @throws IllegalArgumentException 如果字符串为空
   */
  public static void notEmpty(String text, String message) {
    if (text == null || text.isEmpty()) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void notEmpty(String text) {
    notEmpty(text, "[断言失败] - 字符串不能为空");
  }

  /**
   * 断言字符串包含实际文本（非 null 且至少一个非空白字符）
   * @param text 待检查字符串
   * @param message 异常消息
   * @throws IllegalArgumentException 如果字符串无有效内容
   */
  public static void hasText(String text, String message) {
    if (text == null || text.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void hasText(String text) {
    hasText(text, "[断言失败] - 字符串必须包含非空白字符");
  }

  // ---------------------- 集合断言 ----------------------

  /**
   * 断言集合非空（非 null 且至少有一个元素）
   * @param collection 待检查集合
   * @param message 异常消息
   * @throws IllegalArgumentException 如果集合为空
   */
  public static void notEmpty(Collection<?> collection, String message) {
    if (collection == null || collection.isEmpty()) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void notEmpty(Collection<?> collection) {
    notEmpty(collection, "[断言失败] - 集合不能为空");
  }

  // ---------------------- 类型断言 ----------------------

  /**
   * 断言对象是指定类型的实例
   * @param type 目标类型
   * @param obj 待检查对象
   * @param message 异常消息
   * @throws IllegalArgumentException 如果对象类型不匹配
   */
  public static void isInstanceOf(Class<?> type, Object obj, String message) {
    notNull(type, "目标类型不能为 null");
    if (!type.isInstance(obj)) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void isInstanceOf(Class<?> type, Object obj) {
    isInstanceOf(type, obj,
        String.format("[断言失败] - 对象类型必须是 %s", type.getName()));
  }

  // ---------------------- 状态断言 ----------------------

  /**
   * 断言状态条件为 true（通常用于验证对象状态）
   * @param condition 条件
   * @param message 异常消息
   * @throws IllegalStateException 如果条件为 false
   */
  public static void state(boolean condition, String message) {
    if (!condition) {
      throw new IllegalStateException(message);
    }
  }

  public static void state(boolean condition) {
    state(condition, "[断言失败] - 状态无效");
  }
}