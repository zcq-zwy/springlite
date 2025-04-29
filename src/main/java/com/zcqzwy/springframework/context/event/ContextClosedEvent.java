package com.zcqzwy.springframework.context.event;

import com.zcqzwy.springframework.context.ApplicationContext;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ContextClosedEvent </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class ContextClosedEvent extends ApplicationContextEvent {

/**
   * 创建一个新的 ContextClosedEvent。
   * @param source 已关闭的 {@code ApplicationContext}（不得为 {@code null}）
   */
  public ContextClosedEvent(ApplicationContext source) {
    super(source);
  }

}
