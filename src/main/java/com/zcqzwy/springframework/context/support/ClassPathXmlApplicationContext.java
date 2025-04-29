package com.zcqzwy.springframework.context.support;

import com.zcqzwy.springframework.beans.BeansException;
import com.zcqzwy.springframework.context.ApplicationContext;
import com.zcqzwy.springframework.core.io.Resource;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ClassPathXmlApplicationContext </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/25 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

  private String[] configLocations;

  public ClassPathXmlApplicationContext() {
  }

  /**
   * 从 XML 中加载 BeanDefinition，并刷新上下文
   *
   * @param configLocations 配置文件位置
   * @throws BeansException 解析异常
   */
  public ClassPathXmlApplicationContext(String configLocations) throws BeansException {
    this(new String[]{configLocations});
  }

  /**
   * 从 XML 中加载 BeanDefinition，并刷新上下文
   * @param configLocations  配置文件位置
   * @throws BeansException 解析异常
   */
  public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
    this.configLocations = configLocations;
    refresh();
  }

  @Override
  protected String[] getConfigLocations() {
    return configLocations;
  }


}
