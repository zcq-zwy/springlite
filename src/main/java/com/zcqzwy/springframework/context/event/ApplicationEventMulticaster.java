package com.zcqzwy.springframework.context.event;

import com.zcqzwy.springframework.context.ApplicationEvent;
import com.zcqzwy.springframework.context.ApplicationListener;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ApplicationEventMulticaster </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 * 该接口需要由能够管理多个 ApplicationListener（应用程序监听器）对象并向其发布事件的对象实现。
 * 通常，Spring 的 ApplicationEventPublisher（例如 Spring 的 ApplicationContext）
 * 可以将 ApplicationEventMulticaster 作为委托（Delegate），通过它实际执行事件的发布操作。
 * ApplicationEventPublisher：事件发布器的通用接口，ApplicationContext 是其典型实现。
 * ApplicationEventMulticaster：事件广播器，负责管理监听器集合并将事件分发给所有匹配的监听器。
 * 委托模式：ApplicationEventPublisher 将事件发布的具体逻辑委托给 ApplicationEventMulticaster 处理
 * 职责分离：发布器（Publisher）：仅负责触发事件。广播器（Multicaster）：专注于事件分发和监听器管理。
 * 这种分离提高了扩展性，允许自定义事件分发策略（如同步/异步）
 * 开发者可以替换默认的 ApplicationEventMulticaster 实现，
 * 例如：SimpleApplicationEventMulticaster：Spring 默认的同步广播器。
 * 自定义实现：支持异步事件处理或特定过滤逻辑。
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public interface ApplicationEventMulticaster {

  /**
   * 添加监听器以接收所有事件的通知。
   * @param listener 中要添加的 listener
   */
  void addApplicationListener(ApplicationListener<?> listener);

/**
   * 从监听器列表中删除监听器。
   * @param listener to remove
   */
  void removeApplicationListener(ApplicationListener<?> listener);

/**
   * 将给定的应用程序事件多播到适当的监听器。
   * @param event 事件
   *
   */
  void multicastEvent(ApplicationEvent event);

}
