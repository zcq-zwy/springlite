package com.zcqzwy.springframework.util;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: StringUtils </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class StringUtils {

  public static final String EMPTY = "";

  private StringUtils() {
    // 工具类不能进行实例化
  }


  /**
   * 将delimiter分隔的字符串分割成数组，自动处理空格和空元素
   * @param input 输入字符串（允许为 null 或空）
   * @return 分割后的非空字符串数组（不会返回 null）
   */
  public static String[] splitToArray(String input,char delimiter) {
    if (input == null || input.isEmpty()) {
      // 处理 null 和空输入
      return new String[0];
    }

    return Arrays.stream(input.split(String.valueOf(delimiter)))
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .toArray(String[]::new);
  }

  /**
   * 标准化路径，处理冗余的".."、"."和斜杠，并将反斜杠替换为正斜杠
   * 示例：
   * "a/b/../c"    → "a/c"
   * "a//b"        → "a/b"
   * "a/./b"       → "a/b"
   * "/../a/b"     → "/a/b"
   * "C:\\Users\\" → "C:/Users"
   */
  public static String cleanPath(String path) {
    if (path == null || path.isEmpty()) {
      return "";
    }

    // 统一替换Windows反斜杠为正斜杠
    String normalizedPath = path.replace('\\', '/');

    // 处理连续的多个斜杠（保留头部斜杠）
    normalizedPath = normalizedPath.replaceAll("/{2,}", "/");

    // 分割路径片段
    String[] segments = normalizedPath.split("/");
    Deque<String> stack = new ArrayDeque<>();

    // 判断是否以斜杠开头（绝对路径）
    boolean startsWithSlash = normalizedPath.startsWith("/");

    for (String segment : segments) {
      // 跳过空片段和当前目录"."
      if (segment.isEmpty() || ".".equals(segment)) {
        continue;
      }

      // 处理上级目录".."
      if ("..".equals(segment)) {
        // 绝对路径下根目录的".."直接忽略
        if (startsWithSlash && stack.isEmpty()) {
          continue;
        }
        // 常规处理：可抵消前一级目录，或保留相对路径的".."
        if (!stack.isEmpty() && !"..".equals(stack.peek())) {
          stack.pop();
        } else {
          stack.push("..");
        }
      } else {
        stack.push(segment);
      }
    }

    // 构建最终路径
    StringBuilder result = new StringBuilder();
    boolean endsWithSlash = normalizedPath.endsWith("/") && segments.length > 0;

    // 保留绝对路径的头部斜杠
    if (startsWithSlash) {
      result.append('/');
    }

    // 反向拼接（栈是先进后出）
    Deque<String> reversed = new ArrayDeque<>();
    while (!stack.isEmpty()) {
      reversed.push(stack.pop());
    }
    while (!reversed.isEmpty()) {
      result.append(reversed.pop());
      if (!reversed.isEmpty()) {
        result.append('/');
      }
    }

    // 保留尾部斜杠（仅当原始路径有且处理后非空）
    if (endsWithSlash && result.length() > 0 && result.charAt(result.length() - 1) != '/') {
      result.append('/');
    }

    return result.toString();
  }

  /**
   * 检查字符串是否为空（null 或空字符串）
   */
  public static boolean isEmpty(CharSequence str) {
    return str == null || str.length() == 0;
  }

  /**
   * 检查字符串是否非空（非 null 且非空字符串）
   */
  public static boolean isNotEmpty(CharSequence str) {
    return !isEmpty(str);
  }

  /**
   * 将字符串首字母转为小写
   * @param str 输入字符串（允许为 null）
   * @return 首字母小写的字符串，或原值（如果输入为空）
   */
  public static String lowerFirst(String str) {
    if (isEmpty(str)) {
      return str;
    }
    char firstChar = str.charAt(0);
    if (Character.isLowerCase(firstChar)) {
      // 已经是小写直接返回
      return str;
    }
    return Character.toLowerCase(firstChar) + str.substring(1);
  }
}
