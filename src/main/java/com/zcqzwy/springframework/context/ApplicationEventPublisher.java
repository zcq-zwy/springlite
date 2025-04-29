package com.zcqzwy.springframework.context;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ApplicationEventPublisher </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface ApplicationEventPublisher {

/**
   * 通知向此应用程序注册的所有侦听器应用程序事件。事件可以是框架事件（例如 RequestHandledEvent）
   * 或特定于应用程序的事件。
   * @param event 要发布的事件
   */
  void publishEvent(ApplicationEvent event);

}
