package com.zcqzwy.springframework.context.event;

import com.zcqzwy.springframework.context.ApplicationContext;
import com.zcqzwy.springframework.context.ApplicationEvent;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ApplicationContextEvent </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public abstract class ApplicationContextEvent extends ApplicationEvent {

/**
   * 创建一个新的 ContextStartedEvent。
   * @param  source 为其引发事件的 {@code ApplicationContext} 提供源不得为 {@code null}）
   */
  public ApplicationContextEvent(ApplicationContext source) {
    super(source);
  }

/**
   * 获取为其引发事件的 {@code ApplicationContext}。
   */
  public final ApplicationContext getApplicationContext() {
    return (ApplicationContext) getSource();
  }

}
