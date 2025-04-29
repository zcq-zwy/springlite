package com.zcqzwy.springframework.beans.factory;

import com.zcqzwy.springframework.beans.BeansException;
import java.util.Map;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ListableBeanFactory </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/24 </p>
 * 可枚举容器 ==> 支持枚举容器中所有Bean的实例（按类型、注解等批量获取Bean信息）
 * 批量操作Bean，可以在启动时初始化所有实现某接口的Bean
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface ListableBeanFactory extends BeanFactory {

  /**
   * 按照类型返回 Bean 实例
   *
   * @param type 需要返回的Bean的类型
   * @param <T>  bean的类型
   * @return 返回的Bean实例
   * @throws BeansException bean异常
   */
  <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

  /**
   * 返回注册表中所有的Bean名称
   *
   * @return Bean名称
   */
  String[] getBeanDefinitionNames();

}
