package com.zcqzwy.springframework.context.event;

import com.zcqzwy.springframework.context.ApplicationContext;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ContextRefreshedEvent </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class ContextRefreshedEvent extends ApplicationContextEvent {

/**
   * 创建新的 ContextRefreshedEvent。
   * @param source 已初始化的 {@code ApplicationContext}或 refreshed（不得为 {@code null}）
   */
  public ContextRefreshedEvent(ApplicationContext source) {
    super(source);
  }

}