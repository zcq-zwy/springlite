package com.zcqzwy.springframework.context;

import java.util.EventObject;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: ApplicationEvent </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public abstract class ApplicationEvent extends EventObject {



  /** 事件发生的系统时间 */
  private final long timestamp;


/**
   * 创建一个新的 ApplicationEvent。
   * @param source 源发布事件的组件（从 {@code null} ）
   */
  public ApplicationEvent(Object source) {
    super(source);
    this.timestamp = System.currentTimeMillis();
  }


 /**
   * 返回事件发生时的系统时间（以毫秒为单位）。
   */
  public final long getTimestamp() {
    return this.timestamp;
  }

}
