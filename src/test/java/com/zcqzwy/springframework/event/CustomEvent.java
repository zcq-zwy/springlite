package com.zcqzwy.springframework.event;

import com.zcqzwy.springframework.context.ApplicationContext;
import com.zcqzwy.springframework.context.event.ApplicationContextEvent;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: CustomEvent </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/26 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class CustomEvent extends ApplicationContextEvent {

  private Long id;
  private String message;

  /**
   * Constructs a prototypical Event.
   *
   * @param source The object on which the Event initially occurred.
   * @throws IllegalArgumentException if source is null.
   */
  public CustomEvent(Object source, Long id, String message) {
    super((ApplicationContext) source);
    this.id = id;
    this.message = message;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
