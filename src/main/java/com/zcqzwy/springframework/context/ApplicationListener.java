package com.zcqzwy.springframework.context;

import java.util.EventListener;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ApplicationListener </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 * 由应用程序事件侦听器实现的接口。
 * 基于标准 {@code java.util.EventListener} 接口表示 Observer 设计模式。
 * <p>从 Spring 3.0 开始，ApplicationListener 可以通用地声明事件类型
 * 它感兴趣的。当注册到 Spring ApplicationContext 时，事件将相应地进行过滤，并调用侦听器以匹配事件
 * 对象。
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

  /**
   * Handle an application event.
   * @param event the event to respond to
   */
  void onApplicationEvent(E event);

}