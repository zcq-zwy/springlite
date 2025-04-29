package com.zcqzwy.springframework.util;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: BeanFactoryUtils </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class BeanFactoryUtils {

  private BeanFactoryUtils() {
  }
  /**
   * 工厂 Bean 前缀（用于获取 FactoryBean 实例本身，而非其创建的对象）
   */
  public static final String FACTORY_BEAN_PREFIX = "&";

  /**
   * 规范化 Bean 名称，去除工厂前缀（如 "&"）
   * @param name 原始 Bean 名称（可能包含多个工厂前缀）
   * @return 去除所有工厂前缀后的 Bean 名称
   */
  public static String transformedBeanName(String name) {
    String beanName = name;
    // 递归去除所有工厂前缀（例如 "&&myBean" → "myBean"）
    while (beanName.startsWith(FACTORY_BEAN_PREFIX)) {
      beanName = beanName.substring(FACTORY_BEAN_PREFIX.length());
    }
    return beanName;
  }
}
